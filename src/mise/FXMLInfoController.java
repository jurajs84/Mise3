/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mise;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jirig
 */
public class FXMLInfoController implements Initializable {
    
    private static double xOffset = 0;
    private static double yOffset = 0;
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton xButton;

    @FXML
    private JFXButton okButton;
    
    @FXML
    public void handlexButtonAction(ActionEvent event) {
    Stage stage = (Stage) xButton.getScene().getWindow();
    stage.close();
    }
    @FXML
    public void handleOkButtonAction(ActionEvent event) {
    Stage stage = (Stage) okButton.getScene().getWindow();
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
    
    
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
