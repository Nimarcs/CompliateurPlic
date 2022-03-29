package plic.repint;

public class Inegal extends Expression {

    private Expression operande1, operande2;

    public Inegal(Expression op1, Expression op2) {
        operande1= op1;
        operande2= op2;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        if (!operande1.getType().equals("entier")) throw new ErreurSemantique("operande 1 ne peut pas faire parti d'une comparaison, n'est pas de type entier ("+ operande1+')');
        if (!operande2.getType().equals("entier")) throw new ErreurSemantique("operande 2 ne peut pas faire parti d'une comparaison, n'est pas de type entier ("+ operande2+')');
        operande1.verifier();
        operande2.verifier();
    }

    @Override
    public String toMips() {
        int etiquette = GenerateurEtiquette.getInstance().getEtiquette();
        return "#On calcule " + operande1.toString() + " # " + operande2.toString() + '\n' +
                operande1.toMips() + "\n" +
                TDS.getInstance().stockerV0() +  "\n"+
                operande2.toMips() + "\nmove $v1, $v0\n" +
                TDS.getInstance().recupV0() + '\n' +
                "beq $v0, $v1, boolFaux" + etiquette + '\n'+
                "b boolVrai" + etiquette + '\n' +
                "boolVrai" + etiquette + ":\n" +
                "\tli $v0, 1\n" +
                "\tb endBool"+etiquette+"\n" +
                "boolFaux" + etiquette + ":\n" +
                "\tli $v0, 0\n" +
                "\tb endBool"+etiquette+"\n" +
                "endBool" + etiquette + ":\n" +
                "## terminee\n";
    }

    @Override
    public String getType() {
        return "booleen";
    }
}
