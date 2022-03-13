package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.analyse.ErreurSyntaxique;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

public class Plic {

    public static void main(String[] args) throws IOException, ErreurSyntaxique {
        if (args.length != 1) throw new InvalidParameterException("Nombre d'argument invalide");
        if (!args[0].endsWith(".plic")) throw new InvalidParameterException("Le fichier doit etre un .plic");
        new Plic(args[0]);
    }

    public Plic(String nomFichier) throws IOException, ErreurSyntaxique {
        File file =  new File(nomFichier);//erreur
        //creer l'analyseur syntaxique
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(file);
        //on lance l'analyse
        analyseurSyntaxique.analyse();//erreur
    }


}
