package plic.repint;

public class Ecrire extends Instruction {

    private Expression expression;

    public Ecrire(Expression e){
        expression =e;
    }

    @Override
    public String toString() {
        return "Ecrire{" +
                "expression=" + expression +
                '}';
    }

    @Override
    public void verifier() throws ErreurSemantique {
        expression.verifier();
    }
}
