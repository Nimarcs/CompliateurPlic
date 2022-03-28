package plic.repint;

public class Idf extends Acces{

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
     * met l'adresse d'acces dans $a0
     */
    @Override
    public String getAdresseAcces() {
        int deplacement = TDS.getInstance().getSymbole(this).getDeplacement();
        return "#On met l'adresse de " + getNom() + " dans a0\n" +
                "li $v0, " + deplacement + '\n'+
                "li $t1, -4\n" +
                "mult $t1, $v0\n"+
                "mflo $v0\n" +
                "add $a0, $v0, $s7\n";
    }

    /**
     * v0 <- val de l'idf
     * @return mips
     */
    @Override
    public String toMips(){
        return "lw $v0,"+getPointeur();
    }

    @Override
    public String getType() {
        return TDS.getInstance().getSymbole(this).getType();
    }

    /**
     * revoie le pointeur vers l'idf
     * @return mips
     */
    public String getPointeur() {
        int deplacement = TDS.getInstance().getSymbole(this).getDeplacement();
        return deplacement + "($s7)";
    }


}
