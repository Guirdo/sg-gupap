package org.adsoftware.superclases;

import com.alee.laf.window.WebFrame;
import com.alee.managers.style.StyleId;
import javax.swing.JFrame;

public class Pantalla extends WebFrame{
    
    public Pantalla(StyleId id,String titulo){
        super(id);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(titulo);
    }

}
