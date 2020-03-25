package fxReseptivihko;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
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
    @FXML private Label labelVirheet;
    
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
    private Set<Control> virheellisetOsaset = new HashSet<>();
    
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
        String hakuteksti = textAinesosa.getText();
        if (!hakuteksti.matches("^[a-zA-ZÀ-ÿ0-9\\*\\-\\s]*$")) {
            asetaVirhe(textAinesosa, 
                    "Ainesosan hakuteksti ei saa sisältää muita erikoismerkkejä kuin \"*\".");
            return;
        }
        poistaVirheIlmoitukset();
        
        chooserAinesosat.clear();
        List<Ainesosa> ainesosat = this.vihko.haeAinesosat(hakuteksti);
        for (Ainesosa ainesosa : ainesosat) chooserAinesosat.add(ainesosa.getNimi(), ainesosa);
    }
    
    /**
     * Lisää Ainesosan listalle, jos sen nimistä Ainesosaa ei vielä ole.
     */
    private void lisaaAinesosa() {
        String nimi = textAinesosa.getText();
        if (nimi.length() == 0 || !nimi.matches("^[a-zA-ZÀ-ÿ0-9\\-]*$")) {
            asetaVirhe(textAinesosa, 
                    "Ainesosan nimi ei saa tyhjä tai sisältää muita erikoismerkkejä kuin \"-\".");
            return;
        }
        poistaVirheIlmoitukset();
        
        if (this.vihko.lisaaAinesosa(nimi)) paivitaAinesosat();
    }
    
    /**
     * Poistaa Ainesosan, jos se ei ole käytössä missään Reseptissä.
     */
    private void poistaAinesosa() {
        Ainesosa poistettava = chooserAinesosat.getSelectedObject();
        if (poistettava == null) {
            asetaVirhe(chooserAinesosat, "Valitse ensin poistettava ainesosa.");
            return;
        }
        poistaVirheIlmoitukset();
        
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
    
    /** Asettaa virhetekstin virheitä varten olevaan labeliin.
     * Jos virheteksti on null, poistetaan teksti ja virhelabelin korostus.
     * @param osa käyttöliittymän komponentti, jolle asetetaan virhetilamuotoilu.
     * @param virheteksti teksti, joka näytetään käyttäjälle.
     */
    private void asetaVirhe(Control osanen, String virheteksti) {
        osanen.getStyleClass().add("virhe");
        this.virheellisetOsaset.add(osanen);
        labelVirheet.setText(virheteksti);
        labelVirheet.getStyleClass().add("korostus");
    }
    
    /**
     * Poistaa käyttöliittymäkomponenttien virhetilamuotoilut ja 
     * virheilmoituksen virhelabelista.
     */
    private void poistaVirheIlmoitukset() {
        this.virheellisetOsaset.forEach(osanen -> osanen.getStyleClass().removeAll("virhe"));
        labelVirheet.setText("");
        labelVirheet.getStyleClass().removeAll("korostus");
        return;
    }
}
