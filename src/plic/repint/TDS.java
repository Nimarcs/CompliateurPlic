package plic.repint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TDS {

    private static TDS tds;

    private Map<Entree, Symbole>  map;

    int cptDepl;

    private TDS(){
        map = new HashMap<>();
        cptDepl = 0;
    }

    public static synchronized TDS getInstance(){
        if (tds == null) tds = new TDS();
        return tds;
    }

    public static synchronized void resetInstance(){
        tds = new TDS();
    }


    public void ajouterVariable(Entree e, Symbole s) throws DoubleDeclaration{
        if (map.containsKey(e)) throw new DoubleDeclaration(e.getIdf()+ " deja utilisé");
        int taille = s.getTaille();
        s.setDeplacement(cptDepl);
        for (int i = 0; i < taille; i++) {
            cptDepl-=4;
        }
        this.map.put(e, s);
    }



    public boolean estDeclare(Idf idf){
        return map.containsKey(new Entree(idf.getNom()));
    }

    public Symbole getSymbole(Idf idf){return map.get(new Entree(idf.getNom()));}
    public int getCptDepl() {
        return cptDepl;
    }

    public Set<Entree> getEntrees() {return map.keySet();}

    public Symbole getSymbole(Entree entree){return map.get(entree);}


    public String stockerV0() {
        int pos = cptDepl;
        cptDepl-=4;
        return "add $sp,$sp, -4 \nsw $v0, " +pos + "($s7) #Stocker dans la memoire v0";
    }

    public String recupV0(){
        cptDepl+=4;
        return "lw $v0, " +cptDepl + "($s7)\nadd $sp,$sp, 4 #Recuperer dans la memoire v0";
    }

    @Override
    public String toString() {
        return "TDS{" +
                "map=" + map +
                ", cptDepl=" + cptDepl +
                '}';
    }
}
