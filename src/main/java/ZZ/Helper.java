package ZZ;

import java.util.*;

public class Helper {

    private boolean isTerminal(String symbol) {
        return !symbol.matches("[A-Z]|[A-Z]'");
    }

    private List<String> concatenate(List<String> list1, String s) {
        List<String> result = new ArrayList<>(list1);
        result.add(s);
        return result;
    }


    private Set<String> removeEpsilon(Set<String> list) {
        Set<String> result = new HashSet<>(list);
        result.remove("eps");
        return result;
    }

    // Derecurssivation
    public Grammar derecursification(Grammar grammar) {
        Grammar newGrammar = grammar.getCopy();
        List<Regle> result;
        for (Regle regle : grammar.getProductions()) {
            result = new ArrayList<>();
            if (regle.left.equals(regle.right.get(0))) {
                List<Regle> toDiscursiveness = grammar.getAllProductionsOf(regle.left);
                newGrammar.deleteAllProductionsFor(regle.left);
                for (Regle p : toDiscursiveness) {
                    if (p.right.size() == 1) {
                        result.add(new Regle(p.left, concatenate(p.right, regle.left + "'")));
                    } else if (p.right.size() > 1 && p.right.get(0).equals(regle.left)) {
                        result.add(new Regle(p.left + "'", concatenate(p.right.subList(1, p.right.size()), p.left + "'")));
                    }
                }
                result.add(new Regle(regle.left + "'", Collections.singletonList("eps")));
                newGrammar.getNonterminals().add(regle.left + "'");
            }
            newGrammar.getProductions().addAll(result);
        }
        return newGrammar;
    }


    // Calculate Firsts for all nonterminals in grammar
    public HashMap<String, Set<String>> calculateFirsts(Grammar grammar) {
        HashMap<String, Set<String>> firsts = new HashMap<>();
        for (Regle regle : grammar.getProductions()) {
            firsts.put(regle.getLeft(), calculateFirsts(regle.getLeft(), grammar.getProductions(), grammar));
        }
        return firsts;
    }

    public Set<String> calculateFirsts(String left, List<Regle> regles, Grammar grammar) {
        Set<String> firsts = new HashSet<>();
        for (Regle regle : regles) {
            if (regle.getLeft().equals(left)) {
                if (isTerminal(regle.getRight().get(0))) {
                    firsts.add(regle.getRight().get(0));
                } else {
                    for (String s : regle.right) {
                        if (!isTerminal(s))
                            firsts.addAll(calculateFirsts(s, regles, grammar));
                        if (!grammar.doesGiveEpsilon(s))
                            break;
                    }
                }
            }
        }
        return firsts;
    }


    // Calculate Follows for all nonterminals in grammar
    public HashMap<String, Set<String>> calculateFollows(Grammar grammar) {
        HashMap<String, Set<String>> follows = new HashMap<>();
        for (Regle regle : grammar.getProductions()) {
            follows.put(regle.getLeft(), calculateFollows(regle.getLeft(), grammar.getProductions(), grammar));
        }
        return follows;
    }

    public Set<String> calculateFollows(String symbol, List<Regle> regles, Grammar grammar) {
        Set<String> follows = new HashSet<>();
        if (symbol.equals(grammar.getStartSymbol()))
            follows.add("$");
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
                                        follows.addAll(removeEpsilon(calculateFollows(regle.left, regles, grammar)));
                                    }
                                    if(!grammar.doesGiveEpsilon(regle.right.get(j)))
                                        break;
                                }

                                // if surrent symbol can dissapear we also take the firsts of the next symbol
                                follows.addAll(removeEpsilon(calculateFirsts(s, regles, grammar)));
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
}