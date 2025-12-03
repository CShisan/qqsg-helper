package com.cshisan.qqsg.file;

import com.cshisan.qqsg.common.Constant;

/**
 * @author cshisan
 */
public enum GsaBodyMetaEnum {
    /**
     * 内容元数据
     */
    FRAME_WIDTH("帧宽度", "帧宽度?(待定)"),
    FRAME_HEIGHT("帧高度", "帧高度?(待定)"),
    DELAY_MS("延迟时间(MS)", "每帧间隔时间"),
    DECODE_TYPE("解码类型", "解码类型,参考DecodeTypeEnum"),
    ;
    /**
     * int数组索引、偏移字节数、名称、描述
     */
    public static final int LENGTH = GsaBodyMetaEnum.values().length;
    public static final int BYTE_LEN = LENGTH * Constant.BYTE_EACH_INT;
    public final int index;
    public final int offset;
    public final String name;
    public final String desc;

    GsaBodyMetaEnum(String name, String desc) {
        this.index = this.ordinal();
        this.offset = index * 4;
        this.name = name;
        this.desc = desc;
    }
}
