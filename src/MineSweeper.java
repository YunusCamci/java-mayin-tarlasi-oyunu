import java.util.*;

public class MineSweeper {
    private int rowSize;
    private int colSize;
    private int[][] gameBoard;
    private boolean[][] mines;

    public MineSweeper(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.gameBoard = new int[rowSize][colSize];
        this.mines = new boolean[rowSize][colSize];
        initializeGame(); // Oyun tahtası oluşturulur ve mayınlar yerleştirilir
    }

    private void initializeGame() {
        // Oyun tahtası sıfırlanır
        for (int i = 0; i < rowSize; i++) {
            Arrays.fill(gameBoard[i], 0);
        }

        // Rastgele mayınların yerleştirilmesi
        int totalMines = rowSize * colSize / 4;
        Random random = new Random();

        while (totalMines > 0) {
            int randRow = random.nextInt(rowSize);
            int randCol = random.nextInt(colSize);

            if (!mines[randRow][randCol]) {
                mines[randRow][randCol] = true;
                totalMines--;
            }
        }
    }

    public void printBoard() {
        // Oyun tahtası ekrana bastırılır
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (mines[i][j]) {
                    System.out.print("* ");
                } else {
                    System.out.print(gameBoard[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean isValid(int row, int col) {
        // Girilen satır ve sütun değerlerinin geçerliliği kontrol edilir
        return row >= 0 && row < rowSize && col >= 0 && col < colSize;
    }

    public int countAdjacentMines(int row, int col) {
        // Seçilen hücrenin çevresindeki mayın sayısı hesaplanır
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isValid(row + i, col + j) && mines[row + i][col + j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean checkWinCondition() {
        // Oyunun kazanılıp kazanılmadığı kontrol edilir
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (!mines[i][j] && gameBoard[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean gameEnd = false;

        while (!gameEnd) {
            printBoard();
            System.out.print("Satır Giriniz: ");
            int selectedRow = scanner.nextInt();
            System.out.print("Sütun Giriniz: ");
            int selectedCol = scanner.nextInt();

            if (!isValid(selectedRow, selectedCol)) {
                System.out.println("===========================");
                System.out.println("Geçersiz giriş! Lütfen geçerli satır ve sütun değerleri giriniz.");
                continue;
            }

            if (mines[selectedRow][selectedCol]) {
                printBoard();
                System.out.println("===========================");
                System.out.println("Game Over!!");
                gameEnd = true;
            } else {
                int count = countAdjacentMines(selectedRow, selectedCol);
                gameBoard[selectedRow][selectedCol] = count;

                if (checkWinCondition()) {
                    printBoard();
                    System.out.println("===========================");
                    System.out.println("Tebrikler! Oyunu kazandınız!");
                    gameEnd = true;
                }
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Satır sayısını giriniz: ");
        int rows = input.nextInt();
        System.out.print("Sütun sayısını giriniz: ");
        int columns = input.nextInt();

        MineSweeper game = new MineSweeper(rows, columns);
        game.playGame();
        input.close();
    }
}
