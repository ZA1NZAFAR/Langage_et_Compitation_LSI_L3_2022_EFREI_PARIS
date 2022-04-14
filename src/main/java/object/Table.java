package object;

import java.util.List;

public class Table {
    List<TableCell> cells;

    public Table(List<TableCell> cells) {
        this.cells = cells;
    }

    public boolean containsCouple(String terminal, String nonTerminal) {
        for (TableCell cell : cells) {
            if (cell.terminal.equals(terminal) && cell.nonTerminal.equals(nonTerminal)) {
                return true;
            }
        }
        return false;
    }

    public void add(TableCell tmp) {
        cells.add(tmp);
    }

    @Override
    public String toString() {
        return "Table{" +
                "cells=" + cells +
                '}';
    }
}
