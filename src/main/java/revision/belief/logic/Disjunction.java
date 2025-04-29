package revision.belief.logic;

import revision.belief.beliefbase.BeliefBase;

public class Disjunction implements Formula {
    private final Formula left;
    private final Formula right;

    public Disjunction(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    public Formula getLeft() {
        return left;
    }

    public Formula getRight() {
        return right;
    }

    @Override
    public Formula negate() {
        // De Morgan's Law: ¬(A ∨ B) ⟺ (¬A ∧ ¬B)
        return new Conjunction(left.negate(), right.negate());
    }

    @Override
    public boolean isConsistent() {
        BeliefBase tempBase = new BeliefBase();
        tempBase.addBelief(this,1);

        Formula contradiction = new Conjunction(new Atom("contradiction"), new Negation(new Atom("contradiction")));
        return !tempBase.entails(contradiction);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Disjunction)) return false;
        Disjunction other = (Disjunction) obj;
        return (left.equals(other.left) && right.equals(other.right)) ||
               (left.equals(other.right) && right.equals(other.left));
    }

    @Override
    public int hashCode() {
        return left.hashCode() | right.hashCode();
    }

    @Override
    public String toString() {
        return "(" + left + " ∨ " + right + ")";
    }
}