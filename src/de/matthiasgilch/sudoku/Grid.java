package de.matthiasgilch.sudoku;

import java.util.HashSet;

/**
 * Grid
 *
 * @author Matthias Gilch
 */
public class Grid {
    private Byte[][] grid;
    private Byte[] fields;

    public Grid() {
        int counter = 0;
        grid = new Byte[9][9];
        fields = new Byte[9 * 9];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = (byte) 0;
            }
        }

        for (Byte[] line : grid) {
            for (Byte field : line) {
                fields[counter] = field;
                counter++;
            }
        }
    }

    public Grid(Byte[][] grid) {
        this();

        int counter = 0;
        this.grid = grid.clone();
        fields = new Byte[9 * 9];

        for (Byte[] line : this.grid) {
            for (Byte field : line) {
                fields[counter] = field;
                counter++;
            }
        }
    }

    public byte get(int row, int col) {
        return grid[row][col];
    }

    public void set(int row, int col, byte value) throws Exception {
        if (grid[row][col] != 0 && value != 0) {
            throw new Exception("Already a value in there");
        }
        grid[row][col] = value;
    }

    public boolean allFilledOut() {
        for (int i = 0; i < 9; i++) {
            if (!isFilled(getRow(i))) {
                return false;
            }
        }

        return true;
    }

    public boolean allCorrect() {
        for (int i = 0; i < 9; i++) {
            boolean correct = true;

            correct = !containsDuplicates(getRow(i));
            correct = correct && !containsDuplicates(getColumn(i));
            correct = correct && !containsDuplicates(getBox(i));

            if (!correct) {
                return false;
            }
        }

        return true;
    }

    private Byte[] getRow(int row) {
        Byte[] retValue = new Byte[9];

        for (int i = 0; i < 9; i++) {
            retValue[i] = grid[row][i];
        }

        return retValue;
    }

    private Byte[] getColumn(int col) {
        Byte[] retValue = new Byte[9];

        for (int i = 0; i < 9; i++) {
            retValue[i] = grid[i][col];
        }

        return retValue;
    }

    private Byte[] getBox(int box) {
        Byte[] retValue = new Byte[9];
        int colOffset = (box % 3) * 3;
        int rowOffset = (box / 3) * 3;

        for (int i = 0; i < 9; i++) {
            int colAddition = (i % 3);
            int rowAddition = (i / 3);

            int col = colOffset + colAddition;
            int row = rowOffset + rowAddition;

            retValue[i] = grid[row][col];
        }

        return retValue;
    }

    private boolean containsDuplicates(Byte[] values) {
        int upTo = values.length + 1;
        HashSet<Byte> possibleValues = new HashSet<>();

        for (int i = 0; i < upTo; i++) {
            possibleValues.add((byte) (i + 1));
        }

        for (Byte value : values) {
            if (value == 0) {
                continue;
            }
            if (!possibleValues.contains(value)) {
                return true;
            } else {
                possibleValues.remove(value);
            }
        }

        return false;
    }

    private boolean isFilled(Byte[] values) {
        for (Byte value : values) {
            if (value == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        final int ROW_STRING_LENGTH = "| x x x | x x x | x x x |\n".length();
        final int STRING_LENGTH = ROW_STRING_LENGTH * 9 * 4;

        StringBuilder sb = new StringBuilder(STRING_LENGTH);

        for (int row = 0; row < 9; row++) {
            if ((row % 3) == 0) {
                sb.append("+-------+-------+-------+\n");
            }

            for (int col = 0; col < 9; col++) {
                if ((col % 3) == 0) {
                    sb.append("| ");
                }
                sb.append(grid[row][col].toString() + " ");
                if (col == 8) {
                    sb.append("|\n");
                }
            }

            if (row == 8) {
                sb.append("+-------+-------+-------+\n");
            }
        }

        return sb.toString();
    }

    @Override
    public Grid clone() {
        return new Grid(grid);
    }
}
