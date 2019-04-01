package shared;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class ImageHandling {

    public static BufferedImage loadImage(String path){
        BufferedImage image = null;
        File file = new File(path);
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return image;
    }

    public static void saveImage(BufferedImage image, String format, String path){
        try {
            ImageIO.write(image, format, new File(path));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static BufferedImage convertIconToImage(ImageIcon icon){
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.createGraphics();
        icon.paintIcon(null, graphics, 0, 0);
        graphics.dispose();
        return image;
    }
}
