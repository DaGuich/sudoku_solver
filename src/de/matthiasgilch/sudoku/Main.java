package de.matthiasgilch.sudoku;

import java.io.FileReader;
import java.io.IOException;

/**
 * Main
 *
 * @author Matthias Gilch
 */
public class Main {
    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println(arg);
        }
        try {
            FileReader fileReader = new FileReader(args[0]);
            Byte[][] field = new Byte[9][9];
            for (int i = 0; i < 81; i++) {
                int x = i % 9;
                int y = i / 9;

                byte nextNumber = getNextNumber(fileReader);
                field[y][x] = nextNumber;
            }
            Grid grid = new Grid(field);
            System.out.println(grid);

            Solver solver = new Solver(grid);
            long startTime = System.currentTimeMillis();
            Grid solvedGrid = solver.solve();
            long endTime = System.currentTimeMillis();
            System.out.println("Execution time: " + (endTime - startTime) + "ms");
            if (solvedGrid == null) {
                System.out.println("No solution found!");
            } else {
                System.out.println(solvedGrid.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte getNextNumber(FileReader fr) throws IOException {
        int readChar;
        while (true) {
            readChar = fr.read();
            if (readChar == -1) {
                break;
            }
            char read = (char) readChar;
            if (read >= '0' && read <= '9') {
                byte number = (byte) Character.getNumericValue(read);
                return number;
            }
        }
        return -1;
    }
}
