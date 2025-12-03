package com.cshisan.qqsg.file;

/**
 * @author cshisan
 */
public enum GsnHeaderMetaEnum {
    /**
     * 头部元数据
     */
    FORMAT_1("文件格式1", "文件格式,与文件格式2合并输出字符串可得到'QQ\u001ADIMG'"),
    FORMAT_2("文件格式2", "文件格式,参考FORMAT_1"),
    VERSION("版本号", "版本号?(待定),具体含义未知"),
    UNKNOWN_1("未知1", "未知1"),
    FRAME_SIZE("帧数", "帧数"),
    UNKNOWN_2("未知2", "未知2"),
    WIDTH("宽度", "宽度"),
    HEIGHT("高度", "高度"),
    UNKNOWN_3("未知3", "未知3"),
    UNKNOWN_4("未知4", "未知4"),
    UNKNOWN_5("未知5", "未知5"),
    ;

    /**
     * int数组索引、偏移字节数、名称、描述
     */
    public final int index;
    public final int offset;
    public final String name;
    public final String desc;

    GsnHeaderMetaEnum(String name, String desc) {
        this.index = this.ordinal();
        this.offset = index * 4;
        this.name = name;
        this.desc = desc;
    }
}
