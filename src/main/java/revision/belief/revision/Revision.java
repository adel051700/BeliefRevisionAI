package revision.belief.revision;

import revision.belief.beliefbase.BeliefBase;
import revision.belief.logic.Formula;

public class Revision {

    public static void contract(BeliefBase base, Formula formula) {
        if (base.contains(formula)) {
            base.removeFormula(formula);
        }
    }

    public static void expand(BeliefBase base, Formula formula, int priority) {
        base.addBelief(formula, priority);
    }

    public static void revise(BeliefBase base, Formula formula, int priority) {
        BeliefBase tempBase = base.clone();
        expand(tempBase, formula, priority);
        if(tempBase.isConsistent()) {
            expand(base, formula, priority);
        }
    }
}
