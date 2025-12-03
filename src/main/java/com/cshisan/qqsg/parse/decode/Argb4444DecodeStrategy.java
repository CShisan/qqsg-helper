package com.cshisan.qqsg.parse.decode;

import java.awt.image.BufferedImage;

/**
 * @author cshisan
 */
public class Argb4444DecodeStrategy implements DecodeStrategy {
    @Override
    public BufferedImage parse(byte[] data, int offset, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int byte1 = data[offset++] & 0xFF;
                int byte2 = data[offset++] & 0xFF;
                int value = byte2 << 8 | byte1;
                int argb = bit16toArgb(value);
                image.setRGB(x, y, argb);
            }
        }
        return image;
    }

    public static int bit16toArgb(int value) {
        return bit16toArgb(value, false);
    }

    public static int bit16toArgb(int value, boolean log) {
        int a = ((value >> 12) & 0xF) & 0xFF, aa = a << 4 | a;
        int r = ((value >> 8) & 0xF) & 0xFF, rr = r << 4 | r;
        int g = ((value >> 4) & 0xF) & 0xFF, gg = g << 4 | g;
        int b = (value & 0xF) & 0xFF, bb = b << 4 | b;
        if (log) {
            System.out.printf("a=%d, r=%d, g=%d, b=%d%n", aa, rr, gg, bb);
        }
        return (aa << 24) | (rr << 16) | (gg << 8) | bb;
    }

    @Override
    public DecodeTypeEnum type() {
        return DecodeTypeEnum.ARGB_4444;
    }
}
