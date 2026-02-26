package alipsa.sieparser;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SieDocumentComparer.
 */
public class SieDocumentComparerTest {

    @Test
    public void identicalDocumentsNoDiffs() {
        SieDocument a = createDoc();
        SieDocument b = createDoc();
        List<String> diffs = SieDocumentComparer.compare(a, b);
        assertTrue(diffs.isEmpty(), "Identical documents should have no diffs: " + diffs);
    }

    @Test
    public void differentFlagga() {
        SieDocument a = createDoc();
        SieDocument b = createDoc();
        b.setFLAGGA(1);
        List<String> diffs = SieDocumentComparer.compare(a, b);
        assertTrue(diffs.stream().anyMatch(d -> d.contains("FLAGGA")));
    }

    @Test
    public void differentSieTyp() {
        SieDocument a = createDoc();
        SieDocument b = createDoc();
        b.setSIETYP(2);
        List<String> diffs = SieDocumentComparer.compare(a, b);
        assertTrue(diffs.stream().anyMatch(d -> d.contains("SIETYP")));
    }

    @Test
    public void differentCompanyName() {
        SieDocument a = createDoc();
        SieDocument b = createDoc();
        b.getFNAMN().setName("Other Corp");
        List<String> diffs = SieDocumentComparer.compare(a, b);
        assertTrue(diffs.stream().anyMatch(d -> d.contains("FNAMN")));
    }

    @Test
    public void missingAccount() {
        SieDocument a = createDoc();
        SieDocument b = createDoc();
        a.getKONTO().put("1910", new SieAccount("1910", "Kassa"));
        List<String> diffs = SieDocumentComparer.compare(a, b);
        assertTrue(diffs.stream().anyMatch(d -> d.contains("KONTO") && d.contains("1910")));
    }

    @Test
    public void differentProgramEntries() {
        SieDocument a = createDoc();
        SieDocument b = createDoc();
        a.setPROGRAM(List.of("App1", "1.0"));
        b.setPROGRAM(List.of("App2", "1.0"));
        List<String> diffs = SieDocumentComparer.compare(a, b);
        assertTrue(diffs.stream().anyMatch(d -> d.contains("PROGRAM")));
    }

    @Test
    public void voucherRowMismatch() {
        SieDocument a = createDoc();
        SieDocument b = createDoc();

        SieVoucher vA = createVoucher("A", "1", "100");
        SieVoucher vB = createVoucher("A", "1", "200");

        a.getVER().add(vA);
        b.getVER().add(vB);

        List<String> diffs = SieDocumentComparer.compare(a, b);
        assertTrue(diffs.stream().anyMatch(d -> d.contains("Vouchers")));
    }

    @Test
    public void nullGenDateHandled() {
        SieDocument a = createDoc();
        SieDocument b = createDoc();
        a.setGEN_DATE(null);
        b.setGEN_DATE(LocalDate.of(2024, 1, 1));
        List<String> diffs = SieDocumentComparer.compare(a, b);
        assertTrue(diffs.stream().anyMatch(d -> d.contains("GEN_DATE")));
    }

    @Test
    public void nullOmfattnHandled() {
        SieDocument a = createDoc();
        SieDocument b = createDoc();
        a.setOMFATTN(LocalDate.of(2024, 6, 30));
        b.setOMFATTN(null);
        List<String> diffs = SieDocumentComparer.compare(a, b);
        assertTrue(diffs.stream().anyMatch(d -> d.contains("OMFATTN")));
    }

    private SieDocument createDoc() {
        SieDocument doc = new SieDocument();
        doc.setFLAGGA(0);
        doc.setSIETYP(4);
        doc.setFORMAT("PC8");
        doc.setGEN_DATE(LocalDate.of(2024, 1, 1));
        doc.setGEN_NAMN("test");
        doc.getFNAMN().setName("Test Company");
        doc.getFNAMN().setOrgIdentifier("556677-8899");
        doc.setPROGRAM(List.of("TestApp", "1.0"));
        return doc;
    }

    private SieVoucher createVoucher(String series, String number, String amount) {
        SieVoucher v = new SieVoucher();
        v.setSeries(series);
        v.setNumber(number);
        v.setVoucherDate(LocalDate.of(2024, 1, 15));
        v.setText("Test");
        v.setToken("#VER");

        SieVoucherRow row = new SieVoucherRow();
        row.setAccount(new SieAccount("1910", "Kassa"));
        row.setAmount(new BigDecimal(amount));
        row.setRowDate(LocalDate.of(2024, 1, 15));
        row.setText("Row");
        row.setCreatedBy("");
        row.setToken("#TRANS");
        v.getRows().add(row);

        return v;
    }
}
