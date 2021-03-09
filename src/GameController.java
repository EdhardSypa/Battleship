import java.io.IOException;
public class GameController {
	private static Player player1;
    private static Player player2;

    public static void createGameFields() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
    }

    public static void initFields() {
        System.out.println(player1.getName() + ", place your ships on the game field");
        player1.placeShips();
        waitForPlayer();
        System.out.println(player2.getName() + ", place your ships on the game field");
        player2.placeShips();
    }

    private static void waitForPlayer() {
        System.out.println("\nPress Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startGame() {
        boolean isFinished = false;

        while (!isFinished) {

            waitForPlayer();
            doTurn(player1);
            isFinished = player1.attack(player2);
            swapTurn();
        }

        System.out.println(player1.getName() + " won!");
    }

    private static void swapTurn() {
        Player temp = player1;
        player1 = player2;
        player2 = temp;
    }

    private static void doTurn(Player player) {
        player.showFields();
        System.out.println();
        System.out.println(player.getName() + ", it's your turn:");
    }
}
