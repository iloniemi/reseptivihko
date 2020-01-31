package fxReseptivihko;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;

public class ReseptivihkoGUILisaysController implements ModalControllerInterface<String> {
    
    @FXML
    private void lisaaAinesosarivi() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }
    
    @FXML
    private void muokkaaAinesosat() {
        ModalController.showModal(ReseptivihkoGUIController.class.getResource("ReseptivihkoGUIAinesosaView.fxml"), "Ainesosien muokkaus", null, "");
    }
    
    @FXML
    private void poistaAinesosarivi() {        
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }
}