package tool;

import object.Grammar;
import object.Regle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Tools {

    /**
     * Returns if a given string is a Terminal or not (a Terminal is a string that contains only one capital character)
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
     * Removes the epsilon from the productions from a grammar
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

}
