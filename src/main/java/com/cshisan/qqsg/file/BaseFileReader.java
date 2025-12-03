package com.cshisan.qqsg.file;

import com.cshisan.qqsg.common.Constant;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author cshisan
 */
public abstract class BaseFileReader {
    protected final int[] data;

    protected BaseFileReader(int[] data) {
        this.data = data;
    }

    public static int[] read(byte[] data, int pos, int metaLen) {
        int[] meta = new int[metaLen];
        int length = metaLen * Constant.BYTE_EACH_INT;
        ByteBuffer metaBuf = ByteBuffer.wrap(data, pos, length).order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < metaLen; i++) {
            meta[i] = metaBuf.getInt();
        }
        return meta;
    }

    public int get(int pos) {
        return data[pos];
    }
}
