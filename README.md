SIEParser
=========

A Java library for reading, writing, and comparing [SIE](https://sie.se/) files (the Swedish standard accounting file format). Supports SIE types 1 through 4 (including 4i) and SIE 5 (XML format).

Originally ported from the .NET [jsisie](https://github.com/idstam/jsisie) parser. Version 2.0 has been substantially modernized: all upstream fixes ported, Java 17+ APIs adopted, and test coverage expanded.

## Requirements

- Java 17 or later
- Gradle 9+ (wrapper included)

## Installation

**Gradle:**
```groovy
implementation 'se.alipsa:SieParser:2.0'
```

**Maven:**
```xml
<dependency>
    <groupId>se.alipsa</groupId>
    <artifactId>SieParser</artifactId>
    <version>2.0</version>
</dependency>
```

> **Note:** Prior to version 2.0, this library was published under `com.github.pernyfelt.sieparser:SieParser`. The old coordinates include a relocation POM pointing to the new group ID.

## Read a SIE file

```java
SieDocumentReader reader = new SieDocumentReader();
SieDocument doc = reader.readDocument("path/to/file.SE");

// Company info
String companyName = doc.getFNAMN().getName();
String orgNr = doc.getFNAMN().getOrgIdentifier();

// Accounts
for (SieAccount account : doc.getKONTO().values()) {
    System.out.println(account.getNumber() + " " + account.getName());
}

// Vouchers (SIE type 4)
for (SieVoucher voucher : doc.getVER()) {
    System.out.println(voucher.getSeries() + " " + voucher.getNumber());
    for (SieVoucherRow row : voucher.getRows()) {
        System.out.println("  " + row.getAccount().getNumber() + " " + row.getAmount());
    }
}
```

### Reader options

The following flags on `SieDocumentReader` control parsing behavior:

| Flag | Default | Description |
|------|---------|-------------|
| `ignoreMissingOMFATTNING` | `false` | Skip validation of missing `#OMFATTN` |
| `ignoreBTRANS` | `false` | Ignore `#BTRANS` (removed transaction) rows |
| `ignoreRTRANS` | `false` | Ignore `#RTRANS` (added transaction) rows |
| `allowUnbalancedVoucher` | `false` | Skip the zero-sum check on voucher rows |
| `ignoreKSUMMA` | `false` | Skip `#KSUMMA` checksum verification |
| `ignoreMissingDIM` | `false` | Allow unresolved temporary dimensions |
| `streamValues` | `false` | Don't store values internally; use callbacks instead |
| `throwErrors` | `true` | Throw exceptions on errors (when `false`, collect in `getValidationExceptions()`) |

You can also restrict which SIE types are accepted:

```java
reader.setAcceptSIETypes(EnumSet.of(SieType.TYPE_4));
```

### Streaming with callbacks

For large files, use callbacks to process records without storing them all in memory:

```java
SieDocumentReader reader = new SieDocumentReader();
reader.streamValues = true;
reader.callbacks.setVER(voucher -> {
    // Process each voucher as it is parsed
});
reader.callbacks.setIB(periodValue -> {
    // Process each opening balance entry
});
reader.readDocument("large-file.SE");
```

## Write a SIE file

```java
SieDocumentWriter writer = new SieDocumentWriter(doc);

// Write to a file
writer.write("output.SE");

// Or write to an OutputStream
writer.write(outputStream);
```

To enable `#KSUMMA` checksum writing:

```java
WriteOptions options = new WriteOptions();
options.setWriteKSUMMA(true);
SieDocumentWriter writer = new SieDocumentWriter(doc, options);
writer.write("output.SE");
```

## Compare SIE documents

```java
List<String> differences = SieDocumentComparer.compare(docA, docB);
if (differences.isEmpty()) {
    System.out.println("Documents are identical");
} else {
    differences.forEach(System.out::println);
}
```

## SIE 5 (XML format)

SIE 5 is the XML-based successor to SIE 1-4. It uses the `http://www.sie.se/sie5` namespace and supports a richer data model including customer/supplier invoices, fixed assets, multi-currency, and documents.

All SIE 5 classes are in the `alipsa.sieparser.sie5` package.

### Read a SIE 5 file

```java
Sie5DocumentReader reader = new Sie5DocumentReader();

// Full document
Sie5Document doc = reader.readDocument("path/to/file.sie");
String companyName = doc.getFileInfo().getCompany().getName();
String currency = doc.getFileInfo().getAccountingCurrency().getCurrency();

for (Account account : doc.getAccounts()) {
    System.out.println(account.getId() + " " + account.getName() + " (" + account.getType() + ")");
}

for (Journal journal : doc.getJournals()) {
    for (JournalEntry entry : journal.getJournalEntries()) {
        System.out.println(entry.getJournalDate() + " " + entry.getText());
    }
}

// Entry (import) document
Sie5Entry entry = reader.readEntry("path/to/entry.sie");
```

### Write a SIE 5 file

```java
Sie5DocumentWriter writer = new Sie5DocumentWriter();

// Write full document
writer.write(doc, "output.sie");

// Write entry document
writer.writeEntry(entry, "output-entry.sie");

// Or write to an OutputStream
writer.write(doc, outputStream);
```

### Build a SIE 5 document programmatically

```java
Sie5Entry entry = new Sie5Entry();

FileInfoEntry fileInfo = new FileInfoEntry();
SoftwareProduct sp = new SoftwareProduct();
sp.setName("MyApp");
sp.setVersion("1.0");
fileInfo.setSoftwareProduct(sp);

CompanyEntry company = new CompanyEntry();
company.setOrganizationId("556789-1234");
company.setName("Demo AB");
fileInfo.setCompany(company);

entry.setFileInfo(fileInfo);

AccountEntry acc = new AccountEntry();
acc.setId("1910");
acc.setName("Kassa");
acc.setType(AccountTypeValue.ASSET);
entry.setAccounts(List.of(acc));

new Sie5DocumentWriter().writeEntry(entry, "new-entry.sie");
```

## SIE specification

The SIE file format is defined by SIE-gruppen (formerly SIE-föreningen). The specification is available at [sie.se](https://sie.se/).

Even if you use this parser, you should familiarize yourself with the file specification to understand the data model.

## License

MIT License. See [LICENSE](LICENSE) or the file headers for details.

## Change log

**Version 2.0** (in development):
- Ported all upstream bug fixes and features from jsisie (2017-2026)
- Java 17 minimum, `java.time.LocalDate` throughout, `java.util.function.Consumer` callbacks
- `#UNDERDIM` support, `#KSUMMA` writing, stream I/O, SIE type filtering
- Exception hierarchy (`SieException` base class), proper field encapsulation
- 112 tests across 8 test classes for SIE 1-4
- SIE 5 (XML) read/write support via JAXB (`alipsa.sieparser.sie5` package)
- New dependencies
  - jakarta.xml.bind:jakarta.xml.bind-api [4.0.5]
  - org.glassfish.jaxb:jaxb-runtime [4.0.6]
- Upgraded dependencies
  - org.slf4j:slf4j-api [2.0.16 -> 2.0.17]

**Version 1.0**:
- Initial working port of the jsisie parser to Java
