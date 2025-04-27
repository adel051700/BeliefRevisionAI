package revision.belief.logic;

public class Negation implements Formula {
    private final Formula formula;

    public Negation(Formula formula) {
        this.formula = formula;
    }

    public Formula getFormula() {
        return formula;
    }

    @Override
    public Formula negate() {
        return formula; // Double negation elimination
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Negation)) return false;
        return formula.equals(((Negation) obj).formula);
    }

    @Override
    public int hashCode() {
        return ~formula.hashCode();
    }

    @Override
    public String toString() {
        return "¬" + formula;
    }
}