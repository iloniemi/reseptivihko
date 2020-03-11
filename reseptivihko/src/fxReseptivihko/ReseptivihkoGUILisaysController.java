package fxReseptivihko;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;
import reseptivihko.Ainesosa;
import reseptivihko.Ainesosarivi;
import reseptivihko.Resepti;
import reseptivihko.Reseptivihko;

/**
 * @author Juho
 * @version 27 Feb 2020
 *
 */
public class ReseptivihkoGUILisaysController implements ModalControllerInterface<Pair<Reseptivihko, Resepti>> {
    @FXML private TextArea textAreaTyoOhje;
    @FXML private TextField textReseptinNimi;
    @FXML private TextField textAinesosa;
    @FXML private TextField textMaara;
    @FXML private TextField textYksikko;
    @FXML private Button buttonLisaaRivi;
    @FXML private Button buttonPoistaRivi;
    @FXML private StringGrid<Integer> stringGridRivit;
    @FXML private ListChooser<Ainesosa> chooserAinesosat; //TODO: Jotenkin tuoda ainesosatkin tanne
    
    
    
    
    @FXML
    private void handleLisaaAinesosarivi() {
        lisaaAinesosarivi();
    }

    @FXML
    private void handlePoistaAinesosarivi() {        
        poistaAinesosarivi();
    }
        
    @FXML
    private void handleAinesosaValittu() {        
        this.valittuAinesosa = chooserAinesosat.getSelectedObject();
    }
    
    @FXML
    private void handleMuokkaaAinesosat() {
        ModalController.showModal(ReseptivihkoGUIController.class.getResource("ReseptivihkoGUIAinesosaView.fxml"), "Ainesosien muokkaus", null, "");
    }
    
    @FXML
    private void handleTallenna() {
        tallenna();
        ModalController.closeStage(textReseptinNimi);
    }

    @FXML
    private void handlePeruuta() {
        ModalController.closeStage(textReseptinNimi);
    }

    @FXML
    private void handlePaivitaAinesosat() {
        paivitaAinesosat();
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    //-------------------------------------------------------------------------
    
    private Reseptivihko vihko;
    private Resepti resepti;
    private int riveja = 0;
    private Ainesosa valittuAinesosa = null;


    @Override
    public void setDefault(Pair<Reseptivihko, Resepti> vihkoJaResepti) {
        this.vihko = vihkoJaResepti.getKey();
        this.resepti = vihkoJaResepti.getValue();
        textAreaTyoOhje.setText(this.resepti.getOhje());
        textReseptinNimi.setText(this.resepti.getNimi());
        
        //TODO: Tästä funktio, koska mainissakin samanlainen?
        stringGridRivit.clear();
        Collection<Ainesosarivi> reseptinRivit = this.vihko.haeRivit(this.resepti);
        if (reseptinRivit == null) return;
        for (Ainesosarivi ainesosarivi: reseptinRivit) {
            // Määrä tekstiksi
            String maara = String.format("%.4f", ainesosarivi.getMaara());
            //Haetaan ainesosa
            Ainesosa ainesosa = this.vihko.haeAinesosa(ainesosarivi.getAinesosaId());
            if (ainesosa == null) continue;
            //Kootaan esitettävän rivi
            String[] taulukonrivi = {maara, ainesosarivi.getYksikko(), ainesosa.getNimi()};
            //Lisätään rivi
            stringGridRivit.add(ainesosa.getId(), taulukonrivi);
            this.riveja++;
        }
        paivitaAinesosat();
    }

    @Override
    public Pair<Reseptivihko, Resepti> getResult() {
        Pair<Reseptivihko, Resepti> palautettava = new Pair<Reseptivihko, Resepti>(this.vihko, this.resepti);
        return palautettava;
    }
    
    /**
     * Tallentaa Reseptiin nimen ja työohjeen, asettaa Reseptille id:n, 
     * lisää sen vihkoon sekä
     * poistaa reseptin vanhat rivit ja lisää uudet rivit vihkoon. 
     */
    private void tallenna() {
        this.resepti.setId();
        this.resepti.setNimi(textReseptinNimi.getText());
        this.resepti.setOhje(textAreaTyoOhje.getText());
        this.vihko.lisaa(this.resepti);
        this.vihko.poistaReseptinRivit(this.resepti);
        for (int riviNro = 0; riviNro < this.riveja; riviNro++) {
            double maara = Double.parseDouble(this.stringGridRivit.get(riviNro, 0).replace(',', '.'));
            String yksikko = this.stringGridRivit.get(riviNro, 1);
            Ainesosarivi rivi = new Ainesosarivi(this.resepti.getId(), stringGridRivit.getObject(riviNro), maara, yksikko);
            this.vihko.lisaa(rivi);
        }
    }
    
    private void vahennaRiveja() {
        if (this.riveja > 0) this.riveja--;
    }
    
    private void lisaaAinesosarivi() {
        String[] osaset = new String[3];
        if (!(textMaara.getText().matches("^[0-9]+(((,|.)([0-9]*))?$)"))) return;
        if (this.valittuAinesosa == null) return;
        osaset[0] = textMaara.getText();
        osaset[1] = textYksikko.getText();
        osaset[2] = this.valittuAinesosa.getNimi();
        stringGridRivit.add(this.valittuAinesosa.getId(), osaset);
        this.riveja++;
    }
    
    private void poistaAinesosarivi() {
        //TODO: tarkista, etta getRowNr antaa oikean rivinumeron aina
        poistaRiviStringGridista(this.stringGridRivit.getRowNr());
        vahennaRiveja();
    }
    
    private void poistaRiviStringGridista(int riviNro) {
        ArrayList<Integer> luvut = new ArrayList<Integer>();
        ArrayList<String[]> solut = new ArrayList<String[]>();
        
        for (int i = 0; i < this.riveja; i++) {
            if (i == riviNro) continue;
            luvut.add(this.stringGridRivit.getObject(i));
            String[] rivinSolut = new String[3];
            rivinSolut[0] = this.stringGridRivit.get(i, 0);
            rivinSolut[1] = this.stringGridRivit.get(i, 1);
            rivinSolut[2] = this.stringGridRivit.get(i, 2);
            solut.add(rivinSolut);
        }
        this.stringGridRivit.clear();
        for (int i = 0; i < luvut.size(); i++) {
            this.stringGridRivit.add(luvut.get(i), solut.get(i));
        }
    }
        
    private void paivitaAinesosat() {
        this.chooserAinesosat.clear();
        Collection<Ainesosa> ainesosat = this.vihko.haeAinesosat(this.textAinesosa.getText());
        if (ainesosat == null) return;
        for (Ainesosa ainesosa: ainesosat) {
            this.chooserAinesosat.add(ainesosa.getNimi(), ainesosa);
        }
    }
}