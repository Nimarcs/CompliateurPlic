package plic.repint;

public class Affectation extends Instruction{

    private Expression expression;

    private Idf idf;

    public Affectation(Idf i, Expression e){
        idf = i;
        expression =e;
    }

    public Expression getExpression() {
        return expression;
    }

    public Idf getIdf() {
        return idf;
    }

    @Override
    public String toString() {
        return "Affectation{" +
                "idf=" + idf +
                ", expression=" + expression +
                '}';
    }

    @Override
    public void verifier() throws ErreurSemantique {
        idf.verifier();
        expression.verifier();
    }
}
