package fxReseptivihko;

import java.util.List;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import reseptivihko.Ainesosa;
import reseptivihko.Resepti;
import reseptivihko.Reseptivihko;

/**
 * @author Juho
 * @version 31 Jan 2020
 * 
 */
public class ReseptivihkoGUIAinesosaController implements ModalControllerInterface<Reseptivihko> {
    @FXML private TextField textAinesosa;
    @FXML private ListChooser<Ainesosa> chooserAinesosat;
    
    @FXML
    private void handleUusiAinesosa() {
        lisaaAinesosa();
    }
    
    @FXML
    private void handlePaivitaAinesosat() {
        paivitaAinesosat();
    }
    
    @FXML
    private void handlePoistaAinesosa() {
        poistaAinesosa();
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public Reseptivihko getResult() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void setDefault(Reseptivihko oletus) {
        alusta(oletus);
    }
//============================================================================================
    // Loppu FXML liittymätöntä koodia.
    
    private Reseptivihko vihko;
    /**
     * Alustaa ruudun.
     * @param reseptivihko jota käytetään.
     */
    private void alusta(Reseptivihko reseptivihko) {
        this.vihko = reseptivihko;
        textAinesosa.clear();
        chooserAinesosat.clear();
        paivitaAinesosat();
    }
    
    /**
     * Päivittää Ainesosia näyttävän ListChooserin.
     */
    private void paivitaAinesosat() {
        chooserAinesosat.clear();
        List<Ainesosa> ainesosat = this.vihko.haeAinesosat(textAinesosa.getText());
        for (Ainesosa ainesosa : ainesosat) chooserAinesosat.add(ainesosa.getNimi(), ainesosa);
    }
    
    /**
     * Lisää Ainesosan listalle, jos sen nimistä Ainesosaa ei vielä ole.
     */
    private void lisaaAinesosa() {
        if (this.vihko.lisaaAinesosa(textAinesosa.getText())) paivitaAinesosat();
    }
    
    /**
     * Poistaa Ainesosan, jos se ei ole käytössä missään Reseptissä.
     */
    private void poistaAinesosa() {
        Ainesosa poistettava = chooserAinesosat.getSelectedObject();
        if (poistettava == null) return;
        if (!Dialogs.showQuestionDialog("Poisto", 
                String.format("Poistetaanko %s?",poistettava.getNimi()), "Kyllä", "Ei")) return;
        List<Resepti> poistettavanReseptit = this.vihko.poistaAinesosa(poistettava);
        if (poistettavanReseptit.size() > 0) {
            StringBuilder viesti = new StringBuilder(poistettava.getNimi())
                    .append(" ei poistettu, koska sitä käytetään resepteissä:");
            for (Resepti resepti: poistettavanReseptit) 
                viesti.append('\n').append(resepti.getNimi());
            Dialogs.showMessageDialog(viesti.toString());
            return;
        }
        paivitaAinesosat();
    }
}
