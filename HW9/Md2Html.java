package md2html;

import md2html.markup.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {

    private static final char[] markupSpecials = {'*', '-', '_', '`', '%'};

    static int charsRead;
    static int occurrenceFound;

    static StringBuilder str;

    private static void replaceNextOccurrence(String markupSpecial) {
        String toConvert;
        int lengthOfOcc = markupSpecial.length();

        for (int i = occurrenceFound; i < str.length(); i++) {
            try {
                if (markupSpecial.equals(str.substring(i, i + lengthOfOcc)) && (i + lengthOfOcc <= str.length())){
                    if (str.substring(i + 1, i + 2).equals(markupSpecial)) {
                        i++;
                        continue;
                    }
                    toConvert = convert(str.substring(occurrenceFound - 1, i + lengthOfOcc), markupSpecial);
                    str.replace(occurrenceFound - 1, i + lengthOfOcc, toConvert);
                    return;
                }
            } catch (IndexOutOfBoundsException e) {
                return;
            }
        }
    }

    static String convert(String str, String markupSpecial) {
        String convertedStr = str;
        convertedStr = switch (markupSpecial) {
            case "*", "_" -> new Emphasis(convertedStr).toMarkdown();
            case "**", "__" -> new Strong(convertedStr).toMarkdown();
            case "--" -> new Strikeout(convertedStr).toMarkdown();
            case "`" -> new Code(convertedStr).toMarkdown();
            case "%" -> new Variable(convertedStr).toMarkdown();
            default -> str;
        };
        return convertedStr;
    }

    public static void main(String[] args) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {

            String line = in.readLine();
            while (line != null) {
                //init static vars to be used
                charsRead = 0;
                occurrenceFound = 0;

                //str to get input
                StringBuilder currentBlockTag = new StringBuilder();

                //str to get string that need to be written into file
                str = new StringBuilder();

                //reading logic
                while (line.isEmpty()) line = in.readLine();
                while (line != null && !line.isEmpty()) {
                    //System.err.println("here");
                    line = line.replace("&", "&amp;");
                    line = line.replace("<", "&lt;");
                    line = line.replace(">", "&gt;");
                    if (currentBlockTag.length() > 0) currentBlockTag.append(System.lineSeparator());
                    currentBlockTag.append(line);
                    line = in.readLine();
                }
                //get all read chars to <p> or <h*>
                if (currentBlockTag.length() > 0) {
                    int headerLevel = 0;
                    for (int i = 0; i < currentBlockTag.length(); i++) {
                        if (currentBlockTag.charAt(i) == '#') {
                            headerLevel++;
                        } else if (Character.isWhitespace(currentBlockTag.charAt(i)) && headerLevel > 0) {
                            str.append(new Header(currentBlockTag.toString(), headerLevel).toMarkdown());
                            str.append(System.lineSeparator());
                            break;
                        } else {
                            str.append(new Paragraph(currentBlockTag.toString()).toMarkdown());
                            str.append(System.lineSeparator());
                            break;
                        }
                    }
                    //find and replace markdown
                    for (int i = 0; i < str.length() - 1; i++) {
                        char currentChar = str.charAt(i);
                        //escaping symbols - simply ignore next char
                        if (currentChar == '\\') {
                            str.delete(i, i + 1);
                            continue;
                        }
                        //searching for the markdown symbol
                        char nextChar = str.charAt(i + 1);
                        for (char mark : markupSpecials) {
                            if (currentChar == mark && !Character.isWhitespace(nextChar)) {
                                occurrenceFound = i + 1;
                                //may cause errors
                                //if found - find pairing markdown symbol and replace with HTML
                                if (nextChar == mark) {
                                    replaceNextOccurrence("" + mark + mark);
                                } else {
                                    replaceNextOccurrence("" + mark);
                                }
                            }
                        }
                    }
                    //write here
                    out.write(str.toString());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
