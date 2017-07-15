package de.matthiasgilch.sudoku;

/**
 * Solver
 *
 * @author Matthias Gilch
 */
public class Solver {
    private Grid grid;

    public Solver(Grid grid) {
        this.grid = grid.clone();
    }

    public Grid solve() {
        if (solve(0, 0)) {
            return grid;
        } else {
            return null;
        }
    }

    private boolean solve(int x, int y) {
        if (grid.allFilledOut() && grid.allCorrect()) {
            return true;
        }

        int nextX = (x + 1) % 9;
        int nextY = y + ((x + 1) / 9);

        if (grid.get(x, y) != 0) {
            return solve(nextX, nextY);
        }

        for (int i = 0; i < 9; i++) {
            try {
                grid.set(x, y, (byte) (i + 1));
                if (!grid.allCorrect()) {
                    grid.set(x, y, (byte) 0);
                    continue;
                }

                if (!solve(nextX, nextY)) {
                    grid.set(x, y, (byte) 0);
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
