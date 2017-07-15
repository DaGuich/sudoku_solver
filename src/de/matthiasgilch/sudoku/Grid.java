package de.matthiasgilch.sudoku;

import java.util.HashSet;

/**
 * Grid
 *
 * @author Matthias Gilch
 */
public class Grid {
    /**
     * The grid represented as a two-dimensional Array
     */
    private Byte[][] grid;

    /**
     * Initialize an empty sudoku grid
     */
    public Grid() {
        int counter = 0;
        grid = new Byte[9][9];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = (byte) 0;
            }
        }
    }

    /**
     * Build a grid with the given byte-array
     *
     * @param grid
     */
    public Grid(Byte[][] grid) {
        this();

        int counter = 0;
        this.grid = grid.clone();
    }

    /**
     * Get the stored value in cell row, col
     *
     * @param row row in range from 1 to 9
     * @param col column in range from 1 to 9
     * @return returns the value in the specified cell.
     *      0 means the cell is currently empty
     */
    public byte get(int row, int col) {
        return grid[row][col];
    }

    /**
     * Sets the value in a specified cell row, col
     *
     * @param row row in range from 1 to 9
     * @param col column in range from 1 to 9
     * @param value value to store
     * @throws Exception is thrown when a cell is already occupied
     */
    public void set(int row, int col, byte value) throws Exception {
        if (grid[row][col] != 0 && value != 0) {
            throw new Exception("Already a value in there");
        }
        grid[row][col] = value;
    }

    /**
     * Checks if the whole grid is filled with numbers
     *
     * @return true if every cell is occupied
     */
    public boolean allFilledOut() {
        for (int i = 0; i < 9; i++) {
            if (!isFilled(getRow(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if there's a incorrect number
     *
     * @return true if there's anywhere an incorrect number
     */
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

    /**
     * Fetch a row
     *
     * @param row row number to fetch (0 to 8)
     * @return the row values
     */
    private Byte[] getRow(int row) {
        Byte[] retValue = new Byte[9];

        for (int i = 0; i < 9; i++) {
            retValue[i] = grid[row][i];
        }

        return retValue;
    }

    /**
     * Fetch a column
     *
     * @param col column number to fetch (0 to 8)
     * @return the column values
     */
    private Byte[] getColumn(int col) {
        Byte[] retValue = new Byte[9];

        for (int i = 0; i < 9; i++) {
            retValue[i] = grid[i][col];
        }

        return retValue;
    }

    /**
     * Fetch a box
     *
     * @param box box number to fetch (0 to 8)
     *            <pre>
     *            +-+-+-+
     *            |0|1|2|
     *            +-+-+-+
     *            |3|4|5|
     *            +-+-+-+
     *            |6|7|8|
     *            +-+-+-+
     *            </pre>
     * @return the box values
     */
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

    /**
     * Checks a array of values if there are duplicates (from 1 to 9)
     *
     * @param values the values to check
     * @return true if there are duplicates, otherwise false
     */
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

    /**
     * Check if there is a array of values not filled
     *
     * @param values values to check
     * @return true if everything is filled out,
     *         otherwise false
     */
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
