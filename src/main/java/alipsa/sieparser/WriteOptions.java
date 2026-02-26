package alipsa.sieparser;

/**
 * Options for controlling how a SIE document is written.
 */
public class WriteOptions {
    private boolean writeKSUMMA = false;

    /** Creates a new instance. */
    public WriteOptions() {}

    /**
     * Returns whether the KSUMMA checksum should be written.
     * @return {@code true} if KSUMMA should be included in the output
     */
    public boolean isWriteKSUMMA() {
        return writeKSUMMA;
    }

    /**
     * Sets whether the KSUMMA checksum should be written.
     * @param writeKSUMMA {@code true} to include KSUMMA in the output
     */
    public void setWriteKSUMMA(boolean writeKSUMMA) {
        this.writeKSUMMA = writeKSUMMA;
    }
}
