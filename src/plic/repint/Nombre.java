package plic.repint;

public class Nombre extends Expression{

    private int val;

    public Nombre(int v){
        val = v;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Nombre{" +
                "val=" + val +
                '}';
    }

    @Override
    public void verifier() {

    }

    /**
     * v0 <- val
     * @return mips
     */
    @Override
    public String toMips() {
        return "li $v0,"+getVal();
    }
}
