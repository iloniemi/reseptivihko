package fxReseptivihko;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.util.Pair;
import reseptivihko.Ainesosa;
import reseptivihko.Ainesosarivi;
import reseptivihko.Resepti;
import reseptivihko.Reseptivihko;
import reseptivihko.VirheellinenSyottotietoException;

/**
 * @author Juho
 * @version 14 Jan 2020
 * 
 */
public class ReseptivihkoGUIController implements Initializable {
    
    @FXML private TextField textAinesosaHaku;
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
    private void handleTallenna() {
        tallenna();
    }

    @FXML
    private void handleAvaaReseptivihko() {
        vaihdaVihkoa();
    }
 
    @FXML
    private void handleTulostaResepti() {
        tulostaResepti();
    }

    @FXML
    private void handleLopeta() {
        if (saakoSulkea()) Platform.exit();
    }
    
    @FXML
    private void handleApua() {
        apua();
    }
    
    @FXML
    private void handleTietoja() {
        ohjelmanTiedot();
    }

    @FXML
    private void handleHaeReseptit() {
        paivitaReseptit();
    }

    @FXML
    private void handlePaivitaAinesosat() {
        paivitaAinesosat();
    }

    @FXML
    private void handleLisaaAinesosaListalle() {
        lisaaAinesosaListalle();
    }

    @FXML
    private void handlePoistaAinesosalistalta() {
        poistaAinesosaListalta();
    }

    @FXML
    private void handleUusiResepti() {
        reseptinKasittely(new Resepti());
    }

    @FXML
    private void muokkaaReseptia() {
        if (this.valittuResepti != null) reseptinKasittely(this.valittuResepti);
    }
    
    
    @FXML
    private void handlePoistaResepti() {
        poistaResepti();
    }

    @FXML
    private void handleNaytaResepti() {
        naytaResepti();
    }

    @FXML
    private void  handlePaivitaRivit() {
        paivitaRivit();
    }
    
 //-----------------------------------------------------------------------------
 // Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia
    
    //TODO: lisää reseptin poistaminen
    //TODO: aineosilla haku
    
    
    private Reseptivihko vihko;
    private Resepti valittuResepti = null;
    
    private void alusta() {
        valittuResepti = null;
        textAinesosaHaku.clear();
        chooserAinesosat.clear();
        chooserValitutAinesosat.clear();
        labelReseptinNimi.setText("");
        stringGridRivit.clear();
        textAreaTyoOhje.clear();
        textKerroin.setText("1");
    }
    
    /**
     * Asettaa Reseptivihon
     * @param vihko Avattava Reseptivihko.
     */
    public void setVihko(Reseptivihko vihko) {
        this.vihko = vihko;
        try {
            vihko.lue();
        } catch (VirheellinenSyottotietoException e) {
            this.vihko = new Reseptivihko();
            Dialogs.showMessageDialog("Virheitä luettavissa tiedostoissa.\n"
                    + "Avattiin uusi reseptivihko.\nVirheviesti:\n"
                    + e.getMessage());
        }
        paivitaKaikki();
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
        this.valittuResepti = chooserReseptit.getSelectedObject();
        paivitaNimi();
        paivitaRivit();
        paivitaOhje();
    }

    /**
     * Lisää ainesosan hakuehtoja sisältävään chooseriin.
     */
    private void lisaaAinesosaListalle() {
        Ainesosa valittu = chooserAinesosat.getSelectedObject();
        if (valittu == null) return;
        List<Ainesosa> valitut = chooserValitutAinesosat.getObjects();
        if (valitut == null) return;
        if (valitut.contains(valittu)) return;
        chooserValitutAinesosat.add(valittu.getNimi(), valittu);
        paivitaReseptit();
    }
    
    /**
     * Poistaa ainesosan hakuehtoja sisältävästä chooserista.
     */
    private void poistaAinesosaListalta() {
        Ainesosa valittu = chooserValitutAinesosat.getSelectedObject();
        if (valittu == null) return;
        List<Ainesosa> valitut = chooserValitutAinesosat.getObjects();
        valitut.removeIf(ainesosa -> ainesosa == valittu);
        chooserValitutAinesosat.clear();
        for (Ainesosa ainesosa: valitut) chooserValitutAinesosat.add(ainesosa.getNimi(), ainesosa);
        paivitaReseptit();
    }
    
    /**
     * Päivittää hakua varten näytettävät ainesosat.
     */
    private void paivitaAinesosat() {
        chooserAinesosat.clear();
        ArrayList<Ainesosa> ainesosat = this.vihko.haeAinesosat(textAinesosaHaku.getText());
        for (Ainesosa ainesosa : ainesosat) chooserAinesosat.add(ainesosa.getNimi(), ainesosa);
    }

    /**
     * Päivittää näytetävän Reseptin nimen.
     */
    private void paivitaNimi() {
        if (this.valittuResepti == null) return;
        labelReseptinNimi.setText(this.valittuResepti.getNimi());
    }
    
    /**
     * Päivittää työohjeen.
     */
    private void paivitaOhje() {
        if (this.valittuResepti == null) return;
        textAreaTyoOhje.setText(this.valittuResepti.getOhje());
    }
    
    /**
     * Päivittää näytettävät Ainesosarivit
     */
    private void paivitaRivit() {
        if (this.valittuResepti == null) return;
        
        stringGridRivit.clear();
        Collection<Ainesosarivi> reseptinRivit = this.vihko.haeRivit(this.valittuResepti);
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
    
    /** Avaa ikkunan Reseptin käsittelyä varten.
     * Jos palautetulle Reseptille annettiin id eli se tallennettiin,
     * asetetaan se valituksi reseptiksi.
     * @param vihkoJaResepti Pari, jossa on Reseptivihko ja Resepti
     */
    private void reseptinKasittely(Resepti resepti) {
        Pair<Reseptivihko, Resepti> tulos = ModalController
                .showModal(ReseptivihkoGUIController.class
                        .getResource("ReseptivihkoGUILisaysView.fxml"), "Reseptin lisäys", 
                        null, new Pair<Reseptivihko, Resepti>(this.vihko, resepti));
        Resepti saatu = tulos.getValue();
        if (saatu.getId() > 0) this.valittuResepti = saatu;
        paivitaKaikki();
    }
    
    /**
     * Poistaa valitun Reseptin.
     */
    private void poistaResepti() {
        if (valittuResepti == null) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko " 
                + this.valittuResepti.getNimi() + "?", "Kyllä", "Ei") ) return;
        this.vihko.poistaResepti(this.valittuResepti);
        this.valittuResepti = null;
        paivitaKaikki();
    }
    
    /**
     * Päivittää päänäkymän.
     */
    private void paivitaKaikki() {
        tyhjennaReseptiNakyma();
        paivitaReseptit();
        paivitaNimi();
        paivitaOhje();
        paivitaRivit();
        paivitaAinesosat();
    }
    
    /**
     * Tyhjentää valitun Reseptin tietoja esittelevät kentät.
     */
    private void tyhjennaReseptiNakyma() {
        labelReseptinNimi.setText("");
        textAreaTyoOhje.clear();
        stringGridRivit.clear();
    }
    
    /**
     * Tallentaa tiedot.
     */
    private void tallenna() {
        this.vihko.tallenna();
    }
    
    /** Tarkistaa halutaanko tallentaa tehdyt muutokset ennenkuin antaa luvan sulkea.
     * @return saako ohjelman sulkea.
     */
    public boolean saakoSulkea() {
        if (!this.vihko.muutoksia()) return true;
        if (Dialogs.showQuestionDialog("Tallennetaanko?", "Vihkoon on tehty muutoksia.\n"
                + "Haluatko tallentaa ne ennen sulkemista?","Kyllä", "Ei") ) tallenna();
        return true;
    }
    
    /**
     * Avaa tiedostonvalitsimen ja antaa valita kansion.
     * Luodaan uusi vihko, jonka tallennuskansioksi asetetaan valittu kansio.
     * Jos kansiosta löytyy tarvittavat tiedostot, ne luetaan.
     */
    private void vaihdaVihkoa()  {
        DirectoryChooser valitsin = new DirectoryChooser();
        valitsin.setInitialDirectory(new File("."));
        File valittuKansio = valitsin.showDialog(null);
        if (valittuKansio == null) return; 
        Dialogs.showMessageDialog("Avattiin\n" + valittuKansio.getName());
        Reseptivihko uusiVihko = new Reseptivihko();
        uusiVihko.asetaKansio(valittuKansio.toString());
        alusta();
        setVihko(uusiVihko);
    }
    
    /**
     * Avataan suunnitelman selaimeeen.
     */
    private void apua() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2020k/ht/jumailon");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
    
    /**
     * Avaa tulostusikkunan ja vie siihen valitun Reseptin tiedot.
     */
    private void tulostaResepti() {
        if (this.valittuResepti == null) return;
        ModalController.showModal(ReseptivihkoGUIController.class
                .getResource("ReseptivihkoGUITulostusView.fxml"), "Reseptin tulostus", 
                null, this.vihko.reseptiMerkkijonona(this.valittuResepti));
    }
    
    /**
     * Avaa ohjelman tiedot esittelevän ikkunan.
     */
    private void ohjelmanTiedot() {
        String[] tiedot = {"Reseptivihko", "Versio 1.1", "Juho Iloniemi"};
        ModalController.showModal(ReseptivihkoGUIController.class
                .getResource("ReseptivihkoGUITietojaView.fxml"), "Tietoja", 
                null, tiedot);
    }
}
