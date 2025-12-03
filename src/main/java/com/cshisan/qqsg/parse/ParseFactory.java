package com.cshisan.qqsg.parse;

import com.cshisan.qqsg.file.FileTypeEnum;

import java.util.Map;

/**
 * @author cshisan
 */
public class ParseFactory {
    public static final Map<FileTypeEnum, ParseStrategy> STRATEGY_MAP = Map.of(
            FileTypeEnum.GSO, new GsoParseStrategy(),
            FileTypeEnum.GSN, new GsnParseStrategy(),
            FileTypeEnum.GSA, new GsaParseStrategy()
    );

    public static ParseStrategy typeOf(FileTypeEnum type) {
        return STRATEGY_MAP.get(type);
    }
}
