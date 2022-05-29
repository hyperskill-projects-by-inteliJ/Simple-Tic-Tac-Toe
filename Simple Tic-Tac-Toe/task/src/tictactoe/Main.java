package tictactoe;

import java.util.Scanner;

public class Main {

    static String cellInput = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter cells: ");
        cellInput = scanner.nextLine();

        createCell();
    }

    public static void createCell() {
        printBoundaries();

        int cellInputLength = cellInput.length();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < cellInputLength; i++) {
            if (i % 3 == 0) {
                sb.append("| ").append(cellInput.charAt(i)).append(" ");
            } else if ((i + 1) % 3 == 0) {
                sb.append(cellInput.charAt(i)).append(" ").append("|");
            } else {
                sb.append(cellInput.charAt(i)).append(" ");
            }

            if (i < cellInputLength - 1 && (i + 1) % 3 == 0) {
                sb.append("\n");
            }
        }
        System.out.println(sb);

        printBoundaries();
    }

    public static void printBoundaries() {
        System.out.println("---------");
    }
}
