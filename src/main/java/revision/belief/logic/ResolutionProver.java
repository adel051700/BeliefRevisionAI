package revision.belief.logic;

import java.util.*;

public class ResolutionProver {

    public static boolean entails(Set<Formula> knowledgeBase, Formula query) {
        // Convert all KB formulas to CNF and extract clauses
        Set<Set<Logic.Literal>> clauses = new HashSet<>();
        for (Formula formula : knowledgeBase) {
            Formula cnf = CNFConverter.toCNF(formula);
            clauses.addAll(extractClauses(cnf));
        }

        // Negate the query, convert to CNF, and extract clauses
        Formula negatedQuery = new Negation(query);
        Formula negatedQueryCNF = CNFConverter.toCNF(negatedQuery);
        clauses.addAll(extractClauses(negatedQueryCNF));

        // Resolution loop
        Set<Set<Logic.Literal>> newClauses = new HashSet<>();

        while (true) {
            List<Set<Logic.Literal>> clauseList = new ArrayList<>(clauses);
            for (int i = 0; i < clauseList.size(); i++) {
                for (int j = i + 1; j < clauseList.size(); j++) {
                    Set<Set<Logic.Literal>> resolvents = resolve(clauseList.get(i), clauseList.get(j));
                    if (resolvents.contains(new HashSet<>())) {
                        return true;
                    }
                    newClauses.addAll(resolvents);
                }
            }
            if (clauses.containsAll(newClauses)) {
                return false;
            }
            clauses.addAll(newClauses);
        }
    }

    private static Set<Set<Logic.Literal>> extractClauses(Formula formula) {
        Set<Set<Logic.Literal>> clauses = new HashSet<>();
        if (formula instanceof Conjunction) {
            clauses.addAll(extractClauses(((Conjunction) formula).getLeft()));
            clauses.addAll(extractClauses(((Conjunction) formula).getRight()));
        } else if (formula instanceof Disjunction) {
            Set<Logic.Literal> clause = new HashSet<>();
            collectLiterals((Disjunction) formula, clause);
            clauses.add(clause);
        } else if (formula instanceof Atom) {
            Set<Logic.Literal> clause = new HashSet<>();
            clause.add(new Logic.Literal((Atom) formula, false));
            clauses.add(clause);
        } else if (formula instanceof Negation) {
            Formula inner = ((Negation) formula).getFormula();
            if (inner instanceof Atom) {
                Set<Logic.Literal> clause = new HashSet<>();
                clause.add(new Logic.Literal((Atom) inner, true));
                clauses.add(clause);
            }
        }
        return clauses;
    }

    private static void collectLiterals(Formula formula, Set<Logic.Literal> literals) {
        if (formula instanceof Disjunction) {
            collectLiterals(((Disjunction) formula).getLeft(), literals);
            collectLiterals(((Disjunction) formula).getRight(), literals);
        } else if (formula instanceof Atom) {
            literals.add(new Logic.Literal((Atom) formula, false));
        } else if (formula instanceof Negation) {
            Formula inner = ((Negation) formula).getFormula();
            if (inner instanceof Atom) {
                literals.add(new Logic.Literal((Atom) inner, true));
            }
        }
    }

    private static Set<Set<Logic.Literal>> resolve(Set<Logic.Literal> c1, Set<Logic.Literal> c2) {
        Set<Set<Logic.Literal>> resolvents = new HashSet<>();
        for (Logic.Literal l1 : c1) {
            for (Logic.Literal l2 : c2) {
                if (isComplementary(l1, l2)) {
                    Set<Logic.Literal> resolvent = new HashSet<>();
                    resolvent.addAll(c1);
                    resolvent.addAll(c2);
                    resolvent.remove(l1);
                    resolvent.remove(l2);
                    resolvents.add(resolvent);
                }
            }
        }
        return resolvents;
    }

    private static boolean isComplementary(Logic.Literal l1, Logic.Literal l2) {
        return l1.getAtom().equals(l2.getAtom()) && (l1.isNegated() != l2.isNegated());
    }

    public static boolean isContradiction(Set<Formula> formulas) {
        // An empty set of formulas is consistent
        if (formulas.isEmpty()) {
            return false;
        }

        // A set of formulas is inconsistent if it entails false
        // This can be tested by seeing if formulas ⊢ ⊥
        // Equivalently, we can check if adding a tautology's negation leads to a contradiction
        // by using entails(formulas, tautology)

        // Using p ∨ ¬p as a tautology
        Atom p = new Atom("p");
        Formula tautology = new Disjunction(p, new Negation(p));

        return entails(formulas, tautology);
    }
}
