/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mise;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jirig
 */
public class Datum {
    
    
    private static DateTimeFormatter formatData = DateTimeFormatter.ofPattern("d.M.y");
   
    
    public static void nastavPulnoc(LocalDate datum){
        datum.atTime(0, 0, 0, 0);
    }
    
    
    public static String zformatuj(LocalDate datum){
        String datumText = formatData.format(datum);
        return datumText;
    }
    
    public static LocalDate naparsuj(String datumText) throws ParseException
    {
        LocalDate datum = LocalDate.parse(datumText, DateTimeFormatter.ofPattern("d.M.y"));
        return datum;
    }
    //zaokrouhleni na pozadovany pocet mist-precision
    public static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
    
    
    
}
