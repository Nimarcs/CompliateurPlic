package plic.analyse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AnalyseurSyntaxique {

    private AnalyseurLexical analyseurLexical;

    private String uniteCourante;

    private static final String[] operateurs = {};

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
        if (!uniteCourante.equals("EOF")) throw new ErreurSyntaxique("ERREUR:EOF attendu");
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
                analyseTerminal("ecrire");
                analyseExpression();
            } else if (isIDF()){ //affectation
                analyseAcces();
                analyseTerminal(":=");
                analyseExpression();
            } else throw new ErreurSyntaxique("ERREUR:instruction invalide");
            //pas enciore si et pour
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
        else throw new ErreurSyntaxique("ERREUR:Operande inconnue : " + uniteCourante);
    }

    private void analyseDeclaration() throws ErreurSyntaxique {
        analyseType();
        analyseIDF();
        analyseTerminal(";");
    }

    private void analyseType() throws ErreurSyntaxique {
        if (!isType()) throw new ErreurSyntaxique("ERREUR:Type attendu : entier");
        uniteCourante = analyseurLexical.next();
    }

    private void analyseIDF() throws ErreurSyntaxique {
        if (!isIDF()) throw new ErreurSyntaxique("ERREUR:identificateur attendu : [A-Za-Z]");
        uniteCourante = analyseurLexical.next();
    }

    private void analyseCstEntiere() throws ErreurSyntaxique {
        if (!isCstEntiere()) throw new ErreurSyntaxique("ERREUR:constante entiere attendu : [0-9]");
        uniteCourante = analyseurLexical.next();
    }

    private void analyseTerminal(String terminal) throws ErreurSyntaxique {
        if (!uniteCourante.equals(terminal)) throw new ErreurSyntaxique("ERREUR:"+ terminal+ "attendu");
        uniteCourante = analyseurLexical.next();
    }

    private boolean isType(){
        return uniteCourante.equals("entier");
    }

    private boolean isIDF(){
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
