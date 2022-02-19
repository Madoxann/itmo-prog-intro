import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class WsppPosition {
    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> dict = new LinkedHashMap<>();
        try (MyReader fileInput = new MyReader(new InputStreamReader(new FileInputStream(args[0]), "utf-8"))) {
            int stringCounter = 1, prevCountLines = fileInput.getCountLines();
            while (fileInput.hasNextWord())
            {
                String nextWord = fileInput.nextWord();
                int currLine = fileInput.getCountLines();
                if(currLine != prevCountLines) {
                    stringCounter = 1;
                    prevCountLines = currLine;
                }
                if (nextWord != null) {
                    dict.putIfAbsent(nextWord, new ArrayList<>());
                    dict.get(nextWord).add(currLine + ":" + stringCounter++);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try (BufferedWriter fileOutput = new BufferedWriter(new FileWriter(args[1], Charset.defaultCharset()))) {
            String[] outputSet = dict.keySet().toArray(new String[0]);
            for (String str : outputSet) {
                StringBuilder valueStr = new StringBuilder();
                ArrayList<String> wordValues = dict.get(str);
                for (String value : wordValues) {
                    value = " " + value;
                    valueStr.append(value);
                }
                fileOutput.write(str + " " + wordValues.size() + valueStr);
                fileOutput.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}