package reseptivihko;

public class Reseptilista {
    private int lkm = 0;
    private int maxLkm = 50;
    private Resepti[] reseptit;

    /**
     * Metodi Reseptilista -luokan testaamiseen.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Reseptilista reseptilista = new Reseptilista();
        for (int i = 0; i < 5; i++) {
            reseptilista.lisaa(Resepti.luoTee());
        }
        
        
    }

    

}
