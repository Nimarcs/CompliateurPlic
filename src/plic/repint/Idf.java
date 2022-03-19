package plic.repint;

public class Idf extends Expression{

    private String nom;

    public Idf(String n){
        nom = n;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Idf{" +
                "nom='" + nom + '\'' +
                '}';
    }

    @Override
    public void verifier() throws ErreurSemantique {
        if (!TDS.getInstance().estDeclare(this)) throw new ErreurSemantique(nom + " n'est pas déclaré");
    }

    /**
     * v0 <- val de l'idf
     * @return mips
     */
    @Override
    public String toMips(){
        return "lw $v0,"+getPointeur();
    }

    /**
     * revoie le pointeur vers l'idf
     * @return mips
     */
    public String getPointeur(){
        int deplacement = TDS.getInstance().getSymbole(this).getDeplacement();
        return deplacement + "($s7)";
    }


}
