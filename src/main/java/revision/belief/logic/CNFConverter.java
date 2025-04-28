package revision.belief.logic;

public class CNFConverter {

    public static Formula toCNF(Formula formula) {

        // Eliminate implications
        Formula noImplications = eliminateImplications(formula);

        // Push negations inward
        Formula negationsInward = pushNegations(noImplications);

        // Distribute disjunctions over conjunctions
        Formula cnf = distributeOrOverAnd(negationsInward);

        return cnf;
    }

    private static Formula eliminateImplications(Formula formula) {
        if (formula instanceof Implication) {
            Implication impl = (Implication) formula;
            return new Disjunction(
                    new Negation(eliminateImplications(impl.getAntecedent())),
                    eliminateImplications(impl.getConsequent())
            );
        } else if (formula instanceof Negation) {
            Negation neg = (Negation) formula;
            return new Negation(eliminateImplications(neg.getFormula()));
        } else if (formula instanceof Conjunction) {
            Conjunction conj = (Conjunction) formula;
            return new Conjunction(
                    eliminateImplications(conj.getLeft()),
                    eliminateImplications(conj.getRight())
            );
        } else if (formula instanceof Disjunction) {
            Disjunction disj = (Disjunction) formula;
            return new Disjunction(
                    eliminateImplications(disj.getLeft()),
                    eliminateImplications(disj.getRight())
            );
        }
        return formula;
    }

    private static Formula pushNegations(Formula formula) {
        if (formula instanceof Negation) {
            Formula inner = ((Negation) formula).getFormula();
            if (inner instanceof Negation) {
                return pushNegations(((Negation) inner).getFormula());
            }
            if (inner instanceof Conjunction) {
                Conjunction conj = (Conjunction) inner;
                return new Disjunction(
                        pushNegations(new Negation(conj.getLeft())),
                        pushNegations(new Negation(conj.getRight()))
                );
            }
            if (inner instanceof Disjunction) {
                Disjunction disj = (Disjunction) inner;
                return new Conjunction(
                        pushNegations(new Negation(disj.getLeft())),
                        pushNegations(new Negation(disj.getRight()))
                );
            }

            return new Negation(pushNegations(inner));
        } else if (formula instanceof Conjunction) {
            Conjunction conj = (Conjunction) formula;
            return new Conjunction(
                    pushNegations(conj.getLeft()),
                    pushNegations(conj.getRight())
            );
        } else if (formula instanceof Disjunction) {
            Disjunction disj = (Disjunction) formula;
            return new Disjunction(
                    pushNegations(disj.getLeft()),
                    pushNegations(disj.getRight())
            );
        }
        return formula;
    }

    private static Formula distributeOrOverAnd(Formula formula) {
        if (formula instanceof Disjunction) {
            Disjunction disj = (Disjunction) formula;
            Formula A = distributeOrOverAnd(disj.getLeft());
            Formula B = distributeOrOverAnd(disj.getRight());

            if (A instanceof Conjunction) {
                Conjunction conjA = (Conjunction) A;
                return new Conjunction(
                        distributeOrOverAnd(new Disjunction(conjA.getLeft(), B)),
                        distributeOrOverAnd(new Disjunction(conjA.getRight(), B))
                );
            }
            if (B instanceof Conjunction) {
                Conjunction conjB = (Conjunction) B;
                return new Conjunction(
                        distributeOrOverAnd(new Disjunction(A, conjB.getLeft())),
                        distributeOrOverAnd(new Disjunction(A, conjB.getRight()))
                );
            }
            return new Disjunction(A, B);
        } else if (formula instanceof Conjunction) {
            Conjunction conj = (Conjunction) formula;
            return new Conjunction(
                    distributeOrOverAnd(conj.getLeft()),
                    distributeOrOverAnd(conj.getRight())
            );
        }
        return formula;
    }
}
