package fxReseptivihko;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import reseptivihko.Ainesosa;
import reseptivihko.Ainesosarivi;
import reseptivihko.Resepti;
import reseptivihko.Reseptivihko;

/**
 * @author Juho
 * @version 14 Jan 2020
 * 
 */
public class ReseptivihkoGUIController implements Initializable {
    
    @FXML private TextField textReseptiHaku;
    @FXML private TextField textKerroin;
    @FXML private ListChooser<Resepti> chooserReseptit;
    @FXML private ListChooser<Ainesosa> chooserValitutAinesosat;
    @FXML private ListChooser<Ainesosa> chooserAinesosat;
    @FXML private Label labelReseptinNimi;
    @FXML private TextArea textAreaTyoOhje;
    @FXML private StringGrid<Ainesosarivi> stringGridRivit;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
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
    private void handleHaeReseptit() {
        paivitaReseptit();
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
    }

    @FXML
    private void muokkaaReseptia() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }
    
    @FXML
    private void handleNaytaResepti() {
        naytaResepti();
    }

    @FXML
    private void  muokkkaaReseptia() {
        Dialogs.showMessageDialog("Tämä toiminto ei ole vielä käytössä.");
    }

    @FXML
    private void  handlePaivitaRivit() {
        paivitaRivit();
    }
    
 //-----------------------------------------------------------------------------
 // Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia
    
    private Reseptivihko vihko;
    
    private void alusta() {
        // TODO Auto-generated method stub
        setVihko(Reseptivihko.mallivihko());
        chooserValitutAinesosat.clear();
        paivitaReseptit();
    }
    
    /**
     * Asettaa Reseptivihon
     */
    private void setVihko(Reseptivihko vihko) {
        this.vihko = vihko;
    }
    
    /**
     * Päivittää näytettävät Reseptien nimet
     */
    private void paivitaReseptit() {
        chooserReseptit.clear();
        String hakuteksti = textReseptiHaku.getText();
        //TODO: varmista, etta tama on toimiva menetelma 
        ArrayList<Ainesosa> valitsimenAinesosat = chooserValitutAinesosat.getItems().stream()
            .map(stringJaObject -> stringJaObject.getObject())
            .collect(Collectors.toCollection(ArrayList::new));
        this.vihko.haeReseptit(hakuteksti, valitsimenAinesosat).stream()
            .forEach(resepti -> chooserReseptit.add(resepti.getNimi(), resepti));
    }
    
    /**
     * Näyttää valitun reseptin
     */
    private void naytaResepti() {
        Resepti resepti = chooserReseptit.getSelectedObject();
        labelReseptinNimi.setText(resepti.getNimi());
        paivitaRivit();
        textAreaTyoOhje.setText(resepti.getOhje());
    }
    
    /**
     * Päivittää näytettävät Ainesosarivit
     */
    private void paivitaRivit() {
        Resepti resepti = chooserReseptit.getSelectedObject();
        stringGridRivit.clear();
        Collection<Ainesosarivi> reseptinRivit = this.vihko.haeRivit(resepti);
        if (reseptinRivit == null) return;
        for (Ainesosarivi ainesosarivi: reseptinRivit) {
            // Määrä tekstiksi
            String maara = String.format("%.1f", ainesosarivi.getMaara()*this.kerroin());
            //Haetaan ainesosa
            Ainesosa ainesosa = this.vihko.haeAinesosa(ainesosarivi.getAinesosaId());
            if (ainesosa == null) continue;
            //Kootaan esitettävän rivi
            String[] taulukonrivi = {maara, ainesosarivi.getYksikko(), ainesosa.getNimi()};
            //Lisätään rivi
            stringGridRivit.add(ainesosarivi, taulukonrivi);
        }
    }
    
    /** Lukee kerroin kentästä kertoimen ja plauttaa sen.
     * Lukemisen epäonnistuessa esimerkiksi virheellisen kertoimen takia
     * palauttaa kertoimen 1.0
     * @return Luettu kerroin.
     */
    private double kerroin() {
        double kerroin = 1.0;
        try { kerroin = Double.parseDouble(textKerroin.getText());
        } catch (NumberFormatException e) {e.getMessage();}
        return kerroin;
    }
}
