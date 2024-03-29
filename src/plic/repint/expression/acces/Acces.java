package plic.repint.expression.acces;

import plic.repint.exceptions.ErreurSemantique;
import plic.repint.expression.Expression;

public abstract class Acces extends Expression {

    /**
     * Pour le debuggage et commentaires
     */
    public abstract String getNom();

    /**
     * Verifie semantiquement l'acces
     */
    public abstract void verifier() throws ErreurSemantique;

    /**
     * met l'adresse d'acces dans $a0
     */
    public abstract String getAdresseAcces();
}
