import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String grammar = "";

        while (true) {
            String input;

            System.out.println("Donnez le nom du fichier, qui contient le grammaire (ou ecrivez 'quitter' pour arreter le programme):");
            input = scanner.nextLine();

            if (input.equals("quitter"))
                break;

            try {
                GrammarReader grammarReader = new GrammarReader(input);
                grammar = grammarReader.getGrammar();
            } catch (IOException e) {
                System.out.println("Failed to read an input grammar file: " + e.getMessage());
            }

            FirstAndFollowSetsCalculation algorithm = new FirstAndFollowSetsCalculation(grammar);
            algorithm.calculateFirstAndFollows();
            System.out.println();
        }
    }
}
