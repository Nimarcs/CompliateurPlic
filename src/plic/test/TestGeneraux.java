package plic.test;

import plic.analyse.AnalyseurSyntaxique;

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
            if(file.isDirectory()) {
                runDirectory(file);
            } else {
                try {
                    runFile(file);
                    System.out.println("Exécution OK");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static void runFile(File file) throws Exception {
        System.out.println("run du fichier '" + file + "'");
        if(!(file.getName().endsWith(".plic"))) throw new InvalidParameterException("ERREUR:Le fichier doit etre un .plic");
        try {
            AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(file);
            analyseurSyntaxique.analyse();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("ERREUR:Le fichier fourni n'a pas été trouvé \n" + e.getMessage());
        }
    }

}
