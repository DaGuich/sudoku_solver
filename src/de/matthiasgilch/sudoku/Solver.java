package de.matthiasgilch.sudoku;

/**
 * Solver
 *
 * @author Matthias Gilch
 */
public class Solver {
    /**
     * Sudoku grid to work on
     */
    private Grid grid;

    /**
     * Creates a solver instance that should solve
     * the given grid
     *
     * @param grid The grid that should be solved
     */
    public Solver(Grid grid) {
        this.grid = grid.clone();
    }

    /**
     * Solves the grid given in constructor
     *
     * @return the solved grid
     */
    public Grid solve() {
        if (solve(0, 0)) {
            return grid;
        } else {
            return null;
        }
    }

    /**
     * Recursive function to solve the sudoku puzzle
     *
     * @param column start column
     * @param row    start row
     * @return true if the puzzle is solved, false when
     * something went wrong
     */
    private boolean solve(int column, int row) {
        if (grid.allFilledOut() && grid.allCorrect()) {
            return true;
        }

        int nextColumn = (column + 1) % 9;
        int nextRow = (row + ((column + 1) / 9)) % 9;

        if (grid.get(column, row) != 0) {
            return solve(nextColumn, nextRow);
        }

        for (int i = 0; i < 9; i++) {
            try {
                grid.set(column, row, (byte) (i + 1));
                if (!grid.allCorrect()) {
                    grid.set(column, row, (byte) 0);
                    continue;
                }

                if (!solve(nextColumn, nextRow)) {
                    grid.set(column, row, (byte) 0);
                } else {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
