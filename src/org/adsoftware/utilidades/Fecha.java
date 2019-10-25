package org.adsoftware.utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fecha {
    
    public static String actual(){
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhhmm");
        Date d = new Date();        
        return f.format(d);
    }

    public static String toString(Date fecha){
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");    
        return f.format(fecha);
    }
    
    public static String formatoHumano(Date fecha){
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");    
        return f.format(fecha);
    }
}
