package alipsa.sieparser.sie5;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Sie5DocumentReaderTest {

    private final Sie5DocumentReader reader = createReader();

    private static Sie5DocumentReader createReader() {
        Sie5DocumentReader reader = new Sie5DocumentReader();
        // Sample fixtures contain legacy signatures that require compatibility mode.
        reader.setAllowLegacyInsecureSignatureAlgorithms(true);
        return reader;
    }

    @Test
    void legacyInsecureSignatureFallbackDisabledByDefault() {
        Sie5DocumentReader defaultReader = new Sie5DocumentReader();
        assertFalse(defaultReader.isAllowLegacyInsecureSignatureAlgorithms());
    }

    @Test
    void readSampleDocument() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/samples/sie5/Sample.sie")) {
            assertNotNull(is, "Sample.sie not found on classpath");
            Sie5Document doc = reader.readDocument(is);

            // FileInfo
            assertNotNull(doc.getFileInfo());
            assertEquals("Edison Ekonomi", doc.getFileInfo().getSoftwareProduct().getName());
            assertEquals("6.0B", doc.getFileInfo().getSoftwareProduct().getVersion());
            assertEquals("LH", doc.getFileInfo().getFileCreation().getBy());
            assertNotNull(doc.getFileInfo().getFileCreation().getTime());

            // Company
            assertEquals("555555-5555", doc.getFileInfo().getCompany().getOrganizationId());
            assertEquals("Övningsbolaget AB", doc.getFileInfo().getCompany().getName());
            assertEquals("1", doc.getFileInfo().getCompany().getClientId());

            // FiscalYears
            assertEquals(2, doc.getFileInfo().getFiscalYears().size());

            // AccountingCurrency
            assertEquals("SEK", doc.getFileInfo().getAccountingCurrency().getCurrency());

            // Accounts - should have many accounts
            assertFalse(doc.getAccounts().isEmpty(), "Should have accounts");
            assertTrue(doc.getAccounts().size() > 100, "Should have many accounts, got " + doc.getAccounts().size());

            // Verify first account
            Account first = doc.getAccounts().get(0);
            assertEquals("1010", first.getId());
            assertEquals("Balanserade utgifter", first.getName());
            assertEquals(AccountTypeValue.ASSET, first.getType());

            // Verify account with balances (id=1210)
            Account acc1210 = doc.getAccounts().stream()
                    .filter(a -> "1210".equals(a.getId()))
                    .findFirst()
                    .orElseThrow();
            assertEquals("Maskiner och andra tekn anl", acc1210.getName());
            assertFalse(acc1210.getOpeningBalances().isEmpty(), "1210 should have opening balance");
            assertFalse(acc1210.getClosingBalances().isEmpty(), "1210 should have closing balance");

            // Dimensions
            assertNotNull(doc.getDimensions());
            assertEquals(2, doc.getDimensions().size());
            assertEquals(1, doc.getDimensions().get(0).getId());
            assertEquals("Kostnadsställe", doc.getDimensions().get(0).getName());
            assertTrue(doc.getDimensions().get(0).getObjects().size() > 0);

            // Customer invoices
            assertFalse(doc.getCustomerInvoices().isEmpty(), "Should have customer invoices");
            assertEquals("1510", doc.getCustomerInvoices().get(0).getPrimaryAccountId());
            assertFalse(doc.getCustomerInvoices().get(0).getCustomerInvoices().isEmpty());

            // Supplier invoices
            assertFalse(doc.getSupplierInvoices().isEmpty(), "Should have supplier invoices");
            assertEquals("2440", doc.getSupplierInvoices().get(0).getPrimaryAccountId());

            // Journals
            assertFalse(doc.getJournals().isEmpty(), "Should have journals");
            assertTrue(doc.getJournals().size() > 1, "Should have multiple journals");
        }
    }

    @Test
    void readSampleEntryDocument() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/samples/sie5/SampleEntry.sie")) {
            assertNotNull(is, "SampleEntry.sie not found on classpath");
            Sie5Entry entry = reader.readEntry(is);

            // FileInfo
            assertNotNull(entry.getFileInfo());
            assertEquals("Anonymous software Ltd", entry.getFileInfo().getSoftwareProduct().getName());
            assertEquals("0.0.007B", entry.getFileInfo().getSoftwareProduct().getVersion());
            assertEquals("LH", entry.getFileInfo().getFileCreation().getBy());

            // Company
            assertEquals("56334-3689", entry.getFileInfo().getCompany().getOrganizationId());
            assertEquals("Universal Exports AB", entry.getFileInfo().getCompany().getName());

            // No AccountingCurrency in entry file
            assertNull(entry.getFileInfo().getAccountingCurrency());

            // Accounts
            assertEquals(2, entry.getAccounts().size());
            assertEquals("1910", entry.getAccounts().get(0).getId());
            assertEquals("Kassa", entry.getAccounts().get(0).getName());
            assertEquals(AccountTypeValue.ASSET, entry.getAccounts().get(0).getType());
            assertEquals("1930", entry.getAccounts().get(1).getId());
        }
    }

    @Test
    void roundTrip() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/samples/sie5/SampleEntry.sie")) {
            assertNotNull(is, "SampleEntry.sie not found on classpath");
            Sie5Entry original = reader.readEntry(is);

            // Write to bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Sie5DocumentWriter writer = new Sie5DocumentWriter();
            writer.writeEntry(original, baos);

            // Read back
            Sie5Entry roundTripped = reader.readEntry(new ByteArrayInputStream(baos.toByteArray()));

            // Verify structural equality
            assertEquals(original.getFileInfo().getSoftwareProduct().getName(),
                    roundTripped.getFileInfo().getSoftwareProduct().getName());
            assertEquals(original.getFileInfo().getCompany().getOrganizationId(),
                    roundTripped.getFileInfo().getCompany().getOrganizationId());
            assertEquals(original.getAccounts().size(), roundTripped.getAccounts().size());
            for (int i = 0; i < original.getAccounts().size(); i++) {
                assertEquals(original.getAccounts().get(i).getId(), roundTripped.getAccounts().get(i).getId());
                assertEquals(original.getAccounts().get(i).getName(), roundTripped.getAccounts().get(i).getName());
                assertEquals(original.getAccounts().get(i).getType(), roundTripped.getAccounts().get(i).getType());
            }
        }
    }

    @Test
    void invalidSignatureRejected() throws Exception {
        Sie5Document doc = new Sie5Document();
        FileInfo fileInfo = new FileInfo();
        SoftwareProduct sp = new SoftwareProduct();
        sp.setName("TestApp");
        sp.setVersion("2.0");
        fileInfo.setSoftwareProduct(sp);

        FileCreation fc = new FileCreation();
        fc.setTime(OffsetDateTime.of(2024, 6, 1, 12, 0, 0, 0, ZoneOffset.UTC));
        fc.setBy("Admin");
        fileInfo.setFileCreation(fc);

        Company company = new Company();
        company.setOrganizationId("556789-1234");
        company.setName("Demo AB");
        fileInfo.setCompany(company);

        FiscalYear fy = new FiscalYear();
        fy.setStart(YearMonth.of(2024, 1));
        fy.setEnd(YearMonth.of(2024, 12));
        fy.setPrimary(true);
        fileInfo.setFiscalYears(List.of(fy));

        AccountingCurrency currency = new AccountingCurrency();
        currency.setCurrency("SEK");
        fileInfo.setAccountingCurrency(currency);
        doc.setFileInfo(fileInfo);

        Account acc = new Account();
        acc.setId("1910");
        acc.setName("Kassa");
        acc.setType(AccountTypeValue.ASSET);
        doc.setAccounts(List.of(acc));

        Sie5DocumentWriter signedWriter = new Sie5DocumentWriter(TestSigningCredentials.create());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        signedWriter.write(doc, baos);

        String tampered = baos.toString(StandardCharsets.UTF_8).replace("Demo AB", "Hacked AB");
        assertThrows(alipsa.sieparser.SieException.class,
            () -> reader.readDocument(new ByteArrayInputStream(tampered.getBytes(StandardCharsets.UTF_8))));
    }

    // === Phase 5 tests ===

    @Test
    void strictValidationFiscalYearPrimary() {
        Sie5DocumentReader strictReader = new Sie5DocumentReader();
        strictReader.setVerifySignatures(false);
        strictReader.setStrictValidation(true);

        Sie5Document doc = createMinimalSie5Doc();
        // No fiscal year has primary=true
        doc.getFileInfo().getFiscalYears().get(0).setPrimary(null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sie5DocumentWriter writer = new Sie5DocumentWriter();
        writer.setRequireSignatureForFullDocuments(false);
        writer.write(doc, baos);

        Sie5Document result = strictReader.readDocument(new ByteArrayInputStream(baos.toByteArray()));
        assertTrue(strictReader.getValidationWarnings().stream()
                .anyMatch(w -> w.contains("primary")),
                "Should warn about no primary fiscal year");
    }

    @Test
    void strictValidationAccountingCurrencyMissing() {
        Sie5DocumentReader strictReader = new Sie5DocumentReader();
        strictReader.setVerifySignatures(false);
        strictReader.setStrictValidation(true);

        Sie5Document doc = createMinimalSie5Doc();
        doc.getFileInfo().setAccountingCurrency(null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sie5DocumentWriter writer = new Sie5DocumentWriter();
        writer.setRequireSignatureForFullDocuments(false);
        writer.write(doc, baos);

        Sie5Document result = strictReader.readDocument(new ByteArrayInputStream(baos.toByteArray()));
        assertTrue(strictReader.getValidationWarnings().stream()
                .anyMatch(w -> w.contains("AccountingCurrency")),
                "Should warn about missing accounting currency");
    }

    @Test
    void strictValidationJournalEntryIdOrder() {
        Sie5DocumentReader strictReader = new Sie5DocumentReader();
        strictReader.setVerifySignatures(false);
        strictReader.setStrictValidation(true);

        Sie5Document doc = createMinimalSie5Doc();
        Journal journal = new Journal();
        journal.setId("A");
        journal.setName("Main");

        JournalEntry e1 = createJournalEntry(BigInteger.valueOf(2), "2024-01-01");
        JournalEntry e2 = createJournalEntry(BigInteger.valueOf(1), "2024-01-02");
        journal.setJournalEntries(List.of(e1, e2));
        doc.setJournals(List.of(journal));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sie5DocumentWriter writer = new Sie5DocumentWriter();
        writer.setRequireSignatureForFullDocuments(false);
        writer.write(doc, baos);

        Sie5Document result = strictReader.readDocument(new ByteArrayInputStream(baos.toByteArray()));
        assertTrue(strictReader.getValidationWarnings().stream()
                .anyMatch(w -> w.contains("not strictly ascending")),
                "Should warn about non-ascending journal entry IDs");
    }

    @Test
    void strictValidationQuantitySignMismatch() {
        Sie5DocumentReader strictReader = new Sie5DocumentReader();
        strictReader.setVerifySignatures(false);
        strictReader.setStrictValidation(true);

        Sie5Document doc = createMinimalSie5Doc();
        Journal journal = new Journal();
        journal.setId("A");
        journal.setName("Main");

        JournalEntry entry = createJournalEntry(BigInteger.ONE, "2024-01-01");
        LedgerEntry le = new LedgerEntry();
        le.setAccountId("1910");
        le.setAmount(new BigDecimal("100"));
        le.setQuantity(new BigDecimal("-5"));  // sign mismatch
        entry.setLedgerEntries(List.of(le));
        journal.setJournalEntries(List.of(entry));
        doc.setJournals(List.of(journal));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sie5DocumentWriter writer = new Sie5DocumentWriter();
        writer.setRequireSignatureForFullDocuments(false);
        writer.write(doc, baos);

        Sie5Document result = strictReader.readDocument(new ByteArrayInputStream(baos.toByteArray()));
        assertTrue(strictReader.getValidationWarnings().stream()
                .anyMatch(w -> w.contains("quantity sign")),
                "Should warn about quantity sign mismatch");
    }

    @Test
    void strictValidationForeignCurrencySignMismatch() {
        Sie5DocumentReader strictReader = new Sie5DocumentReader();
        strictReader.setVerifySignatures(false);
        strictReader.setStrictValidation(true);

        Sie5Document doc = createMinimalSie5Doc();
        Journal journal = new Journal();
        journal.setId("A");
        journal.setName("Main");

        JournalEntry entry = createJournalEntry(BigInteger.ONE, "2024-01-01");
        LedgerEntry le = new LedgerEntry();
        le.setAccountId("1910");
        le.setAmount(new BigDecimal("100"));
        ForeignCurrencyAmount fca = new ForeignCurrencyAmount();
        fca.setAmount(new BigDecimal("-50"));  // sign mismatch
        fca.setCurrency("USD");
        le.setForeignCurrencyAmount(fca);
        entry.setLedgerEntries(List.of(le));
        journal.setJournalEntries(List.of(entry));
        doc.setJournals(List.of(journal));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sie5DocumentWriter writer = new Sie5DocumentWriter();
        writer.setRequireSignatureForFullDocuments(false);
        writer.write(doc, baos);

        Sie5Document result = strictReader.readDocument(new ByteArrayInputStream(baos.toByteArray()));
        assertTrue(strictReader.getValidationWarnings().stream()
                .anyMatch(w -> w.contains("foreign currency")),
                "Should warn about foreign currency amount sign mismatch");
    }

    @Test
    void noWarningsWithoutStrictValidation() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/samples/sie5/Sample.sie")) {
            assertNotNull(is, "Sample.sie not found on classpath");
            Sie5Document doc = reader.readDocument(is);
            assertTrue(reader.getValidationWarnings().isEmpty(),
                    "Should have no warnings when strict validation is disabled");
        }
    }

    @Test
    void getActiveLedgerEntriesFiltersOverstriken() {
        JournalEntry entry = new JournalEntry();
        LedgerEntry active = new LedgerEntry();
        active.setAccountId("1910");
        active.setAmount(new BigDecimal("100"));

        LedgerEntry overstriken = new LedgerEntry();
        overstriken.setAccountId("2440");
        overstriken.setAmount(new BigDecimal("-100"));
        Overstrike os = new Overstrike();
        os.setDate(LocalDate.of(2024, 1, 15));
        os.setBy("Admin");
        overstriken.setOverstrike(os);

        entry.setLedgerEntries(List.of(active, overstriken));

        assertEquals(2, entry.getLedgerEntries().size(), "All entries should be in full list");
        assertEquals(1, entry.getActiveLedgerEntries().size(), "Only active entries should be returned");
        assertEquals("1910", entry.getActiveLedgerEntries().get(0).getAccountId());
    }

    private Sie5Document createMinimalSie5Doc() {
        Sie5Document doc = new Sie5Document();
        FileInfo fileInfo = new FileInfo();
        SoftwareProduct sp = new SoftwareProduct();
        sp.setName("TestApp");
        sp.setVersion("1.0");
        fileInfo.setSoftwareProduct(sp);

        FileCreation fc = new FileCreation();
        fc.setTime(OffsetDateTime.of(2024, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC));
        fc.setBy("Test");
        fileInfo.setFileCreation(fc);

        Company company = new Company();
        company.setOrganizationId("556677-8899");
        company.setName("Test AB");
        fileInfo.setCompany(company);

        FiscalYear fy = new FiscalYear();
        fy.setStart(YearMonth.of(2024, 1));
        fy.setEnd(YearMonth.of(2024, 12));
        fy.setPrimary(true);
        fileInfo.setFiscalYears(List.of(fy));

        AccountingCurrency currency = new AccountingCurrency();
        currency.setCurrency("SEK");
        fileInfo.setAccountingCurrency(currency);
        doc.setFileInfo(fileInfo);

        Account acc = new Account();
        acc.setId("1910");
        acc.setName("Kassa");
        acc.setType(AccountTypeValue.ASSET);
        doc.setAccounts(List.of(acc));

        return doc;
    }

    private JournalEntry createJournalEntry(BigInteger id, String dateStr) {
        JournalEntry entry = new JournalEntry();
        entry.setId(id);
        entry.setJournalDate(LocalDate.parse(dateStr));
        EntryInfo info = new EntryInfo();
        info.setDate(LocalDate.parse(dateStr));
        info.setBy("Test");
        entry.setEntryInfo(info);
        return entry;
    }

    @Test
    void unsignedFullDocumentRejectedByDefault() throws Exception {
        Sie5Document doc = new Sie5Document();
        FileInfo fileInfo = new FileInfo();
        SoftwareProduct sp = new SoftwareProduct();
        sp.setName("TestApp");
        sp.setVersion("2.0");
        fileInfo.setSoftwareProduct(sp);

        FileCreation fc = new FileCreation();
        fc.setTime(OffsetDateTime.of(2024, 6, 1, 12, 0, 0, 0, ZoneOffset.UTC));
        fc.setBy("Admin");
        fileInfo.setFileCreation(fc);

        Company company = new Company();
        company.setOrganizationId("556789-1234");
        company.setName("Demo AB");
        fileInfo.setCompany(company);
        fileInfo.setFiscalYears(List.of());
        fileInfo.setAccountingCurrency(new AccountingCurrency());
        fileInfo.getAccountingCurrency().setCurrency("SEK");
        doc.setFileInfo(fileInfo);
        doc.setAccounts(List.of());

        Sie5DocumentWriter unsignedWriter = new Sie5DocumentWriter();
        unsignedWriter.setRequireSignatureForFullDocuments(false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        unsignedWriter.write(doc, baos);

        assertThrows(alipsa.sieparser.SieException.class,
            () -> reader.readDocument(new ByteArrayInputStream(baos.toByteArray())));
    }
}
