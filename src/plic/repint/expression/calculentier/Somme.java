package plic.repint.expression.calculentier;

import plic.repint.expression.Expression;
import plic.repint.declaration.TDS;

public class Somme extends CalculEntier {

    public Somme(Expression op1, Expression op2) {
        super(op1, op2);
    }

    @Override
    public String toMips() {
        return "#On calcule " + operande1.toString() + " + " + operande2.toString() + '\n' +
                operande1.toMips() + "\n" +
                TDS.getInstance().stockerV0() +  "\n"+
                operande2.toMips() + "\nmove $v1, $v0\n" +
                TDS.getInstance().recupV0() + '\n' +
                "add $v0, $v0, $v1 #Somme terminee\n";
    }

}
