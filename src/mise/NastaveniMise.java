/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mise;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author jirig
 */
public class NastaveniMise implements Serializable{
    
    private static final long serialVersionUID = -55857686305273843L;
    
    private String zacatekMise;
    private String konecMise;
    private String nazevMise;
    private double vyplata;
    private String celkemDnu;
    private String celkemKc;
    private LocalDate zacatekMiseDatum;
    private LocalDate konecMiseDatum;
    private double xOffset;
    private double yOfsset;
    
    //private StringProperty nazevMise;
    //private DoubleProperty vyplata;
    //private StringProperty zacatekMiseString;
    //private StringProperty konecMiseString;
    //private StringProperty celkemDnu;
    
    
    private transient Alert alert = new Alert(AlertType.WARNING);
    
    public transient static SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.y");
    
    public NastaveniMise() {
    
    }
    
    public NastaveniMise (String nazevMise, LocalDate zacatekMise, LocalDate konecMise, String vyplata) throws IllegalArgumentException {
        if (nazevMise.isEmpty()) {
            alert.setTitle("Warning");
            alert.setHeaderText("Zadej název mise");
            alert.showAndWait();
            throw new IllegalArgumentException ("Zadej nazev mise");
        }

        if (zacatekMise == null) {
            alert.setTitle("Warning");
            alert.setHeaderText("Zadej správně začátek mise");
            alert.showAndWait();
            throw new IllegalArgumentException("spatne datum zacatku");
        }
        if (zacatekMise.isAfter(LocalDate.now())) {
            alert.setTitle("Warning");
            alert.setHeaderText("Mise ještě nezačala!");
            alert.showAndWait();
            throw new IllegalArgumentException("spatne datum zacatku");
        }
        if (konecMise == null || konecMise.isBefore(zacatekMise)) {
            alert.setTitle("Warning");
            alert.setHeaderText("Zadej správně konec mise");
            alert.showAndWait();
            throw new IllegalArgumentException("spatne datum konce");
        }
        if (vyplata.isEmpty() || !isStringDouble(vyplata)) {
            alert.setTitle("Warning");
            alert.setHeaderText("Zadej správně hodnotu výplaty");
            alert.showAndWait();
            throw new IllegalArgumentException("spatna hodnota vyplaty");
        }

        this.nazevMise = nazevMise;
        this.zacatekMise = zacatekMise.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        this.konecMise = konecMise.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        this.vyplata = Double.parseDouble(vyplata);
        this.celkemDnu = Integer.toString((int) zacatekMise.until(konecMise, ChronoUnit.DAYS)+1);
        this.celkemKc = Double.toString(this.vyplata*(zacatekMise.until(konecMise, ChronoUnit.DAYS)+1));
        this.zacatekMiseDatum = zacatekMise;
        this.konecMiseDatum = konecMise;
//        setNazevMise(nazevMise);
//        setVyplata(Double.parseDouble(vyplata));
//        setZacatekMiseString(zacatekMise.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
//        setKonecMiseString(konecMise.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
    }
    
    //metoda pro urceni zda uzivatel zadal do pole spravnou hodnotu ve forme double
    private Boolean isStringDouble(String vyplata) {
        String cisla = "0123456789,.";
        int pocet = 0;
        for (char i : vyplata.toCharArray()) {
            if (!cisla.contains(String.valueOf(i))) {
                pocet++;
            }  
        }
        if (pocet>0) {
            return false;
        }
        else if (vyplata.startsWith("'") || vyplata.startsWith(".")) {
            return false;
        }
        else
            return true;
    }

//    public final String getNazevMise() {
//        if (nazevMise != null)   
//        return nazevMise.get();
//        return "";
//    }
//    public final void setNazevMise(String nazevMise) {
//        this.stringPropertyNazevMise().set(nazevMise);
//    }
//    public final StringProperty stringPropertyNazevMise(){
//        if (nazevMise == null) {
//            nazevMise = new SimpleStringProperty("");
//        }
//        return nazevMise;
//    }
//
//    public final Double getVyplata() {
//        if (vyplata != null)   
//        return vyplata.get();
//        return 0.0;
//    }
//    public final void setVyplata(Double vyplata) {
//        this.doubleProperty().set(vyplata);
//    }
//    public final DoubleProperty doubleProperty(){
//        if (vyplata == null) {
//            vyplata = new SimpleDoubleProperty(0);
//        }
//        return vyplata;
//    }
//    
//    public final String getZacatekMiseString() {
//        if (zacatekMiseString != null)   
//        return zacatekMiseString.get();
//        return "";
//    }
//    public final void setZacatekMiseString(String zacatekMiseString) {
//        this.stringPropertyzacatekMiseString().set(zacatekMiseString);
//    }
//    public final StringProperty stringPropertyzacatekMiseString(){
//        if (zacatekMiseString == null) {
//            zacatekMiseString = new SimpleStringProperty("");
//        }
//        return zacatekMiseString;
//    }
//    
//    public final String getKonecMiseString() {
//        if (konecMiseString != null)   
//        return konecMiseString.get();
//        return "";
//    }
//    public final void setKonecMiseString(String konecMiseString) {
//        this.stringPropertykonecMiseString().set(konecMiseString);
//    }
//    public final StringProperty stringPropertykonecMiseString(){
//        if (konecMiseString == null) {
//            konecMiseString = new SimpleStringProperty("");
//        }
//        return konecMiseString;
//    }

    public String getZacatekMise() {
        return zacatekMise;
    }

    public String getKonecMise() {
        return konecMise;
    }

    public String getNazevMise() {
        return nazevMise;
    }

    public String getCelkemDnu() {
        return celkemDnu;
    }

    public String getCelkemKc() {
        return celkemKc;
    }

    public void setCelkemKc(String celkemKc) {
        this.celkemKc = celkemKc;
    }

    public double getVyplata() {
        return vyplata;
    }

    public void setVyplata(double vyplata) {
        this.vyplata = vyplata;
    }

    public LocalDate getZacatekMiseDatum() {
        return zacatekMiseDatum;
    }

    public LocalDate getKonecMiseDatum() {
        return konecMiseDatum;
    }

    public double getxOffset() {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOfsset() {
        return yOfsset;
    }

    public void setyOfsset(double yOfsset) {
        this.yOfsset = yOfsset;
    }
}
