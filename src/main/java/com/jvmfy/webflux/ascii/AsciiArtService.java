package com.jvmfy.webflux.ascii;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Slf4j
@Service
public class AsciiArtService {

    private static final int IMG_WIDTH = 120;
    private static final int IMG_HEIGHT = 25;
    private static final int EMPTY_RGB = -16777216;

    public String generateAsciiArt(String text) {
        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        graphics2D.drawString(text, 12, 20);

        StringBuilder asciiArt = new StringBuilder();

        for (int i = 0; i < IMG_HEIGHT; i++) {
            for (int j = 0; j < IMG_WIDTH; j++) {

                if (image.getRGB(j, i) == EMPTY_RGB) {
                    asciiArt.append("*");
                    continue;
                }
                asciiArt.append(" ");
            }
            asciiArt.append("\n");
        }

        return asciiArt.toString();
    }
}
