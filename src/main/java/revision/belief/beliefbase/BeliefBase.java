package revision.belief.beliefbase;

import revision.belief.logic.Formula;

import java.util.*;

public class BeliefBase {
    private final List<BeliefEntry> beliefs;

    public BeliefBase() {
        this.beliefs = new ArrayList<>();
    }

    public void addBelief(Formula formula, int priority) {
        beliefs.add(new BeliefEntry(formula, priority));
        // Sort beliefs by priority (higher priority first)
        beliefs.sort(Comparator.comparing(BeliefEntry::getPriority).reversed());
    }

    public boolean contains(Formula formula) {
        return beliefs.stream().anyMatch(entry -> entry.getFormula().equals(formula));
    }

    public List<Formula> getAllFormulas() {
        return beliefs.stream().map(BeliefEntry::getFormula).toList();
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
        private final int priority;

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
}