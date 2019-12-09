package org.adsoftware.goodies;

import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Credencial extends JPanel {

    public Credencial(LayoutManager layout) {
        super(layout);
    }

    public BufferedImage crearImagen() {
        int w = this.getWidth();
        int h = this.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        this.paint(g);
        g.dispose();
        return bi;
    }

}
