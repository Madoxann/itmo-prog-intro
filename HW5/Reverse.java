import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Reverse {
    public static int[] getDoubledArray(int[] arr) {
        return Arrays.copyOf(arr, arr.length * 2);
    }

    public static void main(String[] args) {
        ArrayList<int[]> matrix = new ArrayList<>();
        try (MyReader in = new MyReader(System.in)) {
            while (in.hasNextLine()) {
                try (MyReader inLine = new MyReader(in.nextLine())) {
                    if (inLine.hasNextInt()) {
                        int[] readIntLine = new int[10];
                        int counter = 0;
                        while (inLine.hasNextInt()) {   
                            if (counter == readIntLine.length) {
                                readIntLine = getDoubledArray(readIntLine);
                            }
                            readIntLine[counter++] = inLine.nextInt(); 
                        }
                        int[] trueIntArr = new int[counter];
                        for (int i = 0; i < counter; i++) {
                            trueIntArr[i] = readIntLine[i];
                        }
                        matrix.add(trueIntArr);
                    } else {
                        matrix.add(new int[0]);
                    }
                }
            } 
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (int i = matrix.size() - 1; i > -1; i--) {
            int[] currentArr = matrix.get(i);
            for (int j = currentArr.length - 1; j > -1; j--) {
                System.out.print(currentArr[j] + " ");
            }
            System.out.println();
        }
    }
}
