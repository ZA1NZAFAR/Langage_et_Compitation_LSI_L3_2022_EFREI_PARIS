package tool;

import object.Grammar;
import object.Regle;
import object.Table;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Tools {

    /**
     * Returns if a given string is a Terminal or not (a Terminal is anything other than an upper case letter or an upper case letter + apostrophe)
     *
     * @param symbol the string to test
     * @return true if the string is a Terminal, false otherwise
     */
    public static boolean isTerminal(String symbol) {
        return !symbol.matches("[A-Z]|[A-Z]'");
    }

    /**
     * Concatenates a string to a list of strings and returns the list
     *
     * @param list1 the list to concatenate to
     * @param s     the string to concatenate
     * @return the list with the string concatenated
     */
    public static List<String> concatenate(List<String> list1, String s) {
        List<String> result = new ArrayList<>(list1);
        result.add(s);
        return result;
    }


    /**
     * Removes the epsilon from the productions of a grammar
     *
     * @param list the list of productions to remove the epsilon productions from
     * @return the list of productions without the epsilon productions
     */
    public static Set<String> removeEpsilon(Set<String> list) {
        Set<String> result = new HashSet<>(list);
        result.remove("eps");
        return result;
    }

    /**
     * Reads a grammar from a file and returns it as a Grammar object with the start symbol and the productions
     *
     * @param fileName the name of the file to read the grammar from
     * @return a Grammar object with the start symbol and the productions
     * @throws FileNotFoundException if the file is not found in the directory of the program
     */
    public static Grammar readFileToGrammar(String fileName) throws FileNotFoundException {
        Grammar grammar = new Grammar();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String line = br.readLine();
            grammar.setStartSymbol(String.valueOf(line.charAt(0)));
            while (line != null) {
                if (line.contains("->")) {
                    String[] firstCut = line.split("->");
                    String[] secondCut = firstCut[1].split("\\|");
                    for (String part : secondCut) {
                        if (part.contains("eps"))
                            grammar.getProductions().add(new Regle(firstCut[0].trim(), com.sun.tools.javac.util.List.of("eps")));
                        else
                            grammar.getProductions().add(new Regle(firstCut[0].trim(), Arrays.asList(part.trim().split(""))));
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        grammar.setTerminals(Tools.getTerminals(grammar));
        grammar.setNonTerminals(Tools.getNonTerminals(grammar));
        grammar = new Helper().removeRecursion(grammar);
        return grammar;
    }

    /**
     * Returns the list of terminals of a grammar
     *
     * @param grammar the grammar to get the terminals from
     * @return the list of terminals of the grammar
     */
    public static List<String> getTerminals(Grammar grammar) {
        Set<String> result = new HashSet<>();
        for (Regle regle : grammar.getProductions()) {
            for (String symbol : regle.getSymboles()) {
                if (isTerminal(symbol)) {
                    result.add(symbol);
                }
            }
        }
        return new ArrayList<>(result);
    }

    /**
     * Returns the list of non-terminals of a grammar
     *
     * @param grammar the grammar to get the non-terminals from
     * @return the list of non-terminals of the grammar
     */
    public static List<String> getNonTerminals(Grammar grammar) {
        Set<String> result = new HashSet<>();
        for (Regle regle : grammar.getProductions()) {
            for (String symbol : regle.getSymboles()) {
                if (!isTerminal(symbol)) {
                    result.add(symbol);
                }
            }
        }
        return new ArrayList<>(result);
    }

    /**
     * Returns a stack of the given word
     *
     * @param word the word to get the stack from
     * @return a stack of the given word
     */
    public static Stack<String> wordToStack(String word) {
        Stack<String> tmp = new Stack<>();
        tmp.push("$");
        for (int i = word.length() - 1; i >= 0; i--) {
            if (word.charAt(i) != ' ')
                tmp.push(word.charAt(i) + "");
        }
        return tmp;
    }

    /**
     * Display a message followed by a little loading animation
     *
     * @param message the message to display
     */
    public static void displayLoading(String message) {
        System.out.print(message + "...");
        for (int i = 0; i < 20; i++) {
            System.out.print(".");
            try {
                Thread.sleep((int) Math.floor(Math.random() * (100 - 50 + 1) + 50));
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("Success!");
    }

    /**
     * Align given strings (first string 80 characters long, second string 60 characters long, third string 30 characters long)
     *
     * @param stack  the stack to align
     * @param word   the word to align
     * @param result the result to align
     */
    public static void alignAndDisplay(String stack, String word, String result) {
        String column1Format = "%-80.80s";  // fixed size 80 characters, left aligned
        String column2Format = "%-50.50s";  // fixed size 50 characters, left aligned
        String column3Format = "%30.30s";   // fixed size 30 characters, right aligned
        String formatInfo = column1Format + " " + column2Format + " " + column3Format;

        System.out.format(formatInfo, stack, word, result);
        System.out.println();
    }

    /**
     * Displays the given analysis table
     *
     * @param table   the table to display
     * @param grammar the grammar used to generate the table
     */
    public static void displayTable(Table table, Grammar grammar) {
        if (table == null) {
            System.out.println("Unable to display the table(the table is null/empty)");
            return;
        }
        displayLoading("Generating table");
        String format = "%-20.20s";  // fixed size 10 characters, left aligned

        System.out.format(format, " ");
        for (String nonTerminal : grammar.getTerminals()) {
            System.out.format(format, nonTerminal);
        }
        System.out.println();
        for (String terminal : grammar.getNonTerminals()) {
            System.out.format(format, terminal);
            for (String nonTerminal : grammar.getTerminals()) {
                System.out.format(format, table.getRegleForCouple(terminal, nonTerminal) == null ? "" : table.getRegleForCouple(terminal, nonTerminal).regle.right);
                System.out.print("");

            }
            System.out.println();
        }
    }

    /**
     * Displays first or follow sets for the given grammar
     *
     * @param firstsOrFollows the first or follow sets to display
     * @param firsts          firsts or follow
     */
    public static void displayFirstsOrFollows(String firstsOrFollows, HashMap<String, Set<String>> firsts) {
        displayLoading("Calculating " + firstsOrFollows);
        System.out.println(firstsOrFollows + " : ");
        for (String key : firsts.keySet()) {
            System.out.println(key + " : " + firsts.get(key));
        }
    }

    /**
     * Displays the given grammar
     *
     * @param grammar the grammar to display
     */
    public static void displayGrammar(Grammar grammar) {
        displayLoading("Generating grammar");
        System.out.println("Terminals : " + grammar.getNonTerminals());
        System.out.println("Non Terminals : " + grammar.getTerminals());
        System.out.println("Productions : ");
        for (Regle regle : grammar.getRegles()) {
            System.out.println(regle.left + " -> " + regle.right);
        }
    }

    /**
     * Checks if a nonTerminal has epsilon as first
     *
     * @param s       the nonTerminal to check
     * @param grammar the grammar to check in
     * @return true if the nonTerminal has epsilon as first
     */
    public static boolean hasEpsilonAsFirst(String s, Grammar grammar) {
        for (Regle regle : grammar.getAllProductionsOf(s)) {
            if (regle.getLeft().equals(s) && regle.getRight().get(0).equals("eps")) {
                return true;
            } else {
                for (int i = 0; i < regle.getRight().size(); i++) {
                    String right = regle.getRight().get(i);
                    if (i == regle.right.size() - 1 && grammar.doesGiveEpsilon(right) && !isTerminal(right)) {
                        return true;
                    }
                    if (isTerminal(right) || (!grammar.doesGiveEpsilon(right) && !hasEpsilonAsFirst(right, grammar))) {
                        break;
                    }
                }
            }
        }
        return false;
    }
}
