package plic.repint.expression.comparateur;

import plic.repint.exceptions.ErreurSemantique;
import plic.repint.expression.Expression;

public abstract class Comparateur extends Expression {

    protected Expression operande1, operande2;

    public Comparateur(Expression op1, Expression op2) {
        operande1= op1;
        operande2= op2;
    }

    public abstract String toMips();

    @Override
    public String getType() {
        return "booleen";
    }

    @Override
    public void verifier() throws ErreurSemantique {
        if (!operande1.getType().equals("entier")) throw new ErreurSemantique("operande 1 ne peut pas faire parti d'une comparaison, n'est pas de type entier ("+ operande1+')');
        if (!operande2.getType().equals("entier")) throw new ErreurSemantique("operande 2 ne peut pas faire parti d'une comparaison, n'est pas de type entier ("+ operande2+')');
        operande1.verifier();
        operande2.verifier();
    }

}
