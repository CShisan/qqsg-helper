package com.cshisan.qqsg.parse.decode;

import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

/**
 * @author cshisan
 */
public interface DdsDecodeStrategy extends DecodeStrategy {
    /**
     * 解包565颜色格式
     *
     * @param c     color
     * @param r     red
     * @param g     greed
     * @param b     blue
     * @param index index
     */
    default void unpack565(int c, int[] r, int[] g, int[] b, int index) {
        int rv = (c >> 11) & 0x1F;
        int gv = (c >> 5) & 0x3F;
        int bv = c & 0x1F;

        r[index] = (rv * 255) / 31;
        g[index] = (gv * 255) / 63;
        b[index] = (bv * 255) / 31;
    }

    /**
     * 颜色插值
     *
     * @param r r
     * @param g g
     * @param b b
     */
    default void colorLerp(int[] r, int[] g, int[] b) {
        // 4-color mode
        r[2] = (2 * r[0] + r[1]) / 3;
        g[2] = (2 * g[0] + g[1]) / 3;
        b[2] = (2 * b[0] + b[1]) / 3;

        r[3] = (r[0] + 2 * r[1]) / 3;
        g[3] = (g[0] + 2 * g[1]) / 3;
        b[3] = (b[0] + 2 * b[1]) / 3;
    }

    /**
     * 计算块长度
     *
     * @param value value
     * @return len
     */
    default int blockLen(int value) {
        return (value + 3) / 4;
    }

    /**
     * 写入4x4像素块
     *
     * @param img       图像
     * @param bx        块X
     * @param by        块Y
     * @param width     宽度
     * @param height    高度
     * @param r         红色数组
     * @param g         绿色数组
     * @param b         蓝色数组
     * @param colorBits 颜色位
     * @param function  A计算函数
     */
    default void write44Pixels(
            BufferedImage img, int bx, int by, int width, int height,
            int[] r, int[] g, int[] b, int colorBits,
            BiFunction<Integer, Integer, Integer> function
    ) {
        for (int py = 0; py < 4; py++) {
            for (int px = 0; px < 4; px++) {
                int index = py * 4 + px;
                int ci = (colorBits >> (2 * index)) & 0x3;

                int x = bx * 4 + px;
                int y = by * 4 + py;
                if (x < width && y < height) {
                    int a = function.apply(index, ci);
                    int argb = (a << 24) | (r[ci] << 16) | (g[ci] << 8) | b[ci];
                    img.setRGB(x, y, argb);
                }
            }
        }
    }
}
