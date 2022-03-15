package plic.repint;

import java.util.ArrayList;
import java.util.List;

public class Bloc {

    private List<Instruction> instructions;

    public Bloc(){
        instructions = new ArrayList<>();
    }

    public void ajouterInstruction(Instruction i){
        instructions.add(i);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Bloc{\n" +
                "instructions=\n");
        for (int i = 0; i < instructions.size()-1 ; i++) {
            Instruction instruction = instructions.get(i);
            res.append(instruction.toString()).append(",\n");

        }
        res.append(instructions.get(instructions.size()-1).toString());
        res.append("\n}");
        return res.toString();
    }

    public void verifier() throws ErreurSemantique{
        for (Instruction i:instructions) {
            i.verifier();
        }
    }

}
