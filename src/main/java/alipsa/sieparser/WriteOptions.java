package alipsa.sieparser;

/**
 * Options for controlling how a SIE document is written.
 */
public class WriteOptions {
    private boolean writeKSUMMA = false;

    public boolean isWriteKSUMMA() {
        return writeKSUMMA;
    }

    public void setWriteKSUMMA(boolean writeKSUMMA) {
        this.writeKSUMMA = writeKSUMMA;
    }
}
