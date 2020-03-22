package fxReseptivihko;

import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/** Kontrolleri tietoja ikkunalle.
 * @author Juho
 * @version 22 Mar 2020
 */
public class ReseptivihkoGUITietojaController implements ModalControllerInterface<String[]> {
    @FXML private Label ohjelma;
    @FXML private Label versio;
    @FXML private Label tekija;
    
    @Override
    public String[] getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String[] oletus) {
        alusta(oletus);
    }
  //===========================================================================================
 // Ei suoraan käyttöliittymään liittyvää koodia tämän jälkeen.
    
    /** Asettaa tekstit labeleihin.
     * @param tekstit ohjelman nimi, versio, tekijä.
     */
    private void alusta(String[] tekstit) {
        Label[] labelit = {ohjelma, versio, tekija};
        
        if (tekstit == null) return;
        for (int i = 0; i < tekstit.length && i < 3; i++) {
            labelit[i].setText(tekstit[i]);
        }
    }
}
