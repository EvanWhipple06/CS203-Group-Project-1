import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // COMMENTED OUT FOR DEBUGGING, TAKES FIRST INPUTTED VALUE FOR CLA AND ASSIGNS IT TO fName VARIABLE
        File file;

//        if (args.length > 0) {
//            file = new File("src/"+args[0]);
//        }

        //debug for file reading
        file = new File("src/graphinput.txt");

        Main main = new Main();

        ArrayList<int[][]> fileContents = main.parseFile(file);

        ArrayList<ArrayList<Integer>> temp = main.getComponents(fileContents.get(1));

        for (int i = 0; i < temp.size(); i++) {
            for (int j : temp.get(i)) {
                System.out.println(j);
            }
            System.out.println();
        }
    }

    // parses the graph input file provided into an arraylist of 2d arrays
    public ArrayList<int[][]> parseFile(File file) {
        // store the results from the file
        ArrayList<int[][]> fileContents = new ArrayList<>();

        // open and start reading the file
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                int trim = 0, numEdges = 0;
                String edges = myReader.nextLine();

                // loop through and find index after num vertices
                for (int i = 0; i < edges.length() - 1; i++) {
                    if(edges.charAt(i) == '(' && numEdges == 0) {trim = i;}
                    if(edges.charAt(i) == ',') {numEdges++;}
                }

                // remove the '## ' at the start of the line
                edges = edges.substring(trim);

                // initialize array to go in arraylist
                int[][] temp = new int[numEdges][2];

                // trim the string of nodes into an array for easy mapping to return array
                String[] strTemp = edges.substring(1).split("[(),]+");

                // populate the innermost array with the graph connections
                for(int i = 0, j = 0; i < temp.length; i++) {
                    temp[i][j%2] = Integer.parseInt(strTemp[j++]);
                    temp[i][j%2] = Integer.parseInt(strTemp[j++]);
                }

//                DEBUG PLEASE IGNORE FOR NOW DELETE FOR SUBMISSION
//                for(int i = 0; i < temp.length; i++) {
//                    for (int j : temp[i]) {
//                        System.out.print(j);
//                    }
//                    System.out.print(" ");
//                }
//
//                System.out.println();

                fileContents.add(temp);
            }
        } catch(Exception ex) {
            System.out.println(ex);
        }

        // fileContents contains an arraylist of 2d integer arrays containing the coordinates
        return fileContents;
    }

    // print a gui displaying which tree is being processed and how many components are in it
    public void printGUI() {

    }

    // get the components of a tree and return them
    public ArrayList<ArrayList<Integer>> getComponents(int[][] inputGraph) {
        /* idea 2
            1) one arraylist stores the original graph (arrlist of int[]) ([1][2], [2],[3])
            2) one arraylist stores integers the represent each unique value present in the graph components list
            3) one arraylist of the arraylists in number 3

            start by taking the first node from 1) and putting each of those numbers into 2, remove first node of 1)
            then iterate through 1) and if any numbers from [a][b] match any numbers in 2), do a sorted insertion for new unique values. remove from 1)
            when we get to the end, repeat until no swaps are performed (this is to eliminate any values that become valid after another was added to 1) )

            add 2) to 3)
            if 1) is not empty, clear 2) and start the process on the remainder of 1)
         */

       // initialize arrayLists
        ArrayList<int[]> graph = new ArrayList<>(Arrays.asList(inputGraph));
        ArrayList<ArrayList<Integer>> components = new ArrayList<>();

        boolean run = true;

        while (run) {
            // if the last arraylist in components is empty then move the first element from graph to components
            // move the first node into the components arraylist if they arent the same number, if they are only add one of it
            if (!graph.isEmpty()) {
                components.add(new ArrayList<>());

                if (graph.getFirst()[0] != graph.getFirst()[1]) {
                    components.getLast().add(graph.getFirst()[1]);
                }
                components.getLast().addFirst(graph.getFirst()[0]);

                // remove first node from graph
                graph.removeFirst();
            }

            run = false;
            // iterate through graph, and compare each index of the inner most array to each element of components
            for (int i = 0; i < graph.size(); i++) {
                for (int j = 0; j < components.getLast().size(); j++) {
                    int a = graph.get(i)[0];
                    int b = graph.get(i)[1];
                    int x = components.getLast().get(j);

                    // if either of the innermost numbers match any of the unique numbers in the components list, break and add the new unique values then restart the loop
                    if (a == x || b == x) {
                        // if number is missing from component add it
                        if (!components.getLast().contains(a)) {
                            sortedInsertion(components, a);
                            run = true;
                        }
                        if (!components.getLast().contains(b)) {
                            sortedInsertion(components, b);
                            run = true;
                        }

                        // then remove node from graph
                        graph.remove(i);
                    }
                }
            }
        }
        return components;
    }

    // sorted insertion of value into components arraylist
    public void sortedInsertion(ArrayList<ArrayList<Integer>> arr, int value) {
        for (int i = 0; i < arr.getLast().size(); i++) {
            if (value < arr.getLast().get(i)) {arr.getLast().add(i, value); return;}
        }

        arr.getLast().addLast(value);
    }
}