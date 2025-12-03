package com.cshisan.qqsg.file;

/**
 * @author cshisan
 */
public class FrameMeta extends BaseFileReader {
    public final int width;
    public final int height;

    public FrameMeta(int[] data, int width, int height) {
        super(data);
        this.width = width;
        this.height = height;
    }
}

