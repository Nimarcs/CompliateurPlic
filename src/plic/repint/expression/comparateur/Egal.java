package plic.repint.expression.comparateur;

import plic.repint.expression.Expression;
import plic.repint.GenerateurEtiquette;
import plic.repint.declaration.TDS;

public class Egal extends Comparateur {

    public Egal(Expression op1, Expression op2) {
        super(op1, op2);
    }

    @Override
    public String toMips() {
        int etiquette = GenerateurEtiquette.getInstance().getEtiquette();
        return "#On calcule " + operande1.toString() + " = " + operande2.toString() + '\n' +
                operande1.toMips() + "\n" +
                TDS.getInstance().stockerV0() +  "\n"+
                operande2.toMips() + "\nmove $v1, $v0\n" +
                TDS.getInstance().recupV0() + '\n' +
                "beq $v0, $v1, boolVrai" + etiquette + '\n'+
                "b boolFaux" + etiquette + '\n' +
                "boolVrai" + etiquette + ":\n" +
                "\tli $v0, 1\n" +
                "\tb endBool"+etiquette+"\n" +
                "boolFaux" + etiquette + ":\n" +
                "\tli $v0, 0\n" +
                "\tb endBool"+etiquette+"\n" +
                "endBool" + etiquette + ":\n" +
                "#= terminee\n";
    }

}
