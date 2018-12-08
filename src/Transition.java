import java.util.List;

public class Transition {
    int firstState;
    int firstTapeSymbol;
    int secondState;
    int secondTapeSymbol;
    char direction;


    public Transition(int firstState, int firstTapeSymbol, int secondState, int secondTapeSymbol, char direction) {
        this.firstState = firstState;
        this.firstTapeSymbol = firstTapeSymbol;
        this.secondState = secondState;
        this.secondTapeSymbol = secondTapeSymbol;
        this.direction = direction;
    }
}
