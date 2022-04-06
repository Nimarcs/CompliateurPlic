package plic.repint.instruction.iteration;

import plic.repint.Bloc;
import plic.repint.GenerateurEtiquette;
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
        int etiquette = GenerateurEtiquette.getInstance().getEtiquette();
        return "#début pour\n" +
                expression1.toMips() + '\n' +
                "sw $v0, " + idf.getPointeur() + '\n' +
                expression2.toMips() + '\n' +
                "lw $v1, " + idf.getPointeur() + '\n' +
                "ble $v1, $v0, pour" + etiquette + " #pour "  +idf + " de " + expression1 + " à " + expression2 + '\n' +
                "b fpour" + etiquette + '\n' +
                "pour" + etiquette + ":" + '\n' +
                bloc.toMipsSansIntroEtOutro() + '\n' + "#FIN DU POUR \n" +
                expression2.toMips() + '\n' +
                "lw $v1, " + idf.getPointeur() + '\n' +
                "li $t1, 1" + '\n' +
                "add $v1, $v1, $t1 \n" +
                "sw $v1, " + idf.getPointeur() + '\n' +
                "ble $v1, $v0, pour" + etiquette + '\n'+
                "fpour" + etiquette + ":";

    }
}
