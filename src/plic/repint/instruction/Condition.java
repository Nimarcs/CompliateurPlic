package plic.repint.instruction;

import plic.repint.Bloc;
import plic.repint.GenerateurEtiquette;
import plic.repint.exceptions.ErreurSemantique;
import plic.repint.expression.Expression;

public class Condition extends Instruction{

    private final Expression expression;
    private final Bloc bloc1, bloc2;

    public Condition(Expression expression, Bloc bloc1, Bloc bloc2) {
        this.expression = expression;
        this.bloc1 = bloc1;
        this.bloc2 = bloc2;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        expression.verifier();
        bloc1.verifier();
        if (bloc2 != null) bloc2.verifier();
        if (!expression.getType().equals("booleen")) throw new ErreurSemantique(expression + " n'est pas de type booleen");
    }

    @Override
    public String toMips() {
        int etiquette = GenerateurEtiquette.getInstance().getEtiquette();
        return "#début si\n" +
                expression.toMips() + '\n' +
                "li $t1, 0" + '\n' +
                "beq $v0, $t1, sinon" + etiquette + " #si " + expression + '\n' +
                "alors" + etiquette + ":" + '\n' +
                bloc1.toMipsSansIntroEtOutro() + '\n' + "b fsi" + etiquette + '\n' +
                "sinon" + etiquette + ":" + '\n' +
                bloc2.toMipsSansIntroEtOutro() + '\n' + "b fsi" + etiquette + '\n' +
                "fsi" + etiquette + ":";
    }
}
