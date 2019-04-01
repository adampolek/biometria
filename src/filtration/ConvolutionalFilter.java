package filtration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public abstract class ConvolutionalFilter {

    public static BufferedImage filterImage(BufferedImage img) {
        BufferedImage copy = deepCopy(img);
        for(int w = 1; w < img.getWidth() - 1; w++) {
            for(int h = 1; h < img.getHeight() - 1; h++) {
                copy.setRGB(w, h, calculateNewPixelValue(img, w, h));
            }
        }
        return copy;
    }

    public static int calculateNewPixelValue(BufferedImage img, int w, int h) {
        double sumRed = 0, sumGreen = 0, sumBlue = 0;
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                Color c = new Color(img.getRGB(w + i, h + j));
                sumRed += c.getRed();
                sumBlue += c.getBlue();
                sumGreen += c.getGreen();
            }
        }
        sumRed = sumRed / 9;
        sumBlue = sumBlue / 9;
        sumGreen = sumGreen / 9;

        Color c = new Color((int) sumRed, (int) sumGreen, (int) sumBlue);
        return c.getRGB();
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

}
