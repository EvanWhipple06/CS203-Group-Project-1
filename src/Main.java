// Author: Austyn Streeter
// Term: Fall 2026 Date: 11/2/2026
// Project: CS-203 Group Activity 1

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        runProgram();
    }

    public static void runProgram() {
        Scanner scan = new Scanner(System.in);
        int [] inputArray = readArray(scan);
        int k = readK(scan, inputArray.length);

        System.out.println();
        System.out.println("Input array before: ");
        printArray(inputArray);
        System.out.println("k = " + k);

        int result = select(inputArray, k, Integer.MIN_VALUE, Integer.MAX_VALUE);

        System.out.println("Result: " + result);
        System.out.println("Input array after (Should match the input array before):");
        printArray(inputArray);
    }

    public static int[] readArray(Scanner scan) {
        int size = 0;
        while (size <= 0) {  // Prompt the user until they enter a positive integer
            System.out.println("How many integers do you want in your array?");
            size = scan.nextInt();
            if (size <= 0) {
                System.out.println("Please enter a positive integer.");
            }
        }
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            System.out.println("Input an int in your array");
            array[i] = scan.nextInt();
        }
        return array;
    }

    public static int readK(Scanner scan, int size) {
        int k = -1;
        while (k < 0 || k >= size) {  // Prompt the user until they enter a valid index
            System.out.println("What sorted index value would you like to know in this unsorted array? (0 to " + (size - 1) + ")");
            k = scan.nextInt();
            if (k < 0 || k >= size) {
                System.out.println("Invalid index value. Please enter a value between 0 and " + (size - 1));
            }
        }
        return k;
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    // Never writes to A. lo and hi are exclusive bounds.
    // elements with lo < x < hi are still "in play"
    public static int select( int[] A, int k, int lo, int hi) {
        // Pick a pivot: the first element still in range
        int pivot = 0;
        for (int x : A) {
            if (x > lo && x < hi) {
                pivot = x;
                break;
            }
        }

        // Count L and E amoung the in-range elements only.
        int less = 0; 
        int equal = 0;
        for( int x : A) {
            if (x > lo && x < hi) {
                if (x < pivot) less++;
                else if (x == pivot) equal++;
            }   
        }

        if (k < less) {                         // k falls in L
            return select(A, k, lo, pivot);
        } else if (k < less + equal) {          // k falls in E: Done
            return pivot;
        } else {                                // k falls in G
            return select(A, k - less - equal, pivot, hi);
        }
    }   
}