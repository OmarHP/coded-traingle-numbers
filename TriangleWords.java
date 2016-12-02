import java.io.*;
import java.util.StringTokenizer;

/**
* Created by Omar Ham on 14/10/2016.
*
* Solution:
* First of all, we have this formula to calculate
* the nth value of the sequence
*
*       1/2 * n *(n+1)=value
*
* where value (v) is in this case the word value,
* so in order to figure out if some value belongs
* to the sequence, we need to find the value of n
* for that value, to do this we isolate the n variable
* and we get the next quadratic equation:
*
*   n^2 + n = 2 * value
*
*   n^2 + n - 2 * value = 0
*
*   We solve the equation using the quadratic
*   formula and check if any square root
*   is positive and an integer. if it's true
*   the word value is a triangle number and
*   belongs to the sequence, in any other case
*   it does not.
*
*   Possible improvements:
*
*   1.- Using a table to record the values already
*   calculated, avoiding solving the same equation
*   multiple times (dynamic programming)
*
*
* */
public class TriangleWords {

    public static void main(String[] args) throws IOException {

        //Default file
        String filePath = "words.txt";

        //We take the file path from args
        if (args.length > 0) {
            filePath = args[0];
        }

        try {
            TriangleWords tw = new TriangleWords();
            //We pass as parameter the path of the file which contains the words
            int num = tw.solve(filePath);

            //We print the solution
            System.out.println("Triangle words:" + num);
        }catch (FileNotFoundException e){
            System.out.println("File not found. Make sure that file exists: "+new File(filePath).getAbsolutePath());
        }

    }

    //Function to solve the problem
    public int solve(String filePath) throws IOException {
        //Variable to count the number
        // of triangle words in the file
        int counter = 0;

        File file = new File(filePath);
        FileReader f = null;

        f = new FileReader(new File(filePath));
        BufferedReader b = new BufferedReader(f);
        String line;
        long initTime = System.currentTimeMillis();
        long totalTime = 0;
        //We read the file line by line
        while ((line = b.readLine()) != null) {
            //We obtain all the words in such line
            StringTokenizer st = new StringTokenizer(line, ",");
            //We process word by word
            while (st.hasMoreTokens()) {
                String word = st.nextToken();
                //We get the word value
                int value = getWordValue(word.replace("\"", ""));
                //We find the value of n
                double n = findN(value);
                //if n is positive and an integer, the word
                // value is triangle and so does the word
                if (n == (int) n) {
                    counter++;
                }
            }
        }
        totalTime = System.currentTimeMillis() - initTime;
        b.close();

        System.out.println("Solution took: " + totalTime + " ms");
        return counter;

    }

    //Function to calculate the  value of n
    public double findN(int value){
        return solveCuadratic(1,1,-2*value);
    }

    //Function to solve a quadratic equation using the positive root
    //Quadratic formula= (-b +/- sqrt(-b^2-4ac))/(2a)
    //For this particular case we only need the positive square root
    public double solveCuadratic(int a, int b, int c){

        return (-b+Math.sqrt(b*b-4*a*c))/2*a;
    }

    /*Function to get the word value*/
    public int getWordValue(String word){
        word=word.toUpperCase();
        int value=0;
        for(char c: word.toCharArray()){
            //We get the alphabetical position by substracting the ascii value
            // of 'A' from the ascii value of c and we add 1 unit to start in that index
            value+=c-'A'+1;
        }
        return value;
    }
}