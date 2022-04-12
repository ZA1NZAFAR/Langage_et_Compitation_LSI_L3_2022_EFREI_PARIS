import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String grammar = "";

        try {
            GrammarReader grammarReader = new GrammarReader("grammar3.txt");
            grammar = grammarReader.getGrammar();
        } catch (IOException e) {
            System.out.println("Failed to read an input grammar file: " + e.getMessage());
        }

        FirstAndFollowSetsCalculation algorithm = new FirstAndFollowSetsCalculation(grammar);
        algorithm.calculateFirstAndFollows();
    }
}
