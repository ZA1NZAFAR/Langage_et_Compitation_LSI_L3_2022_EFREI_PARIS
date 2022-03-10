import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GrammarReader {
    private BufferedReader br;
    private StringBuilder sb;
    private String grammar;

    public GrammarReader(String filename) throws IOException {
        br = new BufferedReader(new FileReader(filename));

        try {
            sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                if (line.charAt(line.indexOf('-') + 1) == '>') {
                    // Get rid of whitespaces if the line contains one or more, then
                    // if the line contains an arrow character '->', replace it with '=', so the algorithm would work correctly
                    line = line.replaceAll("\\s+", "").replaceFirst("->", "=");

                    // Form two lines from one if the line contains '|' symbol. E.g.: R=+TR|# would result in: 1) R=+TR and 2) R=#
                    if (line.indexOf('|') != -1) {
                        sb.append(line.substring(0, line.indexOf('|')));
                        sb.append(System.lineSeparator());
                        sb.append(Character.toString(line.charAt(0)) + Character.toString(line.charAt(1)) + line.substring(line.indexOf('|') + 1, line.length()));
                    } else {
                        sb.append(line);
                    }
                }

                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            this.grammar = sb.toString();
        } finally {
            br.close();
        }
    }

    public String getGrammar() {
        return grammar;
    }
}
