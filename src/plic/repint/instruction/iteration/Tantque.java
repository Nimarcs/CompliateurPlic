package plic.repint.instruction.iteration;

import plic.repint.Bloc;
import plic.repint.exceptions.ErreurSemantique;
import plic.repint.expression.Expression;



public class Tantque extends Iteration{
    private final Expression expression;
    private final Bloc bloc;

    public Tantque(Expression expression, Bloc bloc) {
        this.expression = expression;
        this.bloc = bloc;

    }

    @Override
    public void verifier() throws ErreurSemantique {
        expression.verifier();
        bloc.verifier();
        if (!expression.getType().equals("booleen")) throw new ErreurSemantique(expression + " n'est pas de type booleen");
    }

    @Override
    public String toMips() {
        return null;
    }
}
