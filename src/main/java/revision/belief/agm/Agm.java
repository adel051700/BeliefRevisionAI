package revision.belief.agm;

import revision.belief.beliefbase.BeliefBase;
import revision.belief.logic.Formula;
import revision.belief.revision.Revision;

public class Agm {

    // Success P includes K revised P
    public boolean satisfySuccess(BeliefBase base, Formula formula, int priority) {
        if (!base.contains(formula)) {
            Revision.revise(base, formula, priority);
            return base.contains(formula);
        }
        return false;
    }

    public boolean satisfyInclusion(BeliefBase base, Formula formula, int priority) {

    }

    public boolean satisfyVacuity(BeliefBase base, Formula formula, int priority){
        if(!base.contains(formula.negate())){
            BeliefBase beliefBase = base.clone();
            Revision.revise(base, formula, priority);
            Revision.expand(beliefBase, formula, priority);
            return base.equals(beliefBase);
        }
        return false;
    }
}
