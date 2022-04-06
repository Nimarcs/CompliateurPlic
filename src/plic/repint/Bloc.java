package plic.repint;

import plic.repint.declaration.Entree;
import plic.repint.declaration.TDS;
import plic.repint.exceptions.ErreurSemantique;
import plic.repint.instruction.Instruction;

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

    public void verifier() throws ErreurSemantique {
        for (Instruction i:instructions) {
            i.verifier();
        }
    }

    public String toMipsSansIntroEtOutro(){
        StringBuilder res = new StringBuilder();

        //les instructions
        for (Instruction i: instructions) {
            res.append("\n\n#INSTRUCTION SUIVANTE\n").append(i.toMips()).append("\n");
        }

        return res.toString();
    }

    public String toMips(){
        StringBuilder res = new StringBuilder();
        //Init
        res.append("""
                .data
                newline: .asciiz "\\r\\n"
                erreur_str: .asciiz "ERREUR: "
                .text
                main:
                #initialiser $S7 avec $sp
                \tmove $s7,$sp
                """);

        //affectation des variables
        res.append("#reserver la place pour "+(Math.abs(TDS.getInstance().getCptDepl())/4)+" variables\n" +
                "\tadd $sp,$sp,"+TDS.getInstance().getCptDepl()).append('\n');
        res.append("#VARIABLES DEF:\n");
        for (Entree e:TDS.getInstance().getEntrees()) {
            res.append("# ").append(e.getIdf()).append(" => ").append( TDS.getInstance().getSymbole(e).getDeplacement()).append('\n');
        }

        //les instructions
        for (Instruction i: instructions) {
            res.append("\n\n#INSTRUCTION SUIVANTE\n").append(i.toMips()).append("\n");
        }

        //fin
        res.append("""

                #Fin de programme
                end:
                \tli $v0,10
                \tsyscall
                
                	
                #Erreur
                erreur:
                	#On affiche l'erreur
                	li $v0, 4
                	la $a0, erreur_str
                	syscall	
                	
                	b end""");

        return res.toString();
    }

}
