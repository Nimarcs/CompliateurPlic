package plic.repint.instruction;

import plic.repint.exceptions.ErreurSemantique;

public abstract class Instruction {

    public abstract void verifier() throws ErreurSemantique;

    public abstract String toMips();
}
