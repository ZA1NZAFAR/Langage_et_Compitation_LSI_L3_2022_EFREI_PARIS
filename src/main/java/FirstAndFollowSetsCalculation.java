public class FirstAndFollowSetsCalculation {
    // Algoritme qui calcule les premiers et les suivants de la grammaire donne
    int count, n = 0;

    // Stores the final result of the First Sets
    char calcFirst[][];
    // Stores the final result of the Follow Sets
    char calcFollow[][];
    int m = 0;

    // Stores the production rules
    char production[][];
    char f[], first[];
    int k;
    char ck;
    int e;

    public FirstAndFollowSetsCalculation() {
        calcFirst = new char[10][100];
        calcFollow = new char[10][100];
        production = new char[10][10];
        f = new char[30];
        first = new char[10];
    }

    public FirstAndFollowSetsCalculation(String grammar) {
        // Count number of lines and assign it to count
        String[] lines = grammar.split("\r\n|\r|\n");
        int count = lines.length;
        this.count = count;

        calcFirst = new char[count + 2][100];
        calcFollow = new char[count + 2][100];
        production = new char[count][10];
        f = new char[(count + 2) * 3];
        first = new char[count * 2];

        int i = 0;
        // The Input grammar
        for (String line : lines)
            production[i++] = line.toCharArray();
    }

    public void calculateFirstAndFollows() {
        int jm = 0;
        int km = 0;
        int i, choice;
        char c, ch;
        count = this.count;

        System.out.println("Grammaire :");
        for (int k = 0; k < production.length; k++) {
            for (int j = 0; j < production[k].length; j++) {
                System.out.print(production[k][j]);
            }
            System.out.println();
        }

        int kay;
        char done[] = new char[count];
        int ptr = -1;

        // Initializing the calcFirst array
        for (k = 0; k < count; k++) {
            for (kay = 0; kay < 100; kay++) {
                calcFirst[k][kay] = '!';
            }
        }

        int point1 = 0, point2, xxx;

        for (k = 0; k < count; k++) {
            c = production[k][0];
            point2 = 0;
            xxx = 0;

            // Checking if First of c has already been calculated
            for (kay = 0; kay <= ptr; kay++)
                if (c == done[kay])
                    xxx = 1;

            if (xxx == 1)
                continue;

            // Function call
            findfirst(c, 0, 0);
            ptr += 1;

            // Adding c to the calculated list
            done[ptr] = c;
            System.out.print("\n Premier(" + c + ") = { ");
            calcFirst[point1][point2++] = c;

            // Printing the First Sets of the grammar
            for (i = 0 + jm; i < n; i++) {
                int lark = 0, chk = 0;

                for (lark = 0; lark < point2; lark++) {

                    if (first[i] == calcFirst[point1][lark]) {
                        chk = 1;
                        break;
                    }
                } if(chk == 0) {
                    System.out.print(first[i] + ", ");
                    calcFirst[point1][point2++] = first[i];
                }
            }

            System.out.print("}");
            jm = n;
            point1++;
        }

        System.out.println();
        System.out.println("\n-----------------------------------------------\n");
        char donee[] = new char[count];
        ptr = -1;

        // Initializing the calcFollow array
        for (k = 0; k < count; k++) {
            for (kay = 0; kay < 100; kay++) {
                calcFollow[k][kay] = '!';
            }
        }

        point1 = 0;
        int land = 0;
        for (e = 0; e < count; e++) {
            ck = production[e][0];
            point2 = 0;
            xxx = 0;

            // Checking if Follow of ck has already been calculated
            for (kay = 0; kay <= ptr; kay++)
                if (ck == donee[kay])
                    xxx = 1;

            if (xxx == 1)
                continue;
            land += 1;

            // Function call
            follow(ck);
            ptr += 1;

            // Adding ck to the calculated list
            donee[ptr] = ck;
            System.out.print(" Suivant(" + ck + ") = { ");
            calcFollow[point1][point2++] = ck;

            // Printing the Follow Sets of the grammar
            for (i = 0 + km; i < m; i++) {
                int lark = 0, chk = 0;
                for (lark = 0; lark < point2; lark++) {
                    if (f[i] == calcFollow[point1][lark]) {
                        chk = 1;
                        break;
                    }
                } if (chk == 0) {
                    System.out.print(f[i] + ", ");
                    calcFollow[point1][point2++] = f[i];
                }
            }

            System.out.print(" }\n");
            km = m;
            point1++;
        }
    }

    // Function qui calcule les suivants
    public void follow(char c) {
        int i, j;

        // Adding "$" to the follow set of the start symbol
        if(production[0][0] == c) {
            f[m++] = '$';
        }

        for (i = 0; i < production.length; i++) {
            for (j = 2; j < production[i].length; j++) {
                if (production[i][j] == c) {
                    if (j + 1 != production[i].length) {
                        // Calculate the first of the next Non-Terminal in the production
                        followfirst(production[i][j + 1], i, (j + 2));
                    }

                    if (j + 1 == production[i].length && c != production[i][0]) {
                        // Calculate the follow of the Non-Terminal in the L.H.S. of the production
                        follow(production[i][0]);
                    }
                }
            }
        }
    }

    // Function qui calculent les premiers
    public void findfirst(char c, int q1, int q2) {
        int j;

        // The case where we encounter a Terminal
        if (!Character.isUpperCase(c)) {
            first[n++] = c;
        }

        for (j = 0; j < count; j++) {
            if (production[j][0] == c) {
                if (production[j][2] == '#') {
                    if (q2 == production[q1].length)
                        first[n++] = '#';
                    else if (q2 != production[q1].length && (q1 != 0 || q2 != 0)) {
                        // Recursion to calculate First of New Non-Terminal we encounter after epsilon
                        findfirst(production[q1][q2], q1, (q2 + 1));
                    } else
                        first[n++] = '#';
                } else if(!Character.isUpperCase(production[j][2])) {
                    first[n++] = production[j][2];
                } else {
                    // Recursion to calculate First of New Non-Terminal we encounter at the beginning
                    findfirst(production[j][2], j, 3);
                }
            }
        }
    }

    // Function qui calcule les suivants
    public void followfirst(char c, int c1, int c2) {
        int k;

        // The case where we encounter a Terminal
        if (!Character.isUpperCase(c))
            f[m++] = c;
        else {
            int i = 0, j = 1;
            for (i = 0; i < count; i++) {
                if (calcFirst[i][0] == c)
                    break;
            }

            //Including the First set of the Non-Terminal in the Follow of the original query
            while (calcFirst[i][j] != '!') {
                if (calcFirst[i][j] != '#') {
                    f[m++] = calcFirst[i][j];
                } else {
                    if (c2 == production[c1].length) {
                        // Case where we reach the end of a production
                        follow(production[c1][0]);
                    } else {
                        // Recursion to the next symbol in case we encounter a "#"
                        followfirst(production[c1][c2], c1, c2 + 1);
                    }
                }

                j++;
            }
        }
    }
}
