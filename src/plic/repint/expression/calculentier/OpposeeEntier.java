package plic.repint.expression.calculentier;

import plic.repint.exceptions.ErreurSemantique;
import plic.repint.expression.Expression;

public class OpposeeEntier extends Expression {

    private Expression expression;

    public OpposeeEntier(Expression res) {
        super();
        expression = res;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        expression.verifier();
        if (!expression.getType().equals("entier")) throw new ErreurSemantique("On ne peut faire l'oppose que de valeures entieres, hors " + expression + "n'est pas de type entier");
    }

    @Override
    public String toMips() {
        return "#Debut opposee\n" +
                expression.toMips() + '\n' +
                "li $t1, -1\n" +
                "mult $v0, $t1\n" +
                "mflo $v0\n" +
                "#Opposee terminee\n";
    }

    @Override
    public String getType() {
        return "entier";
    }
}
