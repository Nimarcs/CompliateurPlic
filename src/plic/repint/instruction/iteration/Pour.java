package plic.repint.instruction.iteration;

import plic.repint.Bloc;
import plic.repint.exceptions.ErreurSemantique;
import plic.repint.expression.Expression;
import plic.repint.expression.acces.Idf;

public class Pour extends Iteration {

    private final Idf idf;
    private final Expression expression1, expression2;
    private final Bloc bloc;

    public Pour(Idf idf, Expression expression1, Expression expression2, Bloc bloc) {
        this.idf = idf;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.bloc = bloc;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        idf.verifier();
        expression1.verifier();
        expression2.verifier();
        bloc.verifier();
        if (!idf.getType().equals("entier")) throw new ErreurSemantique(idf.getNom() + " n'est pas de type entier");
        if (!expression1.getType().equals("entier")) throw new ErreurSemantique(expression1 + " n'est pas de type entier");
        if (!expression2.getType().equals("entier")) throw new ErreurSemantique(expression2 + " n'est pas de type entier");

    }

    @Override
    public String toMips() {
        return null;
    }
}
