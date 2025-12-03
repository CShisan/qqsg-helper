package com.cshisan.qqsg.file;

import com.cshisan.qqsg.common.Constant;

/**
 * @author cshisan
 */
public enum GsnBodyMetaEnum {
    /**
     * 内容元数据
     */
    UNKNOWN_1("未知1", "未知1"),
    UNKNOWN_2("未知2", "未知2"),
    UNKNOWN_3("未知3", "未知3"),
    UNKNOWN_4("未知4", "未知4"),
    UNKNOWN_5("未知5", "未知5"),
    FRAME_WIDTH("帧宽度", "帧宽度?(待定)"),
    FRAME_HEIGHT("帧高度", "帧高度?(待定)"),
    LINE_BYTES("行字节数", "每行字节数?(待定),如果是宽度*2则表示每个像素2字节"),
    DECODE_TYPE("解码类型", "解码类型,参考DecodeTypeEnum"),
    ;
    /**
     * int数组索引、偏移字节数、名称、描述
     */
    public static final int LENGTH = GsnBodyMetaEnum.values().length;
    public static final int BYTE_LEN = LENGTH * Constant.BYTE_EACH_INT;
    public final int index;
    public final int offset;
    public final String name;
    public final String desc;

    GsnBodyMetaEnum(String name, String desc) {
        this.index = this.ordinal();
        this.offset = index * 4;
        this.name = name;
        this.desc = desc;
    }
}
