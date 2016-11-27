/*
MIT License

Copyright (c) 2015 Johan Idstam
Modifications by Per Nyfelt Copyright (c) 2016 Alipsa HB

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package alipsa.sieparser;

import java.util.ArrayList;
import java.util.List;

public class SieDocumentComparer {
    private SieDocument docA;
    private SieDocument docB;
    private List<String> errors;

    private SieDocumentComparer(SieDocument docA, SieDocument docB) throws Exception {
        this.docA = docA;
        this.docB = docB;
        errors = new ArrayList<>();
    }

    public static List<String> compare(SieDocument docA, SieDocument docB) throws Exception {
        SieDocumentComparer comp = new SieDocumentComparer(docA, docB);
        comp.doCompare();
        return comp.errors;
    }

    private void doCompare() throws Exception {
        compareNonListItems();
        compareDIM(docA, docB, "First", "Second");
        compareDIM(docB, docA, "Second", "First");
        comparePeriodValueList(docA.getIB(), docB.getIB(), "IB", "First", "Second");
        comparePeriodValueList(docB.getIB(), docA.getIB(), "IB", "Second", "First");
        comparePeriodValueList(docA.getUB(), docB.getUB(), "UB", "First", "Second");
        comparePeriodValueList(docB.getUB(), docA.getUB(), "UB", "Second", "First");
        comparePeriodValueList(docA.getOIB(), docB.getOIB(), "OIB", "First", "Second");
        comparePeriodValueList(docB.getOIB(), docA.getOIB(), "OIB", "Second", "First");
        comparePeriodValueList(docA.getOUB(), docB.getOUB(), "OUB", "First", "Second");
        comparePeriodValueList(docB.getOUB(), docA.getOUB(), "OUB", "Second", "First");
        comparePeriodValueList(docA.getPBUDGET(), docB.getPBUDGET(), "PBUDGET", "First", "Second");
        comparePeriodValueList(docB.getPBUDGET(), docA.getPBUDGET(), "PBUDGET", "Second", "First");
        comparePeriodValueList(docA.getPSALDO(), docB.getPSALDO(), "PSALDO", "First", "Second");
        comparePeriodValueList(docB.getPSALDO(), docA.getPSALDO(), "PSALDO", "Second", "First");
        comparePeriodValueList(docA.getRES(), docB.getRES(), "RES", "First", "Second");
        comparePeriodValueList(docB.getRES(), docA.getRES(), "RES", "Second", "First");
        compareKONTO(docA, docB, "First", "Second");
        compareKONTO(docB, docA, "Second", "First");
        compareRAR(docA, docB, "First", "Second");
        compareRAR(docB, docA, "Second", "First");
        compareVER(docA, docB, "First", "Second");
        compareVER(docB, docA, "Second", "First");
    }

    private void compareNonListItems() throws Exception {
        if (docA.getFLAGGA() != docB.getFLAGGA())
            errors.add("FLAGGA differs First, Second: '" + docA.getFLAGGA() + "' , '" + docB.getFLAGGA()+"'");

        if (!docA.getFORMAT().equals(docB.getFORMAT()))
            errors.add("FORMAT differs First, Second: '" + docA.getFORMAT() + "' , '" + docB.getFORMAT()+"'");

        compareFNAMN();
        if (!docA.getFORMAT().equals(docB.getFORMAT()))
            errors.add("FORMAT differs First, Second: '" + docA.getFORMAT() + "' , '" + docB.getFORMAT()+"'");

        if (docA.getGEN_DATE() == null && docB.getGEN_DATE() != null && docA.getGEN_DATE().equals(docB.getGEN_DATE())) {
            errors.add("GEN_DATE differs");
        }

        if (docA.getOMFATTN() == null && docB.getOMFATTN() != null && docA.getOMFATTN().equals(docB.getOMFATTN())) {
            errors.add("OMFATTN differs");
        }

        if (!docA.getGEN_NAMN().equals(docB.getGEN_NAMN()))
            errors.add("GEN_NAMN differs First, Second: '" + docA.getGEN_NAMN() + "' , '" + docB.getGEN_NAMN() + "'");

        if (docA.getKPTYP() != null && !docA.getKPTYP().equals(docB.getKPTYP()))
            errors.add("KPTYP differs First, Second: '" + docA.getKPTYP() + "' , '" + docB.getKPTYP() + "'");

        if (docA.getKSUMMA() != docB.getKSUMMA()) errors.add("KSUMMA differs First, Second: '" + docA.getKSUMMA() + "', '" + docB.getKSUMMA());

        String a = docA.getPROSA() != null ? docA.getPROSA() : "";
        String b = docB.getPROSA() != null ? docB.getPROSA() : "";
        if (!a.equals(b))
            errors.add("PROSA differs First, Second: '" + a + "' , '" + b+"'");

        if (docA.getSIETYP() != docB.getSIETYP())
            errors.add("SIETYP differs First, Second: '" + docA.getSIETYP() + "' , '" + docB.getSIETYP()+"'");

        if (docA.getTAXAR() != docB.getTAXAR())
            errors.add("TAXAR differs First, Second: '" + docA.getTAXAR() + "' , '" + docB.getTAXAR()+"'");

        if (!docA.getVALUTA().equals(docB.getVALUTA()))
            errors.add("VALUTA differs First, Second: '" + docA.getVALUTA() + "' , '" + docB.getVALUTA() + "'");

    }

    private void comparePeriodValueList(List<SiePeriodValue> listA, List<SiePeriodValue> listB, String listName, String nameA, String nameB) throws Exception {
        for (SiePeriodValue pA : listA) {
            boolean foundIt = false;
            for (SiePeriodValue pB : listB) {
                if (periodValueComparer(pA, pB)) {
                    foundIt = true;
                    break;
                }

            }
            if (!foundIt) {
                errors.add(listName + " differs account, YearNo, period not found or different in " + nameB + ": " +
                        "Account=" + pA.getAccount().getNumber() + ", Year=" + pA.getYearNr() + ", Period=" +
                        pA.getPeriod());
            }

        }
    }

    private boolean periodValueComparer(SiePeriodValue a, SiePeriodValue b) throws Exception {
        if (!a.getAccount().getNumber().equals(b.getAccount().getNumber()))
            return false;

        if (a.getAmount().compareTo(b.getAmount()) != 0)
            return false;

        if (a.getQuantity().compareTo(b.getQuantity()) != 0)
            return false;

        if (a.getPeriod() != b.getPeriod())
            return false;

        if (!a.getToken().equals(b.getToken()))
            return false;

        if (a.getYearNr() != b.getYearNr())
            return false;

        if (!compareObjects(a.getObjects(), b.getObjects()))
            return false;

        return true;
    }

    private boolean compareObjects(List<SieObject> a, List<SieObject> b) throws Exception {
        if (a != null && b == null)
            return false;

        if (b != null && a == null)
            return false;

        if (a != null) {
            if (a.size() != b.size())
                return false;

            for (int i = 0; i < a.size(); i++) {
                if (!a.get(i).getDimension().getNumber().equals(b.get(i).getDimension().getNumber()))
                    return false;

                if (!a.get(i).getName().equals(b.get(i).getName()))
                    return false;

                if (!a.get(i).getNumber().equals(b.get(i).getNumber()))
                    return false;

            }
        }

        return true;
    }

    private void compareFNAMN() throws Exception {

        if (docA.getFNAMN() != null && docB.getFNAMN() != null) {
            if (!StringUtil.equals(docA.getFNAMN().getCode(),docB.getFNAMN().getCode()))
                errors.add("FNAMN.Code differs First, Second: '" + docA.getFNAMN().getCode() + "' , '" + docB.getFNAMN()
                        .getCode() + "'");

            if (!StringUtil.equals(docA.getFNAMN().getContact(),docB.getFNAMN().getContact()))
                errors.add("ADRESS.Contact differs First, Second: '" + docA.getFNAMN().getContact() + "' , '" + docB
                        .getFNAMN().getContact()+"'");

            if (!StringUtil.equals(docA.getFNAMN().getName(),docB.getFNAMN().getName()))
                errors.add("FNAMN.Name differs First, Second: '" + docA.getFNAMN().getName() + "' , '" + docB.getFNAMN()
                        .getName()+"'");

            if (!StringUtil.equals(docA.getFNAMN().getOrgIdentifier(),docB.getFNAMN().getOrgIdentifier()))
                errors.add("ORGNR.OrgIdentifier (#FNAMN) differs First, Second: '" + docA.getFNAMN().getOrgIdentifier()
                        + "', '" + docB.getFNAMN().getOrgIdentifier()+"'");

            if (!StringUtil.equals(docA.getFNAMN().getOrgType(),docB.getFNAMN().getOrgType()))
                errors.add("FTYP differs First, Second: '" + docA.getFNAMN().getOrgType() + "' , '" + docB.getFNAMN()
                        .getOrgType()+"'");

            if (!StringUtil.equals(docA.getFNAMN().getPhone(),docB.getFNAMN().getPhone()))
                errors.add("ADRESS.Phone differs First, Second: '" + docA.getFNAMN().getPhone() + "' , '" + docB
                        .getFNAMN().getPhone()+"'");

            if (docA.getFNAMN().getSni() != docB.getFNAMN().getSni())
                errors.add("FNAMN.SNI differs First, Second: '" + docA.getFNAMN().getSni() + "' , '" + docB.getFNAMN()
                        .getSni()+"'");

            if (!StringUtil.equals(docA.getFNAMN().getStreet(),docB.getFNAMN().getStreet()))
                errors.add("ADRESS.Street differs First, Second: '" + docA.getFNAMN().getStreet() + "' , '" + docB
                        .getFNAMN().getStreet()+"'");

            if (!StringUtil.equals(docA.getFNAMN().getZipCity(),docB.getFNAMN().getZipCity()))
                errors.add("ADRESS.ZipCity differs First, Second: '" + docA.getFNAMN().getZipCity() + "' , '" + docB
                        .getFNAMN().getZipCity()+"'");

        } else {
            errors.add("FNAMN differs.");
        }
    }

    private void compareKONTO(SieDocument docA, SieDocument docB, String nameA, String nameB) throws Exception {
        for (SieAccount kA : docA.getKONTO().values()) {
            if (docB.getKONTO().containsKey(kA.getNumber())) {
                SieAccount kB = docB.getKONTO().get(kA.getNumber());
                if (!kA.getName().equals(kB.getName()))
                    errors.add("KONTO.Name differ  " + kA.getNumber());

                if (!StringUtil.equals(kA.getType(), kB.getType()))
                    errors.add("KONTO.Type differ " + kA.getNumber());

                if (!StringUtil.equals(kA.getUnit(), kB.getUnit()))
                    errors.add("KONTO.Unit differ " + kA.getNumber());

                if (kA.getSRU().size() == kB.getSRU().size()) {
                    for (int i = 0; i < kA.getSRU().size(); i++) {
                        if (!kA.getSRU().get(i).equalsIgnoreCase(kB.getSRU().get(i))) {
                            errors.add("KONTO.SRU differ " + kA.getNumber());
                            break;
                        }

                    }
                } else {
                    errors.add("KONTO.SRU differ " + kA.getNumber());
                }
            } else {
                errors.add(nameB + " is missing KONTO: " + kA.getNumber());
            }
        }
    }

    private void compareDIM(SieDocument docA, SieDocument docB, String nameA, String nameB) throws Exception {
        for (String dimKey : docA.getDIM().keySet()) {
            if (docB.getDIM().containsKey(dimKey)) {
                if (!docA.getDIM().get(dimKey).getName().equals(docB.getDIM().get(dimKey).getName()))
                    errors.add("DIM " + dimKey + " Name differ " + nameA + "," + nameB + ":" + docA.getDIM().get(dimKey).getName() + " , " + docB.getDIM().get(dimKey).getName());

                if (!docA.getDIM().get(dimKey).getNumber().equals(docB.getDIM().get(dimKey).getNumber()))
                    errors.add("DIM " + dimKey + " Number differ " + nameA + "," + nameB + ":" + docA.getDIM().get(dimKey).getNumber() + " , " + docB.getDIM().get(dimKey).getNumber());

                if (docA.getDIM().get(dimKey).getSuperDim() != null && docB.getDIM().get(dimKey).getSuperDim() == null)
                    errors.add("DIM " + dimKey + " SuberDim differ " + nameA + " has DIM ," + nameB + " is NULL, ");

                if (docA.getDIM().get(dimKey).getSuperDim() != null && docB.getDIM().get(dimKey).getSuperDim() != null) {
                    if (!docA.getDIM().get(dimKey).getSuperDim().getName().equals(docB.getDIM().get(dimKey).getSuperDim().getName()))
                        errors.add("DIM " + dimKey + " SuperDim.Name differ " + nameA + "," + nameB + ":" + docA.getDIM().get(dimKey).getSuperDim().getName() + " , " + docB.getDIM().get(dimKey).getSuperDim().getName());

                    if (!docA.getDIM().get(dimKey).getSuperDim().getNumber().equals(docB.getDIM().get(dimKey).getSuperDim().getNumber()))
                        errors.add("DIM " + dimKey + " SuperDim.Number differ " + nameA + "," + nameB + ":" + docA.getDIM().get(dimKey).getSuperDim().getNumber() + " , " + docB.getDIM().get(dimKey).getSuperDim().getNumber());

                }

            } else {
                errors.add(nameB + " DIM is missing " + dimKey);
            }
        }
    }

    private void compareRAR(SieDocument docA, SieDocument docB, String nameA, String nameB) throws Exception {
        for (SieBookingYear rarA : docA.getRars().values()) {
            if (docB.getRars().containsKey(rarA.getId())) {
                SieBookingYear rarB = docB.getRars().get(rarA.getId());
                if (rarA.getStart() != null && !rarA.getStart().equals(rarB.getStart()))
                    errors.add(nameB + " RAR differs: id=" + rarA.getId() + " " + nameA + ".start='"+rarA.getStart() +
                            "' " + nameB + ".start='"+rarB.getStart()+"'");

                if (rarA.getEnd() != null && !rarA.getEnd().equals(rarB.getEnd()))
                    errors.add(nameB + " RAR differs " + rarA.getId() + " " + nameA + ".end='"+rarA.getEnd() +
                            "' " + nameB + ".end='"+rarB.getEnd()+"'");

            } else {
                errors.add(nameB + " RAR is missing " + rarA.getId());
            }
        }
    }

    private void compareVER(SieDocument docA, SieDocument docB, String nameA, String nameB) throws Exception {
        for (SieVoucher vA : docA.getVER()) {
            boolean foundIt = false;
            for (SieVoucher vB : docB.getVER()) {
                if (voucherComparer(vA, vB)) {
                    foundIt = true;
                    break;
                }

            }
            if (!foundIt) {
                errors.add("Vouchers differs Series, Number not found or different in " + nameB + ": " + vA.getSeries() + ", " + vA.getNumber());
            }

        }
    }

    private boolean voucherComparer(SieVoucher vA, SieVoucher vB) throws Exception {
        if (!vA.getNumber().equals(vB.getNumber()))
            return false;

        if (!vA.getSeries().equals(vB.getSeries()))
            return false;

        if (!vA.getText().equals(vB.getText()))
            return false;

        if (!vA.getToken().equals(vB.getToken()))
            return false;

        if (!vA.getVoucherDate().equals(vB.getVoucherDate()))
            return false;

        if (vA.getRows().size() != vB.getRows().size()) {
            return false;
        } else {
            for (SieVoucherRow rA : vA.getRows()) {
                boolean foundIt = true;
                for (SieVoucherRow rB : vB.getRows()) {
                    if (!rA.getAccount().getNumber().equals(rB.getAccount().getNumber()))
                        foundIt = false;

                    if (rA.getAmount() != rB.getAmount())
                        foundIt = false;

                    if (!rA.getCreatedBy().equals(rB.getCreatedBy()))
                        foundIt = false;

                    if (!rA.getRowDate().equals(rB.getRowDate()))
                        foundIt = false;

                    if (!(rA.quantity == null || rA.quantity.equals(rB.quantity)))
                        foundIt = false;

                    if (!compareObjects(rA.getObjects(), rB.getObjects()))
                        foundIt = false;

                    if (foundIt)
                        break;

                }
                if (foundIt) {
                    return true;
                }

            }
        }
        return true;
    }

}


