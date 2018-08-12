/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.utilities;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Diego Lemus
 */
public class Fechas {
    public static Date sumarDias(Date fecha, int dias) {
        if (dias == 0) {
            return fecha;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }
}
