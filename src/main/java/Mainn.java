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
                    "\nMain Menu: " +
                            "\n0- to exit" +
                            "\n1- to choose a file and transform it into a usable grammar (if it's already usable, nothing will happen (don't worry..))" +
                            "\n2- to calculate firsts of the given grammmar" +
                            "\n3- to calculate follows of the given grammmar" +
                            "\n4- to calculate table of the given grammmar" +
                            "\n5- check a word in the given grammmar" +
                            "\n6- for a HUGE demo" +
                            "\n7- for the rules" +
                            "\n(✌ﾟ∀ﾟ)☞ Anything else to do the whole process ! " +
                            "\n\n~ Answer ~ : ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("You already want to leave ? Well... see you soon ! ☁︎ ⋆⁺₊⋆ ☾ ☼ ⋆⁺₊⋆ ☁︎");
                    System.exit(0);
                    break;
                case 1:
                    System.out.println("Please write a file among the following : grammar.txt | grammar1.txt | grammar2.txt | grammar3.txt. \n ✨ Your choice below ✨ : ");
                    String str = scanner.next();
                    grammar = Tools.readFileToGrammar(str);
                    break;
                case 2:
                    if (grammar == null)
                        System.out.println("༼☯﹏☯༽ Please read a grammar first ! Sooo maybe you can press 1 this time ?...");
                    else {
                        firsts = helper.calculateFirsts(grammar);
                        Tools.displayFirstsOrFollows("Firsts", firsts);
                    }
                    break;
                case 3:
                    if (grammar == null)
                        System.out.println("༼☯﹏☯༽ Please read a grammar first ! Sooo maybe you can press 1 this time ?...");
                    else {
                        follows = helper.calculateFollows(grammar);
                        Tools.displayFirstsOrFollows("Follows", follows);
                    }
                    break;
                case 4:
                    if (grammar == null || firsts == null || follows == null)
                        System.out.println("୧༼ಠ益ಠ༽୨ No, no, no !... Grammar have to be choose (if you haven't already choose it ჴ˘ര‸രჴ), and firsts + follows MUST BE calculated first ! ");
                    else {
                        descendingAnalyzerTable = helper.getDescendingAnalyzerTable(grammar, firsts, follows);
                        Tools.displayTable(descendingAnalyzerTable, grammar);
                    }
                    break;
                case 5:
                    System.out.println("Type the word you want to check : ");
                    String scan = scanner.next();
                    helper.wordIsKnown(descendingAnalyzerTable, scan, grammar);
                    break;
                case 6:
                    System.out.println("\n READY ?! GOOOO ᕕ༼ •̀︿•́༽ᕗ \n ");
                    grammar = Tools.readFileToGrammar("grammar1.txt");
                    Tools.displayGrammar(grammar);

                    firsts = helper.calculateFirsts(grammar);
                    Tools.displayFirstsOrFollows("Firsts", firsts);

                    follows = helper.calculateFollows(grammar);
                    Tools.displayFirstsOrFollows("Follows", follows);

                    descendingAnalyzerTable = helper.getDescendingAnalyzerTable(grammar, firsts, follows);
                    Tools.displayTable(descendingAnalyzerTable, grammar);

                    helper.wordIsKnown(descendingAnalyzerTable, "(((i)))", grammar);
                    break;
                case 7:
                    System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t~~~ The list of rules ~~~ \n\n\n" +
                            "➮ Rule 1: a String (symbol) is said terminal if it's a letter in lower case, and conversely a symbol is said not terminal if it's a letter in upper case\n" +
                            "➮ Rule 2: We can therefore deduce from the first rule that a series of lower case letters is considered like unique not terminal symbol \n" +
                            "➮ Rule 3: All characters to the left of \"->\" will be considered as not terminal symbol\n" +
                            "➮ Rule 4: The start symbol is the first left character of the given grammar\n" +
                            "➮ Rule 5: epsilon (eps) isn't considered as a terminal state, nor as a non-terminal symbol\n" +
                            "➮ Rule 6: A grammar with a left recursion cannot be studied, we must remove the recursion before (a grammar with a right recursion is not considered as we didn't study it in class\n" +
                            "\n" +
                            "◌ The other rules like concerning the calculation of the first and the following symbols are the same as those studied in class\n\n" +
                            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t=== THE END ===\n\n" +
                            "*** Let’s pick up where we left off ? ٩(◕‿◕｡)۶"
                    );
                    break;
                default:
                    System.out.println("Please write a file among the following : grammar.txt | grammar1.txt | grammar2.txt | grammar3.txt. \n ✨ Your choice below ✨ : ");
                    String str2 = scanner.next();
                    grammar = Tools.readFileToGrammar(str2);
                    //grammar = Tools.readFileToGrammar("Type the name of file you want to read:");
                    Tools.displayGrammar(grammar);

                    firsts = helper.calculateFirsts(grammar);
                    Tools.displayFirstsOrFollows("Firsts", firsts);

                    follows = helper.calculateFollows(grammar);
                    Tools.displayFirstsOrFollows("Follows", follows);

                    descendingAnalyzerTable = helper.getDescendingAnalyzerTable(grammar, firsts, follows);
                    Tools.displayTable(descendingAnalyzerTable, grammar);
                    break;
            }
        }
    }
}