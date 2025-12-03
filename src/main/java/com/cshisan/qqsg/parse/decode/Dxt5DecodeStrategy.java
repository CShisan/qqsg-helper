package com.cshisan.qqsg.parse.decode;

/**
 * @author cshisan
 */
public class Dxt5DecodeStrategy extends BaseDdsDecodeStrategy {
    private static final int ALPHA_PALETTE = 6;
    private static final int ALPHA_PALETTE_2 = 4;

    @Override
    protected int calcA(byte[] data, int rawPos, int c0, int c1, int index, int ci) {
        // 透明调色板
        int a0 = data[rawPos] & 0xFF;
        int a1 = data[rawPos + 1] & 0xFF;
        int[] alphaPalette = this.alphaPalette(a0, a1);

        long alphaBits = 0L;
        for (int i = 0; i < ALPHA_PALETTE; i++) {
            alphaBits |= ((long) (data[rawPos + 2 + i] & 0xFF)) << (8 * i);
        }

        int ai = (int) ((alphaBits >> (3 * index)) & 0x7);
        return alphaPalette[ai] & 0xFF;
    }

    private int[] alphaPalette(int a0, int a1) {
        int[] alphaPalette = new int[8];
        alphaPalette[0] = a0;
        alphaPalette[1] = a1;
        if (a0 > a1) {
            for (int i = 1; i <= ALPHA_PALETTE; i++) {
                alphaPalette[i + 1] = ((7 - i) * a0 + i * a1) / 7;
            }
        } else {
            for (int i = 1; i <= ALPHA_PALETTE_2; i++) {
                alphaPalette[i + 1] = ((5 - i) * a0 + i * a1) / 5;
            }
            alphaPalette[6] = 0;
            alphaPalette[7] = 255;
        }
        return alphaPalette;
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
        return DecodeTypeEnum.DXT5;
    }
}
