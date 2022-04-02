package plic.repint.expression.calculbooleen;

import plic.repint.exceptions.ErreurSemantique;
import plic.repint.expression.Expression;

public abstract class CalculBooleen extends Expression {

    protected Expression operande1, operande2;

    public CalculBooleen(Expression op1, Expression op2) {
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
        if (!operande1.getType().equals("booleen")) throw new ErreurSemantique("operande 1 ne peut pas faire parti d'un calcul booleen, n'est pas de type booleen ("+ operande1+')');
        if (!operande2.getType().equals("booleen")) throw new ErreurSemantique("operande 2 ne peut pas faire parti d'un calcul booleen, n'est pas de type booleen ("+ operande2+')');
        operande1.verifier();
        operande2.verifier();
    }

}
