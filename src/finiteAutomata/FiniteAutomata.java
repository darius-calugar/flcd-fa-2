package finiteAutomata;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FiniteAutomata {
    public String           initialState;
    public List<String>     states      = new LinkedList<>();
    public List<String>     alphabet    = new LinkedList<>();
    public List<Transition> transitions = new LinkedList<>();
    public List<String>     finalStates = new LinkedList<>();

    public FiniteAutomata(String file) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(file));

        var statesSize = scanner.nextInt();
        for (int i = 0; i < statesSize; i++) {
            var state = scanner.next();
            if (states.contains(state))
                throw new IOException("Invalid input file (Duplicate states are not allowed)");
            states.add(state);
        }
        initialState = states.get(0);

        var alphabetSize = scanner.nextInt();
        for (int i = 0; i < alphabetSize; i++) {
            var character = scanner.next();
            if (alphabet.contains(character))
                throw new IOException("Invalid input file (Duplicate alphabet entries are not allowed)");
            alphabet.add(character);
        }

        var transitionsSize = scanner.nextInt();
        if (transitionsSize <= 0)
            throw new IOException("Invalid input file (There cannot be no transitions)");
        for (int i = 0; i < transitionsSize; i++) {
            var transition = new Transition(scanner.next(), scanner.next(), scanner.next());
            if (!states.contains(transition.originState))
                throw new IOException("Invalid input file (Transition origin state is not within the defined states)");
            if (!states.contains(transition.destinationState))
                throw new IOException("Invalid input file (Transition destination state is not within the defined states)");
            if (!alphabet.contains(transition.character))
                throw new IOException("Invalid input file (Transition character is not within the alphabet)");
            transitions.add(transition);
        }

        var finalStateSize = scanner.nextInt();
        if (finalStateSize <= 0)
            throw new IOException("Invalid input file (There cannot be no final states)");
        for (int i = 0; i < finalStateSize; i++) {
            var finalState = scanner.next();
            if (!states.contains(finalState))
                throw new IOException("Invalid input file (Final state is not within the defined states)");
            finalStates.add(finalState);
        }
    }

    public boolean verify(String sequence) {
        return move(initialState, sequence);
    }

    public boolean move(String state, String sequence) {
        System.out.println(state + ", " + sequence);
        return transitions.stream()
                .filter((t) -> t.originState.equals(state) &&
                               sequence.startsWith(t.character))
                .anyMatch(t -> (finalStates.contains(t.destinationState) && t.character.equals(sequence)) ||
                               move(t.destinationState, sequence.substring(t.character.length())));
    }
}
