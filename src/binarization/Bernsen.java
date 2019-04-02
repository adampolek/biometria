package binarization;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Bernsen {

    public static BufferedImage bernsen (BufferedImage img, int contrastThreshold, int setThreshold){
        BufferedImage copy = deepCopy(img);
        for(int w=0; w<copy.getWidth();w++){
            for(int h=0; h<copy.getHeight(); h++){
                int max = 0;
                int min = 255;
                for(int i=w-1; i<=w+1; i++){
                    for(int j=h-1; j<=h+1; j++){
                        if(i>=0 && i <copy.getWidth() && j>=0 && j<copy.getHeight()){
                            Color color = new Color(img.getRGB(i,j));
                            int c = color.getRed();
                            if(c>max){
                                max=c;
                            }
                            if(c<min){
                                min=c;
                            }
                        }
                    }
                }
                int avg = (min + max)/2;
                int localContrast = max - min;
                Color color = new Color(img.getRGB(w,h));
                int d=color.getRed();
                if(localContrast<contrastThreshold){
                    if(avg>= setThreshold){
                        copy.setRGB(w, h, Color.WHITE.getRGB());
                    }else{
                        copy.setRGB(w, h, Color.BLACK.getRGB());
                    }
                }else{
                    if(d>=avg){
                        copy.setRGB(w, h, Color.WHITE.getRGB());
                    }else{
                        copy.setRGB(w, h, Color.BLACK.getRGB());
                    }
                }
            }
        }

        return copy;
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
