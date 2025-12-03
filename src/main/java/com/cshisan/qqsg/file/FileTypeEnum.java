package com.cshisan.qqsg.file;

/**
 * @author cshisan
 */
public enum FileTypeEnum {
    /**
     * 文件类型
     */
    GSO(20),
    GSN(20),
    GSA(13),
    ;

    public final int offset;

    FileTypeEnum(int offset) {
        this.offset = offset;
    }

    public static FileTypeEnum fromExt(String ext) {
        for (FileTypeEnum type : FileTypeEnum.values()) {
            if (type.name().equalsIgnoreCase(ext)) {
                return type;
            }
        }
        return null;
    }
}
