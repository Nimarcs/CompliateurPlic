package plic.repint.expression.calculbooleen;

import plic.repint.GenerateurEtiquette;
import plic.repint.declaration.TDS;
import plic.repint.exceptions.ErreurSemantique;
import plic.repint.expression.Expression;

public class NonBooleen extends Expression {

    private final Expression expression;

    public NonBooleen(Expression expression) {
        super();
        this.expression = expression;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        expression.verifier();
        if (!expression.getType().equals("booleen")) throw new ErreurSemantique("l'expression ne peut etre inverser par un calcul booleen, n'est pas de type booleen ("+ expression +')');
    }

    @Override
    public String toMips() {
        int etiquette = GenerateurEtiquette.getInstance().getEtiquette();
        return "#On calcule non : " + expression + '\n' +
                expression.toMips() + "\n" +
                "li $t1, 0\n" +
                "beq $v0, $t1, boolVrai" + etiquette + '\n'+
                "b boolFaux" + etiquette + '\n' +
                "boolVrai" + etiquette + ":\n" +
                "\tli $v0, 1\n" +
                "\tb endBool"+etiquette+"\n" +
                "boolFaux" + etiquette + ":\n" +
                "\tli $v0, 0\n" +
                "\tb endBool"+etiquette+"\n" +
                "endBool" + etiquette + ":\n" +
                "#non terminee\n";
    }

    @Override
    public String getType() {
        return "booleen";
    }
}
