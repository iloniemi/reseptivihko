package reseptivihko.test;

import reseptivihko.*;
import static org.junit.Assert.*;
import java.util.List;
import org.junit.*;

/**
 * @author Juho
 * @version 28 Feb 2020
 * Testataan Reseptilista -luokkaa
 */
public class ReseptilistaTest {
        
    /**
     * Testaa reseptin lisäämistä reseptilistaan
     */
    @Test
    public void testLisaa() {
        Reseptilista reseptilista = new Reseptilista();
        for (int i = 1; i <= 51; i++) {
            Resepti resepti = new Resepti();
            resepti.setId();
            resepti.setNimi("Tee" + i); 
            resepti.setOhje("Kuumenna vesi kiehuvaksi.\nUita teepussia vedessä.\nNauti.");
            reseptilista.lisaa(resepti);
        }
        assertEquals("Lisätty väärä määrä reseptejä", 51, reseptilista.haeReseptit("*").size());
    }
    
    /**
     * Testaa reseptien hakemista
     */
    @Test
    public void testHaeReseptit() {
        Reseptilista reseptilista = new Reseptilista();
        for (int i = 0; i < 3; i++) {
            Resepti resepti = new Resepti();
            resepti.setId();
            resepti.setNimi("Tee" + i); 
            resepti.setOhje("Kuumenna vesi kiehuvaksi.\nUita teepussia vedessä.\nNauti.");
            reseptilista.lisaa(resepti);
        }
        Resepti pelkkaTee = new Resepti();
        pelkkaTee.setId();
        pelkkaTee.setNimi("Tee"); 
        pelkkaTee.setOhje("Kuumenna vesi kiehuvaksi.\nUita teepussia vedessä.\nNauti.");
        reseptilista.lisaa(pelkkaTee);
        for (int i = 0; i < 2; i++) {
            Resepti resepti = new Resepti();
            resepti.setId();
            resepti.setNimi("" + i +"Tee"); 
            resepti.setOhje("Kuumenna vesi kiehuvaksi.\nUita teepussia vedessä.\nNauti.");
            reseptilista.lisaa(resepti);
        }
        
        List<Resepti> haun1tulos = reseptilista.haeReseptit(" tee   ");
        assertEquals("Väärä määrä reseptejä haun tuloksena", 1, haun1tulos.size());
        assertEquals("Piti tulla pelkästään tee", pelkkaTee, haun1tulos.get(0));
        
        List<Resepti> haun2tulos = reseptilista.haeReseptit("tee*");
        assertEquals("Piti tulla tee ja teei:t", 4, haun2tulos.size());
        
        List<Resepti> haun3tulos = reseptilista.haeReseptit("*tee*");
        assertEquals("Piti tulla kaikki teet", 6, haun3tulos.size());

        List<Resepti> haun4tulos = reseptilista.haeReseptit("*tee");
        assertEquals("Piti tulla tee ja itee:t", 3, haun4tulos.size());
        
        List<Resepti> haun5tulos = reseptilista.haeReseptit("*");
        assertEquals("Piti tulla kaikki teet", 6, haun5tulos.size());
    }

    /**
     * Testaa reseptin poistamista
     */
    @Test
    public void testPoistaResepti() {
        Reseptilista reseptilista = new Reseptilista();
        Resepti resepti1 = new Resepti();
        try {resepti1.parse("1|Tee1|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti.");            
        } catch (VirheellinenSyottotietoException e) { e.getMessage(); };
        Resepti resepti = new Resepti();
        resepti.setId();
        resepti.setNimi("Tee2"); 
        reseptilista.lisaa(resepti);
        
        reseptilista.poistaResepti(1);
        List<Resepti> hauntulos = reseptilista.haeReseptit("*");
        assertEquals("Väärä määrä Reseptejä", 1, hauntulos.size());
        assertEquals("Oikea määrä Reseptejä, mutta väärä resepti", resepti, hauntulos.get(0));        
        }
    
    /**
     * Testaa reseptin hakemista
     */
    @Test
    public void testHaeResepti() {
        Reseptilista reseptilista = new Reseptilista();
        Resepti resepti1 = new Resepti();
        try {resepti1.parse("1|Tee1|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti.");            
        } catch (VirheellinenSyottotietoException e) { e.getMessage(); };
        Resepti resepti2 = new Resepti();
        try {resepti2.parse("2|Tee2|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti.");            
        } catch (VirheellinenSyottotietoException e) { e.getMessage(); };
        
        assertEquals("Väärä määrä Reseptejä", resepti2, reseptilista.haeResepti(2));      
        assertEquals("Väärä määrä Reseptejä", resepti1, reseptilista.haeResepti(1));      
    }
}
