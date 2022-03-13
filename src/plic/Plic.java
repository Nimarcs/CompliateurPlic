package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.analyse.ErreurSyntaxique;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

public class Plic {

    public static void main(String[] args) throws IOException, ErreurSyntaxique {
        if (args.length != 1) throw new InvalidParameterException("ERREUR:Nombre d'argument invalide");
        if (!args[0].endsWith(".plic")) throw new InvalidParameterException("ERREUR:Le fichier doit etre un .plic");
        new Plic(args[0]);
    }

    public Plic(String nomFichier) throws IOException, ErreurSyntaxique {
        File file =  new File(nomFichier);
        //creer l'analyseur syntaxique
        AnalyseurSyntaxique analyseurSyntaxique;
        try {
            analyseurSyntaxique = new AnalyseurSyntaxique(file);
        } catch (FileNotFoundException fnfe){
            throw new FileNotFoundException("ERREUR:Le fichier fourni n'a pas été trouvé \n" + fnfe.getMessage());
        }
        //on lance l'analyse
        analyseurSyntaxique.analyse();
    }


}
