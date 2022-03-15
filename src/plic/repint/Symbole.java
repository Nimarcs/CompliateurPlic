package plic.repint;

public class Symbole {

    private String type;

    private int deplacement;

    public Symbole(String t, int depl){
        type = t;
        deplacement = depl;
    }

    public String getType() {
        return type;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(int deplacement){
        this.deplacement=deplacement;
    }
}
