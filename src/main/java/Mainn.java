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
        Grammar grammer = Tools.readFileToGrammar("grammar1.txt");
        grammer = helper.removeRecursion(grammer);
        System.out.println("Grammaire : \n" + grammer + "\n");

        HashMap<String, Set<String>> firsts = helper.calculateFirsts(grammer);
        Tools.displayFirstsOrFollows("Firsts", firsts);

        HashMap<String, Set<String>> follows = helper.calculateFollows(grammer);
        Tools.displayFirstsOrFollows("Follows", firsts);

        Table descendingAnalyzerTable = helper.getDescendingAnalyzerTable(grammer, firsts, follows);
        Tools.displayTable(descendingAnalyzerTable, grammer);

        System.out.println("Word is known? : ");
        helper.wordIsKnown(descendingAnalyzerTable, "(((i)))");
    }
}