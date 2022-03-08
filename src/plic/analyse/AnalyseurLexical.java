package plic.analyse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class AnalyseurLexical {

    private Scanner sc;

    public AnalyseurLexical(File fichier) throws IOException {
        sc = new Scanner(fichier, Charset.defaultCharset());
    }

    public String next(){
        //si y'en a un suivant
        if(sc.hasNext()){

            //on le recup
            String res = sc.next();

            //si c'est un commentaire
            if (res.startsWith("//")) {

                //On passe a la ligne suivante
                if (sc.hasNextLine()){
                    sc.nextLine();
                    return next(); //on renvoie le suivant
                }
                else return "EOF"; // si y(a pas de ligne suivante on revoie le EOF
            } else {
                return res;//si c'est pas un commentaire on le renvoie
            }
        } else {
            return "EOF";//si y'a plsu de suivant on retourne EOF
        }
    }

}
