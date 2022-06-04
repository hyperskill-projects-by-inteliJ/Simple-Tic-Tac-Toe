package tictactoe;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static List<String> cellInput = new ArrayList<String>(Collections.nCopies(9, "_"));
    static int[][] winningCombinations = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    static int xCount = 0;
    static int oCount = 0;
    static int blankCount = 0;

    static boolean xWin = false;
    static boolean oWin = false;
    static boolean draw = false;

    static boolean xTurn = true;

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        createCell();
        while (!(xWin || oWin || draw)) {
            getCoordinate();
        }

        printGameStatus();
    }

    public static void countCellTypes() {
        xCount = Collections.frequency(cellInput, "X");
        oCount = Collections.frequency(cellInput, "O");
        blankCount = Collections.frequency(cellInput, "_");
    }

    public static void getCoordinate() {
        boolean valid = false;

        int rowInt = 0;
        int colInt = 0;

        while (!valid) {
            System.out.print("Enter the coordinates: ");
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
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    valid = true;
                }
            }
        }

        int cellIndex = convertCoordinateToCellIndex(rowInt, colInt);
        updateCell(cellIndex);

        countCellTypes();
        createCell();

        xWin = checkWinCombinations("X");
        oWin = checkWinCombinations("O");
        draw = checkForDraw();
    }

    static boolean checkCoordinateAvailability(int row, int col) {
        int cellIndex = convertCoordinateToCellIndex(row, col);

        if (cellInput.get(cellIndex).matches("_")) {
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
        cellInput.set(cellIndex, xTurn ? "X" : "O");
        xTurn = !xTurn;
    }


    public static void createCell() {
        printBoundaries();

        System.out.printf("| %s %s %s |\n", mapCell(0), mapCell(1), mapCell(2));
        System.out.printf("| %s %s %s |\n", mapCell(3), mapCell(4), mapCell(5));
        System.out.printf("| %s %s %s |\n", mapCell(6), mapCell(7), mapCell(8));

        printBoundaries();
    }

    public static String mapCell(int cellIndex) {
        return cellInput.get(cellIndex).matches("_") ? " " : cellInput.get(cellIndex);
    }

    public static void printGameStatus() {
        String status = "";

        if (xWin) {
            status = "X wins";
        } else if (oWin) {
            status = "O wins";
        } else if (draw) {
            status = "Draw";
        }

        System.out.println(status);
    }

    public static boolean checkForDraw() {
        return blankCount == 0 && xCount + oCount == 9;
    }

    public static boolean checkWinCombinations(String letter) {
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
