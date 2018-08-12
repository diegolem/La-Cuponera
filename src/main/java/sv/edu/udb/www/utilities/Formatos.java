/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Diego Lemus
 */
public class Formatos {
    public static double roundDbl(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    public static String formatearFecha(Date _d, String _f){
        SimpleDateFormat format = new SimpleDateFormat(_f);
        return format.format(_d);
    }
}
