package tictactoe;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static List<String> cellInput = new ArrayList<String>();
    static int[][] winningCombinations = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    static int xCount = 0;
    static int oCount = 0;
    static int blankCount = 0;

    static boolean xWin = false;
    static boolean oWin = false;

    public static void main(String[] args) {

        System.out.print("Enter cells: ");
        Collections.addAll(cellInput, scanner.nextLine().replace("_", " ").split(""));

        xCount = Collections.frequency(cellInput, "X");
        oCount = Collections.frequency(cellInput, "O");
        blankCount = Collections.frequency(cellInput, "_");


        createCell();
        getCoordinate();

        // printGameStatus();
    }

    public static void getCoordinate() {
        boolean valid = false;

        int rowInt = 0;
        int colInt = 0;

        while (!valid) {
            System.out.println("Enter the coordinates: ");
            String row = scanner.next();
            String col = scanner.next();

            if (!row.matches("\\d") || !col.matches("\\d")) {
                System.out.println("You should enter numbers!");
            } else {
                rowInt = Integer.parseInt(row);
                colInt = Integer.parseInt(col);
                if (!checkCoordinateValidity(rowInt, colInt)) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else if (!checkCoordinateAvailability(rowInt, colInt)) {
                    System.out.println(rowInt + " " + colInt);
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    valid = true;
                }
            }
        }

        int cellIndex = convertCoordinateToCellIndex(rowInt, colInt);
        updateCell(cellIndex);
        createCell();

    }


    static boolean checkCoordinateAvailability(int row, int col) {
        int cellIndex = convertCoordinateToCellIndex(row, col);

        if (cellInput.get(cellIndex).matches(" ")) {
            return true;
        }
        return false;
    }

    public static int convertCoordinateToCellIndex(int row, int col) {
        return row > 1 ? (row - 1) * 3 + (col - 1) : col - 1;
    }

    public static boolean checkCoordinateValidity(int row, int col) {
        if (row < 1 || row > 3 || col < 1 || col > 3) {
            return false;

        }
        return true;
    }

    public static void updateCell(int cellIndex) {
        cellInput.set(cellIndex, "X");
    }


    public static void createCell() {
        printBoundaries();

        System.out.printf("| %s %s %s |\n", cellInput.get(0), cellInput.get(1), cellInput.get(2));
        System.out.printf("| %s %s %s |\n", cellInput.get(3), cellInput.get(4), cellInput.get(5));
        System.out.printf("| %s %s %s |\n", cellInput.get(6), cellInput.get(7), cellInput.get(8));

        printBoundaries();
    }

    public static void printGameStatus() {
        String status = "";

        if (checkImpossibility()) {
            status = "Impossible";
        } else if (xWin) {
            status = "X wins";
        } else if (oWin) {
            status = "O wins";
        } else if (blankCount == 0) {
            status = "Draw";
        } else {
            status = "Game not finished";
        }

        System.out.println(status);

    }

    public static boolean checkImpossibility() {
        xWin = checkInRowCombinations("X");
        oWin = checkInRowCombinations("O");

        if (Math.abs(xCount - oCount) > 1 || (xWin && oWin)) {
            return true;
        }

        return false;
    }

    public static boolean checkInRowCombinations(String letter) {
        boolean comboAvailable = false;
        int comboLength = 0;

        for (int[] combo : winningCombinations
        ) {
            comboLength = combo.length;

            combo:
            for (int i = 0; i < comboLength; i++) {
                if (i == 0 && Objects.equals(letter, cellInput.get(combo[i]))) {
                    comboAvailable = true;
                } else if (i != 0 && i != comboLength - 1 && Objects.equals(cellInput.get(combo[i - 1]), cellInput.get(combo[i]))) {
                    comboAvailable = true;
                } else if (i == comboLength - 1 && Objects.equals(cellInput.get(combo[i - 1]), cellInput.get(combo[i])) && comboAvailable) {
                    return true;
                } else {
                    comboAvailable = false;
                    break combo;
                }
            }
        }

        return false;
    }

    public static void printBoundaries() {
        System.out.println("---------");
    }

}
