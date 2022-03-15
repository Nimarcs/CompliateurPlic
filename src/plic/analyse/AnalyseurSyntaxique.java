package plic.analyse;

import plic.repint.*;

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
     * On se contente de l'analyse syntaxique sans produire le fichier intermediaire
     */
    public Bloc analyse() throws ErreurSyntaxique, DoubleDeclaration {
        //on construit la premiere unite lexicale
        uniteCourante = analyseurLexical.next();
        //analyse du texte
        Bloc res = analyseProg();
        if (!uniteCourante.equals("EOF")) throw new ErreurSyntaxique("EOF attendu");
        return res;
    }

    private Bloc analyseProg() throws ErreurSyntaxique, DoubleDeclaration {
        analyseTerminal("programme");
        analyseIDF();
        return analyseBloc();
    }

    private Bloc analyseBloc() throws ErreurSyntaxique, DoubleDeclaration {
        Bloc res = new Bloc();
        analyseTerminal("{");
        while (isType()) {
            analyseDeclaration();
        }
        res.ajouterInstruction(analyseInstruction());
        while (!uniteCourante.equals("}")) {
            res.ajouterInstruction(analyseInstruction());
        }
        analyseTerminal("}");
        return res;
    }

    private Instruction analyseInstruction() throws ErreurSyntaxique {
        Instruction res;
        //System.out.println(uniteCourante);
        if (uniteCourante.equals("ecrire")) { //ES
            res = new Ecrire( analyseEs());
        } else if (isIDF()) { //affectation
            res = analyseAffectation();
        } else throw new ErreurSyntaxique("instruction invalide");
        //pas enciore si et pour
        return res;
    }

    private Affectation analyseAffectation() throws ErreurSyntaxique {
        Affectation res;
        Idf idf = analyseAcces();
        analyseTerminal(":=");
        Expression expression = analyseExpression();
        analyseTerminal(";");
        return new Affectation(idf, expression);
    }

    private Expression analyseEs() throws ErreurSyntaxique {
        analyseTerminal("ecrire");
        Expression res = analyseExpression();
        analyseTerminal(";");
        return res;
    }

    private Idf analyseAcces() throws ErreurSyntaxique {
        return new Idf(analyseIDF());
        //pas encore possible
        /*if (uniteCourante.equals("[")){
            analyseTerminal("[");
            analyseExpression();

        }*/
    }

    private Expression analyseExpression() throws ErreurSyntaxique {
        Expression res = analyseOperande();
        //calcul pas encore là
        return res;
    }

    private Expression analyseOperande() throws ErreurSyntaxique {
        if (isCstEntiere()) return analyseCstEntiere();
        else if (isIDF()) return new Idf(analyseIDF());
        else throw new ErreurSyntaxique("Operande inconnue : " + uniteCourante);
    }

    private void analyseDeclaration() throws ErreurSyntaxique, DoubleDeclaration {
        Symbole symbole = analyseType();
        Entree entree = new Entree(analyseIDF());
        analyseTerminal(";");
        TDS tds = TDS.getInstance();
        tds.ajouterVariable(entree, symbole);
    }

    private Symbole analyseType() throws ErreurSyntaxique {
        if (!isType()) throw new ErreurSyntaxique("Type attendu : entier");
        Symbole res = new Symbole(uniteCourante, 1);
        uniteCourante = analyseurLexical.next();
        return res;
    }

    private String analyseIDF() throws ErreurSyntaxique {
        if (!isIDF()) throw new ErreurSyntaxique("identificateur attendu : [A-Za-Z]");
        String res = uniteCourante;
        uniteCourante = analyseurLexical.next();
        return res;
    }

    private Nombre analyseCstEntiere() throws ErreurSyntaxique {
        if (!isCstEntiere()) throw new ErreurSyntaxique("constante entiere attendu : [0-9]");
        String nb = uniteCourante;
        uniteCourante = analyseurLexical.next();
        return new Nombre(Integer.parseInt(nb));
    }

    private void analyseTerminal(String terminal) throws ErreurSyntaxique {
        if (!uniteCourante.equals(terminal)) throw new ErreurSyntaxique(terminal + " attendu");
        uniteCourante = analyseurLexical.next();
    }

    private boolean isType() {
        return uniteCourante.equals("entier");
    }

    private boolean isIDF() {
        if (Arrays.stream(motsClee).toList().contains(uniteCourante)) return false; //les mots clee sont exclus
        return uniteCourante.matches("[A-Za-z]+");
    }

    private boolean isCstEntiere() {
        return uniteCourante.matches("[0-9]+");
    }

    private boolean isOperateur() {
        for (String s : operateurs) {
            if (s.equals(uniteCourante)) return true;
        }
        return false;
    }

}
