package reseptivihko;

import java.util.ArrayList;

public class Reseptilista {
    private int lkm = 0;
    private int maxLkm = 50;
    private Resepti[] reseptit;

    
    public void lisaa(Resepti resepti) {
        // TODO Auto-generated method stub
        
    }
    
    public Resepti haeResepti(int reseptinId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void poistaResepti(int i) {
        // TODO Auto-generated method stub
        
    }

    public ArrayList<Resepti> haeReseptit(String hakusanat) {
        
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Metodi Reseptilista -luokan testaamiseen.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Reseptilista reseptilista = new Reseptilista();
        for (int i = 0; i < 5; i++) reseptilista.lisaa(Resepti.luoTee());
        try {reseptilista.lisaa(new Resepti().parse("5|Tee|Kuumenna vesi kiehuvaksi.§Uita teepussia vedessä.§Nauti."));
        } catch (VirheellinenSyottotietoException e) {System.out.println(e.getMessage());};
        for (int i = 0; i < 5; i++) reseptilista.lisaa(Resepti.luoTee());
        ArrayList<Resepti> teeReseptit = reseptilista.haeReseptit("*tee*");
        System.out.println(teeReseptit.size());
        Resepti teeResepti = reseptilista.haeResepti(5);
        System.out.println(teeResepti.toString());
    }


    

}
