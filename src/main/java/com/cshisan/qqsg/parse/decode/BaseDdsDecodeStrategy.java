package com.cshisan.qqsg.parse.decode;

import java.awt.image.BufferedImage;

/**
 * @author cshisan
 */
public abstract class BaseDdsDecodeStrategy implements DdsDecodeStrategy {

    @Override
    public BufferedImage parse(byte[] data, int offset, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int blockW = this.blockLen(width), blockH = this.blockLen(height);

        for (int by = 0; by < blockH; by++) {
            for (int bx = 0; bx < blockW; bx++) {
                int rawPos = offset + (by * blockW + bx) * this.bytePerBlock();
                int[] r = new int[4], g = new int[4], b = new int[4];

                // 跳过指定偏移
                int pos = rawPos + this.skip();

                // color (8 bytes)
                int c0 = (data[pos++] & 0xFF) | ((data[pos++] & 0xFF) << 8);
                int c1 = (data[pos++] & 0xFF) | ((data[pos++] & 0xFF) << 8);

                int colorBits = (data[pos++] & 0xFF)
                        | ((data[pos++] & 0xFF) << 8)
                        | ((data[pos++] & 0xFF) << 16)
                        | ((data[pos] & 0xFF) << 24);

                // 处理c0,c1
                this.unpack565(c0, r, g, b, 0);
                this.unpack565(c1, r, g, b, 1);

                // 处理c2,c3
                this.process(c0, c1, r, g, b);

                // ===== write 4x4 pixels =====
                this.write44Pixels(
                        img, bx, by, width, height, r, g, b, colorBits,
                        (index, ci) -> this.calcA(data, rawPos, c0, c1, index, ci)
                );
            }
        }

        return img;
    }

    /**
     * 跳过的字节数
     *
     * @return int
     */
    protected int skip() {
        return 0;
    }

    /**
     * 写4*4像素块前处理
     *
     * @param c0 c0
     * @param c1 c1
     * @param r  r
     * @param g  g
     * @param b  b
     */
    protected abstract void process(int c0, int c1, int[] r, int[] g, int[] b);

    /**
     * 每个块的字节数
     *
     * @return int
     */
    protected abstract int bytePerBlock();

    /**
     * 计算A值
     *
     * @param data   data
     * @param rawPos 原始偏移
     * @param c0     c0
     * @param c1     c1
     * @param index  index
     * @param ci     ci
     * @return a
     */
    protected abstract int calcA(byte[] data, int rawPos, int c0, int c1, int index, int ci);

}
