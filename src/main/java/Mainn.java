import object.Grammar;
import object.Table;
import tool.Helper;
import tool.Tools;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Mainn {
    public static void main(String[] args) throws FileNotFoundException {
        Helper helper = new Helper();
        Scanner scanner = new Scanner(System.in);
        Grammar grammar = null;
        HashMap<String, Set<String>> firsts = null;
        HashMap<String, Set<String>> follows = null;
        Table descendingAnalyzerTable = null;

        while (true) {
            System.out.println(
                    "Main Menu: " +
                            "\n0 to exit" +
                            "\n1 to read a new file" +
                            "\n2 to calculate firsts" +
                            "\n3 to calculate follows" +
                            "\n4 to calculate table" +
                            "\n5 check a word" +
                            "\n6 for a demo" +
                            "\nAnything else to do the whole process!" +
                            "\nAnswer: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    grammar = Tools.readFileToGrammar(scanner.nextLine());
                    break;
                case 2:
                    if (grammar == null)
                        System.out.println("Please read a grammar first!");
                    else
                        helper.calculateFirsts(grammar);
                    break;
                case 3:
                    if (grammar == null)
                        System.out.println("Please read a grammar first!");
                    else
                        helper.calculateFollows(grammar);
                    break;
                case 4:
                    if (grammar == null || firsts == null || follows == null)
                        System.out.println("Grammar, firsts and follows must be calculated first!");
                    else
                        helper.getDescendingAnalyzerTable(grammar, firsts, follows);
                    break;
                case 5:
                    System.out.println("Type the word you want to check: ");
                    helper.wordIsKnown(descendingAnalyzerTable, scanner.nextLine());
                    break;
                case 6:

                    grammar = Tools.readFileToGrammar("grammar1.txt");
                    Tools.displayGrammar(grammar);

                    firsts = helper.calculateFirsts(grammar);
                    Tools.displayFirstsOrFollows("Firsts", firsts);

                    follows = helper.calculateFollows(grammar);
                    Tools.displayFirstsOrFollows("Follows", follows);

                    descendingAnalyzerTable = helper.getDescendingAnalyzerTable(grammar, firsts, follows);
                    Tools.displayTable(descendingAnalyzerTable, grammar);

                    helper.wordIsKnown(descendingAnalyzerTable, "(((i)))");
                    break;
                default:
                    grammar = Tools.readFileToGrammar("Type the name of file you want to read:");
                    Tools.displayGrammar(grammar);

                    firsts = helper.calculateFirsts(grammar);
                    Tools.displayFirstsOrFollows("Firsts", firsts);

                    follows = helper.calculateFollows(grammar);
                    Tools.displayFirstsOrFollows("Follows", follows);

                    descendingAnalyzerTable = helper.getDescendingAnalyzerTable(grammar, firsts, follows);
                    Tools.displayTable(descendingAnalyzerTable, grammar);

                    helper.wordIsKnown(descendingAnalyzerTable, "(((i)))");
                    break;
            }
        }
    }
}