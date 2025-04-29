package revision.belief.logic;
import revision.belief.beliefbase.BeliefBase;

public interface Formula {
    Formula negate();

    boolean isConsistent();

}