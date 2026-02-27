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

/**
 * Implementation of the CRC32 checksum algorithm as specified in the SIE file format (#KSUMMA).
 * Used to verify the integrity of SIE file data.
 */
public class SieCRC32 {
    private boolean started = false;
    private static long CRC32_POLYNOMIAL = 0xEDB88320;
    private long[] CRCTable = new long[256];
    private long crc;

    /** Creates a new CRC32 instance and initializes the CRC table. */
    public SieCRC32() {
        createCrcTable();
    }

    /**
     * Returns whether the CRC calculation has been started.
     * @return {@code true} if {@link #start()} has been called
     */
    public boolean isStarted() {
        return started;
    }

    private void createCrcTable() {
        int i;
        long j;
        long crc;
        for (i = 0; i <= 255; i++) {
            crc = i;
            for (j = 8; j > 0; j--) {
                if ((crc & 1) == 1) {
                    crc = (crc >> 1) ^ CRC32_POLYNOMIAL;
                } else {
                    crc >>= 1;
                }
            }
            CRCTable[i] = crc;
        }
    }

    /** Resets and starts the CRC calculation. */
    public void start() {
        crc = 0xFFFFFFFF;
        started = true;
    }

    /**
     * Adds the data from a SIE data item to the running CRC calculation.
     * @param item the data item to include in the checksum
     */
    public void addData(SieDataItem item) {
        crcAccumulate(Encoding.getBytes(item.getItemType()));
        for (String d : item.getData()) {
            String foo = d.replace("{", "").replace("}", "")
                          .replace("\"", "").replace(" ", "").replace("\t", "");
            crcAccumulate(Encoding.getBytes(foo));
        }
    }

    /**
     * Returns the computed CRC32 checksum.
     * @return the CRC32 checksum value
     */
    public long checksum() {
        return (~crc);
    }

    private void crcAccumulate(byte[] buffer) {
        long temp1;
        long temp2;
        for (byte p : buffer) {
            temp1 = (crc >> 8) & 0x00FFFFFF;
            temp2 = CRCTable[((int) crc ^ p) & 0xff];
            crc = temp1 ^ temp2;
        }
    }
}
