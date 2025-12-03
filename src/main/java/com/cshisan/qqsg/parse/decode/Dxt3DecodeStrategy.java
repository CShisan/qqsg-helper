package com.cshisan.qqsg.parse.decode;

/**
 * @author cshisan
 */
public class Dxt3DecodeStrategy extends BaseDdsDecodeStrategy {
    private static final int ALPHA_BYTE = 8;

    @Override
    protected int calcA(byte[] data, int rawPos, int c0, int c1, int index, int ci) {
        long alpha64 = 0L;
        for (int i = 0; i < ALPHA_BYTE; i++) {
            alpha64 |= ((long) (data[rawPos + i] & 0xFF)) << (8 * i);
        }
        int a4 = (int) ((alpha64 >> (index * 4)) & 0xF);
        // expand 4bit->8bit (x17)
        return (a4 << 4) | a4;
    }

    @Override
    protected void process(int c0, int c1, int[] r, int[] g, int[] b) {
        this.colorLerp(r, g, b);
    }

    @Override
    protected int bytePerBlock() {
        return 16;
    }

    @Override
    protected int skip() {
        return 8;
    }

    @Override
    public DecodeTypeEnum type() {
        return DecodeTypeEnum.DXT3;
    }
}
