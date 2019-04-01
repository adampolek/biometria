package viewer;

import binarization.Bernsen;
import binarization.Niblack;
import filtration.SimpleFilter;
import histogram.HistogramOperations;
import shared.ImageHandling;
import shared.ImageOperations;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Viewer extends JFrame {
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu files = new JMenu("File");
    private final JMenuItem loadImage = new JMenuItem("Load image");
    private final JMenuItem saveImage = new JMenuItem("Save image");
    private final JMenu histogramButton = new JMenu("Histograms");
    private final JMenuItem createHistogram = new JMenuItem("Create Histogram");
    private final JMenuItem alignHistogram = new JMenuItem("Align Histogram");
    private final JMenuItem stretchHistogram = new JMenuItem("Stretch Histogram");
    private final JMenu imageMenu = new JMenu("Image");
    private final JMenuItem lightenImage = new JMenuItem("lighten Image");
    private final JMenuItem darkenImage = new JMenuItem("Darken Image");
    private final JMenu tresholding = new JMenu("Tresholding");
    private final JMenu filtration = new JMenu("Filtration");
    private final JMenuItem redTresh = new JMenuItem("Grey scale red");
    private final JMenuItem greenTresh = new JMenuItem("Grey scale green");
    private final JMenuItem blueTresh = new JMenuItem("Grey scale blue");
    private final JMenuItem grayTresh = new JMenuItem("Grey scale gray");
    private final JMenuItem manualTresh = new JMenuItem("Manual Treshold");
    private final JMenuItem otsuTresh = new JMenuItem("OTSU");
    private final JMenuItem niblack = new JMenuItem("Niblack");
    private final JMenuItem bernsen = new JMenuItem("Bernsen");
    private final JMenuItem simpleFilter = new JMenuItem("Simple Filter");
    private final JLabel imageLabel = new JLabel();
    private final JPanel imagePanel = new JPanel();
    private final JPanel editPanel = new JPanel();
    private final JLabel labelR = new JLabel("R");
    private final JLabel labelG = new JLabel("G");
    private final JLabel labelB = new JLabel("B");
    private final JScrollPane scrollPane = new JScrollPane();
    private final JTextField fieldR = new JTextField("0");
    private final JTextField fieldG = new JTextField("0");
    private final JTextField fieldB = new JTextField("0");
    private final JButton accept = new JButton("Change");

    int pixelX = 0;
    int pixelY = 0;
    int width = 0;
    int height = 0;
    public BufferedImage image = null;

    public Viewer() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        this.setLayout(null);
        this.setTitle("Podstawy Biometrii");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.menuBar.add(this.files);
        this.files.add(this.loadImage);
        this.files.add(this.saveImage);
        this.menuBar.add(imageMenu);
        this.menuBar.add(histogramButton);
        this.menuBar.add(tresholding);
        this.menuBar.add(filtration);
        histogramButton.add(createHistogram);
        histogramButton.add(stretchHistogram);
        histogramButton.add(alignHistogram);
        imageMenu.add(lightenImage);
        imageMenu.add(darkenImage);
        tresholding.add(redTresh);
        tresholding.add(greenTresh);
        tresholding.add(blueTresh);
        tresholding.add(grayTresh);
        tresholding.add(manualTresh);
        tresholding.add(otsuTresh);
        tresholding.add(niblack);
        tresholding.add(bernsen);
        filtration.add(simpleFilter);

        this.setVisible(true);

        this.add(this.menuBar);
        menuBar.setBounds(0, 0, screenWidth, screenHeight / 40);
        imagePanel.setLayout(null);
        imagePanel.add(imageLabel);
        this.add(scrollPane);
        imagePanel.setBounds(screenWidth / 96, screenHeight / 21, (int) (screenWidth / (1.2)), screenHeight - 2 * screenHeight / 15);
        imagePanel.setVisible(true);

        scrollPane.setBounds(screenWidth / 96, screenHeight / 21, imagePanel.getWidth(), imagePanel.getHeight());
        imageLabel.setBounds(JLabel.CENTER, JLabel.CENTER, imagePanel.getWidth(), imagePanel.getHeight());
        this.add(imagePanel);
        scrollPane.setViewportView(imagePanel);

        this.add(editPanel);
        editPanel.setBounds((screenWidth / (96) * 2) + (int) (screenWidth / (1.2)), screenHeight / 21, screenWidth - (int) (screenWidth / (1.2)) - screenWidth / 96 * 3, screenHeight - 2 * screenHeight / 15);
        editPanel.setVisible(true);

        editPanel.setLayout(null);
        editPanel.add(labelR);
        labelR.setBounds(editPanel.getWidth() / 20, editPanel.getHeight() / 40, editPanel.getWidth() / 2 - 2 * (editPanel.getWidth() / 20), screenHeight / 21);
        editPanel.add(labelG);
        labelG.setBounds(editPanel.getWidth() / 20, editPanel.getHeight() / 40 + screenHeight / 21, editPanel.getWidth() / 2 - 2 * (editPanel.getWidth() / 20), screenHeight / 21);
        editPanel.add(labelB);
        labelB.setBounds(editPanel.getWidth() / 20, editPanel.getHeight() / 40 + (screenHeight / 21) * 2, editPanel.getWidth() / 2 - 2 * (editPanel.getWidth() / 20), screenHeight / 21);
        editPanel.add(fieldR);
        fieldR.setBounds(editPanel.getWidth() / 20 + editPanel.getWidth() / 2 - 2 * (editPanel.getWidth() / 20), editPanel.getHeight() / 40, editPanel.getWidth() / 2 - 2 * (editPanel.getWidth() / 20), screenHeight / 30);
        editPanel.add(fieldG);
        fieldG.setBounds(editPanel.getWidth() / 20 + editPanel.getWidth() / 2 - 2 * (editPanel.getWidth() / 20), editPanel.getHeight() / 40 + screenHeight / 30 + screenHeight / 50, editPanel.getWidth() / 2 - 2 * (editPanel.getWidth() / 20), screenHeight / 30);
        editPanel.add(fieldB);
        fieldB.setBounds(editPanel.getWidth() / 20 + editPanel.getWidth() / 2 - 2 * (editPanel.getWidth() / 20), editPanel.getHeight() / 40 + screenHeight / 30 + screenHeight / 15, editPanel.getWidth() / 2 - 2 * (editPanel.getWidth() / 20), screenHeight / 30);
        editPanel.add(accept);
        accept.setBounds(editPanel.getWidth() / 20, editPanel.getHeight() - editPanel.getHeight() / 15, editPanel.getWidth() / 2, editPanel.getHeight() / 30);

        this.loadImage.addActionListener((ActionEvent e) -> {
            JFileChooser imageOpener = new JFileChooser();
            imageOpener.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    String fileName = f.getName().toLowerCase();
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".png")
                            || fileName.endsWith(".tiff")) {
                        return true;
                    } else return false;
                }

                @Override
                public String getDescription() {
                    return "Image files (.jpg, .png, .tiff)";
                }
            });

            int returnValue = imageOpener.showDialog(null, "Select image");
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                BufferedImage img = ImageHandling.loadImage(imageOpener.getSelectedFile().getPath());
//                if(img.getHeight()<imagePanel.getHeight()){
//                    scrollPane.setViewportView(imagePanel);
//                }else{
                scrollPane.setViewportView(imageLabel);
//                }
                this.imageLabel.setIcon(new ImageIcon(img));
                imageLabel.setSize(img.getWidth(), img.getHeight());
                image = img;
            }
            width = image.getWidth();
            height = image.getHeight();
        });

        this.saveImage.addActionListener((ActionEvent e) -> {
            JFileChooser imageSaver = new JFileChooser();
            FileFilter jpgFilter = new FileTypeFilter(".jpg", "JPG format image");
            FileFilter pngFilter = new FileTypeFilter(".png", "PNG format image");
            FileFilter tiffFilter = new FileTypeFilter(".tiff", "TIFF format image");
            FileFilter bmpFilter = new FileTypeFilter(".bmp", "BMP format image");
            FileFilter svgFilter = new FileTypeFilter(".svg", "SVG format image");
            imageSaver.addChoosableFileFilter(jpgFilter);
            imageSaver.addChoosableFileFilter(pngFilter);
            imageSaver.addChoosableFileFilter(tiffFilter);
            imageSaver.addChoosableFileFilter(bmpFilter);
            int returnValue = imageSaver.showDialog(null, "Save");
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                BufferedImage img = ImageHandling.convertIconToImage((ImageIcon) this.imageLabel.getIcon());
                if (imageSaver.getFileFilter().getDescription().equals("JPG format image (*.jpg)")) {
                    ImageHandling.saveImage(img, "jpg", imageSaver.getSelectedFile().toString() + ".jpg");
                } else if (imageSaver.getFileFilter().getDescription().equals("PNG format image (*.png)")) {
                    ImageHandling.saveImage(img, "png", imageSaver.getSelectedFile().toString() + ".png");
                } else if (imageSaver.getFileFilter().getDescription().equals("TIFF format image (*.tiff)")) {
                    System.out.println("Jestem tiff");
                    ImageHandling.saveImage(img, "jpg", imageSaver.getSelectedFile().toString() + ".tiff");
                } else if (imageSaver.getFileFilter().getDescription().equals("BMP format image (*.bmp)")) {
                    ImageHandling.saveImage(img, "bmp", imageSaver.getSelectedFile().toString() + ".bmp");
                } else {
                    System.out.println(imageSaver.getFileFilter().getDescription());
                    ImageHandling.saveImage(img, "jpg", imageSaver.getSelectedFile().toString() + ".jpg");
                }
            }
        });

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (image != null) {
                    int colorValue = image.getRGB(e.getX(), e.getY());
                    Color color = new Color(colorValue);
                    fieldR.setText(String.valueOf((color.getRed())));
                    fieldG.setText(String.valueOf((color.getGreen())));
                    fieldB.setText(String.valueOf((color.getBlue())));
                    pixelX = e.getX();
                    pixelY = e.getY();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }
        });

        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rgb = new Color(Integer.parseInt(fieldR.getText()), Integer.parseInt(fieldG.getText()), Integer.parseInt(fieldB.getText())).getRGB();
                image.setRGB(pixelX, pixelY, rgb);
                imageLabel.setIcon(new ImageIcon(image));
            }
        });

        imageLabel.addMouseWheelListener(e -> {
            double scale;
            int mouseRotation = e.getWheelRotation();
            if (mouseRotation >= 0) {
                scale = 0.95;

            } else {
                scale = 1.05;
            }
            BufferedImage bufferedImage = ImageHandling.convertIconToImage((ImageIcon) imageLabel.getIcon());
            int newImageWidth = (int) (bufferedImage.getWidth() * scale);
            int newImageHeight = (int) (bufferedImage.getHeight() * scale);
            BufferedImage scaleImage = new BufferedImage(newImageWidth, newImageHeight, bufferedImage.getType());
            Graphics2D g = scaleImage.createGraphics();
            g.drawImage(bufferedImage, 0, 0, newImageWidth, newImageHeight, null);
            g.dispose();
            imageLabel.setIcon(new ImageIcon(scaleImage));
        });

        lightenImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    BufferedImage newImage = ImageOperations.lightenImage(image);
                    imageLabel.setIcon(new ImageIcon(image));
                }
            }
        });

        darkenImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    BufferedImage newImage = ImageOperations.darkenImage(image);
                    imageLabel.setIcon(new ImageIcon(image));
                }
            }
        });

        createHistogram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    HistogramsViewer histogramsViewer = new HistogramsViewer();
                    histogramsViewer.generateHistograms(HistogramOperations.calculateHistograms(image));
                    histogramsViewer.setVisible(true);
                }
            }
        });

        stretchHistogram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    List values = HistogramOperations.getValues(HistogramOperations.calculateHistograms(image));
                    BufferedImage newImage = image;
                    List<int[]> luts = HistogramOperations.getLUT(values);
                    int[] _R = luts.get(0);
                    int[] _G = luts.get(1);
                    int[] _B = luts.get(2);
                    int colorValue;
                    Color color;
                    Color newColor;
                    int R_value, G_value, B_value;
                    for (int w = 0; w < newImage.getWidth(); w++) {
                        for (int h = 0; h < newImage.getHeight(); h++) {
                            colorValue = newImage.getRGB(w, h);
                            color = new Color(colorValue);
                            R_value = color.getRed();
                            G_value = color.getGreen();
                            B_value = color.getBlue();
                            newColor = new Color(_R[R_value], _G[G_value], _B[B_value], 255);
                            newImage.setRGB(w, h, newColor.getRGB());
                        }
                    }
                    imageLabel.setIcon(new ImageIcon(newImage));
                    image = newImage;
                    List<int[]> stretchedHistograms = new ArrayList<>();
                    stretchedHistograms = HistogramOperations.calculateHistograms(newImage);
                    HistogramsViewer histogramsViewer = new HistogramsViewer();
                    histogramsViewer.setVisible(true);
                    histogramsViewer.generateHistograms(stretchedHistograms);
                }
            }
        });
        alignHistogram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    List values = HistogramOperations.getValues(HistogramOperations.calculateHistograms(image));
                    BufferedImage newImage = image;
                    List distrs = HistogramOperations.getDistr(newImage, HistogramOperations.calculateHistograms(newImage));
                    List<double[]> _distrs = HistogramOperations.getDistrLUT(distrs);
                    double[] distr_LUT_R = _distrs.get(0);
                    double[] distr_LUT_G = _distrs.get(1);
                    double[] distr_LUT_B = _distrs.get(2);
                    int colorValue;
                    Color color;
                    Color newColor;
                    int R_value, G_value, B_value;
                    for (int w = 0; w < newImage.getWidth(); w++) {
                        for (int h = 0; h < newImage.getHeight(); h++) {
                            colorValue = newImage.getRGB(w, h);
                            color = new Color(colorValue);
                            R_value = color.getRed();
                            G_value = color.getGreen();
                            B_value = color.getBlue();
                            newColor = new Color((int) distr_LUT_R[R_value], (int) distr_LUT_G[G_value], (int) distr_LUT_B[B_value], 255);
                            newImage.setRGB(w, h, newColor.getRGB());
                        }
                    }
                    imageLabel.setIcon(new ImageIcon(newImage));
                    image = newImage;
                    List<int[]> alignedHistograms = new ArrayList<>();
                    alignedHistograms = HistogramOperations.calculateHistograms(image);
                    HistogramsViewer histogramsViewer = new HistogramsViewer();
                    histogramsViewer.setVisible(true);
                    histogramsViewer.generateHistograms(alignedHistograms);
                }
            }
        });
        redTresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    BufferedImage newImage = image;
                    for (int w = 0; w < newImage.getWidth(); w++) {
                        for (int h = 0; h < newImage.getHeight(); h++) {
                            Color c = new Color(newImage.getRGB(w, h));
                            int colRed = c.getRed();
                            newImage.setRGB(w, h, new Color(colRed, colRed, colRed).getRGB());
                        }
                    }
                    imageLabel.setIcon(new ImageIcon(newImage));
                    image = newImage;
                }
            }
        });
        blueTresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    BufferedImage newImage = image;
                    for (int w = 0; w < newImage.getWidth(); w++) {
                        for (int h = 0; h < newImage.getHeight(); h++) {
                            Color c = new Color(newImage.getRGB(w, h));
                            int colBlue = c.getRed();
                            newImage.setRGB(w, h, new Color(colBlue, colBlue, colBlue).getRGB());
                        }
                    }
                    imageLabel.setIcon(new ImageIcon(newImage));
                    image = newImage;
                }
            }
        });
        greenTresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    BufferedImage newImage = image;
                    for (int w = 0; w < newImage.getWidth(); w++) {
                        for (int h = 0; h < newImage.getHeight(); h++) {
                            Color c = new Color(newImage.getRGB(w, h));
                            int colGreen = c.getGreen();
                            newImage.setRGB(w, h, new Color(colGreen, colGreen, colGreen).getRGB());
                        }
                    }
                    imageLabel.setIcon(new ImageIcon(newImage));
                    image = newImage;
                }
            }
        });
        grayTresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    BufferedImage newImage = image;
                    for (int w = 0; w < newImage.getWidth(); w++) {
                        for (int h = 0; h < newImage.getHeight(); h++) {
                            Color c = new Color(newImage.getRGB(w, h));
                            int colAvg = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                            newImage.setRGB(w, h, new Color(colAvg, colAvg, colAvg).getRGB());
                        }
                    }
                    imageLabel.setIcon(new ImageIcon(newImage));
                    image = newImage;
                }
            }
        });
        manualTresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    String name = JOptionPane.showInputDialog(null, "Enter treshold value", null);
                    int nameInt = Integer.parseInt(name);
                    if (nameInt < 0 || nameInt > 255) {
                        return;
                    }
                    BufferedImage newImage = image;
                    for (int w = 0; w < newImage.getWidth(); w++) {
                        for (int h = 0; h < newImage.getHeight(); h++) {
                            Color c = new Color(newImage.getRGB(w, h));
                            int colRed = c.getRed();
                            if (colRed <= nameInt) {
                                newImage.setRGB(w, h, Color.BLACK.getRGB());
                            } else {
                                newImage.setRGB(w, h, Color.WHITE.getRGB());
                            }
                        }
                    }
                    imageLabel.setIcon(new ImageIcon(newImage));
                    image = newImage;
                }
            }
        });
        otsuTresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    double p0 = 0;
                    double d0 = 0;
                    double p1 = 0;
                    double d1 = 0;
                    int numOfPixels = image.getHeight() * image.getWidth();
                    int[] histogram = new int[256];
                    histogram = HistogramOperations.calcOneHist(image);
                    double eta[] = new double[256];
                    for (int t = 1; t < eta.length; t++) {
                        p0 = 0;
                        p1 = 0;
                        d0 = 0;
                        d1 = 0;
                        for (int i = 0; i < t - 1; i++) {
                            p0 = p0 + histogram[i];
                            d0 = d0 + histogram[i] * i;
                        }
                        for (int i = t; i <= 255; i++) {
                            p1 = p1 + histogram[i];
                            d1 = d1 + histogram[i] * i;
                        }
                        d0 = d0 / p0;
                        d1 = d1 / p1;
                        p0 = p0 / (double) numOfPixels;
                        p1 = p1 / (double) numOfPixels;
                        eta[t] = p0 * p1 * Math.pow((d0 - d1), 2);
                    }
                    int ind_max = 0;
                    for (int i = 0; i < eta.length; i++) {
                        if (eta[i] > eta[ind_max]) {
                            ind_max = i;
                        }
                    }
                    System.out.println(ind_max);
                    BufferedImage newImage = image;
                    for (int w = 0; w < newImage.getWidth(); w++) {
                        for (int h = 0; h < newImage.getHeight(); h++) {
                            Color c = new Color(newImage.getRGB(w, h));
                            int colRed = c.getRed();
                            if (colRed <= ind_max) {
                                newImage.setRGB(w, h, Color.BLACK.getRGB());
                            } else {
                                newImage.setRGB(w, h, Color.WHITE.getRGB());
                            }
                        }
                    }
                    imageLabel.setIcon(new ImageIcon(newImage));
                    image = newImage;
                }
            }
        });
        niblack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    String constantA = JOptionPane.showInputDialog(null, "Enter constant a", null);
                    double a = Double.parseDouble(constantA);
                    String windowSize = JOptionPane.showInputDialog(null, "Enter window size", null);
                    int size = Integer.parseInt(windowSize);
                    if(size%2!=1){
                        return;
                    }
                    int[][] tresh = new int[image.getWidth()][image.getHeight()];
                    tresh = Niblack.calculateTresholds(image, a, size);
                    BufferedImage newImage = image;
                    for (int w = 0; w < newImage.getWidth(); w++) {
                        for (int h = 0; h < newImage.getHeight(); h++) {
                            Color c = new Color(newImage.getRGB(w, h));
                            int colRed = c.getRed();
                            if (colRed <= tresh[w][h]) {
                                newImage.setRGB(w, h, Color.BLACK.getRGB());
                            } else {
                                newImage.setRGB(w, h, Color.WHITE.getRGB());
                            }
                        }
                    }
                    imageLabel.setIcon(new ImageIcon(newImage));
                    image = newImage;
                }
            }
        });
        bernsen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(image!=null){
                    String contrastThreshold = JOptionPane.showInputDialog(null, "Enter contrast Threshold", null);
                    int constantThresh = Integer.parseInt(contrastThreshold);
                    String pixelThreshold = JOptionPane.showInputDialog(null, "Enter pixel threshold", null);
                    int pixelThresh = Integer.parseInt(pixelThreshold);
                    BufferedImage newImage = Bernsen.bernsen(image, constantThresh, pixelThresh);
                    imageLabel.setIcon(new ImageIcon(newImage));
                    image = newImage;
                }
            }
        });
        simpleFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(image!=null){
                    BufferedImage newImage = SimpleFilter.filterImage(image);
                    imageLabel.setIcon(new ImageIcon(newImage));
                    image = newImage;
                }
            }
        });
    }
}
