package com.cshisan.qqsg.parse.decode;

import java.awt.image.BufferedImage;

/**
 * @author cshisan
 */
public class Argb8888DecodeStrategy implements DecodeStrategy {
    @Override
    public BufferedImage parse(byte[] data, int offset, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int b = data[offset++] & 0xFF;
                int g = data[offset++] & 0xFF;
                int r = data[offset++] & 0xFF;
                int alpha = data[offset++] & 0xFF;
                int argb = (alpha << 24) | (r << 16) | (g << 8) | b;
                image.setRGB(x, y, argb);
            }
        }
        return image;
    }

    @Override
    public DecodeTypeEnum type() {
        return DecodeTypeEnum.ARGB_8888;
    }
}
