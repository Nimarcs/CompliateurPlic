package plic.repint;

public abstract class CalculEntier extends Expression {

    protected Expression operande1, operande2;

    public CalculEntier(Expression op1, Expression op2) {
        operande1= op1;
        operande2= op2;
    }

    public abstract String toMips();

    @Override
    public String getType() {
        return "entier";
    }

    @Override
    public void verifier() throws ErreurSemantique {
        if (!operande1.getType().equals("entier")) throw new ErreurSemantique("operande 1 n'est pas de type entier ("+ operande1+')');
        if (!operande2.getType().equals("entier")) throw new ErreurSemantique("operande 2 n'est pas de type entier ("+ operande2+')');
        operande1.verifier();
        operande2.verifier();
    }

}
