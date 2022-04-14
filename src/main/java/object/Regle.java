package object;

import java.util.List;

public class Regle {
    public String left;
    public List<String> right;

    public Regle(String left, List<String> right) {
        this.left = left;
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public List<String> getRight() {
        return right;
    }

    @Override
    public String toString() {
        return left + " --> " + right;
    }

    public String[] getSymboles() {
        String[] symbols = new String[right.size() + 1];
        for (int i = 0; i < right.size(); i++) {
            symbols[i] = right.get(i);
        }
        symbols[right.size()] = left;
        return symbols;
    }
}