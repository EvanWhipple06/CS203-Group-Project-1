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

        main.printGUI(fileContents);

//        ArrayList<ArrayList<Integer>> temp = main.getComponents(fileContents.get(1));
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

                fileContents.add(temp);
            }
        } catch(Exception ex) {
            System.out.println(ex);
        }

        // fileContents contains an arraylist of 2d integer arrays containing the coordinates
        return fileContents;
    }

    // print a gui displaying which tree is being processed and how many components are in it
    public void printGUI(ArrayList<int[][]> fileContents) {
        System.out.println("----------------------------------------------------------");

        // iterate through file contents (each individual line is its own tree)
        for (int i = 0; i < fileContents.size(); i++) {
            // make a storage variable for an individual line's
            ArrayList<ArrayList<Integer>> temp = getComponents(fileContents.get(i));
            System.out.printf("Graph %d:\n", i+1);
            System.out.printf("%d %s: ", temp.size(), temp.size() > 1 ? "connected components" : "connected component");

            for (int j = 0; j < temp.size(); j++) {
                System.out.print("{");
                for(int k = 0; k < temp.get(j).size(); k++) {
                    System.out.printf("%s", temp.get(j).get(k));
                    if(k != temp.get(j).size() - 1) {
                        System.out.print(",");
                    }
                }
                System.out.print("} ");
            }
            System.out.println();
        }

        System.out.println("----------------------------------------------------------");
    }

    // get the components of a tree and return them
    public ArrayList<ArrayList<Integer>> getComponents(int[][] inputGraph) {
        // changes inputGraph type so it works with getComponent()
        ArrayList<int[]> newInputGraph = new ArrayList<>(Arrays.asList(inputGraph));

        // initialize variables
        ArrayList<Integer> visited = new ArrayList<>();
        ArrayList<ArrayList<Integer>> components = new ArrayList<>();

        // go through every edge in the graph
        for (int i = 0; i < newInputGraph.size(); i++) {
            // check both vertices in each edge
            for (int j = 0; j < newInputGraph.get(i).length; j++) {
                // if vertex is unvisited, start a new DFS
                if (!visited.contains(newInputGraph.get(i)[j])) {

                    // create a new component and call DFS
                    ArrayList<Integer> component = new ArrayList<>();
                    DFS(newInputGraph.get(i)[j], newInputGraph, visited, component);

                    // save completed component to be added to the return arraylist
                    components.add(component);
                }
            }
        }

        return components;
    }

    // recursive implementation of a depth first search
    public void DFS(int current, ArrayList<int[]> graph, ArrayList<Integer> visited, ArrayList<Integer> graphComponents) {
        // add current node to visited (this is our traversal tracker) then add the unvisited component to the DFS call arraylist
        visited.add(current);
        graphComponents.add(current);

        // iterate through input graph
        for (int i = 0; i < graph.size(); i++) {
            // track both node values
            int a = graph.get(i)[0];
            int b = graph.get(i)[1];

            // if node value matches current value and hasnt been visited jump to that node now
            if (a == current && !visited.contains(b)) {
                DFS(b, graph, visited, graphComponents);
            } else if (b == current && !visited.contains(a)) {
                DFS(a, graph, visited, graphComponents);
            }
        }
    }
}