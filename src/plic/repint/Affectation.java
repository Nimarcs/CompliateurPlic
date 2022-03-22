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
    }

    /**
     * renvoie le code de l'affectation
     * @return mips
     */
    @Override
    public String toMips()  {
        String res = "#"+acces.getNom()+" = " + expression.toString() +"\n" +
                expression.toMips()+"\n" ;//met la valeur dans $v0
                if (acces instanceof AccesTab){
                    res += TDS.getInstance().stockerV0()+ '\n';//on met $v0 dans la mémoire
                    res += acces.toMips() + '\n'; //on met le decalage dans v0
                    res += "move $a0, $v0\n" ;//on recupere le decalage dans $a0
                    res += "li $t1, -4\n";
                    res += "mult $t1, $a0\n";
                    res += "mflo $a0\n";
                    res += "add $a0, $a0, $s7\n";
                    res+=TDS.getInstance().recupV0()+ '\n';//on recupere la valeur dans la memoire dans $v0
                    res += "sw $v0, 0($a0)";
                } else if (acces instanceof Idf) {
                    res += "sw $v0," + ((Idf)acces).getPointeur();
                } else throw new IllegalStateException("Acces ne peut etre que un accestab ou un idf (entier)");
                return res;
    }
}
