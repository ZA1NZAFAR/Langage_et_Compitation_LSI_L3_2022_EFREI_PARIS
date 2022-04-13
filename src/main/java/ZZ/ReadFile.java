package ZZ;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class ReadFile {
    public Grammar readFile(String fileName) throws FileNotFoundException {
        Grammar grammar = new Grammar();

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        try {
            String line = br.readLine();
            while (line != null) {
                if (line.contains("->")) {
                    String[] firstCut = line.split("->");
                    String[] secondCut = firstCut[1].split("\\|");

                    //TODO: check for epsilon

                    for (String part : secondCut) {
                        grammar.getProductions().add(new Regle(firstCut[0].trim(), Arrays.asList(part.trim().split(""))));
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return grammar;
    }
}
