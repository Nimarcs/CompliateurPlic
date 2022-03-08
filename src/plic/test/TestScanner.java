package plic.test;


import plic.analyse.AnalyseurLexical;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

public class TestScanner {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(
                new File("C:/Users/Elève/Desktop/cours/S4/plic/CompliateurPlic/src/plic/sources/testCommentairePlic0.plic"),
                Charset.defaultCharset());
        while (sc.hasNextLine()) System.out.println(sc.nextLine());
        String res = "";
        AnalyseurLexical analyseurLexical = new AnalyseurLexical(new File("C:/Users/Elève/Desktop/cours/S4/plic/CompliateurPlic/src/plic/sources/testCommentairePlic0.plic"));
        while (!res.equals("EOF")){
            res = analyseurLexical.next();
            System.out.println("\""+ res + "\"");
        }
    }

}
