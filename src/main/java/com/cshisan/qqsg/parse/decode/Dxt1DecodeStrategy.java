package com.cshisan.qqsg.parse.decode;

/**
 * @author cshisan
 */
public class Dxt1DecodeStrategy extends BaseDdsDecodeStrategy {

    @Override
    protected void process(int c0, int c1, int[] r, int[] g, int[] b) {
        if (c0 > c1) {
            // 4-color mode
            this.colorLerp(r, g, b);
        } else {
            // 3-color + transparent
            r[2] = (r[0] + r[1]) / 2;
            g[2] = (g[0] + g[1]) / 2;
            b[2] = (b[0] + b[1]) / 2;
            // transparent
            r[3] = g[3] = b[3] = 0;
        }
    }

    @Override
    protected int bytePerBlock() {
        return 8;
    }

    @Override
    protected int calcA(byte[] data, int rawPos, int c0, int c1, int index, int ci) {
        return c0 <= c1 && ci == 3 ? 0 : 255;
    }

    @Override
    public DecodeTypeEnum type() {
        return DecodeTypeEnum.DXT1;
    }
}
