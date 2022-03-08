package plic.analyse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class AnalyseurLexical {

    private Scanner sc;

    private Iterator<String> curLine;

    public AnalyseurLexical(File fichier) throws IOException {
        sc = new Scanner(fichier, Charset.defaultCharset());
        curLine = Collections.emptyIterator();
    }

    public String next(){

        if (curLine.hasNext()) {
            String next = curLine.next();
            if (next.startsWith("//")) {
                curLine= Collections.emptyIterator();
                return next();
            } else return next;
        } else {
            if (sc.hasNextLine()) {
                curLine = Arrays.stream(sc.nextLine().split(" ")).filter(e -> !e.equals("")).iterator();
                return next();
            } else {
                return "EOF";
            }
        }
    }

}
