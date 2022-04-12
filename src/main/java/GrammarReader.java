import javax.sound.midi.SysexMessage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GrammarReader {
    private BufferedReader br;                              // Input file reader
    private StringBuilder sb;                               // Grammar builder used upon reading an input file
    private String grammar;                                 // Grammar is stored in this variable as a one line String
    private Map<Character, List<Integer>> dictionary;       // Map used to store the indexes of replacement character (S'), used to replace duplicates later on
    private Map<String, Character> replacements;         // Map used to track replacement characters

    public GrammarReader(String filename) throws IOException {
        br = new BufferedReader(new FileReader(filename));
        dictionary = new LinkedHashMap<>();
        replacements = new LinkedHashMap<>();

        try {
            sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                if (line.charAt(line.indexOf('-') + 1) == '>') {
                    // Get rid of whitespaces if the line contains one or more, then
                    // if the line contains an arrow character '->', replace it with '=', so the algorithm would work correctly
                    line = line.replaceAll("\\s+", "").replaceFirst("->", "=");

                    // Handling recursion. Replaces non-terminal character with another char value (e.g. : A -> B)
                    if (line.charAt(line.indexOf('=') + 1) == line.charAt(0)) {
                        char character = line.charAt(0);
                        character += 1;

                        int index = line.indexOf('|');
                        String production;

                        // DeRecursion. Handling every 'beta' after the pipe '|' character
                        while (index >= 0) {
                            String beta = line.substring(index + 1, ((line.indexOf('|', index + 1) == -1) ? line.length() : (line.indexOf('|', index + 1))));
                            for (int i = 0; i < beta.length(); i++) {
                                if (Character.isUpperCase(beta.charAt(i)))
                                    throw new IllegalArgumentException("Le beta contient le non-terminal!");
                            }

                            production = line.charAt(0) + "=" + beta + character;

                            // Storing the indexes of the replacement character (S -> S')
                            if (dictionary.get(character) != null) {
                                dictionary.get(character).add(sb.length() + production.length() - 1);
                            } else {
                                dictionary.put(character, new ArrayList<>());
                                dictionary.get(character).add(sb.length() + production.length() - 1);
                            }

                            index = line.indexOf('|', index + 1);
                            sb.append(production);
                            sb.append(System.lineSeparator());
                        }

                        if (dictionary.get(character) == null)
                            dictionary.put(character, new ArrayList<>());

                        dictionary.get(character).add(sb.length());
                        production = character + "=" + line.substring((line.indexOf(line.charAt(0), 1) + 1), line.indexOf("|")) + character;
                        dictionary.get(character).add(sb.length() + production.length() - 1);
                        sb.append(production);
                        sb.append(System.lineSeparator());
                        dictionary.get(character).add(sb.length());
                        production = character + "=#";
                        sb.append(production);

                        replacements.put(line.charAt(0) + "\'", character);

                    } else if (line.indexOf('|') != -1) {
                        // Form two lines from one if the line contains '|' symbol. E.g.: R=+TR|# would result in: 1) R=+TR and 2) R=#
                        sb.append(line.substring(0, line.indexOf('|')));
                        sb.append(System.lineSeparator());

                        String production = line.substring(line.indexOf('|') + 1, line.length());
                        sb.append(Character.toString(line.charAt(0)) + Character.toString(line.charAt(1)) + line.substring(line.indexOf('|') + 1, (production.indexOf('|') == -1) ? line.length() : line.indexOf('|', line.indexOf('|') + 1)));

                        while (production.indexOf('|') != -1) {
                            sb.append(System.lineSeparator());
                            production = production.substring(production.indexOf('|') + 1, production.length());
                            sb.append(Character.toString(line.charAt(0)) + Character.toString(line.charAt(1)) + production.substring(0, production.length()));
                        }
                    } else {
                        sb.append(line);
                    }
                }

                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            this.grammar = sb.toString();

            // Checking the grammar if it contains duplicate non-terminals, replace if it's a case
            for (Map.Entry<Character, List<Integer>> entry : dictionary.entrySet()) {
                int index = this.grammar.indexOf(entry.getKey());

                // For all occurrences of the character
                while (index > 0) {

                    if (!dictionary.get(entry.getKey()).contains(index)) {
                        char character = entry.getKey();

                        while (this.grammar.indexOf(character) > 0)
                            character++;

                        for (int i : entry.getValue()) {
                            this.grammar = this.grammar.substring(0, i) + (this.grammar.indexOf(character) > 0 && !entry.getValue().contains(this.grammar.indexOf(character)) ? "*" : character) + this.grammar.substring(i + 1, this.grammar.length());
                        }
                    }

                    index = this.grammar.indexOf(entry.getKey(), index + 1);
                }
            }
        } finally {
            br.close();
        }
    }

    public String getGrammar() {
        System.out.println("On reconnait la formule de recursivite gauche :\nA -> Aα | β\nOn applique donc la formule de derecursivation :\nA -> βA'\nA'-> αA' | ε");
        System.out.println(replacements.entrySet() + " sont des non terminaux remplaces lors de derecursivation, # - represente l'epsilon");
        return grammar;
    }
}
