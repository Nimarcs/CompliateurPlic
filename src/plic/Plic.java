package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.analyse.ErreurSyntaxique;
import plic.repint.Bloc;
import plic.repint.DoubleDeclaration;
import plic.repint.ErreurSemantique;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

public class Plic {

    public static void main(String[] args) {
        try {
            if (args.length != 1) throw new InvalidParameterException("ERREUR:Nombre d'argument invalide");
            if (!args[0].endsWith(".plic")) throw new InvalidParameterException("ERREUR:Le fichier doit etre un .plic");
            new Plic(args[0]);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Plic(String nomFichier) throws IOException, ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        File file =  new File(nomFichier);
        //creer l'analyseur syntaxique
        AnalyseurSyntaxique analyseurSyntaxique;
        try {
            analyseurSyntaxique = new AnalyseurSyntaxique(file);
        } catch (FileNotFoundException fnfe){
            throw new FileNotFoundException("ERREUR:Le fichier fourni n'a pas été trouvé \n" + fnfe.getMessage());
        }
        //on lance l'analyse
        Bloc bloc = analyseurSyntaxique.analyse();
        System.out.println(bloc);
        bloc.verifier();
    }


}
