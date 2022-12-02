package com.ca2.ADT;

public class GridPosition {

    private int column;
    private int row;
    private int maxColumns;

    GridPosition(int _column, int _row, int _maxColumns) {
        this.column = _column;
        this.row = _row;
        this.maxColumns = _maxColumns;
    }

    public int getColumn() {
        return column;
    }

    public void next() {
        this.column++;

        if (this.column == 3) {
            this.column = 0;
            this.nextRow();
        }
    }

    public void resetColumn() {
        this.column = 0;
    }

    public int getRow() {
        return row;
    }

    public void nextRow() {
        this.row++;
    }

    public void resetRow() {
        this.row = 0;
    }


    public void resetAll() {
        this.resetRow();
        this.resetColumn();
    }
}
