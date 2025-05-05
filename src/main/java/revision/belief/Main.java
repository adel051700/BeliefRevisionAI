package revision.belief;
import revision.belief.beliefbase.BeliefBase;
import revision.belief.logic.*;
import revision.belief.revision.Revision;
import revision.belief.agm.Agm;

import java.util.Scanner;
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

        boolean result = ResolutionProver.entails(kb, query);
        System.out.println("Entailment result: " + result);

        // Test BeliefBase Entailment

        Formula implication = new Implication(p, q);
        beliefBase.addBelief(p, 3);
        beliefBase.addBelief(implication, 2);

        // Show the belief base
        System.out.println("Belief Base:");
        System.out.println(beliefBase);

        System.out.println("Checking if belief base entails q...");

        //boolean result = beliefBase.entails(q);

        System.out.println("Entailment result: " + result);

        System.out.println(beliefBase);


        // take input from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a formula (a for atom, b for biconditional, c for conjunction, d for disjunction, i for implication, n for negation): ");
        String input = scanner.nextLine();
        Formula userFormula = null;
        while(userFormula==null && !input.equals("exit")) {
            switch (input) {
                case "a":
                    System.out.println("Enter the name of the atom: ");
                    String atomName = scanner.nextLine();
                    userFormula = new Atom(atomName);
                    break;
                case "b":
                    System.out.println("Enter the left formula: ");
                    Formula left = new Atom(scanner.nextLine());
                    System.out.println("Enter the right formula: ");
                    Formula right = new Atom(scanner.nextLine());
                    userFormula = new Biconditional(left, right);
                    break;
                case "c":
                    System.out.println("Enter the left formula: ");
                    left = new Atom(scanner.nextLine());
                    System.out.println("Enter the right formula: ");
                    right = new Atom(scanner.nextLine());
                    userFormula = new Conjunction(left, right);
                    break;
                case "d":
                    System.out.println("Enter the left formula: ");
                    left = new Atom(scanner.nextLine());
                    System.out.println("Enter the right formula: ");
                    right = new Atom(scanner.nextLine());
                    userFormula = new Disjunction(left, right);
                    break;
                case "i":
                    System.out.println("Enter the left formula: ");
                    left = new Atom(scanner.nextLine());
                    System.out.println("Enter the right formula: ");
                    right = new Atom(scanner.nextLine());
                    userFormula = new Implication(left, right);
                    break;
                case "n":
                    System.out.println("Enter the formula to negate: ");
                    Formula negatedFormula = new Atom(scanner.nextLine());
                    userFormula = new Negation(negatedFormula);
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
        if(!input.equals("exit")) {
            System.out.println("You entered: " + userFormula);
        System.out.println("Give a priority to the formula: ");
        int priority = scanner.nextInt();
        beliefBase.addBelief(userFormula, priority);
        System.out.println("Updated belief base after revision:");
        System.out.println(beliefBase);
        System.out.println(("Belief base contains " + userFormula + ": " + beliefBase.contains(userFormula)));
        }

        // Test AGM properties
        Agm agm = new Agm();
        Formula formula = new Biconditional(new Atom("t"), new Atom("k"));
        Formula formula1 = new Atom("dean");
        Formula formula2 = new Atom("phar");
        System.out.println("\nAGM Properties:");
        System.out.println("Satisfy Success: " + agm.satisfySuccess(beliefBase, formula, 1));
        System.out.println("Satisfy Inclusion: " + agm.satisfyInclusion(beliefBase, formula, 1));
        System.out.println("Satisfy Vacuity: " + agm.satisfyVacuity(beliefBase, formula, 1));
        System.out.println("Satisfy Consistency: " + agm.satisfyConsistency(beliefBase, formula, 1));
        System.out.println("Satisfy Extensionality: " + agm.satisfyExtensionality(beliefBase, formula, 1));
    }
}