package com.cshisan;

import com.cshisan.qqsg.file.FileHeader;
import com.cshisan.qqsg.file.FileTypeEnum;
import com.cshisan.qqsg.parse.ParseFactory;
import com.cshisan.qqsg.parse.ParseStrategy;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Unit test for simple App.
 */
public class Test01 extends TestCase {
    public static final String CURRENT_USER = System.getProperty("user.name");
    public Test01(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(Test01.class);
    }

    public void testGsoDxt1() throws IOException {
        this.parse("C:\\Users\\%s\\Desktop\\QQSG\\data\\output-raw\\objects.pkg\\res\\MapRes\\MiniMap\\10-1.map.ful.gso".formatted(CURRENT_USER));
    }

    public void testGsaArgb4444() throws IOException {
        this.parse("C:\\Users\\%s\\Desktop\\QQSG\\data\\output-raw\\objects.pkg\\res\\UiRes\\Version04\\achieve\\9gl_01.gsa".formatted(CURRENT_USER));
    }

    public void testGsaArgb8888() throws IOException {
        this.parse("C:\\Users\\%s\\Desktop\\QQSG\\data\\output-raw\\objects.pkg\\res\\UiRes\\Version04\\achieve\\3100.gsa".formatted(CURRENT_USER), 1);
        this.parse("C:\\Users\\%s\\Desktop\\QQSG\\data\\output-raw\\objects.pkg\\res\\texticon\\0001.gsa".formatted(CURRENT_USER), 2);
    }

    public void testGsnArgb4444() throws IOException {
        this.parse("C:\\Users\\%s\\Desktop\\QQSG\\data\\output-raw\\objects.pkg\\res\\AVATAR\\face\\M\\1392\\040139208.gsn".formatted(CURRENT_USER));
    }

    public void testGsoArgb4444() throws IOException {
        this.parse("C:\\Users\\%s\\Desktop\\QQSG\\data\\output-raw\\objects.pkg\\res\\MapRes\\beijing\\xsxly\\caodi001.gso".formatted(CURRENT_USER), 1);
        this.parse("C:\\Users\\%s\\Desktop\\QQSG\\data\\output-raw\\objects.pkg\\res\\MapRes\\beijing\\xsxly\\zjsuguo001.gso".formatted(CURRENT_USER), 2);
    }

    public void testGsoDxt5() throws IOException {
        this.parse("C:\\Users\\%s\\Desktop\\QQSG\\data\\output-raw\\objects.pkg\\res\\MapRes\\beijing\\shu\\bajun.gso".formatted(CURRENT_USER));
    }

    public void parse(String path) throws IOException {
        this.parse(path, 0);
    }

    public void parse(String path, int index) throws IOException {
        String ext = path.substring(path.length() - 3);

        byte[] data = Files.readAllBytes(Path.of(path));
        FileHeader fileHeader = new FileHeader(data, FileTypeEnum.fromExt(ext));

        ParseStrategy strategy = ParseFactory.typeOf(fileHeader.type);
        List<BufferedImage> images = strategy.parse(fileHeader, data);
        ListIterator<BufferedImage> iterator = images.listIterator();
        while (iterator.hasNext()) {
            String name = "C:\\Users\\%s\\Desktop\\output%s-%s.png".formatted(CURRENT_USER, index, iterator.nextIndex());
//            Images.displayImage(iterator.next());
            ImageIO.write(iterator.next(), "png", new File(name));
        }
    }
}
