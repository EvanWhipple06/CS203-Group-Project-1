import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
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

        ArrayList<Integer[][]> fileContents = main.parseFile(file);

    }

    public ArrayList<Integer[][]> parseFile(File file) {
        // store the results from the file
        ArrayList<Integer[][]> fileContents = new ArrayList<Integer[][]>();

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

                edges = edges.substring(trim);

                Integer[][] temp = new Integer[numEdges][2];

                String[] strTemp = edges.substring(1).split("[(),]+");

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
}