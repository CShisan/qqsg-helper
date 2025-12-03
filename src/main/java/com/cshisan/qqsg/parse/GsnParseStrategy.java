package com.cshisan.qqsg.parse;

import com.cshisan.qqsg.file.*;
import com.cshisan.qqsg.parse.decode.DecodeStrategy;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cshisan
 */
public class GsnParseStrategy implements ParseStrategy {
    @Override
    public List<BufferedImage> parse(FileHeader info, byte[] data) {
        int frameSize = info.get(GsnHeaderMetaEnum.FRAME_SIZE.index);
        int pos = info.headerLen;

        List<BufferedImage> frames = new ArrayList<>();
        for (int index = 0; index < frameSize; index++) {
            int[] metaData = FrameMeta.read(data, pos, GsnBodyMetaEnum.LENGTH);
            int width = metaData[GsnBodyMetaEnum.FRAME_WIDTH.index];
            int height = metaData[GsnBodyMetaEnum.FRAME_HEIGHT.index];
            int decodeType = metaData[GsoBodyMetaEnum.DECODE_TYPE.index];
            DecodeStrategy strategy = this.strategy(decodeType);
            pos += GsnBodyMetaEnum.BYTE_LEN;

            frames.add(strategy.decode(data, pos, width, height));
            pos += this.calcFrameOffset(strategy.type(), width, height);
        }

        return frames;
    }

    @Override
    public boolean support(FileTypeEnum type) {
        return FileTypeEnum.GSN.equals(type);
    }
}
