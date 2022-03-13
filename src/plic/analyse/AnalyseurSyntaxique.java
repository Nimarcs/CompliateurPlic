package plic.analyse;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AnalyseurSyntaxique {

    private AnalyseurLexical analyseurLexical;

    private String uniteCourante;

    private static final String[] operateurs = {};

    private static final String[] motsClee = {"programme", "entier", "ecrire", "tableau", "lire", "si", "alors", "pour", "dans", "repeter", "tantque", "et", "ou", "non"};

    public AnalyseurSyntaxique(File fichier) throws IOException {
        this.analyseurLexical = new AnalyseurLexical(fichier);
    }

    /**
     *On se contente de l'analyse syntaxique sans produire le fichier intermediaire
     */
    public void analyse() throws ErreurSyntaxique {
        //on construit la premiere unite lexicale
        uniteCourante = analyseurLexical.next();
        //analyse du texte
        analyseProg();
        if (!uniteCourante.equals("EOF")) throw new ErreurSyntaxique("EOF attendu");
    }

    private void analyseProg() throws ErreurSyntaxique {
        analyseTerminal("programme");
        analyseIDF();
        analyseBloc();
    }

    private void analyseBloc() throws ErreurSyntaxique {
        analyseTerminal("{");
        while (isType()){
            analyseDeclaration();
        }
        analyseInstruction();
        while (!uniteCourante.equals("}")){
            analyseInstruction();
        }
        analyseTerminal("}");
    }

    private void analyseInstruction() throws ErreurSyntaxique {
        if ( uniteCourante.equals("ecrire")) { //ES
            analyseEs();
        } else if (isIDF()){ //affectation
            analyseAffectation();
        } else throw new ErreurSyntaxique("instruction invalide");
        //pas enciore si et pour
    }

    private void analyseAffectation() throws ErreurSyntaxique {
        analyseAcces();
        analyseTerminal(":=");
        analyseExpression();
        analyseTerminal(";");
    }

    private void analyseEs() throws ErreurSyntaxique {
        analyseTerminal("ecrire");
        analyseExpression();
        analyseTerminal(";");
    }

    private void analyseAcces() throws ErreurSyntaxique {
        analyseIDF();
        //pas encore possible
        /*if (uniteCourante.equals("[")){
            analyseTerminal("[");
            analyseExpression();

        }*/
    }

    private void analyseExpression() throws ErreurSyntaxique {
        analyseOperande();
        //calcul pas encore là
    }

    private void analyseOperande() throws ErreurSyntaxique {
        if (isCstEntiere()) analyseCstEntiere();
        else if (isIDF()) analyseIDF();
        else throw new ErreurSyntaxique("Operande inconnue : " + uniteCourante);
    }

    private void analyseDeclaration() throws ErreurSyntaxique {
        analyseType();
        analyseIDF();
        analyseTerminal(";");
    }

    private void analyseType() throws ErreurSyntaxique {
        if (!isType()) throw new ErreurSyntaxique("Type attendu : entier");
        uniteCourante = analyseurLexical.next();
    }

    private void analyseIDF() throws ErreurSyntaxique {
        if (!isIDF()) throw new ErreurSyntaxique("identificateur attendu : [A-Za-Z]");
        uniteCourante = analyseurLexical.next();
    }

    private void analyseCstEntiere() throws ErreurSyntaxique {
        if (!isCstEntiere()) throw new ErreurSyntaxique("constante entiere attendu : [0-9]");
        uniteCourante = analyseurLexical.next();
    }

    private void analyseTerminal(String terminal) throws ErreurSyntaxique {
        if (!uniteCourante.equals(terminal)) throw new ErreurSyntaxique( terminal+ "attendu");
        uniteCourante = analyseurLexical.next();
    }

    private boolean isType(){
        return uniteCourante.equals("entier");
    }

    private boolean isIDF(){
        if (Arrays.stream(motsClee).toList().contains(uniteCourante)) return false; //les mots clee sont exclus
        return uniteCourante.matches("[A-Za-z]+");
    }

    private boolean isCstEntiere(){
        return uniteCourante.matches("[0-9]+");
    }

    private boolean isOperateur(){
        for (String s: operateurs) {
            if (s.equals(uniteCourante)) return true;
        }
        return false;
    }

}
