package filtration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Arrays;

public abstract class MedianFilter {

    public static BufferedImage medianFilter3x3(BufferedImage image){
        BufferedImage newImage = deepCopy(image);
        for (int w=1; w<image.getWidth()-1; w++){
            for(int h=1; h<image.getHeight()-1; h++){
                int tabRed[] = new int[9];
                int tabGreen[] = new int[9];
                int tabBlue[] = new int[9];
                int counter = -1;
                for(int i=-1; i<2; i++){
                    for(int j=-1; j<2; j++){
                        Color c = new Color(image.getRGB(w+i, h+j));
                        tabRed[++counter] = c.getRed();
                        tabGreen[counter] = c.getGreen();
                        tabBlue[counter] = c.getBlue();
                    }
                }
                Arrays.sort(tabRed);
                Arrays.sort(tabGreen);
                Arrays.sort(tabBlue);
                Color color = new Color(tabRed[4],tabGreen[4],tabBlue[4]);
                newImage.setRGB(w,h,color.getRGB());
            }
        }
        return newImage;
    }

    public static BufferedImage medianFilter5x5(BufferedImage image){
        BufferedImage newImage = deepCopy(image);
        for (int w=2; w<image.getWidth()-2; w++){
            for(int h=2; h<image.getHeight()-2; h++){
                int tabRed[] = new int[25];
                int tabGreen[] = new int[25];
                int tabBlue[] = new int[25];
                int counter = -1;
                for(int i=-2; i<3; i++){
                    for(int j=-2; j<3; j++){
                        Color c = new Color(image.getRGB(w+i, h+j));
                        tabRed[++counter] = c.getRed();
                        tabGreen[counter] = c.getGreen();
                        tabBlue[counter] = c.getBlue();
                    }
                }
                Arrays.sort(tabRed);
                Arrays.sort(tabGreen);
                Arrays.sort(tabBlue);
                Color color = new Color(tabRed[12],tabGreen[12],tabBlue[12]);
                newImage.setRGB(w,h,color.getRGB());
            }
        }
        return newImage;
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

}
