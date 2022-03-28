package plic.repint;

import plic.analyse.ErreurSyntaxique;

public class Affectation extends Instruction{

    private Expression expression;

    private Acces acces;

    public Affectation(Acces ac, Expression e){
        acces = ac;
        expression =e;
    }

    public Expression getExpression() {
        return expression;
    }

    public Acces getAcces() {
        return acces;
    }

    @Override
    public String toString() {
        return "Affectation{" +
                "acces=" + acces +
                ", expression=" + expression +
                '}';
    }

    @Override
    public void verifier() throws ErreurSemantique {
        acces.verifier();
        expression.verifier();
        if (!acces.getType().equals("entier"))  throw new ErreurSemantique(acces.getNom() + " n'est pas un entier");
        if (!expression.getType().equals("entier"))  throw new ErreurSemantique(acces.getNom() + " n'est pas un entier");
    }

    /**
     * renvoie le code de l'affectation
     * @return mips
     */
    @Override
    public String toMips()  {
        String res = "#"+acces.getNom()+" = " + expression.toString() + '\n';
        res += expression.toMips()+"\n";//met la valeur dans $v0
        res += TDS.getInstance().stockerV0()+ '\n';//on met $v0 dans la mémoire
        res += acces.getAdresseAcces() + '\n'; //on met l'adresse dans $a0
        res+=TDS.getInstance().recupV0()+ '\n';//on recupere la valeur dans la memoire dans $v0
        res += "sw $v0, 0($a0) #on met la valeur "+ expression.toString() + " a la position " + acces.getNom() +"\n"; //adresse dans le pointeur a0 <- v0

        return res;
    }
}
