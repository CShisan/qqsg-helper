package com.cshisan.qqsg.file;

/**
 * @author cshisan
 */
public class FileHeader extends BaseFileReader {
    /**
     * 尾部 6 个 uint32
     */
    public static final int TAIL_SIZE = 24;
    public final FileTypeEnum type;
    public final int headerLen;
    public final int contentLen;

    public FileHeader(byte[] data, FileTypeEnum type) {
        super(read(data, 0, switch (type) {
            case GSO -> GsoHeaderMetaEnum.values().length;
            case GSN -> GsnHeaderMetaEnum.values().length;
            case GSA -> GsaHeaderMetaEnum.values().length;
            case null -> throw new IllegalArgumentException("不支持的文件类型: null");
        }));
        this.type = type;
        this.headerLen = this.data.length * 4;
        this.contentLen = data.length - this.headerLen - TAIL_SIZE;
    }
}
