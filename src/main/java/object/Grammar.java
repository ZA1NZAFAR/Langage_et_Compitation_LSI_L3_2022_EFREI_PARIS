package object;

import java.util.ArrayList;
import java.util.List;

public class Grammar {
    private String startSymbol;
    private List<String> terminals;
    private List<String> nonTerminals;
    private List<Regle> regles;

    public Grammar(String startSymbol, List<String> terminals, List<String> nonTerminals, List<Regle> regles) {
        this.startSymbol = startSymbol;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.regles = regles;
    }

    public Grammar() {
         terminals = new ArrayList<>();
         nonTerminals = new ArrayList<>();
        regles = new ArrayList<>();
    }

    public List<String> getTerminals() {
        return terminals;
    }

    public List<String> getNonterminals() {
        return nonTerminals;
    }

    public List<Regle> getProductions() {
        return regles;
    }

    public List<Regle> getAllProductionsOf(String nonTerminal) {
        List<Regle> regles = new java.util.ArrayList<>();
        for (Regle regle : this.regles) {
            if (regle.getLeft().equals(nonTerminal)) {
                regles.add(regle);
            }
        }
        return regles;
    }

    public void deleteAllProductionsFor(String nonTerminal) {
        this.regles.removeIf(regle -> regle.getLeft().equals(nonTerminal));
    }

    public Grammar getCopy() {
        return new Grammar(startSymbol, new ArrayList<>(this.terminals), new ArrayList<>(this.nonTerminals), new ArrayList<>(this.regles));
    }

    public boolean doesGiveEpsilon(String nonTerminal) {
        for (Regle regle : this.regles)
            if (regle.getLeft().equals(nonTerminal) && regle.getRight().get(0).equals("eps"))
                return true;
        return false;
    }

    @Override
    public String toString() {
        return "\nGrammer{" +
                "Start Symbole=" + startSymbol +
                "terminals=" + terminals +
                ", nonTerminals=" + nonTerminals +
                ", productions=" + regles +
                "\n}";
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public void setStartSymbol(String s) {
        this.startSymbol = s;
    }
}
