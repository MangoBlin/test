package com.hbw.springbootapplication.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImgUtil {

    public static File rotateImage90(File file){
        BufferedImage bufferedimage = null;
        try {
            bufferedimage = ImageIO.read(file);
            int w = bufferedimage.getWidth();
            int h = bufferedimage.getHeight();
            int type = bufferedimage.getColorModel().getTransparency();
            BufferedImage img;
            Graphics2D graphics2d;
            (graphics2d =
                    (img = new BufferedImage(h, w, type)).createGraphics()
            ).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2d.rotate(Math.toRadians(270), w / 2, h / 2 + (w - h) / 2);
            graphics2d.drawImage(bufferedimage, 0, 0, null);
            graphics2d.dispose();
            ImageIO.write(img, "jpg", file);

        } catch (IOException e) {
        }
        return file;
    }

    public static void main(String[] args) {
        File file = new File("F:\\springboot_project\\receipt_manage\\upload\\03538229.jpg");
        ImgUtil.rotateImage90(file);
    }
}
