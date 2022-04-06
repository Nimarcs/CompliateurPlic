package plic.repint.instruction.iteration;

import plic.repint.Bloc;
import plic.repint.GenerateurEtiquette;
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
        int etiquette = GenerateurEtiquette.getInstance().getEtiquette();
        return "#début tantque\n" +
                expression.toMips() + '\n' +
                "li $t1, 1" + '\n' +
                "beq $v0, $t1, tantque" + etiquette + " #tantque " + expression + '\n' +
                "b ftantque" + etiquette + '\n' +
                "tantque" + etiquette + ":" + '\n' +
                bloc.toMipsSansIntroEtOutro() + '\n' +
                expression.toMips() + '\n' +
                "li $t1, 1" + '\n' +
                "beq $v0, $t1, tantque" + etiquette+'\n'+
                "ftantque" + etiquette + ":";

    }
}
