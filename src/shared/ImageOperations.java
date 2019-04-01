package shared;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ImageOperations {

    public static BufferedImage lightenImage(BufferedImage image){
        for(int w = 0; w < image.getWidth(); w++) {
            for(int h = 0; h < image.getHeight(); h++) {
                Color color = new Color(image.getRGB(w, h));
                    int colorR = color.getRed();
                    int colorG = color.getGreen();
                    int colorB = color.getBlue();
                    colorR = (int) (43*Math.log(colorR+1));
                    if(colorR>255){
                        colorR=255;
                    }
                    colorG = (int) (43*Math.log(colorG+1));
                    if(colorG>255){
                        colorG=255;
                    }
                    colorB = (int) (43*Math.log(colorB+1));
                    if(colorB>255){
                        colorB=255;
                    }
                    Color newColor = new Color(colorR, colorG, colorB);
                    image.setRGB(w,h,newColor.getRGB());
            }
        }
        return image;
    }
    public static BufferedImage darkenImage(BufferedImage image){
        for(int w = 0; w < image.getWidth(); w++) {
            for(int h = 0; h < image.getHeight(); h++) {
                Color color = new Color(image.getRGB(w, h));
                    int colorR = color.getRed();
                    int colorG = color.getGreen();
                    int colorB = color.getBlue();
                    colorR = (int) (0.002*Math.pow(colorR,2));
                    if(colorR>255){
                        colorR=255;
                    }
                    colorG = (int) (0.002*Math.pow(colorG,2));
                    if(colorG>255){
                        colorG=255;
                    }
                    colorB = (int) (0.002*Math.pow(colorB,2));
                    if(colorB>255){
                        colorB=255;
                    }
                    Color newColor = new Color(colorR, colorG, colorB);
                    image.setRGB(w,h,newColor.getRGB());
            }
        }
        return image;
    }
}
