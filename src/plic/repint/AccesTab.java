package plic.repint;

import plic.analyse.ErreurSyntaxique;

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
        expDecalage.verifier();
    }

    @Override
    public String toMips() {
        return "#On met l'adresse memoire dans v0\n"+expDecalage.toMips();
    }

    @Override
    public String toString() {
        return "AccesTab=" +
                idf.getNom() + "[" +
                expDecalage+ "]";
    }
}
