package ZZ;

import com.sun.tools.javac.util.List;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Mainn {
    public static void main(String[] args) throws FileNotFoundException {
        Helper helper = new Helper();
        ArrayList<Regle> rules = new ArrayList<>();
//        rules.add(new Regle("S", List.of("S", "A", "B", "C")));
//        rules.add(new Regle("S", List.of("n")));
//        rules.add(new Regle("A", List.of("a", "A")));
//        rules.add(new Regle("A", List.of("eps")));
//        rules.add(new Regle("B", List.of("b", "B")));
//        rules.add(new Regle("B", List.of("c", "B")));
//        rules.add(new Regle("B", List.of("eps")));
//        rules.add(new Regle("C", List.of("d", "C")));
//        rules.add(new Regle("C", List.of("d", "a")));
//        rules.add(new Regle("C", List.of("d", "A")));
        rules = (ArrayList<Regle>) new ReadFile().readFile("/Users/macbook_z/Desktop/Data/Projects/IntelliJ/Langage_et_Compitation_LSI_L3_2022_EFREI_PARIS/grammar3.txt").getProductions();
        Grammar grammer = new Grammar("S", List.of("a", "b", "c", "d"), List.of("S", "A", "B", "C"), rules);
        grammer = helper.derecursification(grammer);


        System.out.println("Regles : " + grammer.getProductions());

        HashMap<String, Set<String>> firsts = helper.calculateFirsts(grammer);
        System.out.println("Firsts : " + firsts);

        HashMap<String, Set<String>> follows = helper.calculateFollows(grammer);
        System.out.println("Follows : " + follows);


    }
}