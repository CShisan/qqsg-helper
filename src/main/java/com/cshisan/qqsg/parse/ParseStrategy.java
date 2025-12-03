package com.cshisan.qqsg.parse;

import com.cshisan.qqsg.file.FileHeader;
import com.cshisan.qqsg.file.FileTypeEnum;
import com.cshisan.qqsg.parse.decode.DecodeFactory;
import com.cshisan.qqsg.parse.decode.DecodeStrategy;
import com.cshisan.qqsg.parse.decode.DecodeTypeEnum;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author cshisan
 */
public interface ParseStrategy {
    /**
     * 解析图片数据
     *
     * @param info 文件信息
     * @param data 文件数据
     * @return image[]
     */
    List<BufferedImage> parse(FileHeader info, byte[] data);

    /**
     * 获取解码策略
     *
     * @param type 解码类型
     * @return strategy
     */
    default DecodeStrategy strategy(int type) {
        DecodeStrategy strategy = DecodeFactory.typeOf(type);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported decode type: " + type);
        }
        return strategy;
    }

    default int calcFrameOffset(DecodeTypeEnum type, int width, int height) {
        return switch (type) {
            case DecodeTypeEnum.ARGB_1555, DecodeTypeEnum.ARGB_4444 -> width * height * 2;
            case DecodeTypeEnum.ARGB_8888 -> width * height * 4;
            case DecodeTypeEnum.DXT1 -> ((width + 3) / 4) * ((height + 3) / 4) * 8;
            case DecodeTypeEnum.DXT3, DecodeTypeEnum.DXT5 -> ((width + 3) / 4) * ((height + 3) / 4) * 16;
            case null -> throw new IllegalArgumentException("Unsupported decode type: null");
        };
    }

    /**
     * 是否支持
     *
     * @param type 文件类型
     * @return boolean
     */
    boolean support(FileTypeEnum type);
}
