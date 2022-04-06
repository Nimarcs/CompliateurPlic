package plic.analyse;

import plic.repint.*;
import plic.repint.declaration.Entree;
import plic.repint.declaration.Symbole;
import plic.repint.declaration.TDS;
import plic.repint.exceptions.DoubleDeclaration;
import plic.repint.expression.Expression;
import plic.repint.expression.Nombre;
import plic.repint.expression.acces.Acces;
import plic.repint.expression.acces.AccesTab;
import plic.repint.expression.acces.Idf;
import plic.repint.expression.calculbooleen.EtBooleen;
import plic.repint.expression.calculbooleen.OuBooleen;
import plic.repint.expression.calculentier.Multiplication;
import plic.repint.expression.calculentier.Somme;
import plic.repint.expression.calculentier.Soustraction;
import plic.repint.expression.comparateur.*;
import plic.repint.instruction.Affectation;
import plic.repint.instruction.Condition;
import plic.repint.instruction.Ecrire;
import plic.repint.instruction.Instruction;
import plic.repint.instruction.iteration.Pour;
import plic.repint.instruction.iteration.Tantque;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class AnalyseurSyntaxique {

    private AnalyseurLexical analyseurLexical;

    private String uniteCourante;

    private static final String[] operateurs = {"+",  "-", "*", "et", "ou", "<", ">",  "=", "#", "<=", ">="};

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
        } else if (uniteCourante.equals("si")){
            res =  analyseCondition();
        } else if (uniteCourante.equals("tantque")) {
            res =  analyseIterationTantque();
        } else if (uniteCourante.equals("pour")){
            res =  analyseIterationPour();
        } else throw new ErreurSyntaxique("instruction invalide :" + uniteCourante);
        //pas enciore si et pour
        return res;
    }

    private Instruction analyseIterationPour() throws ErreurSyntaxique {
        analyseTerminal("pour");
        Idf idf =new Idf( analyseIDF());
        analyseTerminal("dans");
        Expression expression1 = analyseExpression();
        analyseTerminal("..");
        Expression expression2 = analyseExpression();
        analyseTerminal("repeter");
        analyseBlocInterne();
        Bloc bloc = analyseBlocInterne();
        return new Pour(idf, expression1, expression2, bloc);
    }

    private Bloc analyseBlocInterne() throws ErreurSyntaxique{
        try {
            return analyseBloc();
        } catch (DoubleDeclaration ignored){
            //declaration pas autoriser mais on teste pas
            throw new ErreurSyntaxique("Les declaration ne sont pas autorisé dans les bloc interne");
        }
    }

    private Instruction analyseIterationTantque() throws ErreurSyntaxique {
        analyseTerminal("tantque");
        analyseTerminal("(");
        Expression expression = analyseExpression();
        analyseTerminal(")");
        analyseTerminal("repeter");
        Bloc bloc = analyseBlocInterne();
        return new Tantque(expression, bloc);
    }


    private Instruction analyseCondition() throws ErreurSyntaxique {
        analyseTerminal("si");
        analyseTerminal("(");
        Expression expression = analyseExpression();
        analyseTerminal(")");
        analyseTerminal("alors");
        Bloc bloc1 = analyseBlocInterne();
        Bloc bloc2 = null;
        if (uniteCourante.equals("sinon")){
            analyseTerminal("sinon");
            bloc2 = analyseBlocInterne();
        }
        return new Condition(expression, bloc1, bloc2);
    }

    private Affectation analyseAffectation() throws ErreurSyntaxique {
        Acces acces = analyseAcces();
        analyseTerminal(":=");
        Expression expression = analyseExpression();
        analyseTerminal(";");
        return new Affectation(acces, expression);
    }

    private Expression analyseEs() throws ErreurSyntaxique {
        analyseTerminal("ecrire");
        Expression res = analyseExpression();
        analyseTerminal(";");
        return res;
    }

    private Acces analyseAcces() throws ErreurSyntaxique {
        String nomIdf = analyseIDF();
        Idf idf = new Idf(nomIdf);
        if(!TDS.getInstance().estDeclare(idf)) throw new ErreurSyntaxique("Variable non déclaré");
        if (uniteCourante.equals("[")){
            analyseTerminal("[");
            Expression expression = analyseExpression();
            analyseTerminal("]");
            return new AccesTab(idf, expression);
        } else {
            if (!TDS.getInstance().getSymbole(idf).getType().equals("entier"))   throw new ErreurSyntaxique("Acces a un tableau doit etre de la forme : 'idf [ cst ]'");
            return idf ;
        }
    }

    /**
     * OPERANDE OPERATEUR OPERANDE | OPERANDE
     */
    private Expression analyseExpression() throws ErreurSyntaxique {
        Expression res;
        Expression operande1 = analyseOperande();
        if (isOperateur()){
            String op = analyseOperateur();
            Expression operande2 = analyseOperande();
            switch (op){
                case "+":
                    res = new Somme(operande1, operande2);
                    break;
                case "-":
                    res = new Soustraction(operande1, operande2);
                    break;
                case "*":
                    res = new Multiplication(operande1, operande2);
                    break;
                case "et":
                    res = new EtBooleen(operande1, operande2);
                    break;
                case "ou":
                    res = new OuBooleen(operande1, operande2);
                    break;
                case "<":
                    res = new InferieurA(operande1, operande2);
                    break;
                case ">":
                    res = new SuperieurA(operande1, operande2);
                    break;
                case "=":
                    res = new Egal(operande1, operande2);
                    break;
                case "#":
                    res = new Inegal(operande1, operande2);
                    break;
                case "<=":
                    res = new InferieurOuEgalA(operande1, operande2);
                    break;
                case ">=":
                    res = new SuperieurOuEgalA(operande1, operande2);
                    break;
                default:
                    throw new IllegalStateException("operateur non pris en compte");
            }
        } else {
            res = operande1;
        }
        return res;
    }

    /**
        + | - | * | et | ou | < | > | = | # | <= | >=
        '#' == '!='
     */
    private String analyseOperateur() throws ErreurSyntaxique {
        if (!isOperateur()) throw new ErreurSyntaxique("operateur attendu : + | - | * | et | ou | < | > | = | # | <= | >=");
        String res = uniteCourante;
        uniteCourante = analyseurLexical.next();
        return res;
    }

    /**
     * cstEntiere | ACCES | - ( EXPRESSION )
     * | non EXPRESSION | ( EXPRESSION )
     */
    private Expression analyseOperande() throws ErreurSyntaxique {
        if (isCstEntiere()) return analyseCstEntiere();//cstEntiere
        else if (isIDF()) { //acces
            Idf idf = new Idf(analyseIDF());
            if (!TDS.getInstance().estDeclare(idf)) throw new ErreurSyntaxique("la variable n'est pas déclaré : " + idf.getNom());
            if (uniteCourante.equals("[")){
                if (!TDS.getInstance().getSymbole(idf).getType().equals("tableau")) throw new ErreurSyntaxique(idf.getNom() + " n'est pas un tableau");
                analyseTerminal("[");
                Expression expre =analyseExpression();
                analyseTerminal("]");
                return new AccesTab(idf, expre);
            } else {
                return idf;
            }
        } else if (uniteCourante.equals("-")) { // - ( EXPRESSION )
            analyseTerminal("-");
            analyseTerminal("(");
            Expression res = analyseExpression();
            analyseTerminal(")");
            //return new OpposeeEntier(res);
            return res;
        } else if (uniteCourante.equals("non")){ // non EXPRESSION
            analyseTerminal("non");

            //return new NonBooleen(analyseExpression());
            return analyseExpression();
        } else if (uniteCourante.equals("(")){ //( EXPRESSION )
            analyseTerminal("(");
            Expression res = analyseExpression();
            analyseTerminal(")");
            return res;
        }
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
        if (!isType()) throw new ErreurSyntaxique("Type attendu : entier ou tableau");
        if (uniteCourante.equals("entier")) {
            Symbole res = new Symbole(uniteCourante, 1);
            analyseTerminal("entier");
            return res;
        } else {
            String type = uniteCourante;
            analyseTerminal("tableau");
            analyseTerminal("[");
            int taille = analyseCstEntiere().getVal();
            if (taille <= 0) throw new ErreurSyntaxique("Taille de tableau invalide : doit etre strictement positif");
            analyseTerminal("]");
            return new Symbole(type, taille);
        }
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
        int res;
        try {
            res = Integer.parseInt(nb);
        } catch (NumberFormatException e){
            throw new ErreurSyntaxique("le nombre est trop grand");
        }
        return new Nombre(res);
    }

    private void analyseTerminal(String terminal) throws ErreurSyntaxique {
        if (!uniteCourante.equals(terminal)) throw new ErreurSyntaxique(terminal + " attendu");
        uniteCourante = analyseurLexical.next();
    }

    private boolean isType() {
        return uniteCourante.equals("entier") || uniteCourante.equals("tableau");
    }

    private boolean isIDF() {
        if (List.of(motsClee).contains(uniteCourante)) return false; //les mots clee sont exclus
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
