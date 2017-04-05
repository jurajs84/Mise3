/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mise;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author jirig
 */
public class FXMLMiniWindowController implements Initializable {
    
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(); 
    DecimalFormat format = new DecimalFormat("#,###.00", symbols);
    
    NastaveniMise nastaveniLoad = null;
    
    private static double miniWindowXOffset = 0;
    private static double miniWindowYOffset = 0;
    
    private FXMLDocumentController controller = new FXMLDocumentController();
    
    @FXML
    private AnchorPane AnchorPane;
    
    @FXML
    private Label minivydelanoLabel;

    @FXML
    private Label miniZbyvaLabel;
    
    @FXML
    public void handleAnchorButtonAction(MouseEvent event) {
        Stage stage = (Stage) miniZbyvaLabel.getScene().getWindow();
        if (event.getClickCount() == 2) {
            if (nastaveniLoad != null) {
                nastaveniLoad.setxOffset(stage.getX());
                nastaveniLoad.setyOfsset(stage.getY());
                try {
                    //ulozeni
                    SerializationUtil.serialize(nastaveniLoad, "NasteveiMise.txt");
                } catch (IOException ex) {
                    Logger.getLogger(FXMLNastaveniController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            stage.hide();
            }
    }
    
    private void bindToTime() {
        Timeline timeline = new Timeline(
        new KeyFrame(Duration.seconds(0),
          new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
              miniZbyvaLabel.setText(zbyvaDnu());
              minivydelanoLabel.setText(dosudVydelano());
            }
          }
        ),
        new KeyFrame(Duration.seconds(1))
      );
      timeline.setCycleCount(Animation.INDEFINITE);
      timeline.play();
     }
    
    public String dosudVydelano () {
        double castka = (nastaveniLoad.getZacatekMiseDatum().atStartOfDay().until(LocalDateTime.now(), ChronoUnit.MILLIS)*(nastaveniLoad.getVyplata()/86400000));
        castka = Datum.round(castka, 2);
        if (castka > Double.parseDouble(nastaveniLoad.getCelkemKc()))
            castka = Double.parseDouble(nastaveniLoad.getCelkemKc());
        symbols.setGroupingSeparator(' ');
        return String.format("Vyděláno: %s Kč", format.format(castka));
    }
    public String zbyvaDnu () {
        int sec = 60 - LocalDateTime.now().getSecond();
        int min = 60 - LocalDateTime.now().getMinute();
        int hodiny = 24 - LocalDateTime.now().getHour();
        int dny = (int)LocalDate.now().until(nastaveniLoad.getKonecMiseDatum(), ChronoUnit.DAYS);
        if (dny < 0)
            return String.format("0 dnů");
        return String.format("Zbývá: %s %s %s h. %s min. %s s.", dny,formatDne(dny), hodiny-1, min-1, sec);
    }
    private String formatDne (int dny) {
        String formatDny = "";
        if (dny < 5 && dny > 1)
            formatDny = "dny";
        else if (dny == 1)
            formatDny = "den";
        else
            formatDny = "dnů";
        return formatDny;
    }
    
    //chyceni okna
    @FXML
    public void handlePrsunOknaNacteniAction(MouseEvent event) {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        miniWindowXOffset = stage.getX() - event.getScreenX();
        miniWindowYOffset = stage.getY() - event.getScreenY();
    }
    @FXML
    public void handlePrsunOknaAction(MouseEvent event) {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.setX(event.getScreenX() + miniWindowXOffset);
        stage.setY(event.getScreenY() + miniWindowYOffset);
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            nastaveniLoad = (NastaveniMise)SerializationUtil.deserialize("NasteveiMise.txt");
        } catch (IOException ex) {
            Logger.getLogger(FXMLNastaveniController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLNastaveniController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (nastaveniLoad != null)
            bindToTime();

    }    
    
}
