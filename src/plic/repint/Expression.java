package plic.repint;

public abstract class Expression {

    public abstract void verifier() throws ErreurSemantique;

    public abstract String toMips();

    public abstract String getType();
}
