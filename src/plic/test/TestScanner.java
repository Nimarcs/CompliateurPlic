package plic.test;


import plic.analyse.AnalyseurLexical;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

public class TestScanner {

    public static void main(String[] args) throws IOException {
        /*Scanner sc = new Scanner(
                new File("src/plic/sources/normal/P0test_syntaxique_normal_commentaire.plic"),
                Charset.defaultCharset());*/
        //while (sc.hasNextLine()) System.out.println(sc.next());
        String res = "";
        AnalyseurLexical analyseurLexical = new AnalyseurLexical(new File("src/plic/sources/normal/P0test_syntaxique_normal_1.plic"));
        while (!res.equals("EOF")){
            res = analyseurLexical.next();
            System.out.println("\""+ res + "\"");
        }
    }

}
