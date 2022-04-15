package tool;

import object.Grammar;
import object.Regle;
import object.Table;
import object.TableCell;

import java.util.*;

import static tool.Tools.isTerminal;

public class Helper {

    /**
     * Checks if a rule is a left recursion rule and removes it from the grammar
     *
     * @param grammar the grammar to check
     * @return the grammar without left recursion rules
     */
    public Grammar removeRecursion(Grammar grammar) {
        Grammar newGrammar = grammar.getCopy();
        List<Regle> result;
        for (Regle regle : grammar.getProductions()) {
            result = new ArrayList<>();
            if (regle.left.equals(regle.right.get(0))) {
                List<Regle> toDiscursiveness = grammar.getAllProductionsOf(regle.left);
                newGrammar.deleteAllProductionsFor(regle.left);
                for (Regle p : toDiscursiveness) {
                    if (!p.right.get(0).equals(regle.left)) {
                        result.add(new Regle(p.left, Tools.concatenate(p.right, regle.left + "'")));
                    } else {
                        result.add(new Regle(p.left + "'", Tools.concatenate(p.right.subList(1, p.right.size()), p.left + "'")));
                    }
                }
                result.add(new Regle(regle.left + "'", Collections.singletonList("eps")));
                newGrammar.getNonterminals().add(regle.left + "'");
            }
            newGrammar.getProductions().addAll(result);
        }
        return newGrammar;
    }

    /**
     * Calculates the follows for all nonTerminals in a grammar and returns them in a HashMap with the key being the nonTerminal
     *
     * @param grammar the grammar to calculate the follows for
     * @return a HashMap with the key being the nonTerminal and the value being the follows
     */
    public HashMap<String, Set<String>> calculateFirsts(Grammar grammar) {
        HashMap<String, Set<String>> firsts = new HashMap<>();
        for (Regle regle : grammar.getProductions()) {
            firsts.put(regle.getLeft(), calculateFirsts(regle.getLeft(), grammar.getProductions(), grammar));
        }
        return firsts;
    }

    /**
     * Calculates the firsts for a nonTerminal in a grammar and returns them in a HashSet
     *
     * @param left    the nonTerminal to calculate the firsts for
     * @param regles  the grammar to calculate the firsts for
     * @param grammar the grammar to calculate the firsts for
     * @return a HashSet with the firsts of the nonTerminal
     */
    public Set<String> calculateFirsts(String left, List<Regle> regles, Grammar grammar) {
        Set<String> firsts = new HashSet<>();
        for (Regle regle : regles) {
            if (regle.getLeft().equals(left)) {
                if (isTerminal(regle.getRight().get(0))) {
                    firsts.add(regle.getRight().get(0));
                } else {
                    for (String s : regle.right) {
                        if (!isTerminal(s) && !s.equals(left))
                            firsts.addAll(calculateFirsts(s, regles, grammar));
                        if (!grammar.doesGiveEpsilon(s))
                            break;
                    }
                }
            }
        }
        return firsts;
    }


    /**
     * Calculates the follows for all nonTerminals in a grammar and returns them in a HashMap with the key being the nonTerminal
     *
     * @param grammar the grammar to calculate the follows for
     * @return a HashMap with the key being the nonTerminal and the value being the follows
     */
    public HashMap<String, Set<String>> calculateFollows(Grammar grammar) {
        HashMap<String, Set<String>> follows = new HashMap<>();
        for (Regle regle : grammar.getProductions()) {
            follows.put(regle.getLeft(), calculateFollows(regle.getLeft(), grammar.getProductions(), grammar));
        }
        return follows;
    }

    /**
     * Calculates the follows for a nonTerminal in a grammar and returns them in a HashSet
     *
     * @param symbol  the nonTerminal to calculate the follows for
     * @param regles  the grammar to calculate the follows for
     * @param grammar the grammar to calculate the follows for
     * @return a HashSet with the follows of the nonTerminal
     */
    public Set<String> calculateFollows(String symbol, List<Regle> regles, Grammar grammar) {
        Set<String> follows = new HashSet<>();
        if (symbol.equals(grammar.getStartSymbol())) {
            follows.add("$");
            grammar.addTerminal("$");
        }
        for (Regle regle : regles) {
            for (int i = 0; i < regle.right.size(); i++) {
                if (regle.right.get(i).equals(symbol)) {
                    if (i == regle.right.size() - 1) {

                        if (regle.right.get(i).equals(regle.getLeft()))
                            continue;
                        follows.addAll(calculateFollows(regle.getLeft(), regles, grammar));
                    } else {
                        if (isTerminal(regle.right.get(i + 1))) {
                            follows.add(regle.right.get(i + 1));
                        } else {


                            for (String s : regle.right.subList(i + 1, regle.right.size())) {

                                //if all symbols following the current symbol can dissapear then add follows of left side
                                for (int j = i + 1; j < regle.right.size(); j++) {
                                    if (j == regle.right.size() - 1) {
                                        follows.addAll(Tools.removeEpsilon(calculateFollows(regle.left, regles, grammar)));
                                    }
                                    if (!grammar.doesGiveEpsilon(regle.right.get(j)))
                                        break;
                                }

                                // if current symbol can dissapear we also take the firsts of the next symbol
                                follows.addAll(Tools.removeEpsilon(calculateFirsts(s, regles, grammar)));
                                if (!grammar.doesGiveEpsilon(s))
                                    break;
                            }
                        }
                    }
                }
            }
        }
        return follows;
    }


    public Table getDescendingAnalyzerTable(Grammar grammar, HashMap<String, Set<String>> firsts, HashMap<String, Set<String>> follows) {
        Table table = new Table(new ArrayList<>());
        for (Regle regle : grammar.getProductions()) {
            if (regle.right.get(0).equals("eps")) {
                for (String s : follows.get(regle.getLeft())) {
                    TableCell tmp = new TableCell(regle.getLeft(), s, regle);
                    if (table.containsCouple(regle.getLeft(), s))
                        throw new RuntimeException("There can't be two rules with the same left side and first");
                    else
                        table.add(tmp);
                }
                continue;
            }

            //S > Tps
            if (isTerminal(regle.getRight().get(0))) {
                TableCell tmp = new TableCell(regle.getLeft(), regle.getRight().get(0), regle);
                if (table.containsCouple(regle.getLeft(), regle.getRight().get(0)))
                    throw new RuntimeException("There can't be two rules with the same left side and first");
                else
                    table.add(tmp);
                continue;
            }
            for (String s : firsts.get(regle.getRight().get(0))) {
                TableCell tmp = new TableCell(regle.getLeft(), s, regle);
                if (table.containsCouple(regle.getLeft(), s))
                    throw new RuntimeException("There can't be two rules with the same left side and first");
                else
                    table.add(tmp);
            }
        }
        return table;
    }
}