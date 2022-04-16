import object.Grammar;
import object.Table;
import tool.Helper;
import tool.Tools;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Set;

public class Mainn {
    public static void main(String[] args) throws FileNotFoundException {
        Helper helper = new Helper();
        Grammar grammar = Tools.readFileToGrammar("grammar1.txt");
        Tools.displayGrammar(grammar);

        HashMap<String, Set<String>> firsts = helper.calculateFirsts(grammar);
        Tools.displayFirstsOrFollows("Firsts", firsts);

        HashMap<String, Set<String>> follows = helper.calculateFollows(grammar);
        Tools.displayFirstsOrFollows("Follows", follows);

        Table descendingAnalyzerTable = helper.getDescendingAnalyzerTable(grammar, firsts, follows);
        Tools.displayTable(descendingAnalyzerTable, grammar);

        helper.wordIsKnown(descendingAnalyzerTable, "(((i)))");
    }
}