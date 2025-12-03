package com.cshisan.qqsg.parse.decode;

/**
 * @author cshisan
 */
public enum DecodeTypeEnum {
    /**
     * 解码类型
     */
    ARGB_1555(0),
    ARGB_4444(1),
    ARGB_8888(2),
    DXT1(3),
    DXT3(4),
    DXT5(5);

    public final int type;

    DecodeTypeEnum(int type) {
        this.type = type;
    }

    public static DecodeTypeEnum typeOf(int type) {
        for (DecodeTypeEnum decodeTypeEnum : values()) {
            if (decodeTypeEnum.type == type) {
                return decodeTypeEnum;
            }
        }
        return null;
    }
}
