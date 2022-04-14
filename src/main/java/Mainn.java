import object.Grammar;
import tool.Helper;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Set;

public class Mainn {
    public static void main(String[] args) throws FileNotFoundException {
        Helper helper = new Helper();
        Grammar grammer = helper.readFileToGrammer("grammar1.txt");
        grammer = helper.derecursification(grammer);

        System.out.println("Grammaire : " + grammer);

        HashMap<String, Set<String>> firsts = helper.calculateFirsts(grammer);
        System.out.println("Firsts : " + firsts);

        HashMap<String, Set<String>> follows = helper.calculateFollows(grammer);
        System.out.println("Follows : " + follows);
    }
}