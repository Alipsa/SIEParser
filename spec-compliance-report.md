# SIE Spec Compliance Report

Based on cross-referencing the implementation against:
- `docs/SIE_filformat_ver_4B_080930.pdf` (SIE 4B, 2008-09-30)
- `docs/SIE-5-rev-161209-konsoliderad.pdf` (SIE 5, 2016-12-09)

Generated: 2026-02-27

---

## SIE 4 Issues

### HIGH — Spec Violations

**1. [FIXED] Unknown labels throw exception instead of being silently ignored**
- **Spec:** "Importing programs must silently ignore unknown record labels."
- **Fix applied:** Unknown labels are now silently ignored in `SieDocumentReader`.

---

**2. [FIXED] Bug in `parseOBJEKT` — wrong dimension ID used when creating on-the-fly dimension**
- **Code:** Was passing the *object* number instead of `dimNumber` to the `SieDimension` constructor.
- **Fix applied:** Corrected to `new SieDimension(dimNumber)`.

---

**3. [FIXED] Writer always emits `#ORGNR` regardless of null/empty value**
- **Spec:** `#ORGNR` is optional.
- **Fix applied:** `getORGNR()` returns null when orgIdentifier is null/empty; guarded in `writeContent()`.

---

**4. [FIXED] Writer emits `#DIM` records for all 19 default dimensions unconditionally**
- **Spec:** `#DIM` is **not permitted** (`-`) in Types 1 and 2.
- **Fix applied:** `writeDIM()`/`writeUNDERDIM()` skipped for SIE types < 3; default dimensions with no objects are skipped.

---

**5. [FIXED] Writer `writePeriodValue` silently drops the quantity field**
- **Spec:** `#IB`, `#UB`, `#OIB`, `#OUB`, `#RES` all have an optional `kvantitet` field.
- **Fix applied:** Quantity is now appended when non-null and non-zero.

---

**6. [FIXED] Writer does not emit `#BKOD`**
- **Fix applied:** Added `writeBKOD()` method, emits `#BKOD <sni>` when `sni > 0`.

---

**7. [FIXED] `makeSieDate` returns `"00000000"` for null — written into `#GEN`**
- **Spec:** `#GEN datum` is a mandatory, valid YYYYMMDD date.
- **Fix applied:** `getGEN()` now falls back to `LocalDate.now()` when `GEN_DATE` is null.

---

### MEDIUM — Missing Mandatory-Field Validation

**8. [FIXED] Mandatory records not validated**

The following records are now validated via soft warnings (added to `getValidationWarnings()`):

| Record | Mandatory in | Status |
|--------|-------------|--------|
| `#PROGRAM` | All types | Validated ✓ |
| `#FORMAT` | All types | Validated ✓ |
| `#FNAMN` | All types | Validated ✓ |
| `#SIETYP` | Types 2, 3, 4 | Validated ✓ |
| `#KONTO` | Types 1, 2, 3, 4E | Validated ✓ |
| `#RAR` | Types 1, 2, 3, 4E | Validated ✓ |

---

**9. [FIXED] Records that are forbidden in certain types are silently accepted**

| Record | Forbidden in | Status |
|--------|-------------|--------|
| `#DIM` | Types 1, 2 | Validated ✓ |
| `#UNDERDIM` | Types 1, 2 | Validated ✓ (also guarded by `allowUnderDimensions` flag) |
| `#OMFATTN` | Type 1 | Validated ✓ |
| `#OIB`/`#OUB` | Types 1, 2 | Was already blocked ✓ |

---

### LOW — Format and Validation Gaps

**10. [FIXED] `allowUnderDimensions` flag has no effect**
- **Fix applied:** Default changed to `true`; guard added in `parseUnderDimension()` that reports via callback and returns when `false`.

**11. [FIXED] Period format not validated**
- **Fix applied:** Period validated as 6 digits with valid month (01-12) in `parsePBUDGET_PSALDO`.

**12. [FIXED] Amount max-2-decimal-places not enforced on read or write**
- **Fix applied:** Reader warns via `getValidationWarnings()` for amounts with >2 decimal places. Writer truncates to 2 decimal places with `HALF_UP` rounding.

**13. [FIXED] Account numbers not validated as numeric**
- **Fix applied:** `parseKONTO` warns if account number is not all digits.

**14. [FIXED] `#ORGNR` format not validated**
- **Fix applied:** `handleORGNR` warns if format does not match `NNNNNN-NNNN`.

**15. [FIXED] Verification ordering not enforced**
- **Fix applied:** `closeVoucher` checks ascending voucher number order per series.

**16. [FIXED] Trailing empty quoted string at end of line is dropped by `splitLine`**
- **Fix applied:** Post-loop guard in `SieDataItem.splitLine` now also checks `isInField == 2`.

**17. [FIXED] CRC32 may include whitespace and quotes inside object lists**
- **Fix applied:** `SieCRC32.addData()` now strips `"`, spaces, and tabs in addition to braces.

---

## SIE 5 Issues

### HIGH — Spec Violations

**18. [FIXED] Digital signature is optional in the writer but mandatory in the spec**
- **Spec:** "Files written by accounting systems **must** contain at least one electronic signature."
- **Fix applied:** `Sie5DocumentWriter` supports signing via `Sie5SigningCredentials`; `requireSignatureForFullDocuments` flag (default `true`) rejects unsigned writes.

---

### MEDIUM — Missing or Partial Validation

**19. [FIXED] `<FiscalYear primary>` not validated**
- **Fix applied:** Strict validation checks exactly one `<FiscalYear>` has `primary="true"` and that fiscal years are in chronological order.

**20. [FIXED] `<AccountingCurrency>` presence not validated for full `<Sie>` documents**
- **Fix applied:** Strict validation warns if `<AccountingCurrency>` is missing.

**21. [FIXED] No cross-year completeness check for `<OpeningBalance>` / `<ClosingBalance>`**
- **Fix applied:** Strict validation warns for balance-sheet accounts that have one balance type but lack the other for a fiscal year.

**22. [FIXED] `<JournalEntry id>` ascending-order rule not enforced**
- **Fix applied:** Strict validation checks strictly ascending IDs per journal.

**23. [FIXED] `<LedgerEntry quantity>` sign rule not validated**
- **Fix applied:** Strict validation warns when quantity sign differs from amount sign.

**24. [FIXED] `<ForeignCurrencyAmount>` sign rule not validated**
- **Fix applied:** Strict validation warns when foreign currency amount sign differs from accounting amount sign.

**25. [FIXED] `<Overstrike>` lines may not be excluded from balance totals**
- **Fix applied:** Added `JournalEntry.getActiveLedgerEntries()` convenience method that filters out overstriken entries.

---

### LOW

**26. SIE 5 dimension numbering differs from SIE 4 pre-populated defaults**
- **SIE 4 spec** reserves 1–10 (8=Kund, 9=Leverantör, 10=Faktura).
- **SIE 5 spec** only reserves 1–7 and 18–19; customer/supplier/invoice ledgers are separate XML sections, not dimensions.
- The `SieDocument` pre-population (dimensions 1–19 with SIE-4 names) is irrelevant for SIE 5 but causes no functional conflict since SIE 4 and SIE 5 are handled by separate classes.
- **Status:** Not a bug — no fix needed.

---

## Summary Table

| # | Severity | Area | Issue | Status |
|---|----------|------|-------|--------|
| 1 | HIGH | SIE4 Reader | Unknown labels throw instead of silently ignored | FIXED |
| 2 | HIGH | SIE4 Reader | `parseOBJEKT` passes wrong ID to `SieDimension` constructor | FIXED |
| 3 | HIGH | SIE4 Writer | `#ORGNR` always written, emits `null` if unset | FIXED |
| 4 | HIGH | SIE4 Writer | All 19 default `#DIM` records written for Types 1 & 2 (forbidden) | FIXED |
| 5 | HIGH | SIE4 Writer | Quantity field dropped from `#IB`/`#UB`/`#OIB`/`#OUB`/`#RES` | FIXED |
| 6 | HIGH | SIE4 Writer | `#BKOD` never written (lossy round-trip) | FIXED |
| 7 | HIGH | SIE4 Writer | `#GEN` emits `00000000` for null date | FIXED |
| 8 | MEDIUM | SIE4 Reader | No mandatory-field validation (#PROGRAM, #FORMAT, #FNAMN, #SIETYP, #KONTO, #RAR) | FIXED |
| 9 | MEDIUM | SIE4 Reader | Records forbidden in certain types not blocked (#DIM in 1/2, #OMFATTN in 1, etc.) | FIXED |
| 10 | MEDIUM | SIE4 Reader | `allowUnderDimensions` flag has no effect (handler always runs regardless) | FIXED |
| 11 | LOW | SIE4 Reader | Period format (YYYYMM) not validated | FIXED |
| 12 | LOW | SIE4 Both | Amount max-2-decimal precision not enforced | FIXED |
| 13 | LOW | SIE4 Reader | Account numbers not validated as numeric | FIXED |
| 14 | LOW | SIE4 Reader | `#ORGNR` hyphen format not validated | FIXED |
| 15 | LOW | SIE4 Reader | Verification ascending-order rule not enforced | FIXED |
| 16 | LOW | SIE4 Reader | Trailing empty quoted string dropped by `splitLine` | FIXED |
| 17 | LOW | SIE4 Both | CRC32 may include whitespace/quotes inside object lists | FIXED |
| 18 | HIGH | SIE5 Writer | Signature is optional but spec mandates ≥1 per file | FIXED |
| 19 | MEDIUM | SIE5 Reader | No validation of `primary="true"` on exactly one `<FiscalYear>` or ordering | FIXED |
| 20 | MEDIUM | SIE5 Reader | No validation that `<AccountingCurrency>` is present in full Sie documents | FIXED |
| 21 | MEDIUM | SIE5 Reader | No cross-year completeness check for `<OpeningBalance>`/`<ClosingBalance>` | FIXED |
| 22 | MEDIUM | SIE5 Reader | `<JournalEntry id>` ascending-order rule not enforced | FIXED |
| 23 | MEDIUM | SIE5 Reader | `quantity` sign rule vs `amount` not validated on `<LedgerEntry>` | FIXED |
| 24 | MEDIUM | SIE5 Reader | `<ForeignCurrencyAmount>` sign rule not validated | FIXED |
| 25 | MEDIUM | SIE5 Reader | `<Overstrike>` lines may not be excluded from balance totals | FIXED |
| 26 | LOW | SIE5 | Dimension numbering differs from SIE 4 defaults | N/A (no bug) |
