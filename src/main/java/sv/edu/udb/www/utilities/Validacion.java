/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.utilities;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Diego Lemus
 */
public class Validacion {
    private static int entero;
    private static double decimal;
    private static String cadena;
    
    public static boolean validar(String rgx, String campo, String mensaje, String apartado){
        boolean response = false;
        Pattern pat = Pattern.compile(rgx);
        Matcher mat = pat.matcher(campo);
        
        if(mat.matches()){
            response = true;
        }else{
            response = false;
        }
        return response;
    }
    
    public static boolean esEntero(String campo){
        try {
            entero = Integer.parseInt(cadena.trim());
            return true;
        } catch (NumberFormatException a) {
            return false;
        }
    }
    
    public static boolean esEnteroPositivo(String cadena) {
        try {
            entero = Integer.parseInt(cadena);
            return entero >= 0;
        } catch (NumberFormatException a) {
            return false;
        }
    }
    
    public static boolean isEmpty(String mensaje) {
        return mensaje.trim().equals("");
    }
    
    public static boolean esDecimal(String cadena) {
        try {
            decimal = Double.parseDouble(cadena.trim());
            return true;
        } catch (NumberFormatException a) {
            return false;
        }
    }
    
    public static boolean esDecimalPositivo(String cadena) {
        try {
            decimal = Double.parseDouble(cadena.trim());
            if (decimal <= 0) {
                return false;
            }
            return true;
        } catch (NumberFormatException a) {
            return false;
        }
    }
    
    public static boolean esTelefono(String cadena){
        Pattern pat = Pattern.compile("^[267]{1}[0-9]{3}([- ])[0-9]{4}$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }
    
    public static boolean esDui(String cadena){
        Pattern pat = Pattern.compile("^[0-9]{1}[0-9]{7}[-]{1}[0-9]{1}$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }
    
    public static boolean esNit(String cadena){
        Pattern pat = Pattern.compile("^[0-9]{1}[0-9]{3}[-]{1}[0-9]{6}[-]{1}[0-9]{3}[-]{1}[0-9]{1}$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }
    
    public static boolean esCodigoEmpresa(String cadena){
        Pattern pat = Pattern.compile("^([a-z]|[A-Z]|[ñÑ]){1}([a-z]|[A-Z]|[ñÑ]){2}([0-9]){3}$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }
    
    public static boolean esNombreEmpresa(String cadena){
        Pattern pat = Pattern.compile("^([a-z]|[A-Z]|[ñÑ]|[0-9]){1}[a-zA-Z0-9 ñÑáéíóú]*$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }
    
    public static boolean esDireccion(String cadena){
        Pattern pat = Pattern.compile("^([A-Z]|[a-z]|[ñÑ])[a-zA-Z ñÑáéíóú,0-9.#-]*$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }
    
    public static boolean esCorreo(String cadena){
        Pattern pat;
        pat = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }
    
    public static boolean esRubro(String cadena){
        Pattern pat = Pattern.compile("^([a-z]|[A-Z]|[ñÑ]){1}[a-zA-Z ñÑáéíóú]*$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }
    
    public static boolean esNombrePersona(String cadena){
        Pattern pat = Pattern.compile("^([a-z]|[A-Z]|[ñÑ]){1}[a-zA-Z ñÑáéíóú]*$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }
    
    public static boolean esCodigoCupon(String cadena){
        Pattern pat = Pattern.compile("^([a-z]|[A-Z]|[ñÑ]){3}[0-9]{3}[0-9]{7}$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }
    
    public static boolean verificarFechas(LocalDate date1, LocalDate date2){
        return date1.isBefore(date2) || date1.equals(date2);
    }
}
