package plic.repint.instruction;

import plic.repint.exceptions.ErreurSemantique;
import plic.repint.expression.acces.Idf;

public class Lire extends Instruction {

    private final Idf idf;

    public Lire(Idf idf) {
        super();
        this.idf = idf;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        idf.verifier();
        if (!idf.getType().equals("entier")) throw new ErreurSemantique("On ne peut lire que des valeurs entiere, hors " + idf + "n'est pas de type entier");
    }

    @Override
    public String toMips() {
        return "#debut de la lecture\n" +
                "li $v0, 5\n" +
                "syscall\n" +
                "#valeur lue dans $v0\n" +
                "sw $v0, " + idf.getPointeur() + '\n'
                +"#Fin lecture \n";
    }
}
