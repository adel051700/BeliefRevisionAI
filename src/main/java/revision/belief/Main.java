package revision.belief;
import revision.belief.beliefbase.BeliefBase;
import revision.belief.logic.*;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("Belief Revision System Demo");
        System.out.println("==========================\n");

        // Create atoms
        Atom p = new Atom("p");
        Atom q = new Atom("q");
        Atom r = new Atom("r");
        Atom s = new Atom("s");

        // Create formulas
        Formula impl1 = new Implication(p, q);
        Formula impl2 = new Implication(q, r);
        Formula conjunction = new Conjunction(impl1, impl2);
        Formula disjunction = new Disjunction(p, q);
        Formula negation = new Negation(p);

        // Display the formulas
        System.out.println("Basic formulas:");
        System.out.println("p: " + p);
        System.out.println("q: " + q);
        System.out.println("r: " + r);
        System.out.println("p → q: " + impl1);
        System.out.println("q → r: " + impl2);
        System.out.println("(p → q) ∧ (q → r): " + conjunction);
        System.out.println("p ∨ q: " + disjunction);
        System.out.println("¬p: " + negation);

        // Demonstrate negation
        System.out.println("\nNegations:");
        System.out.println("¬(p → q): " + impl1.negate());
        System.out.println("¬(p ∨ q): " + disjunction.negate());
        System.out.println("¬(p ∧ q): " + new Conjunction(p, q).negate());
        System.out.println("¬¬p: " + negation.negate());

        // Test equality
        Formula impl1Duplicate = new Implication(p, q);
        System.out.println("\nEquality tests:");
        System.out.println("impl1 equals impl1Duplicate: " + impl1.equals(impl1Duplicate));
        System.out.println("p equals q: " + p.equals(q));

        // Demonstrate BeliefBase
        System.out.println("\nBeliefBase Demo:");
        BeliefBase beliefBase = new BeliefBase();
        beliefBase.addBelief(p, 3);
        beliefBase.addBelief(impl1, 2);
        beliefBase.addBelief(impl2, 1);

        System.out.println("Belief base contents:");
        System.out.println(beliefBase);

        System.out.println("Contains p: " + beliefBase.contains(p));
        System.out.println("Contains r: " + beliefBase.contains(r));

        System.out.println("\nRemoving p from belief base...");
        beliefBase.removeFormula(p);
        System.out.println("Updated belief base:");
        System.out.println(beliefBase);

        System.out.println("\n=== CNF Conversion Test ===");

        // Test CNF Conversion
        System.out.println("\n=== CNF Conversion Test ===");

        Formula implication1 = new Implication(p, q);
        Formula implication2 = new Implication(r, s);
        Formula complexFormula = new Conjunction(implication1, implication2);
        Formula biconditional = new Biconditional(p, q);

        // Print the original formula
        System.out.println("Original formula: " + complexFormula);

        // Convert to CNF
        Formula cnfFormula = CNFConverter.toCNF(complexFormula);

        // Print the CNF version
        System.out.println("CNF formula: " + cnfFormula);

        cnfFormula = CNFConverter.toCNF(biconditional);

        System.out.println("Original formula: " + biconditional);
        System.out.println("CNF formula: " + cnfFormula);

        // Test Resolution
        Set<Formula> kb = new HashSet<>();
        kb.add(new Implication(new Atom("p"), new Atom("q")));
        kb.add(new Atom("p"));

        Formula query = new Atom("q");

        //boolean result = ResolutionProver.entails(kb, query);
        //System.out.println("Entailment result: " + result);

        // Test BeliefBase Entailment

        Formula implication = new Implication(p, q);
        beliefBase.addBelief(p, 3);
        beliefBase.addBelief(implication, 2);

        // Show the belief base
        System.out.println("Belief Base:");
        System.out.println(beliefBase);

        System.out.println("Checking if belief base entails q...");

        boolean result = beliefBase.entails(q);

        System.out.println("Entailment result: " + result);

    }
}