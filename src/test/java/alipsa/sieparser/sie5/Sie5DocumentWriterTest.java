package alipsa.sieparser.sie5;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Sie5DocumentWriterTest {

    private final Sie5DocumentReader reader = new Sie5DocumentReader();

    @Test
    void writeProgrammaticEntryDocument() throws Exception {
        Sie5DocumentWriter writer = new Sie5DocumentWriter();
        Sie5Entry entry = new Sie5Entry();

        // FileInfo
        FileInfoEntry fileInfo = new FileInfoEntry();
        SoftwareProduct sp = new SoftwareProduct();
        sp.setName("TestApp");
        sp.setVersion("1.0");
        fileInfo.setSoftwareProduct(sp);

        FileCreation fc = new FileCreation();
        fc.setTime(OffsetDateTime.of(2024, 1, 15, 10, 30, 0, 0, ZoneOffset.UTC));
        fc.setBy("TestUser");
        fileInfo.setFileCreation(fc);

        CompanyEntry company = new CompanyEntry();
        company.setOrganizationId("123456-7890");
        company.setName("Test AB");
        fileInfo.setCompany(company);

        entry.setFileInfo(fileInfo);

        // Accounts
        AccountEntry acc1 = new AccountEntry();
        acc1.setId("1910");
        acc1.setName("Kassa");
        acc1.setType(AccountTypeValue.ASSET);

        AccountEntry acc2 = new AccountEntry();
        acc2.setId("3010");
        acc2.setName("Försäljning");
        acc2.setType(AccountTypeValue.INCOME);

        entry.setAccounts(List.of(acc1, acc2));

        // Write
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writer.writeEntry(entry, baos);
        String xml = baos.toString("UTF-8");

        // Verify XML contains expected content
        assertTrue(xml.contains("SieEntry"), "Should contain SieEntry root element");
        assertTrue(xml.contains("TestApp"), "Should contain software name");
        assertTrue(xml.contains("123456-7890"), "Should contain organization ID");
        assertTrue(xml.contains("1910"), "Should contain account ID");
        assertTrue(xml.contains("Försäljning"), "Should contain account name");

        // Read back and verify
        Sie5Entry readBack = reader.readEntry(new ByteArrayInputStream(baos.toByteArray()));
        assertEquals("TestApp", readBack.getFileInfo().getSoftwareProduct().getName());
        assertEquals("123456-7890", readBack.getFileInfo().getCompany().getOrganizationId());
        assertEquals(2, readBack.getAccounts().size());
    }

    @Test
    void writeProgrammaticFullDocument() throws Exception {
        Sie5DocumentWriter writer = new Sie5DocumentWriter(TestSigningCredentials.create());
        Sie5Document doc = new Sie5Document();

        // FileInfo
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
        company.setClientId("42");
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

        // Accounts
        Account acc = new Account();
        acc.setId("1910");
        acc.setName("Kassa");
        acc.setType(AccountTypeValue.ASSET);
        doc.setAccounts(List.of(acc));

        // Write and read back
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writer.write(doc, baos);
        String xml = baos.toString("UTF-8");

        assertTrue(xml.contains("<Sie"), "Should contain Sie root element");
        assertTrue(xml.contains("sie.se/sie5"), "Should contain SIE 5 namespace");
        assertTrue(xml.contains("Signature"), "Full document should be digitally signed");

        Sie5Document readBack = reader.readDocument(new ByteArrayInputStream(baos.toByteArray()));
        assertEquals("Demo AB", readBack.getFileInfo().getCompany().getName());
        assertEquals("SEK", readBack.getFileInfo().getAccountingCurrency().getCurrency());
        assertEquals(1, readBack.getFileInfo().getFiscalYears().size());
        assertEquals(1, readBack.getAccounts().size());
        assertEquals("1910", readBack.getAccounts().get(0).getId());
    }

    @Test
    void unsignedFullDocumentFailsWhenSignatureRequired() {
        Sie5DocumentWriter writer = new Sie5DocumentWriter();
        Sie5Document doc = new Sie5Document();
        doc.setFileInfo(new FileInfo());
        doc.setAccounts(List.of());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        assertThrows(alipsa.sieparser.SieException.class, () -> writer.write(doc, baos));
    }
}
