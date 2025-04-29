package revision.belief.logic;

import revision.belief.beliefbase.BeliefBase;

public class Implication implements Formula {
    private final Formula antecedent;
    private final Formula consequent;

    public Implication(Formula antecedent, Formula consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
    }

    public Formula getAntecedent() {
        return antecedent;
    }

    public Formula getConsequent() {
        return consequent;
    }

    @Override
    public Formula negate() {
        // ¬(A → B) ⟺ A ∧ ¬B
        return new Conjunction(antecedent, consequent.negate());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Implication)) return false;
        Implication other = (Implication) obj;
        return antecedent.equals(other.antecedent) && consequent.equals(other.consequent);
    }

    @Override
    public int hashCode() {
        return ~antecedent.hashCode() | consequent.hashCode();
    }

    @Override
    public String toString() {
        return "(" + antecedent + " → " + consequent + ")";
    }
}