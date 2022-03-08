import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class hw2_beyza_aydin {
    static String arr = "i" + "+" + "*" + "(" + ")" + "$" + "E" + "T" + "F";
    static String[][] lrParseTable =
            {
                    {"S5", "", "", "S4", "", "", "1", "2", "3"},
                    {"", "S6", "", "", "", "accept", "", "", ""},
                    {"", "R2", "S7", "", "R2", "R2", "", "", ""},
                    {"", "R4", "R4", "", "R4", "R4", "", "", ""},
                    {"S5", "", "", "S4", "", "", "8", "2", "3"},
                    {"", "R6", "R6", "", "R6", "R6", "", "", ""},
                    {"S5", "", "", "S4", "", "", "", "9", "3"},
                    {"S5", "", "", "S4", "", "", "", "", "10"},
                    {"", "S6", "", "", "S11", "", "", "", ""},
                    {"", "R1", "S7", "", "R1", "R1", "", "", ""},
                    {"", "R3", "R3", "", "R3", "R3", "", "", ""},
                    {"", "R5", "R5", "", "R5", "R5", "", "", ""}
            };

    public hw2_beyza_aydin() {
    }

    public static void main(String[] args) throws Exception {
        String input = "";
        String outputFile = "";
        if (args.length == 2) {
            input = args[0];
            outputFile = args[1];
        }
        writeFile(outputFile, splitInput(input.toCharArray()));
    }

    protected static void writeFile(String fileName, String result) {
        try {
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(
                    fileName));
            f_writer.write(result);
            f_writer.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    protected static String createText(ArrayList<String> stack, ArrayList<String> input, ArrayList<String> action) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < stack.size(); i++) {
            builder.append(stack.get(i)).append("\t\t\t\t\t\t\t\t").append(input.get(i))
                    .append("\t\t\t\t\t\t\t\t").append(action.get(i)).append("\n");
        }
        return builder.toString();
    }

    public static String splitInput(char[] input) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            if (Character.isLetter(input[i])) {
                if (Character.isLowerCase(input[i]) && input[i] == 'i' && input[i + 1] == 'd') {
                    list.add(input[i] + String.valueOf(input[i + 1]));
                    i++;
                } else if (Character.isUpperCase(input[i]) && (input[i] == 'E' || input[i] == 'T' || input[i] == 'F')) {
                    list.add(String.valueOf(input[i]));
                } else
                    throw new Exception("Unknown character -> " + input[i]);
            } else if (input[i] == '+' || input[i] == '*' || input[i] == '('
                    || input[i] == ')' || input[i] == '$') {
                list.add(String.valueOf(input[i]));
            } else
                throw new Exception("Unknown character -> " + input[i]);
        }
        return parse(list);
    }

    public static String parse(ArrayList<String> list) {
        ArrayList<String> stack = new ArrayList<>();
        stack.add("STACK");
        stack.add("0");
        ArrayList<String> input = new ArrayList<>();
        input.add("INPUT");
        String listString = String.join("", list);
        input.add(listString);
        ArrayList<String> action = new ArrayList<>();
        action.add("ACTION");
        String act = lrParseTable[0][arr.indexOf(list.get(0).charAt(0))];
        action.add(act);

        do {
            if (act.equals("")) {
                stack.add("ERROR");
                System.out.println("Error occurred.");
                break;
            }
            if (act.contains("R")) {
                int rule = Integer.parseInt(act.substring(1));
                if (rule == 1) {
                    Pattern pattern = Pattern.compile("(E)(\\d+)(\\+)(\\d+)(T)(\\d+)");
                    Matcher m = pattern.matcher(stack.get(stack.size() - 1));
                    m.find();
                    int start = m.start();
                    int end = m.end();
                    String p = stack.get(stack.size() - 1);
                    String s = p.substring(0, start) + "E" + p.substring(end);
                    String last = lrParseTable[Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))][arr.indexOf(String.valueOf(s.charAt(s.length() - 1)))];
                    if (last.equals("")) {
                        stack.add("ERROR");
                        System.out.println("Error occurred.");
                        break;
                    }

                    s += last;
                    stack.add(s);
                    input.add(input.get(input.size() - 1));
                    act = lrParseTable[Integer.parseInt(last)][arr.indexOf(list.get(0).charAt(0))];

                    if (act.equals("")) {
                        action.add("ERROR");
                        System.out.println("Error occurred.");
                        break;
                    }
                    action.add(act);
                }
                if (rule == 2) {
                    String p = stack.get(stack.size() - 1);
                    Pattern pattern = Pattern.compile("(T)(\\d+)");
                    Matcher m = pattern.matcher(stack.get(stack.size() - 1));
                    m.find();
                    int start = m.start();
                    int end = m.end();
                    String s = p.substring(0, start) + "E" + p.substring(end);
                    String last = lrParseTable[Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))][arr.indexOf(String.valueOf(s.charAt(s.length() - 1)))];
                    if (last.equals("")) {
                        action.add("ERROR");
                        System.out.println("Error occurred.");
                        break;
                    }

                    s += last;
                    stack.add(s);
                    input.add(input.get(input.size() - 1));
                    act = lrParseTable[Integer.parseInt(last)][arr.indexOf(list.get(0).charAt(0))];

                    if (act.equals("")) {
                        action.add("ERROR");
                        System.out.println("Error occurred.");
                        break;
                    }
                    action.add(act);

                }
                if (rule == 3) {
                    Pattern pattern = Pattern.compile("(T)(\\d+)(\\*)(\\d+)(F)(\\d+)");
                    Matcher m = pattern.matcher(stack.get(stack.size() - 1));
                    m.find();
                    int start = m.start();
                    int end = m.end();
                    String p = stack.get(stack.size() - 1);
                    String s = p.substring(0, start) + "T" + p.substring(end);
                    String last = lrParseTable[Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))][arr.indexOf(String.valueOf(s.charAt(s.length() - 1)))];
                    if (last.equals("")) {
                        action.add("ERROR");
                        System.out.println("Error occurred.");
                        break;
                    }

                    s += last;
                    stack.add(s);
                    input.add(input.get(input.size() - 1));
                    act = lrParseTable[Integer.parseInt(last)][arr.indexOf(list.get(0).charAt(0))];

                    if (act.equals("")) {
                        action.add("ERROR");
                        System.out.println("Error occurred.");
                        break;
                    }
                    action.add(act);

                }
                if (rule == 4) {
                    String p = stack.get(stack.size() - 1);
                    Pattern pattern = Pattern.compile("(F)(\\d+)");
                    Matcher m = pattern.matcher(stack.get(stack.size() - 1));
                    m.find();
                    int start = m.start();
                    int end = m.end();
                    String s = p.substring(0, start) + "T" + p.substring(end);
                    String last = lrParseTable[Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))][arr.indexOf(String.valueOf(s.charAt(s.length() - 1)))];
                    if (last.equals("")) {
                        action.add("ERROR");
                        System.out.println("Error occurred.");
                        break;
                    }

                    s += last;
                    stack.add(s);
                    input.add(input.get(input.size() - 1));
                    act = lrParseTable[Integer.parseInt(last)][arr.indexOf(list.get(0).charAt(0))];

                    if (act.equals("")) {
                        action.add("ERROR");
                        System.out.println("Error occurred.");
                        break;
                    }
                    action.add(act);

                }
                if (rule == 5) {
                    String p = stack.get(stack.size() - 1);
                    Pattern pattern = Pattern.compile("(\\()(\\d+)(E)(\\d+)(\\))(\\d+)");
                    Matcher m = pattern.matcher(stack.get(stack.size() - 1));
                    m.find();
                    int start = m.start();
                    int end = m.end();
                    String s = p.substring(0, start) + "F" + p.substring(end);
                    String last = lrParseTable[Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))][arr.indexOf(String.valueOf(s.charAt(s.length() - 1)))];
                    if (last.equals("")) {
                        action.add("ERROR");
                        System.out.println("Error occurred.");
                        break;
                    }

                    s += last;
                    stack.add(s);
                    input.add(input.get(input.size() - 1));
                    act = lrParseTable[Integer.parseInt(last)][arr.indexOf(list.get(0).charAt(0))];

                    if (act.equals("")) {
                        action.add("ERROR");
                        System.out.println("Error occurred.");
                        break;
                    }
                    action.add(act);

                }
                if (rule == 6) {
                    String p = stack.get(stack.size() - 1);
                    Pattern pattern = Pattern.compile("(id)(\\d+)");
                    Matcher m = pattern.matcher(stack.get(stack.size() - 1));
                    m.find();
                    int start = m.start();
                    int end = m.end();
                    String s = p.substring(0, start) + "F" + p.substring(end);
                    String last = lrParseTable[Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))][arr.indexOf(String.valueOf(s.charAt(s.length() - 1)))];
                    if (last.equals("")) {
                        action.add("ERROR");
                        System.out.println("Error occurred.");
                        break;
                    }

                    s += last;
                    stack.add(s);
                    input.add(input.get(input.size() - 1));
                    act = lrParseTable[Integer.parseInt(last)][arr.indexOf(list.get(0).charAt(0))];

                    if (act.equals("")) {
                        action.add("ERROR");
                        System.out.println("Error occurred.");
                        break;
                    }
                    action.add(act);

                }
            } else if (act.contains("S")) {
                int index = Integer.parseInt(act.substring(1));
                stack.add(stack.get(stack.size() - 1) + list.get(0) + index);
                list.remove(0);
                input.add(String.join("", list));
                act = lrParseTable[index][arr.indexOf(list.get(0).charAt(0))];
                if (act.equals("")) {
                    action.add("ERROR");
                    System.out.println("Error occurred.");
                    break;
                }
                action.add(act);
            }
            if (act.equals("accept"))
                System.out.println("The input has been parsed successfully.");

        } while (!act.equals("accept"));
        return createText(stack, input, action);
    }
}