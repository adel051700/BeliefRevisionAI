package revision.belief.logic;

import revision.belief.beliefbase.BeliefBase;

public class Biconditional implements Formula {
    private final Formula left;
    private final Formula right;

    public Biconditional(Formula left, Formula right) {
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
        // ¬(A ↔ B) ⟺ (A ∧ ¬B) ∨ (¬A ∧ B)
        return new Disjunction(
            new Conjunction(left, right.negate()),
            new Conjunction(left.negate(), right)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Biconditional)) return false;
        Biconditional other = (Biconditional) obj;
        return (left.equals(other.left) && right.equals(other.right)) ||
               (left.equals(other.right) && right.equals(other.left));
    }


    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public String toString() {
        return "(" + left + " ↔ " + right + ")";
    }
}