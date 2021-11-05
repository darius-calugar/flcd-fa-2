import finiteAutomata.FiniteAutomata;

import java.util.Scanner;

public class Menu {
    boolean        running      = true;
    FiniteAutomata finiteAutomata;
    Scanner        inputScanner = new Scanner(System.in);

    public void start() {
        try {
            finiteAutomata = new FiniteAutomata("input/FA.in");
            showMenu();
            while (running) {
                System.out.print("\n> ");
                Runnable command = switch (inputScanner.next()) {
                    case "states" -> this::showStates;
                    case "transitions" -> this::showTransitions;
                    case "test1" -> this::test1;
                    case "test2" -> this::test2;
                    case "verify" -> this::verify;
                    case "exit" -> this::exit;
                    default -> null;
                };
                if (command != null)
                    command.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showStates() {
        System.out.print(finiteAutomata.states
                .stream()
                .map(state -> {
                    var stateString = state;
                    if (finiteAutomata.initialState.equals(state))
                        stateString += " - Initial";
                    if (finiteAutomata.finalStates.contains(state))
                        stateString += " - Final";
                    return stateString + "\n";
                })
                .reduce("States:\n", (lhs, rhs) -> lhs + rhs));
    }

    void showTransitions() {
        System.out.print(finiteAutomata.transitions
                .stream()
                .map(transition -> transition + "\n")
                .reduce("Transitions:\n", (lhs, rhs) -> lhs + rhs));
    }

    void test1() {
        var input = "aabc";
        if (finiteAutomata.verify(input))
            System.out.println("Test 1: \"" + input + "\" is accepted");
        else
            System.out.println("Test 1: \"" + input + "\" is not accepted");
    }

    void test2() {
        var input = "aba";
        if (finiteAutomata.verify(input))
            System.out.println("Test 1: \"" + input + "\" is accepted");
        else
            System.out.println("Test 1: \"" + input + "\" is not accepted");
    }

    void verify() {
        var input = inputScanner.next();
        if (finiteAutomata.verify(input))
            System.out.println("Input \"" + input + "\" is accepted");
        else
            System.out.println("Input \"" + input + "\" is not accepted");
    }

    void exit() {
        running = false;
    }

    public void showMenu() {
        System.out.println("states      - Show the list of states");
        System.out.println("transitions - Show the list of showTransitions");
        System.out.println("test1       - Verify the sequence \"aabc\" (should accept)");
        System.out.println("test2       - Verify the sequence \"aba\" (should not accept)");
        System.out.println("verify      - Verify a custom sequence");
        System.out.println("exit        - Exit the program");
    }
}
