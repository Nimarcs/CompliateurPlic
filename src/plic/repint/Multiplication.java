package plic.repint;

public class Multiplication extends Expression {

    private Expression operande1, operande2;

    public Multiplication(Expression op1, Expression op2) {
        operande1= op1;
        operande2= op2;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        if (!operande1.getType().equals("entier")) throw new ErreurSemantique("operande 1 ne pas pas etre multiplie, n'est pas de type entier ("+ operande1+')');
        if (!operande2.getType().equals("entier")) throw new ErreurSemantique("operande 2 ne pas pas etre multiplie, n'est pas de type entier ("+ operande2+')');
        operande1.verifier();
        operande2.verifier();
    }

    @Override
    public String toMips() {
        return "#On calcule " + operande1.toString() + " * " + operande2.toString() + '\n' +
                operande1.toMips() + "\n" +
                TDS.getInstance().stockerV0() +  "\n"+
                operande2.toMips() + "\nmove $v1, $v0\n" +
                TDS.getInstance().recupV0() + '\n' +
                "mult $v0, $v1\n" +
                "mflo $v0 #Multiplication terminee\n";
    }

    @Override
    public String getType() {
        return "entier";
    }
}
