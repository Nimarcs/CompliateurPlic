package plic.repint.instruction.iteration;

import plic.repint.exceptions.ErreurSemantique;
import plic.repint.instruction.Instruction;

public abstract class Iteration extends Instruction {

    public abstract void verifier() throws ErreurSemantique;

    public abstract String toMips();

}
