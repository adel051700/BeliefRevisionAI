package revision.belief.agm;

import revision.belief.beliefbase.BeliefBase;
import revision.belief.logic.*;
import revision.belief.revision.Revision;

import java.text.Normalizer;

public class Agm {

    public Agm() {
        // Constructor
    }

    // Success P includes K revised P
    public boolean satisfySuccess(BeliefBase base, Formula formula, int priority) {
        if (!base.contains(formula)) {
            Revision.revise(base, formula, priority);
            return base.contains(formula);
        }
        return false;
    }

    public boolean satisfyInclusion(BeliefBase base, Formula formula, int priority) {
        BeliefBase expandedBase = base.clone();
        Revision.revise(base, formula, priority);
        Revision.expand(expandedBase, formula, priority);
        return base.isSubsetOf(expandedBase);
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

    public boolean satisfyConsistency(BeliefBase base, Formula formula, int priority) {
        if(formula != null && base.entails(new Negation(formula))) {
            return true;
        }
        Revision.revise(base, formula, priority);
        return base.isConsistent();
    }

    public boolean satisfyExtensionality(BeliefBase base, Formula formula, int priority) {
        if(formula instanceof Biconditional){
            BeliefBase tempBase = base.clone();
            Formula left = new Atom("LR");
            Formula right = new Atom("LR");
            Revision.revise(base, left, priority);
            Revision.revise(tempBase, right, priority);
            return base.equals(tempBase);
        }
        return false;
    }

}
