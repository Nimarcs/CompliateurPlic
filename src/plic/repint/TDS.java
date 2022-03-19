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
        s.setDeplacement(cptDepl);
        cptDepl-=4;
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


}
