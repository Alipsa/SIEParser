# SIE Spec Compliance Report

Based on cross-referencing the implementation against:
- `docs/SIE_filformat_ver_4B_080930.pdf` (SIE 4B, 2008-09-30)
- `docs/SIE-5-rev-161209-konsoliderad.pdf` (SIE 5, 2016-12-09)

Generated: 2026-02-27

---

## SIE 4 Issues

### HIGH — Spec Violations

**1. Unknown labels throw exception instead of being silently ignored**
- **Spec:** "Importing programs must silently ignore unknown record labels."
- **Code:** `SieDocumentReader.java:316` — `callbacks.callbackException(new UnsupportedOperationException(di.getItemType()))` fires on any unrecognised `#TAG`.
- **Fix:** Replace with a no-op (log at debug level at most).

---

**2. Bug in `parseOBJEKT` — wrong dimension ID used when creating on-the-fly dimension**
- **Code:** `SieDocumentReader.java:584` — `sieDocument.getDIM().put(dimNumber, new SieDimension(number))` passes the *object* number (`number`) to the `SieDimension` constructor instead of `dimNumber`. The dimension is stored in the map under the right key but has the wrong internal `number` field, which then pollutes written output and object lookups.
- **Fix:** `new SieDimension(dimNumber)`.

---

**3. Writer always emits `#ORGNR` regardless of null/empty value**
- **Spec:** `#ORGNR` is optional.
- **Code:** `SieDocumentWriter.java:347` — `getORGNR()` always returns `"#ORGNR " + orgIdentifier`. If `orgIdentifier` is null, this writes the literal string `#ORGNR null`.
- **Fix:** Guard with a null/empty check, same pattern used for `#FNR`, `#FTYP`, etc.

---

**4. Writer emits `#DIM` records for all 19 default dimensions unconditionally**
- **Spec:** `#DIM` is **not permitted** (`-`) in Types 1 and 2.
- **Code:** `SieDocumentWriter.java:216-224` — `writeDIM()` iterates all `sieDoc.getDIM().values()` which always contains the 19 pre-populated default dimensions from `SieDocument`'s constructor.
- **Fix:** Either skip pre-populated ("default") dimensions (which have `isDefault=true`) or skip `writeDIM()` entirely for SIE types 1 and 2.

---

**5. Writer `writePeriodValue` silently drops the quantity field**
- **Spec:** `#IB`, `#UB`, `#OIB`, `#OUB`, `#RES` all have an optional `kvantitet` field.
- **Code:** `SieDocumentWriter.java:233-247` — `writePeriodValue` builds the line with `yearNr`, `accountNr`, `objekt`, `amount` but never appends `v.getQuantity()`. Quantity data is lost on write.
- **Fix:** Append quantity when non-null and non-zero, same as `writePeriodSaldo` already does.

---

**6. Writer does not emit `#BKOD`**
- **Code:** `SieDocumentWriter.writeContent()` — `SIE.BKOD` is never written. `SieCompany.sni` is parsed and stored but silently discarded on write. Round-trip is lossy.
- **Fix:** Add a `writeBKOD()` method, guarded by `sni > 0` and SIE type ≠ 4I.

---

**7. `makeSieDate` returns `"00000000"` for null — written into `#GEN`**
- **Spec:** `#GEN datum` is a mandatory, valid YYYYMMDD date.
- **Code:** `SieDocumentWriter.java:408-413` — `makeSieDate(null)` returns `"00000000"`. This is used for `#GEN`'s date field (`getGEN()`, line 362) if `GEN_DATE` is null. `00000000` is not a valid date.
- **Fix:** Throw or log on null; or ensure `GEN_DATE` is set before writing.

---

### MEDIUM — Missing Mandatory-Field Validation

The `validateDocument()` method at `SieDocumentReader.java:776` checks only `#GEN` date, `#OMFATTN`, and `#KSUMMA`. The following mandatory records are never validated:

| Record | Mandatory in | Missing check |
|--------|-------------|---------------|
| `#PROGRAM` | All types | Not validated |
| `#FORMAT` | All types | Not validated (value also not checked to be "PC8") |
| `#FNAMN` | All types | Not validated |
| `#SIETYP` | Types 2, 3, 4 | Not validated |
| `#KONTO` | Types 1, 2, 3, 4E | Not validated |
| `#SRU` | Types 1, 2 | Not validated |
| `#RAR` | Types 1, 2, 3, 4E | Not validated |

---

**8. Records that are forbidden in certain types are silently accepted**

| Record | Forbidden in | Enforcement |
|--------|-------------|-------------|
| `#BKOD` | Type 4I | None |
| `#DIM` | Types 1, 2 | None |
| `#UNDERDIM` | Types 1, 2 | `allowUnderDimensions` flag exists but is not checked inside `parseUnderDimension()`; the handler is always registered and always runs |
| `#OMFATTN` | Type 1 | Not blocked |
| `#PSALDO`/`#PBUDGET` | Type 1, 4I | Type 1 raises exception ✓; Type 4I not blocked |
| `#OIB`/`#OUB` | Types 1, 2 | Correctly blocked (checks `< 3`) ✓ |

---

### LOW — Format and Validation Gaps

**9. Period format not validated**
- **Spec:** Period is ÅÅÅÅMM (exactly 6 digits).
- **Code:** `parsePBUDGET_PSALDO` reads period with `di.getInt(1)` — any integer is accepted. No 6-digit format validation.

**10. Amount max-2-decimal-places not enforced on read or write**
- **Spec:** "At most two decimal places (ören) are permitted."
- `BigDecimal` on read accepts any precision. `toPlainString()` on write may emit more than 2 decimal places if the stored value has them.

**11. Account numbers not validated as numeric**
- **Spec:** "`kontonr` must be numeric."
- **Code:** `parseKONTO` stores whatever string it receives.

**12. `#ORGNR` format not validated**
- **Spec:** Must contain a hyphen after the 6th digit (e.g. `556334-3689`).
- **Code:** `SieDocumentReader.java:351` — stored as-is.

**13. Verification ordering not enforced**
- **Spec:** "Numbered verifications within a series must appear in ascending verification number order."
- **Code:** No ordering check in `closeVoucher`.

**14. Trailing empty quoted string at end of line is dropped by `splitLine`**
- **Spec:** Empty quoted fields (`""`) are valid.
- **Code:** `SieDataItem.java:179` — the final `buffer.length() > 0` guard misses an empty string that was just closed (e.g. the trailing `""` in `#GEN 20080101 ""`).

**15. CRC32 may include whitespace and quotes inside object lists**
- **Spec:** "Whitespace and tabs between fields — excluded; Enclosing quote characters — excluded; Braces — excluded."
- **Code:** `SieCRC32.addData()` strips `{` and `}` but the object list data (e.g. `1 "0123"`) retains internal whitespace and quotes because `splitLine` does not strip quotes when `isInObject == true`. The CRC feeds `1 "0123"` to the hash instead of the spec-compliant `10123`. This may cause checksum mismatch with external SIE-compliant tools.

---

## SIE 5 Issues

### HIGH — Spec Violations

**16. Digital signature is optional in the writer but mandatory in the spec**
- **Spec:** "Files written by accounting systems **must** contain at least one electronic signature."
- **Code:** `Sie5DocumentWriter` — signing is optional, gated on `Sie5SigningCredentials`. A file can be written without any signature.
- **Note:** The reader correctly warns on invalid signatures. The writing gap is the compliance problem.

---

### MEDIUM — Missing or Partial Validation

**17. `<FiscalYear primary>` not validated**
- **Spec:** Exactly one `<FiscalYear>` must have `primary="true"`. Fiscal years must appear in chronological order, be contiguous, and not overlap.
- **Status:** The JAXB model has the attribute, but there is no read-time validation that exactly one primary exists and that the ordering/contiguity rules hold.

**18. `<AccountingCurrency>` presence not validated for full `<Sie>` documents**
- **Spec:** Required for `<Sie>`; optional only for `<SieEntry>`.
- **Status:** No validation that the element is present when writing or reading a full Sie document.

**19. No cross-year completeness check for `<OpeningBalance>` / `<ClosingBalance>`**
- **Spec:** Both are required (for non-zero values) for every declared fiscal year on balance accounts.
- **Status:** No completeness validation in the reader.

**20. `<JournalEntry id>` ascending-order rule not enforced**
- **Spec:** "`id` must be a positive integer appearing in strictly ascending order within a journal series."
- **Status:** No ordering validation in `Sie5DocumentReader`.

**21. `<LedgerEntry quantity>` sign rule not validated**
- **Spec:** "Quantity must have the same sign as amount if amount is non-zero."
- **Status:** No validation.

**22. `<ForeignCurrencyAmount>` sign rule not validated**
- **Spec:** "Foreign currency amount must have the same sign as the accounting currency amount."
- **Status:** No validation.

**23. `<Overstrike>` lines may not be excluded from balance totals**
- **Spec:** "Reading systems **must** handle `<Overstrike>` so struck lines do not affect totals."
- **Status:** Needs verification that `Sie5DocumentReader` excludes overstriken `<LedgerEntry>` elements from balance checks.

---

### LOW

**24. SIE 5 dimension numbering differs from SIE 4 pre-populated defaults**
- **SIE 4 spec** reserves 1–10 (8=Kund, 9=Leverantör, 10=Faktura).
- **SIE 5 spec** only reserves 1–7 and 18–19; customer/supplier/invoice ledgers are separate XML sections, not dimensions.
- The `SieDocument` pre-population (dimensions 1–19 with SIE-4 names) is irrelevant for SIE 5 but causes no functional conflict since SIE 4 and SIE 5 are handled by separate classes.

---

## Summary Table

| # | Severity | Area | Issue |
|---|----------|------|-------|
| 1 | HIGH | SIE4 Reader | Unknown labels throw instead of silently ignored |
| 2 | HIGH | SIE4 Reader | `parseOBJEKT` passes wrong ID to `SieDimension` constructor |
| 3 | HIGH | SIE4 Writer | `#ORGNR` always written, emits `null` if unset |
| 4 | HIGH | SIE4 Writer | All 19 default `#DIM` records written for Types 1 & 2 (forbidden) |
| 5 | HIGH | SIE4 Writer | Quantity field dropped from `#IB`/`#UB`/`#OIB`/`#OUB`/`#RES` |
| 6 | HIGH | SIE4 Writer | `#BKOD` never written (lossy round-trip) |
| 7 | HIGH | SIE4 Writer | `#GEN` emits `00000000` for null date |
| 8 | MEDIUM | SIE4 Reader | No mandatory-field validation (#PROGRAM, #FORMAT, #FNAMN, #SIETYP, #KONTO, #SRU, #RAR) |
| 9 | MEDIUM | SIE4 Reader | Records forbidden in certain types not blocked (#BKOD in 4I, #DIM in 1/2, #OMFATTN in 1, etc.) |
| 10 | MEDIUM | SIE4 Reader | `allowUnderDimensions` flag has no effect (handler always runs regardless) |
| 11 | LOW | SIE4 Reader | Period format (YYYYMM) not validated |
| 12 | LOW | SIE4 Both | Amount max-2-decimal precision not enforced |
| 13 | LOW | SIE4 Reader | Account numbers not validated as numeric |
| 14 | LOW | SIE4 Reader | `#ORGNR` hyphen format not validated |
| 15 | LOW | SIE4 Reader | Verification ascending-order rule not enforced |
| 16 | LOW | SIE4 Reader | Trailing empty quoted string dropped by `splitLine` |
| 17 | LOW | SIE4 Both | CRC32 may include whitespace/quotes inside object lists |
| 18 | HIGH | SIE5 Writer | Signature is optional but spec mandates ≥1 per file |
| 19 | MEDIUM | SIE5 Reader | No validation of `primary="true"` on exactly one `<FiscalYear>` or ordering/contiguity |
| 20 | MEDIUM | SIE5 Reader | No validation that `<AccountingCurrency>` is present in full Sie documents |
| 21 | MEDIUM | SIE5 Reader | No cross-year completeness check for `<OpeningBalance>`/`<ClosingBalance>` |
| 22 | MEDIUM | SIE5 Reader | `<JournalEntry id>` ascending-order rule not enforced |
| 23 | MEDIUM | SIE5 Reader | `quantity` sign rule vs `amount` not validated on `<LedgerEntry>` |
| 24 | MEDIUM | SIE5 Reader | `<ForeignCurrencyAmount>` sign rule not validated |
| 25 | MEDIUM | SIE5 Reader | `<Overstrike>` lines may not be excluded from balance totals |
