package filtration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public abstract class ConvolutionalFilter {

    public static BufferedImage filterImage(BufferedImage img, int[][] values) {
        BufferedImage copy = deepCopy(img);
        for(int w = 1; w < img.getWidth()-1; w++) {
            for(int h = 1; h < img.getHeight()-1; h++) {
                copy.setRGB(w, h, calculateNewPixelValue(img, w, h, values));
            }
        }
        return copy;
    }

    public static int calculateNewPixelValue(BufferedImage img, int w, int h, int[][] values) {
        double sumRed = 0, sumGreen = 0, sumBlue = 0, sum=0;
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                Color c = new Color(img.getRGB(w + i, h + j));
                sumRed += c.getRed() * values[i + 1][j + 1];
                sumBlue += c.getBlue() * values[i + 1][j + 1];
                sumGreen += c.getGreen() * values[i + 1][j + 1];
                sum += values[i + 1][j + 1];
            }
        }
        if(sum!=0){
            sumRed=sumRed/sum;
            sumGreen=sumGreen/sum;
            sumBlue=sumBlue/sum;
        }
        if(sumRed>255)
            sumRed=255;
        if(sumGreen>255)
            sumGreen=255;
        if(sumBlue>255)
            sumBlue=255;
        if(sumRed<0)
            sumRed=0;
        if(sumGreen<0)
            sumGreen=0;
        if(sumBlue<0)
            sumBlue=0;

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
