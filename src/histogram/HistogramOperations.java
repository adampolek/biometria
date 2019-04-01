package histogram;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public abstract class HistogramOperations {

    public static List<int[]> calculateHistograms(BufferedImage image) {
        int[] histogramRed = new int[256];
        int[] histogramGreen = new int[256];
        int[] histogramBlue = new int[256];
        int[] histogramGray = new int[256];

        for(int w = 0; w < image.getWidth(); w++) {
            for(int h = 0; h < image.getHeight(); h++) {
                Color color = new Color(image.getRGB(w, h));
                histogramRed[color.getRed()]++;
                histogramGreen[color.getGreen()]++;
                histogramBlue[color.getBlue()]++;
                histogramGray[(color.getRed()+color.getBlue()+color.getGreen())/3]++;
            }
        }

        List<int[]> histograms = new ArrayList<>();
        histograms.add(histogramRed);
        histograms.add(histogramGreen);
        histograms.add(histogramBlue);
        histograms.add(histogramGray);

        return histograms;
    }

    public static int[] calcOneHist(BufferedImage image){
        int[] histogram = new int[256];
        for(int w = 0; w < image.getWidth(); w++) {
            for(int h = 0; h < image.getHeight(); h++) {
                Color color = new Color(image.getRGB(w, h));
                histogram[color.getRed()]++;
            }
        }
        return histogram;
    }

    public static List getValues(List<int[]> histograms){
        int []histRed=histograms.get(0);
        int []histGreen=histograms.get(1);
        int []histBlue=histograms.get(2);
        int []histGray=histograms.get(3);
        int minR = 255;
        int minG = 255;
        int minB = 255;
        int minGray = 255;
        int maxR = 1;
        int maxG = 1;
        int maxB = 1;
        int maxGray = 1;
        for(int i=0;i<256;i++){
            if(histRed[i] > 0 && i < minR){
                minR=i;
            }
            if (histGreen[i]>0 && i<minG){
                minG = i;
            }
            if (histBlue[i]>0 && i<minB){
                minB = i;
            }
            if (histGray[i]>0 && i<minGray){
                minGray = i;
            }
            if (histRed[i]>0 && i>maxR){
                maxR = i;
            }
            if (histGreen[i]>0 && i>maxG){
                maxG = i;
            }
            if (histBlue[i]>0 && i>maxB){
                maxB = i;
            }
            if (histGray[i]> 0 && i>maxGray){
                maxGray = i;
            }
        }
        List values = new ArrayList();
        values.add(minR);
        values.add(maxR);
        values.add(minG);
        values.add(maxG);
        values.add(minB);
        values.add(maxB);
        values.add(minGray);
        values.add(maxGray);
        return values;
    }

    public static List<int[]>  getLUT(List values){
        double r,g,b;
        int minR= (int) values.get(0);
        int maxR= (int) values.get(1);
        int minG= (int) values.get(2);
        int maxG= (int) values.get(3);
        int minB= (int) values.get(4);
        int maxB= (int) values.get(5);
        int minGray= (int) values.get(6);
        int maxGray= (int) values.get(7);
        int []LUT_R = new int[256];
        int []LUT_G = new int[256];
        int []LUT_B = new int[256];
        for(int i = 0;i<256;i++){
            r =  255.0/(maxR-minR)*(i-minR);
            if(r>255){
                LUT_R[i] = 255;}
            else if(r<0){
                LUT_R[i] = 0;
            }
            else {
                LUT_R[i] = (int) r;
            }
            g = 255.0/(maxG-minG)*(i-minG);
            if(g>255){
                LUT_G[i] = 255;}
            else if(g<0){
                LUT_G[i] = 0;
            }
            else {
                LUT_G[i] = (int) g;
            }
            b = 255.0/(maxB-minB)*(i-minB);
            if(b>255){
                LUT_B[i] = 255;}
            else if(b<0){
                LUT_B[i] = 0;
            }
            else {
                LUT_B[i] = (int) b;
            }
        }
        List<int[]> LUTs = new ArrayList<>();
        LUTs.add(LUT_R);
        LUTs.add(LUT_G);
        LUTs.add(LUT_B);
        return LUTs;
    }

    public static List<double[]> getDistr(BufferedImage img,List<int[]> histograms){
        double sumR = 0;
        double sumG = 0;
        double sumB = 0;
        double distrR[] = new double[256];
        double distrG[] = new double[256];
        double distrB[] = new double[256];
        for (int i =0;i<256;i++){
            distrR[i] = 0;
            distrG[i] = 0;
            distrB[i] = 0;
        }
        int[] histogramRed = histograms.get(0);
        int[] histogramGreen =  histograms.get(1);
        int[] histogramBlue =  histograms.get(2);
        double numberOfPixels = img.getHeight()*img.getWidth();
        for(int i=0;i<256;i++){
            sumR += (histogramRed[i]/numberOfPixels);
            sumG += (histogramGreen[i]/numberOfPixels);
            sumB += (histogramBlue[i]/numberOfPixels);
            distrR[i] += sumR;
            distrG[i] += sumG;
            distrB[i] += sumB;
        }

        List<double[]> distr = new ArrayList<>();
        distr.add(distrR);
        distr.add(distrG);
        distr.add(distrB);
        return distr;
    }

    public static List<double[]> getDistrLUT(List<double[]> distrs){
        double D0_R,D0_G,D0_B;
        double[] distr_LUT_R = new double[256];
        double[] distr_LUT_G = new double[256];
        double[] distr_LUT_B = new double[256];

        for (int i=0;i<256;i++){
            distr_LUT_R[i] = 0;
            distr_LUT_G[i] = 0;
            distr_LUT_B[i] = 0;
        }
        int i;
        i=0;
        while (distrs.get(0)[i] ==0) {i++;}
        D0_R = distrs.get(0)[i];

        i=0;
        while (distrs.get(1)[i] ==0) {i++;}
        D0_G = distrs.get(1)[i];

        i=0;
        while (distrs.get(2)[i] ==0) {i++;}
        D0_B = distrs.get(2)[i];

        for(int j=0;j<256;j++){
            distr_LUT_R[j] = ((distrs.get(0)[j]-D0_R)/(1-D0_R))*255;
            distr_LUT_G[j] = ((distrs.get(1)[j]-D0_G)/(1-D0_G))*255;
            distr_LUT_B[j] = ((distrs.get(2)[j]-D0_B)/(1-D0_B))*255;

        }
        List<double[]> distr = new ArrayList<>();
        distr.add(distr_LUT_R);
        distr.add(distr_LUT_G);
        distr.add(distr_LUT_B);
        return distr;
    }
}