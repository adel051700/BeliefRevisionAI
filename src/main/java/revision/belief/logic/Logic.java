package revision.belief.logic;

public class Logic {
    /**
     * Helper class to represent a literal (an atom or its negation)
     */
    public static class Literal {
        private final Atom atom;
        private final boolean negated;

        public Literal(Atom atom, boolean negated) {
            this.atom = atom;
            this.negated = negated;
        }

        public Atom getAtom() {
            return atom;
        }

        public boolean isNegated() {
            return negated;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Literal)) return false;
            Literal other = (Literal) obj;
            return atom.equals(other.atom) && negated == other.negated;
        }

        @Override
        public int hashCode() {
            return atom.hashCode() * (negated ? -1 : 1);
        }

        @Override
        public String toString() {
            return negated ? "¬" + atom : atom.toString();
        }
    }
}