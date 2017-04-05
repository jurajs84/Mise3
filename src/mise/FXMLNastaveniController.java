/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mise;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jirig
 */
public class FXMLNastaveniController implements Initializable {
    
    private static double xOffset = 0;
    private static double yOffset = 0;
    
    @FXML
    private TextField nazevMiseTextField;

    @FXML
    private DatePicker zacatekMiseDatePicker;

    @FXML
    private DatePicker konecMiseDatePicker;

    @FXML
    private TextField vyplataTextField;
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton xButton;

    @FXML
    private JFXButton okButton;
    
    FXMLDocumentController controller = new FXMLDocumentController();
    
    @FXML
    public void handlexButtonAction(ActionEvent event) {
        Stage stage = (Stage) xButton.getScene().getWindow();
        stage.close();
    }
    

    NastaveniMise nastaveni = null;
    NastaveniMise nastaveniLoad = null;

            
    
    @FXML
    public void handleOkButtonAction(ActionEvent event) {
        //vytvoreni nastaveni mise
        LocalDate zacatekMise = zacatekMiseDatePicker.getValue();
        LocalDate konecMise = konecMiseDatePicker.getValue();
        String vyplataNeupravena = vyplataTextField.getText();
        //vymena carky za tecku, aby uzivatel mohl zadavat cislo i s carkou
        String vyplata = vyplataNeupravena.replace(',','.');
        String nazevMise = nazevMiseTextField.getText();
        

        try {
        nastaveni = new NastaveniMise(nazevMise,zacatekMise, konecMise, vyplata);
        if (nastaveniLoad != null) {
            nastaveni.setxOffset(nastaveniLoad.getxOffset());
            nastaveni.setyOfsset(nastaveniLoad.getyOfsset());
        }
        //System.out.println(nastaveni.getNazevMise());
            try {
                //ulozeni
                SerializationUtil.serialize(nastaveni, "NasteveiMise.txt");
            } catch (IOException ex) {
                Logger.getLogger(FXMLNastaveniController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        //zavreni okna
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
        } catch (IllegalArgumentException ex) {
            System.out.println("Chyba : " + ex.getMessage());
        }
        
    }
    //chyceni okna
    @FXML
    public void handlePrsunOknaNacteniAction(MouseEvent event) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }
    @FXML
    public void handlePrsunOknaAction(MouseEvent event) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }
    
    public NastaveniMise getNastaveni() {
        return nastaveni;
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            nastaveniLoad = (NastaveniMise)SerializationUtil.deserialize("NasteveiMise.txt");
        } catch (IOException ex) {
            Logger.getLogger(FXMLNastaveniController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLNastaveniController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //nastaveni = controller.getNastaveni();
        if (nastaveniLoad != null) {
            try {
                nazevMiseTextField.setText(nastaveniLoad.getNazevMise());
                zacatekMiseDatePicker.setValue(Datum.naparsuj(nastaveniLoad.getZacatekMise()));
                konecMiseDatePicker.setValue(Datum.naparsuj(nastaveniLoad.getKonecMise()));
                vyplataTextField.setText(Integer.toString((int)nastaveniLoad.getVyplata()));
            } catch (ParseException ex) {
                Logger.getLogger(FXMLNastaveniController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
    
}
