package fxReseptivihko;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author Juho
 * @version 14 Jan 2020
 * 
 */
public class ReseptivihkoGUIController {
    @FXML private TextField textHaku;
    @FXML private ListChooser<String> listReseptit; // Muuta String Resepti -luokaksi kunhan sellainen on!
    
    @FXML
    private void tallenna() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }

    @FXML
    private void avaaReseptivihko() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }
 
    @FXML
    private void tulostaResepti() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }

    @FXML
    private void lopeta() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }
    
    @FXML
    private void apua() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }
    
    @FXML
    private void tietoja() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }

    @FXML
    private void haeReseptit() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }

    @FXML
    private void paivitaAinesosat() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }

    @FXML
    private void lisaaAinesosa() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }

    @FXML
    private void poistaAinesosa() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }

    @FXML
    private void uusiResepti() {
        ModalController.showModal(ReseptivihkoGUIController.class.getResource("ReseptivihkoGUILisaysView.fxml"), "Reseptin lisäys", null, "");
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }

    @FXML
    private void muokkaaReseptia() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }
    
    @FXML
    private void naytaResepti() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }
    
    @FXML
    private void  muokkkaaReseptia() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }

    @FXML
    private void  paivitaMaarat() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }
}
