package com.cshisan.qqsg.parse.decode;

import java.awt.image.BufferedImage;

/**
 * @author cshisan
 */
public interface DecodeStrategy {
    /**
     * 解析图片数据
     *
     * @param data   文件数据
     * @param offset 数据偏移
     * @param width  图片宽度
     * @param height 图片高度
     * @return image
     */
    default BufferedImage decode(byte[] data, int offset, int width, int height) {
        if (width == 0 && height == 0) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        } else if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be >= 0");
        }
        return parse(data, offset, width, height);
    }

    /**
     * 解析图片数据
     *
     * @param data   文件数据
     * @param offset 数据偏移
     * @param width  图片宽度
     * @param height 图片高度
     * @return image
     */
    BufferedImage parse(byte[] data, int offset, int width, int height);

    /**
     * 解码类型
     *
     * @return type
     */
    DecodeTypeEnum type();
}
