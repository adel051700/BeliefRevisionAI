package revision.belief.logic;

import revision.belief.beliefbase.BeliefBase;

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
    public boolean isConsistent() {
        BeliefBase tempBase = new BeliefBase();
        tempBase.addBelief(this,1);

        Formula contradiction = new Conjunction(new Atom("contradiction"), new Negation(new Atom("contradiction")));
        return !tempBase.entails(contradiction);
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