package plic.repint.exceptions;

public class DoubleDeclaration extends Exception {
    public DoubleDeclaration(String message) {
        super("ERREUR: "+message);
    }
}
