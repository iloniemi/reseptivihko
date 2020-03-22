package fxReseptivihko;

import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;

/**
 * Tulostus ikkunan kontrolleri.
 * @author Juho
 * @version 22 Mar 2020
 */
public class ReseptivihkoGUITulostusController implements ModalControllerInterface<String> {
    @FXML private TextArea textAreaTiedot;
    
    @FXML
    private void handleTulosta() {
        tulosta();
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
    public void setDefault(String tiedot) {
        textAreaTiedot.setText(tiedot);        
    }
//===========================================================================================
// Ei suoraan käyttöliittymään liittyvää koodia tämän jälkeen.
    
    /**
     * Tulostaa textAreassa olevan tekstin.
     */
    private void tulosta() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if ( job != null && job.showPrintDialog(null) ) {
            WebEngine webEngine = new WebEngine();
            webEngine.loadContent("<pre>" + textAreaTiedot.getText() + "</pre>");
            webEngine.print(job);
            job.endJob();
            }
    }
}
