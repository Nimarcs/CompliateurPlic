package plic.repint;

public class AccesTab extends Acces {

    private Idf idf;

    private Expression expDecalage;

    public AccesTab(Idf i, Expression dec){
        idf = i;
        expDecalage = dec;
    }

    @Override
    public String getNom() {
        return idf.getNom() + "[" + expDecalage + "]" ;
    }

    public Expression getExpDecalage() {
        return expDecalage;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        if (!TDS.getInstance().estDeclare(idf)) throw new ErreurSemantique(idf.getNom() + " n'est pas déclaré");
        if (!TDS.getInstance().getSymbole(idf).getType().equals("tableau")) throw new ErreurSemantique(idf.getNom() + " n'est pas un tableau");
        expDecalage.verifier();
    }


    @Override
    public String toMips() {
        return null;
    }

    /**
     * met l'adresse d'acces dans $a0
     */
    @Override
    public String getAdresseAcces() {
        return "#On met l'adresse memoire dans a0\n#On met le decalage dans v0\n"+
                expDecalage.toMips() +
                "#On verifie que ça ne sort pas du tableau\n" +
                "li $a0, " + TDS.getInstance().getSymbole(idf).getTaille() + '\n' +
                "blez $v0 erreur\n" +
                "bge $v0,$a0, erreur\n" +
                "#On met l'adresse dans $a0\n" +
                "li $t1, -4\n" +
                "mult $t1, $v0\n"+
                "mflo $v0\n" +
                "add $a0, $v0, $s7\n";
    }

    @Override
    public String getType() {
        return TDS.getInstance().getSymbole(idf).getType();
    }

    @Override
    public String toString() {
        return "AccesTab=" +
                idf.getNom() + "[" +
                expDecalage+ "]";
    }
}
