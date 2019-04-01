package binarization;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

public abstract class Niblack {

    public static int[][] calculateTresholds(BufferedImage img, double a, int window) {
        int[][] tresholds = new int[img.getWidth()][img.getHeight()];
        BufferedImage copy = deepCopy(img);
        int shift = window / 2;
        List<Integer> list = new ArrayList<>();
        for(int w=0; w<copy.getWidth();w++){
            for(int h=0; h<copy.getHeight(); h++){
                int sum = 0;
                int counter = 0;
                for(int k = w-shift; k<=w+shift;k++){
                    for(int m=h-shift; m<=h+shift; m++){
                        if (k >= 0 && k < copy.getHeight() && m >= 0 && m < copy.getWidth()){
                            Color color = new Color(copy.getRGB(k, m));
                            int c = color.getRed();
                            list.add(c);
                            sum = sum + c;
                            counter++;
                        }
                    }
                }
                double srednia = sum/counter;
                double[] values = new double[list.size()];
                int index = -1;
                for (Integer i : list) {
                    index++;
                    values[index] = Double.parseDouble(i.toString());
                }
                StandardDeviation standardDeviation = new StandardDeviation();
                double result = standardDeviation.evaluate(values);
                tresholds[w][h] = (int) (srednia + a * result);
                list.clear();
            }
        }
        return tresholds;
    }


    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
