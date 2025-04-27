package revision.belief.logic;

public class Atom implements Formula {
    private final String symbol;

    public Atom(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public Formula negate() {
        return new Negation(this);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Atom)) return false;
        return symbol.equals(((Atom) obj).symbol);
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    @Override
    public String toString() {
        return symbol;
    }
}