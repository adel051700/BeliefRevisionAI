package revision.belief.beliefbase;

import revision.belief.logic.Formula;

import java.util.*;
import java.util.stream.Collectors;

public class BeliefBase {
    private final List<BeliefEntry> beliefs;

    public BeliefBase() {
        this.beliefs = new ArrayList<>();
    }

public void addBelief(Formula formula, int priority) {
    // Check if formula already exists
    for (BeliefEntry entry : beliefs) {
        if (entry.getFormula().equals(formula)) {
            // If it exists, update priority if needed and return
            entry.priority = Math.max(entry.priority, priority);
            beliefs.sort(Comparator.comparing(BeliefEntry::getPriority).reversed());
            return;
        }
    }

    // Add the new belief
    beliefs.add(new BeliefEntry(formula, priority));

    // Sort beliefs by priority (higher first)
    beliefs.sort(Comparator.comparing(BeliefEntry::getPriority).reversed());

    // Reassign priorities based on their position in the sorted list
    for (int i = 0; i < beliefs.size(); i++) {
        beliefs.get(i).priority = beliefs.size() - i;
    }
}

    public boolean contains(Formula formula) {
        return beliefs.stream().anyMatch(entry -> entry.getFormula().equals(formula));
    }

    public Set<Formula> getAllFormulas() {
        return beliefs.stream().map(BeliefEntry::getFormula).collect(Collectors.toSet());
    }

    public List<BeliefEntry> getAllBeliefs() {
        return new ArrayList<>(beliefs);
    }

    public void removeFormula(Formula formula) {
        beliefs.removeIf(entry -> entry.getFormula().equals(formula));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BeliefEntry entry : beliefs) {
            sb.append(entry.getFormula()).append(" (priority: ").append(entry.getPriority()).append(")\n");
        }
        return sb.toString();
    }

    // Inner class to represent a belief with its priority
    public static class BeliefEntry {
        private final Formula formula;
        private int priority;

        public BeliefEntry(Formula formula, int priority) {
            this.formula = formula;
            this.priority = priority;
        }

        public Formula getFormula() {
            return formula;
        }

        public int getPriority() {
            return priority;
        }
    }

    public boolean entails(Formula formula) {
        return revision.belief.logic.ResolutionProver.entails(getAllFormulas(), formula);
    }

    public String formulasToString() {
        StringBuilder sb = new StringBuilder();
        for (BeliefEntry entry : beliefs) {
            sb.append(entry.getFormula()).append("\n");
        }
        return sb.toString();
    }

    public boolean isSubsetOf(BeliefBase other) {
        for (BeliefEntry entry : beliefs) {
            if (!other.contains(entry.getFormula())) {
                return false;
            }
        }
        return true;
    }

    public BeliefBase clone() {
        BeliefBase clonedBeliefBase = new BeliefBase();
        for (BeliefEntry entry : beliefs) {
            clonedBeliefBase.addBelief(entry.getFormula(), entry.getPriority());
        }
        return clonedBeliefBase;
    }

    public boolean isConsistent() {
        Set<Formula> formulas = getAllFormulas();
        return !revision.belief.logic.ResolutionProver.isContradiction(formulas);
    }


    public boolean equals(Object obj){
        if (this == obj) return true;
        if (!(obj instanceof BeliefBase)) return false;
        BeliefBase other = (BeliefBase) obj;
        return this.getAllFormulas().equals(other.getAllFormulas());
    }

}