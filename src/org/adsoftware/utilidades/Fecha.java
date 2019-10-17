package org.adsoftware.utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fecha {
    
    public static String actual(){
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        Date d = new Date();        
        return f.format(d);
    }

}
