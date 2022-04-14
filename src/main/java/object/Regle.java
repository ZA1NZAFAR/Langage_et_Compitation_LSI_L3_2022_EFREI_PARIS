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
        return "\n" + left + " --> " + right;
    }
}

//S=nT
//T=ABCT
//T=#
//A=aA
//A=#
//B=bB
//B=cB
//B=#
//C=dC
//C=da
//C=dA