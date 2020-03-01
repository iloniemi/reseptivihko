package reseptivihko;

import java.util.LinkedList;
import java.util.List;

public class Rivilista {
    LinkedList<Ainesosarivi> rivit;
    
    public List<Ainesosarivi> haeReseptinRivit(int i) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void lisaa(Ainesosarivi ainesosarivi) {
        // TODO Auto-generated method stub
        
    }
    
    public void poistaReseptinRivit(int i) {
        // TODO Auto-generated method stub
        
    }
    
    public int[] reseptitAinesosalla(int i) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static void main(String[] args) {
        Rivilista rivilista = new Rivilista();
        rivilista.lisaa(new Ainesosarivi(2, 1, 200, "g"));
        rivilista.lisaa(new Ainesosarivi(2, 1, 200, "g"));
        rivilista.haeReseptinRivit(2);
        rivilista.poistaReseptinRivit(2);
        int[] reseptiId = rivilista.reseptitAinesosalla(1);
    }

}
