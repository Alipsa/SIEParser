# SIEParser Developer Guide

This document describes the architecture, class structure, and development workflow for the SIEParser library.

## What is SIE?

SIE (Standard Import Export) is a Swedish file format for exchanging accounting data between software systems. It was defined by SIE-gruppen and is widely used in Swedish accounting. The format has four types:

| Type | Content | Direction |
|------|---------|-----------|
| 1 | Year-end balances and period balances | Export |
| 2 | Period balances | Export |
| 3 | Object balances | Export |
| 4 | Transactions (vouchers) | Export/Import |

SIE files are plain text encoded in IBM437 (PC8), with lines starting with a `#` tag followed by data fields. Quoted strings use `"`, objects are wrapped in `{}`, and backslash-escaped quotes (`\"`) are supported within strings.

## Project structure

```
SIEParser/
  build.gradle                    # Gradle 9 build (Java 17, JUnit 5)
  src/
    main/java/alipsa/sieparser/
      SIE.java                    # Constants for all #TAG identifiers
      SieType.java                # Enum for SIE types 1-4
      Encoding.java               # IBM437 charset handling
      IoUtil.java                 # Reader/Writer factory for IBM437 I/O
      SieDocument.java            # Top-level document model
      SieDocumentReader.java      # Parser: file/stream -> SieDocument
      SieDocumentWriter.java      # Writer: SieDocument -> file/stream
      SieDocumentComparer.java    # Structural comparison of two documents
      SieDataItem.java            # Single-line parser (splits into typed fields)
      SieCallbacks.java           # Consumer<T> hooks for streaming
      WriteOptions.java           # Writer configuration (KSUMMA, etc.)
      SieCRC32.java               # CRC32 checksum per SIE spec (#KSUMMA)
      SieCompany.java             # Company info (#FNAMN, #ORGNR, #ADRESS, etc.)
      SieAccount.java             # Account definition (#KONTO, #ENHET, #KTYP, #SRU)
      SieDimension.java           # Dimension definition (#DIM, #UNDERDIM)
      SieObject.java              # Object within a dimension (#OBJEKT)
      SieBookingYear.java         # Accounting year (#RAR)
      SieVoucher.java             # Voucher header (#VER)
      SieVoucherRow.java          # Transaction row (#TRANS, #BTRANS, #RTRANS)
      SiePeriodValue.java         # Balance/period value (#IB, #UB, #OIB, #OUB, #RES, etc.)
      StringUtil.java             # Null-safe string utilities
      SieException.java           # Base exception (extends RuntimeException)
      SieParseException.java      # Parsing-specific exception
      ...Exception.java           # Specific error types (checksum, date, file, etc.)
    test/java/alipsa/sieparser/
      TestSieDocument.java        # Round-trip tests against 49 sample files
      TestEncoding.java           # IBM437 encoding verification
      SieDataItemTest.java        # Line parsing and field extraction
      SieDocumentReaderTest.java  # Reader flags and feature tests
      SieDocumentWriterTest.java  # Writer output, stream, and round-trip tests
      SieDocumentComparerTest.java # Comparison logic tests
      SieCRC32Test.java           # Checksum calculation tests
      StringUtilTest.java         # String utility tests
    test/resources/samples/       # 49 real-world SIE files (types 1-4)
```

## Architecture

### Reading flow

```
SIE file (IBM437)
    |
    v
IoUtil.getReader()          -- creates BufferedReader with IBM437 charset
    |
    v
SieDocumentReader           -- iterates lines, dispatches by #TAG
    |
    +-- SieDataItem         -- parses one line: splits into itemType + data fields
    |       |
    |       +-- splitLine() -- handles quoting, escaping, object lists
    |       +-- getInt(), getString(), getDate(), getDecimal(), getObjects()
    |
    +-- SieDocument         -- accumulates parsed data into maps and lists
    |
    +-- SieCallbacks        -- optional Consumer<T> hooks fired during parsing
    |
    +-- SieCRC32            -- accumulates checksum if #KSUMMA is present
    |
    v
SieDocument                 -- fully populated document model
```

`SieDocumentReader.readDocument()` reads line by line. Each line becomes a `SieDataItem` which splits the line into an item type (`#KONTO`, `#VER`, etc.) and a list of data fields. The reader then dispatches to a type-specific parse method (e.g. `parseKONTO`, `parseVER`, `parseTRANS`) which extracts typed values from the data fields and populates the `SieDocument`.

Vouchers (`#VER`) are special: the reader tracks a "current voucher" state. Lines between `{` and `}` after a `#VER` are transaction rows. When `}` is encountered, `closeVoucher()` verifies the sum is zero (unless `allowUnbalancedVoucher` is set).

### Writing flow

```
SieDocument
    |
    v
SieDocumentWriter           -- iterates document fields in SIE-specified order
    |
    +-- writeContent()      -- writes header, accounts, balances, vouchers
    |
    +-- writeLine()         -- writes one line through BufferedWriter
    |
    +-- sieText()           -- escapes quotes in text fields
    |
    +-- makeSieDate()       -- formats LocalDate as yyyyMMdd
    |
    v
SIE file (IBM437) or OutputStream
```

The writer produces output in the order required by the SIE specification: `#FLAGGA`, `#PROGRAM`, `#FORMAT`, `#GEN`, `#SIETYP`, metadata, accounts, balances, and finally vouchers.

### Comparison flow

`SieDocumentComparer.compare()` takes two `SieDocument` instances and returns a `List<String>` of differences. It compares:
- Scalar fields (FLAGGA, SIETYP, FORMAT, GEN, PROSA, etc.)
- Company info (FNAMN, ORGNR, ADRESS, FTYP)
- Program info
- Dimensions and objects
- Accounts (KONTO with SRU, ENHET, KTYP)
- Booking years (RAR)
- All period value lists (IB, UB, OIB, OUB, PSALDO, PBUDGET, RES)
- Vouchers and their transaction rows

Comparison is bidirectional: it checks A-against-B and B-against-A to catch entries present in only one document.

## Key data model

### SieDocument

The central class holding all parsed data:

- **Company info**: `getFNAMN()` returns `SieCompany` (name, org number, address, type)
- **Accounts**: `getKONTO()` returns `Map<String, SieAccount>` keyed by account number
- **Dimensions**: `getDIM()` and `getUNDERDIM()` return `Map<String, SieDimension>`
- **Balances**: `getIB()`, `getUB()`, `getOIB()`, `getOUB()` return `List<SiePeriodValue>`
- **Period values**: `getPSALDO()`, `getPBUDGET()` return `List<SiePeriodValue>`
- **Results**: `getRES()` returns `List<SiePeriodValue>`
- **Vouchers**: `getVER()` returns `List<SieVoucher>` (SIE type 4 only)
- **Booking years**: `getRars()` returns `Map<Integer, SieBookingYear>`
- **Metadata**: `getSIETYP()`, `getFLAGGA()`, `getFORMAT()`, `getGEN_DATE()`, `getPROGRAM()`, etc.

### SieVoucher / SieVoucherRow

A voucher (`#VER`) has a series, number, date, text, and a list of rows. Each row (`#TRANS`, `#BTRANS`, `#RTRANS`) has an account, amount, date, text, optional objects, optional quantity, and optional createdBy. The `token` field preserves which tag type the row came from.

### SieDimension / SieObject

Dimensions (`#DIM`) define categories for multi-dimensional accounting (e.g. cost centers, projects). Standard dimensions 1-19 are predefined. Sub-dimensions (`#UNDERDIM`) can be defined under a parent dimension. Each dimension contains objects (`#OBJEKT`) keyed by their number.

### SiePeriodValue

A generic value record used across multiple SIE tags. Tracks account, year number, period, amount, quantity, objects, and the originating token.

## Exception hierarchy

All SIE-specific exceptions extend `SieException` (which extends `RuntimeException`):

```
RuntimeException
  └── SieException
        ├── SieParseException
        ├── SieInvalidFileException
        ├── SieInvalidChecksumException
        ├── SieInvalidFeatureException
        ├── SieDateException
        ├── SieMissingMandatoryDateException
        ├── SieMissingObjectException
        ├── SieVoucherMismatchException
        └── MissingFieldException
```

`RuntimeException` is used as the base so that exceptions can be thrown from `Consumer<T>` callbacks without requiring checked exception handling.

## Character encoding

SIE files use IBM437 (also called PC8 or Codepage 437). The `Encoding` class provides the charset, and `IoUtil` creates readers/writers with the correct encoding. All I/O goes through these utilities to ensure consistent encoding.

## Building and testing

### Build

```bash
./gradlew build
```

### Run tests

```bash
./gradlew test
```

Test results are written to `build/reports/tests/test/index.html`.

The current suite executes 150+ tests (158 as of 2026-02-27):
- **Round-trip tests** (`TestSieDocument`): reads sample SIE files, writes each document to a temp file, reads it back, and compares the two documents for structural equality. Files with intentionally invalid checksums are excluded from round-trip assertions.
- **Encoding tests** (`TestEncoding`): verifies IBM437 read/write round-trip.
- **Unit tests**: cover line parsing, reader flags, writer output, comparison logic, CRC32 checksums, and string utilities.

### Generate Javadoc

```bash
./gradlew javadoc
```

Output goes to `build/docs/javadoc/`.

### Clean

```bash
./gradlew clean
```

### Pre-release gates

Before changing `version` from `-SNAPSHOT` to a release version, verify:

1. `./gradlew clean build` passes.
2. `./gradlew publishToMavenLocal` passes.
3. A clean-home build works (for example with a fresh `GRADLE_USER_HOME`) without relying on local-only repositories.
4. README examples match runtime behavior (notably SIE 5 full-document signing requirements).
5. Round-trip regression tests cover all supported formats, including SIE 4i.

## Adding a new SIE record type

To add support for a new `#TAG`:

1. Add a constant in `SIE.java`:
   ```java
   public static final String NEWTAG = "#NEWTAG";
   ```

2. Add a model class or extend an existing one to hold the parsed data.

3. Add a `parseNEWTAG(SieDataItem di)` method in `SieDocumentReader` and dispatch to it from `readDocument()`:
   ```java
   } else if (SIE.NEWTAG.equals(itemType)) {
       parseNEWTAG(di);
   ```

4. Add a `writeNEWTAG()` method in `SieDocumentWriter` and call it from `writeContent()`.

5. Add comparison logic in `SieDocumentComparer` if the field should be part of round-trip verification.

6. Add test coverage.

## SIE 5 (XML format)

SIE 5 is the XML-based successor to SIE 1-4. It is implemented in the `alipsa.sieparser.sie5` package using JAXB annotations for XML binding.

### Architecture

SIE 5 uses a completely different architecture from SIE 1-4:

- **Format**: XML with namespace `http://www.sie.se/sie5` (vs. IBM437 text)
- **Parsing**: JAXB unmarshalling (vs. line-by-line text parsing)
- **Schema**: Defined by `sie5.xsd` (bundled in `src/main/resources`)
- **Two root types**: `Sie` (full document) and `SieEntry` (import document)

### Package structure

```
alipsa.sieparser.sie5/
  package-info.java              # Namespace + type adapter declarations
  Sie5Document.java              # Root: <Sie> element
  Sie5Entry.java                 # Root: <SieEntry> element
  Sie5DocumentReader.java        # JAXB-based reader
  Sie5DocumentWriter.java        # JAXB-based writer
  FileInfo.java / FileInfoEntry.java  # File metadata
  Company.java / CompanyEntry.java    # Company info
  Account.java / AccountEntry.java    # Chart of accounts
  AccountTypeValue.java          # Enum: asset, liability, equity, cost, income, statistics
  BaseBalance.java               # Opening/closing balances
  Budget.java / BudgetMultidim.java   # Budget data
  Dimension.java / DimensionEntry.java  # Dimensions and objects
  Journal.java                   # Journal container
  JournalEntry.java              # Individual journal entries
  LedgerEntry.java               # Ledger entry rows
  CustomerInvoices.java          # Customer invoice subdivided accounts
  SupplierInvoices.java          # Supplier invoice subdivided accounts
  FixedAssets.java               # Fixed asset subdivided accounts
  ...                            # ~50 model classes total
```

### JAXB type adapters

Java 8+ time types (`YearMonth`, `LocalDate`, `OffsetDateTime`) are not natively supported by JAXB. Three adapters in the package handle conversion:

- `YearMonthAdapter` - for `xsd:gYearMonth`
- `LocalDateAdapter` - for `xsd:date`
- `OffsetDateTimeAdapter` - for `xsd:dateTime`

These are registered globally via `@XmlJavaTypeAdapters` in `package-info.java`.

### Full vs Entry variants

Several XSD types have separate full and entry variants. The naming convention is:

- Full: `Account`, `Company`, `Dimension`, `JournalEntry`, `LedgerEntry`
- Entry: `AccountEntry`, `CompanyEntry`, `DimensionEntry`, `JournalEntryEntry`, `LedgerEntryEntry`

Entry variants typically have fewer required fields and may use string types where full variants use positiveInteger.

### Scope exclusions

The following SIE 5 features are not yet implemented:

- XML digital signatures (`dsig:Signature`)
- XSD schema validation during reading
- SIE 5 document comparison
- Conversion utilities between SIE 1-4 and SIE 5

## Dependencies

| Dependency | Scope | Purpose |
|-----------|-------|---------|
| `org.slf4j:slf4j-api:2.0.16` | compile (api) | Logging facade |
| `jakarta.xml.bind:jakarta.xml.bind-api:4.0.2` | compile (api) | JAXB API for SIE 5 XML binding |
| `org.glassfish.jaxb:jaxb-runtime:4.0.5` | runtime | JAXB implementation |
| `org.junit.jupiter:junit-jupiter:5.11.4` | test | JUnit 5 test framework |
| `org.junit.platform:junit-platform-launcher` | test runtime | JUnit platform launcher |
| `org.slf4j:slf4j-simple:2.0.16` | test runtime | Simple SLF4J binding for test output |
