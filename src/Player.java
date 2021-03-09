
public class Player {
	private String name;
    private GameField battlefield;
    private GameField fogfield;

    public Player(String name) {
        this.name = name;
        this.battlefield = new GameField();
        this.fogfield = new GameField();
    }

    public String getName() {
        return name;
    }

    public void placeShips() {
        battlefield.printField();
        battlefield.fillWithShips();
    }

    public void showFields() {
        fogfield.printField();
        System.out.println("---------------------");
        battlefield.printField();
    }

    public boolean attack(Player enemy) {
        return fogfield.takeShot(enemy.battlefield);
    }
}
