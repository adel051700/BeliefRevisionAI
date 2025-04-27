package revision.belief.logic;

public class Conjunction implements Formula {
    private final Formula left;
    private final Formula right;

    public Conjunction(Formula left, Formula right) {
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
        // De Morgan's Law: ¬(A ∧ B) ⟺ (¬A ∨ ¬B)
        return new Disjunction(left.negate(), right.negate());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Conjunction)) return false;
        Conjunction other = (Conjunction) obj;
        return (left.equals(other.left) && right.equals(other.right)) ||
               (left.equals(other.right) && right.equals(other.left));
    }

    @Override
    public int hashCode() {
        return left.hashCode() & right.hashCode();
    }

    @Override
    public String toString() {
        return "(" + left + " ∧ " + right + ")";
    }
}