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

import java.util.HashMap;

/**
 * Represents company information in a SIE document.
 * Aggregates data from multiple SIE fields: #FNAMN, #FNR, #ORGNR, #FTYP, #BKOD, and #ADRESS.
 */
public class SieCompany {
    /**
     * The organisation type names as set by Bolagsverket
     */
    private HashMap<String, String> organisationTypeNames;

    private int sni;
    private String name;
    private String code;
    private String orgType;
    private String orgIdentifier;
    private String contact;
    private String street;
    private String zipCity;
    private String phone;

    /**
     * Creates a new SieCompany and initializes the organisation type name lookup.
     */
    public SieCompany() {
        organisationTypeNames = new HashMap<>();
        loadOrgTypeNames();
    }

    /**
     * Returns the SNI code (#BKOD) indicating the company's branch affiliation.
     *
     * @return the SNI code
     */
    public int getSni() {
        return sni;
    }

    /**
     * Sets the SNI code (#BKOD).
     *
     * @param value the SNI code
     */
    public void setSni(int value) {
        sni = value;
    }

    /**
     * Returns the company name (#FNAMN).
     *
     * @return the company name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the company name (#FNAMN).
     *
     * @param value the company name
     */
    public void setName(String value) {
        name = value;
    }

    /**
     * Returns the accounting system's internal code for the company (#FNR).
     *
     * @return the company code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the accounting system's internal code for the company (#FNR).
     *
     * @param value the company code
     */
    public void setCode(String value) {
        code = value;
    }

    /**
     * Returns the organisation type (#FTYP), e.g. "AB", "HB", "EK".
     *
     * @return the organisation type code
     */
    public String getOrgType() {
        return orgType;
    }

    /**
     * Sets the organisation type (#FTYP).
     *
     * @param value the organisation type code
     */
    public void setOrgType(String value) {
        orgType = value;
    }

    /**
     * Returns the organisation number (#ORGNR).
     *
     * @return the organisation number
     */
    public String getOrgIdentifier() {
        return orgIdentifier;
    }

    /**
     * Sets the organisation number (#ORGNR).
     *
     * @param value the organisation number
     */
    public void setOrgIdentifier(String value) {
        orgIdentifier = value;
    }

    /**
     * Returns the contact person from the address record (#ADRESS).
     *
     * @return the contact person
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the contact person from the address record (#ADRESS).
     *
     * @param value the contact person
     */
    public void setContact(String value) {
        contact = value;
    }

    /**
     * Returns the street address from the address record (#ADRESS).
     *
     * @return the street address
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street address from the address record (#ADRESS).
     *
     * @param value the street address
     */
    public void setStreet(String value) {
        street = value;
    }

    /**
     * Returns the zip code and city from the address record (#ADRESS).
     *
     * @return the zip code and city
     */
    public String getZipCity() {
        return zipCity;
    }

    /**
     * Sets the zip code and city from the address record (#ADRESS).
     *
     * @param value the zip code and city
     */
    public void setZipCity(String value) {
        zipCity = value;
    }

    /**
     * Returns the phone number from the address record (#ADRESS).
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number from the address record (#ADRESS).
     *
     * @param value the phone number
     */
    public void setPhone(String value) {
        phone = value;
    }

    private void loadOrgTypeNames() {
        organisationTypeNames.put("AB", "Aktiebolag.");
        organisationTypeNames.put("E", "Enskild näringsidkare.");
        organisationTypeNames.put("HB", "Handelsbolag.");
        organisationTypeNames.put("KB", "Kommanditbolag.");
        organisationTypeNames.put("EK", "Ekonomisk förening.");
        organisationTypeNames.put("KHF", "Kooperativ hyresrättsförening.");
        organisationTypeNames.put("BRF", "Bostadsrättsförening.");
        organisationTypeNames.put("BF", "Bostadsförening.");
        organisationTypeNames.put("SF", "Sambruksförening.");
        organisationTypeNames.put("I", "Ideell förening som bedriver näring.");
        organisationTypeNames.put("S", "Stiftelse som bedriver näring.");
        organisationTypeNames.put("FL", "Filial till utländskt bolag.");
        organisationTypeNames.put("BAB", "Bankaktiebolag.");
        organisationTypeNames.put("MB", "Medlemsbank.");
        organisationTypeNames.put("SB", "Sparbank.");
        organisationTypeNames.put("BFL", "Utländsk banks filial.");
        organisationTypeNames.put("FAB", "Försäkringsaktiebolag.");
        organisationTypeNames.put("OFB", "Ömsesidigt försäkringsbolag.");
        organisationTypeNames.put("SE", "Europabolag.");
        organisationTypeNames.put("SCE", "Europakooperativ.");
        organisationTypeNames.put("TSF", "Trossamfund.");
        organisationTypeNames.put("X", "Annan företagsform.");
    }

}
