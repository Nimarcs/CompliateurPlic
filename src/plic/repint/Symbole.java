package plic.repint;

public class Symbole {

    private String type;

    private int deplacement;

    private int taille;

    public Symbole(String t, int depl, int l){
        type = t;
        deplacement = depl;
        taille = l;
    }

    public String getType() {
        return type;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public int getTaille() {
        return taille;
    }

    public void setDeplacement(int deplacement){
        this.deplacement=deplacement;
    }
}
