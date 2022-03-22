package plic.repint;

public abstract class Acces extends Expression {

    public abstract String getNom();

    public abstract void verifier() throws ErreurSemantique;
}
