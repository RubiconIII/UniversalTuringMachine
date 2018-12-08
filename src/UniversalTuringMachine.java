/** This is an attempt to create a Universal Turing Machine
 * Based on input discussed in Theory of Computation course
 *
 * @author Curtis P. Hohl
 * @date 12/8/2018
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

public class UniversalTuringMachine {
    int numberOfStates;
    int leftEndMarker;
    String inputAlphabet;
    String tapeAlphabet;
    int blankSymbol;
    int startState;
    int rejectState;
    int acceptState;

    public UniversalTuringMachine(
            int numberOfStates,
            int leftEndMarker,
            String inputAlphabet,
            String tapeAlphabet,
            int blank,
            int startState,
            int rejectState,
            int acceptstate) {
        this.numberOfStates = numberOfStates;
        this.leftEndMarker = leftEndMarker;
        this.inputAlphabet = inputAlphabet;
        this.tapeAlphabet = tapeAlphabet;
        this.blankSymbol = blank;
        this.startState = startState;
        this.rejectState = rejectState;
        this.acceptState = acceptstate;
    }

    public static void main(String args[]){
        runUniversalTuringMachineFromFile("input/input1.txt");
    }

    /** Reads from specified input file
     * runs turing machine
     * prints UTM info and transition info to console
     * @param fileToRead
     */
    public static void runUniversalTuringMachineFromFile(String fileToRead) {
        System.out.println("Reading input from file: " + fileToRead);

        try {
            BufferedReader readInput = new BufferedReader(new FileReader(fileToRead));
            var input = readInput.readLine(); //read line of input from file

            String splitMachineInfoFromTransitionInfo[] = input.split("111");
            String machineInfoInput = splitMachineInfoFromTransitionInfo[0]; //parse out the machine info
            String transitions = splitMachineInfoFromTransitionInfo[1]; //parse out all of the transitions

            UniversalTuringMachine utm = createUniversalTuringMachine(machineInfoInput); //get the utm
            printUniversalTuringMachineInfo(utm); //print info about the utm to the user

            Queue<Transition> t = getAllTransitions(transitions); //get all of the transitions in the machine

            executeTransitions(t, utm);

        } catch (Exception e) {
        System.out.println("Read failed with exception: " + e);
        }
    }

    /** gets all of the transition information from the input
     *
     * @param transitions
     * @return Queue of all transitions
     */
    public static Queue<Transition> getAllTransitions (String transitions){
        String[] transitionDefinition = transitions.split("1");
        Queue<Transition> allTransitions = new LinkedList<>();

        int i = 0;
        while ( i < transitionDefinition.length){
            int firstState = transitionDefinition[i].length();
            i++;
            int firstTapeSymbol = transitionDefinition[i].length();
            i++;
            int secondState = transitionDefinition[i].length();
            i++;
            int secondTapeSymbol = transitionDefinition[i].length();
            i++;
            char direction;
            if (transitionDefinition[i].length() == 1){
                direction = 'L';
            } else if (transitionDefinition[i].length() == 2){
                direction = 'R';
            } else {
                direction = 'N';
            }
            i++;

            Transition t = new Transition(firstState, firstTapeSymbol, secondState, secondTapeSymbol, direction);
            allTransitions.add(t);
        }
        return allTransitions;
    }

    /** Executes all transitions and prints results to console
     *
     * @param transitions
     * @param utm
     */
    public static void executeTransitions(Queue<Transition> transitions, UniversalTuringMachine utm){
        int transitionState = utm.startState;
        int head = utm.startState;

        int i = 0;
        while (i < transitions.size()){
            Transition t = transitions.remove();

            if(head != t.firstState){
                System.out.println("Error in input");
            } else {
                transitionState = t.secondState;

                if (t.direction == 'L') {
                    head--;
                }
                else if(t.direction == 'R'){
                    head++;
                }

                if (head == transitionState) {
                    System.out.println("(" + t.firstState + ", " + t.firstTapeSymbol + ")" + " => (" + t.secondState + ", " + t.secondTapeSymbol + ", " + t.direction + ")");
                } else {
                    System.out.println("Error in input");
                }
            }
        }
        if (head == utm.rejectState){
            System.out.println("Rejected");
        }
        if (head == utm.acceptState){
            System.out.println("Accepting");
        }
    }

    /** Creates the UTM based on the file input
     *
     * @param machineInfoInput
     * @return
     */
    public static UniversalTuringMachine createUniversalTuringMachine(String machineInfoInput) {

        String[] machineDefinition = machineInfoInput.split("1");

        int numStates = machineDefinition[0].length();
        int leftMarker = machineDefinition[1].length();
        String inputAlphabet = "\'" + machineDefinition[2] + "\'";
        String tapeAlphabet = "\'" +machineDefinition[3] + "\'";
        int blank = machineDefinition[4].length();
        int start = machineDefinition[5].length();
        int reject = machineDefinition[6].length();
        int accept = machineDefinition[7].length();

        UniversalTuringMachine utm = new UniversalTuringMachine(numStates, leftMarker, inputAlphabet, tapeAlphabet, blank, start, reject, accept);

        return utm;
    }

    /** Prints UTM information to user
     *
     * @param utm
     */
    public static void printUniversalTuringMachineInfo(UniversalTuringMachine utm){
        System.out.println("--------------------------------");
        System.out.println("--- Universal Turing Machine ---");
        System.out.println("--------------------------------");
        System.out.println("The number of states is: " + utm.numberOfStates);
        System.out.println("The start state is: " + utm.startState);
        System.out.println("The accept state is: " + utm.acceptState);
        System.out.println("The reject state is: " + utm.rejectState);
        System.out.println("The blank symbol is: " + utm.blankSymbol);
        System.out.println("The input alphabet is: " + utm.inputAlphabet);
        System.out.println("The tape alphabet is: " + utm.tapeAlphabet);
    }
}
