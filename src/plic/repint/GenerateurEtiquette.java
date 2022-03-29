package plic.repint;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GenerateurEtiquette {

    private static GenerateurEtiquette generateurEtiquette;

    private int cpt;

    private GenerateurEtiquette(){
        cpt = 0;
    }

    public static synchronized GenerateurEtiquette getInstance(){
        if (generateurEtiquette == null) generateurEtiquette = new GenerateurEtiquette();
        return generateurEtiquette;
    }

    public static synchronized void resetInstance(){
        generateurEtiquette = new GenerateurEtiquette();
    }

    public int getEtiquette() {
        return cpt++;
    }
}
