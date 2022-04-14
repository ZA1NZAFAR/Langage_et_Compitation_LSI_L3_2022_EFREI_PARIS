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

    public String getStartSymbol() {
        return startSymbol;
    }

    public void setStartSymbol(String s) {
        this.startSymbol = s;
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

    /**
     * Return true of the given non terminal gives an epislon production
     *
     * @param nonTerminal the non terminal
     * @return true if the non terminal gives an epislon production
     */
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

    public void setTerminals(List<String> terminals) {
        this.terminals = terminals;
    }

    public List<String> getNonTerminals() {
        return nonTerminals;
    }

    public void setNonTerminals(List<String> nonTerminals) {
        this.nonTerminals = nonTerminals;
    }

    public List<Regle> getRegles() {
        return regles;
    }

    public void setRegles(List<Regle> regles) {
        this.regles = regles;
    }

    public void addTerminal(String $) {
        this.terminals.add($);
    }
}
