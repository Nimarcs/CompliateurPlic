package plic.test;


import plic.analyse.AnalyseurLexical;
import plic.repint.GenerateurEtiquette;

import java.io.File;
import java.io.IOException;

public class TestEtiquette {

    public static void main(String[] args) throws IOException {
        int zero = GenerateurEtiquette.getInstance().getEtiquette();
        int un = GenerateurEtiquette.getInstance().getEtiquette();
        assert zero == 0;
        assert un == 1;
        System.out.println(zero);
        System.out.println(un);
    }

}
