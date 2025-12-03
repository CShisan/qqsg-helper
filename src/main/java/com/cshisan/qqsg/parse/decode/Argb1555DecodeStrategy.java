package com.cshisan.qqsg.parse.decode;

import java.awt.image.BufferedImage;

/**
 * @author cshisan
 */
public class Argb1555DecodeStrategy implements DecodeStrategy {
    @Override
    public BufferedImage parse(byte[] data, int offset, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int lo = data[offset++] & 0xFF;
                int hi = data[offset++] & 0xFF;
                int v = lo | (hi << 8);

                int a = ((v & 0x8000) != 0) ? 255 : 0;
                int r5 = (v >> 10) & 0x1F;
                int g5 = (v >> 5) & 0x1F;
                int b5 = v & 0x1F;

                int r = (r5 * 255) / 31;
                int g = (g5 * 255) / 31;
                int b = (b5 * 255) / 31;

                int argb = (a << 24) | (r << 16) | (g << 8) | b;
                img.setRGB(x, y, argb);
            }
        }
        return img;
    }

    @Override
    public DecodeTypeEnum type() {
        return DecodeTypeEnum.ARGB_1555;
    }
}
