package plic.repint.exceptions;

public class ErreurSemantique extends Exception{
    public ErreurSemantique(String message) {
        super("ERREUR: "+message);
    }
}
