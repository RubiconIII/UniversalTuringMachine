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

        //parse transitions out of transition input
        int i = 0;
        while ( i < transitionDefinition.length){ //read all of the input regarding transitions
            int firstState = transitionDefinition[i].length();
            i++;
            int firstTapeSymbol = transitionDefinition[i].length();
            i++;
            int secondState = transitionDefinition[i].length();
            i++;
            int secondTapeSymbol = transitionDefinition[i].length();
            i++;
            char direction;
            if (transitionDefinition[i].length() == 1){ //parse for left head movement
                direction = 'L';
            } else if (transitionDefinition[i].length() == 2){ //parse for right head movement
                direction = 'R';
            } else {
                direction = 'N';
            }
            i++;

            Transition t = new Transition(firstState, firstTapeSymbol, secondState, secondTapeSymbol, direction); //make a new transition
            allTransitions.add(t); //store the transition in the queue
        }
        return allTransitions;
    }

    /** Executes all transitions and prints results to console
     *
     * @param transitions
     * @param utm
     */
    public static void executeTransitions(Queue<Transition> transitions, UniversalTuringMachine utm){
        int transitionState;
        int head = utm.startState; //read head starts at the start state

        int i = 0;
        while (i < transitions.size()){ //for all the transitions
            Transition t = transitions.remove(); //remove the first one

            if(head != t.firstState){ //if the head isn't at the first state
                System.out.println("Error in input"); //there's a problem
            } else {
                transitionState = t.secondState; //otherwise, the transition state is the second state

                if (t.direction == 'L') {
                    head--; //move the head left
                }
                else if(t.direction == 'R'){
                    head++; //move the head right
                }

                if (head == transitionState) { //make sure the head moves as expected
                    System.out.println("(" + t.firstState + ", " + t.firstTapeSymbol + ")" + " => (" + t.secondState + ", " + t.secondTapeSymbol + ", " + t.direction + ")"); //print out the transition
                } else {
                    System.out.println("Error in input"); //otherwise, there's a problem with the instructions.
                }
            }
        }
        if (head == utm.rejectState){
            System.out.println("Rejected"); //machine rejects if the head ends in the reject state
        }
        if (head == utm.acceptState){
            System.out.println("Accepting"); //machine is accepting if the head ends in the accept state
        }
    }

    /** Creates the UTM based on the file input
     *
     * @param machineInfoInput
     * @return
     */
    public static UniversalTuringMachine createUniversalTuringMachine(String machineInfoInput) {

        String[] machineDefinition = machineInfoInput.split("1");

        //parse all of the machine definition input
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
