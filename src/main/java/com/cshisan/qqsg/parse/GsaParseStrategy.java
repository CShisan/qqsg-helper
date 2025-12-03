package com.cshisan.qqsg.parse;

import com.cshisan.qqsg.file.*;
import com.cshisan.qqsg.parse.decode.DecodeStrategy;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cshisan
 */
public class GsaParseStrategy implements ParseStrategy {
    @Override
    public List<BufferedImage> parse(FileHeader header, byte[] data) {
        int frameSize = header.get(GsaHeaderMetaEnum.FRAME_SIZE.index);
        int pos = header.get(GsaHeaderMetaEnum.FIRST_FRAME_OFFSET.index);

        List<BufferedImage> frames = new ArrayList<>();
        for (int index = 0; index < frameSize; index++) {
            int[] meta = FrameMeta.read(data, pos, GsaBodyMetaEnum.values().length);
            int width = meta[GsaBodyMetaEnum.FRAME_WIDTH.index];
            int height = meta[GsaBodyMetaEnum.FRAME_HEIGHT.index];
            int decodeType = meta[GsaBodyMetaEnum.DECODE_TYPE.index];
            DecodeStrategy strategy = this.strategy(decodeType);
            pos += GsaBodyMetaEnum.BYTE_LEN;

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
