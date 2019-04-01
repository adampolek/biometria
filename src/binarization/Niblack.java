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
        int[][] tresholds = new int[img.getHeight()][img.getWidth()];
        BufferedImage copy = deepCopy(img);
        Point[][] pom = new Point[window][window];
        int shift = window / 2;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int sum = 0;
                List list = new ArrayList();
                int counter = 0;
                for (int k = i - shift; k < i + shift; k++) {
                    for (int l = j - shift; l < i + shift; l++) {
                        if (k >= 0 && l >= 0 && k < img.getWidth() && l < img.getHeight()) {
                            counter++;
                            Color c = new Color(copy.getRGB(k, l));
                            int r = c.getRed();
                            list.add(r);
                            sum = sum + r;
                        }
                    }
                }
                double srednia = 0;
                if (counter != 0) {
                    srednia = sum / counter;
                }
                double[] values = new double[list.size()];
                int index = -1;
                for (Object o : list) {
                    index++;
                    values[index] = Double.parseDouble(o.toString());
                }
                StandardDeviation standardDeviation = new StandardDeviation();
                double result = standardDeviation.evaluate(values);
                tresholds[i][j] = (int) (srednia + a * result);
            }
        }
        return tresholds;
    }

    public static int[][] calculateTresholdsMasaka(BufferedImage img, double a, int window) {
        int[][] tresholds = new int[img.getHeight()][img.getWidth()];
        BufferedImage copy = deepCopy(img);
        Point[][] pom = new Point[window][window];
        int shift = window / 2;

        for (int i = 0; i < copy.getWidth(); i++) {
            for (int j = 0; j < copy.getHeight(); j++) {
                int sum = 0, counter = 0, srednia = 0;
                List<Double> list = new ArrayList<>();

                for (int k = i - shift; k < i + shift; i++) {
                    for (int l = j - shift; l < j + shift; j++) {
                        if (k > 0 && l > 0 && k < copy.getWidth() && l < copy.getHeight()) {
                            Color c = new Color(copy.getRGB(k, l));
                            counter++;
                            sum += c.getRed();
                            list.add((double) c.getRed());
                        }
                    }
                }

                if (counter > 0)
                    srednia = sum / counter;

                StandardDeviation standardDeviation = new StandardDeviation();

                double[] values = new double[list.size()];
                int q=-1;
                for (double c : list)
                    values[q++]= c;
                double result = standardDeviation.evaluate(values);
                tresholds[i][j] = (int) (srednia + a * result);

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
