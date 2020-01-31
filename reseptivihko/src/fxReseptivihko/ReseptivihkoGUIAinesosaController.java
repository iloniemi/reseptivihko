package fxReseptivihko;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;

/**
 * @author Juho
 * @version 31 Jan 2020
 * 
 */
public class ReseptivihkoGUIAinesosaController implements ModalControllerInterface<String> {
    @FXML
    private void uusiAinesosa() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }
    
    @FXML
    private void paivitaAinesosat() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }
    
    @FXML
    private void poistaAinesosa() {
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
    public void setDefault(String arg0) {
        // TODO Auto-generated method stub
        
    }
}
