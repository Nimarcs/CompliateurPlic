package plic.test;

import plic.analyse.AnalyseurSyntaxique;
import plic.repint.Bloc;
import plic.repint.declaration.TDS;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;

public class TestGeneraux {

    public static void main(String[] args) throws Exception {
        File file = new File("src/plic/sources/");
        runDirectory(file);
    }

    private static void runDirectory(File directory) {
        System.out.println("\nExploration du dossier '" + directory + "'");
        for (File file : directory.listFiles()) {
            TDS.resetInstance();
            if(file.isDirectory()) {
                runDirectory(file);
            } else {
                try {
                    runFile(file);
                    System.out.println("Exécution OK");
                } catch (Exception e) {
                    System.out.println(e.getMessage().substring(0, 8));
                }
            }
        }
    }

    private static void runFile(File file) throws Exception {
        System.out.print("run du fichier '" + file + "'  :  ");
        if(!(file.getName().endsWith(".plic"))) throw new InvalidParameterException("ERREUR:Le fichier doit etre un .plic");
        try {
            AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(file);
            Bloc bloc = analyseurSyntaxique.analyse();
            bloc.verifier();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("ERREUR:Le fichier fourni n'a pas été trouvé \n" + e.getMessage());
        }
    }

}
