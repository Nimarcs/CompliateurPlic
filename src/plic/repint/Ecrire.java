package plic.repint;

public class Ecrire extends Instruction {

    private Expression expression;

    public Ecrire(Expression e){
        expression =e;
    }

    @Override
    public String toString() {
        return "Ecrire{" +
                "expression=" + expression +
                '}';
    }

    @Override
    public void verifier() throws ErreurSemantique {
        if (!(expression.getType().equals("entier") || expression.getType().equals("booleen")))  throw new ErreurSemantique(expression + " n'est pas un entier ou un booleen");
        expression.verifier();
    }

    @Override
    public String toMips() {
        return "#calcul de l'expression\n" +
                expression.toMips()+ "\n"+
                """
                #ecrire l'expression
                    move $a0,$v0 	#on ecrira la valeur de l'expression
                    li $v0, 1	#code pour ecrire
                    syscall		#on ecrit
                    
                                
                #retour a la ligne
                li $v0, 4
                la $a0, newline
                syscall
                                """;
    }
}
