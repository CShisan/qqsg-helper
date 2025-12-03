package com.cshisan.qqsg.parse.decode;

import java.util.Map;

/**
 * @author cshisan
 */
public class DecodeFactory {
    public static final Map<DecodeTypeEnum, DecodeStrategy> STRATEGY_MAP = Map.of(
            DecodeTypeEnum.ARGB_1555, new Argb1555DecodeStrategy(),
            DecodeTypeEnum.ARGB_4444, new Argb4444DecodeStrategy(),
            DecodeTypeEnum.ARGB_8888, new Argb8888DecodeStrategy(),
            DecodeTypeEnum.DXT1, new Dxt1DecodeStrategy(),
            DecodeTypeEnum.DXT3, new Dxt3DecodeStrategy(),
            DecodeTypeEnum.DXT5, new Dxt5DecodeStrategy()
    );

    public static DecodeStrategy typeOf(int value) {
        DecodeTypeEnum type = DecodeTypeEnum.typeOf(value);
        if (type == null) {
            throw new IllegalArgumentException("Unsupported decode type: " + value);
        }
        return DecodeFactory.typeOf(type);
    }

    public static DecodeStrategy typeOf(DecodeTypeEnum type) {
        if (type == null) {
            throw new IllegalArgumentException("Unsupported decode type: null");
        }
        return STRATEGY_MAP.get(type);
    }
}
