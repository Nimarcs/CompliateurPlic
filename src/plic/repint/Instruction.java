package plic.repint;

public abstract class Instruction {

    public abstract void verifier() throws ErreurSemantique;

    public abstract String toMips();
}
