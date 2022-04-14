package object;

public class TableCell {
    public String terminal;
    public String nonTerminal;
    public Regle regle;

    public TableCell(String terminal, String nonTerminal, Regle regle) {
        this.terminal = terminal;
        this.nonTerminal = nonTerminal;
        this.regle = regle;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public void setNonTerminal(String nonTerminal) {
        this.nonTerminal = nonTerminal;
    }

    public void setRegle(Regle regle) {
        this.regle = regle;
    }

    @Override
    public String toString() {
        return "\nTableCell{" +
                "terminal='" + terminal + '\'' +
                ", nonTerminal='" + nonTerminal + '\'' +
                ", regle=" + regle +
                '}';
    }
}
