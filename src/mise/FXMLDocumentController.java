/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mise;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author jirig
 */
public class FXMLDocumentController implements Initializable {
    
    private static double xOffset = 0;
    private static double yOffset = 0;
    
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(); 
    DecimalFormat format = new DecimalFormat("#,###.00", symbols);
    
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Pane paneMenu;
    @FXML
    private Label label;
    @FXML
    private JFXHamburger ham;
    @FXML
    private JFXDrawer menu;
    @FXML
    private JFXButton minimizeButton;
    @FXML
    private JFXButton xButton;
    @FXML
    private Label celkemDnuLabel;
    @FXML
    private Label zaSebouDnuLabel;
    @FXML
    private Label zbyvaDnuLabel;
    @FXML
    private Label celkemKcLabel;
    @FXML
    private Label vydelanoLabel;    
    @FXML
    private Label odpocetLabel;  
    @FXML
    private Label zacatekMiseLabel;
    @FXML
    private Label konecMiseLabel;
    @FXML
    private Label nazevMiseLabel;
    @FXML
    private Label procentaGreenLabel;
    @FXML
    private Label procentaRedLabel;
    @FXML
    private Arc progress;
    
    //zaviraci metoda
    @FXML
    public void handleMinimizeButtonAction(ActionEvent event) {
      try {
                nastaveni = (NastaveniMise)SerializationUtil.deserialize("NasteveiMise.txt");
            } catch (IOException ex) {
                Logger.getLogger(FXMLNastaveniController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FXMLNastaveniController.class.getName()).log(Level.SEVERE, null, ex);
            }
      Stage stage = (Stage) xButton.getScene().getWindow();
      stage.hide();
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMiniWindow.fxml"));
        Parent root1 = loader.load();
        //FXMLNastaveniController nastaveniController = loader.<FXMLNastaveniController>getController();
        loader.setController(loader);
        Stage stage1 = new Stage();
        stage1.initStyle(StageStyle.TRANSPARENT);
        stage1.getIcons().add(new Image("img/miseicona3.png"));
        stage1.setResizable(false);
        Scene scene1 = new Scene(root1);
        scene1.setFill(Color.TRANSPARENT);
        stage1.setScene(scene1);
        stage1.setX(nastaveni.getxOffset());
        stage1.setY(nastaveni.getyOfsset());
        stage1.showAndWait();
        stage.show();
        //this.nastaveni = nastaveniController.getNastaveni();
        //System.out.println(getNastaveni().getNazevMise());
        prekresleni();
    } catch (IOException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    @FXML
    public void handlexButtonAction(ActionEvent event) {
      Stage stage = (Stage) xButton.getScene().getWindow();
      stage.close();
    }
    //zaviraci metoda
    @FXML
    public void handleOkButtonAction(ActionEvent event) {
    Stage stage = (Stage) minimizeButton.getScene().getWindow();
    stage.close();
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
    
    private NastaveniMise nastaveni = new NastaveniMise("Nastav parametry mise", LocalDate.now(), LocalDate.now().plusMonths(4), "0");
    
    public void predani(NastaveniMise nastaveni) {
        this.nastaveni =  nastaveni;
    }
    
    //prekresleni s bindovanim
//    private void prekresleni() {
//        if (nastaveni != null) {
//            nazevMiseLabel.textProperty().bind(nastaveni.stringPropertyNazevMise());
//            konecMiseLabel.textProperty().bind(nastaveni.stringPropertykonecMiseString());
//            zacatekMiseLabel.textProperty().bind(nastaveni.stringPropertyzacatekMiseString());  
//        }
//    }
    
    //prekresleni bez bindovani 
    public void prekresleni () {
        if (nastaveni != null){
        nazevMiseLabel.setText(nastaveni.getNazevMise());
        zacatekMiseLabel.setText(nastaveni.getZacatekMiseDatum().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        konecMiseLabel.setText(nastaveni.getKonecMiseDatum().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        celkemDnuLabel.setText(String.format("%s %s",nastaveni.getCelkemDnu(), formatDne(Integer.parseInt(nastaveni.getCelkemDnu()))));
        celkemKcLabel.setText(celkemVyplata());
        }
    }
    
    private void bindToTime() {
        Timeline timeline = new Timeline(
        new KeyFrame(Duration.seconds(0),
          new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
              zbyvaDnuLabel.setText(zbyvaDnu());
              zaSebouDnuLabel.setText(zaSebouDnu());
              vydelanoLabel.setText(dosudVydelano());
              progress.setLength(vypocetProgress());
              procentaGreenLabel.setText(procenta());
              procentaRedLabel.setText(zbyvaProcent());
            }
          }
        ),
        new KeyFrame(Duration.seconds(1))
      );
      timeline.setCycleCount(Animation.INDEFINITE);
      timeline.play();
     }
    
    public String zbyvaDnu () {
        int sec = 60 - LocalDateTime.now().getSecond();
        int min = 60 - LocalDateTime.now().getMinute();
        int hodiny = 24 - LocalDateTime.now().getHour();
        int dny = (int)LocalDate.now().until(nastaveni.getKonecMiseDatum(), ChronoUnit.DAYS);
        if (dny < 0)
            return String.format("0 dnů");
        return String.format("%s %s %s h. %s min. %s s.", dny,formatDne(dny), hodiny-1, min-1, sec);
    }
    
    private String zaSebouDnu () {
        int sec = LocalDateTime.now().getSecond();
        int min = LocalDateTime.now().getMinute();
        int hodiny = LocalDateTime.now().getHour();
        int dny = (int) nastaveni.getZacatekMiseDatum().until(LocalDate.now(),ChronoUnit.DAYS);
        if (dny > Integer.parseInt(nastaveni.getCelkemDnu()))
            return String.format(nastaveni.getCelkemDnu() + " dnů");
        //LocalDateTime zacatek = nastaveni.getZacatekMiseDatum().atStartOfDay();
        //LocalDateTime konec = nastaveni.getKonecMiseDatum().atStartOfDay();
        return String.format("%s %s %s h. %s min. %s s.", dny,formatDne(dny), hodiny, min, sec);
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
    
    public String dosudVydelano () {
        double castka = (nastaveni.getZacatekMiseDatum().atStartOfDay().until(LocalDateTime.now(), ChronoUnit.MILLIS)*(nastaveni.getVyplata()/86400000));
        castka = Datum.round(castka, 2);
        if (castka > Double.parseDouble(nastaveni.getCelkemKc()))
            castka = Double.parseDouble(nastaveni.getCelkemKc());
        symbols.setGroupingSeparator(' ');
        return String.format("%s Kč", format.format(castka));
    }
    private String celkemVyplata () {
        symbols.setGroupingSeparator(' ');
        return String.format("%s Kč", format.format(Double.parseDouble(nastaveni.getCelkemKc())));
    }
    
    private double vypocetProgress () {       
        double delka = vypocetProgressNeboProcent(360);
        if (delka > 360)
            return 0;
        return 360-delka;
    }
    private String procenta () {        
        double procent = vypocetProgressNeboProcent(100);
        procent = Datum.round(procent, 1);
        if (procent > 100)
            procent = 100;
        return String.format("%s%%", procent);
    }
    private String zbyvaProcent() {
        double procent = vypocetProgressNeboProcent(100);
        procent = Datum.round(100-procent, 1);
        if (procent < 0)
            procent = 0;
        return String.format("%s%%", procent);
    }
    
    private Double vypocetProgressNeboProcent(int hodnota) {
        double pocetDnu = 0;
        if (Double.parseDouble(nastaveni.getCelkemDnu()) == 0) {
            pocetDnu = 1.0;
        }
        else 
            pocetDnu = Double.parseDouble(nastaveni.getCelkemDnu());
        
        return hodnota/(86400*pocetDnu/((int) nastaveni.getZacatekMiseDatum().atStartOfDay().until(LocalDateTime.now(),ChronoUnit.SECONDS)));
    }

    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            //NastaveniMise nastaveniLoad = null;
            //SerializationUtil.serialize(nastaveni, "NasteveiMise.txt");
            
            try {
                nastaveni = (NastaveniMise)SerializationUtil.deserialize("NasteveiMise.txt");
            } catch (IOException ex) {
                Logger.getLogger(FXMLNastaveniController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FXMLNastaveniController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //nastaveni = nastaveniLoad;
            prekresleni();
            bindToTime();
            
            try {
                VBox box = FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));
                menu.setSidePane(box);
                
                //hamburger menu animace settings
                HamburgerSlideCloseTransition prechod = new HamburgerSlideCloseTransition(ham);
                prechod.setRate(-1);
                ham.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    prechod.setRate(prechod.getRate()*-1);
                    prechod.play();
                    
                    //ovladani vysouvani menu
                    if (menu.isShown()){
                        menu.close();
                    }
                    
                    else
                        menu.open(); {
                }
                });
                
                //nastaveni akci tlacitek
                for (Node node : box.getChildren()) {
                    if (node.getAccessibleText() != null) {
                        node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                            switch (node.getAccessibleText()) {
                                case "Nastaveni" : try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLNastaveni.fxml"));
                                    Parent root1 = loader.load();
                                    FXMLNastaveniController nastaveniController = loader.<FXMLNastaveniController>getController();
                                    loader.setController(loader);
                                    Stage stage1 = new Stage();
                                    stage1.getIcons().add(new Image("img/miseicona3.png"));
                                    stage1.initStyle(StageStyle.UNDECORATED);
                                    stage1.setResizable(false);
                                    stage1.setScene(new Scene(root1));
                                    stage1.initModality(Modality.APPLICATION_MODAL);
                                    menu.close();
                                    stage1.showAndWait();
                                    if (nastaveniController.getNastaveni() != null) {
                                        this.nastaveni = nastaveniController.getNastaveni();
                                    }
                                    //System.out.println(getNastaveni().getNazevMise());
                                    prekresleni();
                                } catch (IOException ex) {
                                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                menu.close();
                                prechod.setRate(-1);
                                prechod.play();
                                
                                break;
                                case "Info" : try {
                                    Parent root2 = FXMLLoader.load(getClass().getResource("FXMLInfo.fxml"));
                                    Stage stage2 = new Stage();
                                    stage2.getIcons().add(new Image("img/miseicona3.png"));
                                    stage2.initStyle(StageStyle.UNDECORATED);
                                    stage2.setResizable(false);
                                    stage2.setScene(new Scene(root2));
                                    stage2.initModality(Modality.APPLICATION_MODAL);
                                    stage2.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                menu.close();
                                prechod.setRate(-1);
                                prechod.play();
                                
                                break;
                                case "Exit" : System.exit(0);
                                break;
                            }
                        });
                    }
                }
                
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }     
        }
        

    public NastaveniMise getNastaveni() {
        return nastaveni;
    }
    
}
 