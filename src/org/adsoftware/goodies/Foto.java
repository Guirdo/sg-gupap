package org.adsoftware.goodies;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Foto extends JPanel{
    
    private Image imagen = null;

    public Foto() {
    }
    
    @Override
    public void paint(Graphics g) {
        if(imagen != null){
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            
            //setOpaque(false);
        }else{
            g.drawString("Foto aquí", 40, 90);
        }
        
    }

    public void setImagen(String ruta){
        imagen = new ImageIcon(ruta).getImage();
    }
    
}
