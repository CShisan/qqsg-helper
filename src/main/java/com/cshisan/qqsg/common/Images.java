package com.cshisan.qqsg.common;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author cshisan
 */
public class Images {
    public static BufferedImage extractImage(int[][] imgData, int imgWidth, int imgHeight) {
        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        for (int row = 0; row < imgHeight; row++) {
            for (int col = 0; col < imgWidth; col++) {
                image.setRGB(col, row, imgData[row][col]);
            }
        }
        return image;
    }

    public static void displayImage(int[][] imgData, int imgWidth, int imgHeight) {
        Images.displayImage(Images.extractImage(imgData, imgWidth, imgHeight));
    }

    public static void displayImage(BufferedImage image) {
        Images.displayImage(image, image.getWidth(), image.getHeight());
    }

    public static void displayImage(BufferedImage image, int imgWidth, int imgHeight) {
        System.out.println("图像尺寸: " + imgWidth + "x" + imgHeight);

        // 创建GUI显示图像
        JFrame frame = new JFrame("SG IMG Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * 保存图像为PNG文件
     */
    public void saveImage(int[][] imgData, String outputPath, int imgWidth, int imgHeight) {
        BufferedImage image = Images.extractImage(imgData, imgWidth, imgHeight);
        try {
            ImageIO.write(image, "PNG", new File(outputPath));
            System.out.println("图像已保存: " + outputPath);
        } catch (IOException ignore) {
        }
    }
}
