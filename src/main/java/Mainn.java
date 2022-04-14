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
        Grammar grammer = Tools.readFileToGrammar("grammar.txt");
        grammer = helper.removeRecursion(grammer);

        System.out.println("Grammaire : \n" + grammer + "\n");

        HashMap<String, Set<String>> firsts = helper.calculateFirsts(grammer);
        System.out.println("Firsts : \n" + firsts + "\n");

        HashMap<String, Set<String>> follows = helper.calculateFollows(grammer);
        System.out.println("Follows : \n" + follows + "\n");

        Table descendingAnalyzerTable = helper.getDescendingAnalyzerTable(grammer, firsts, follows);
        System.out.println("Table : \n" + descendingAnalyzerTable + "\n");
    }
}