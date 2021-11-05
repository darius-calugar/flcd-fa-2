package finiteAutomata;

public class Transition {
    public String originState;
    public String destinationState;
    public String character;

    public Transition(String originState, String destinationState, String character) {
        this.originState      = originState;
        this.destinationState = destinationState;
        this.character        = character;
    }

    @Override
    public String toString() {
        return "(" + originState + "," + character + ")->" + destinationState;
    }
}
