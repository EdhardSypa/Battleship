import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Scanner;
public class GameField {
	private Scanner scan = new Scanner(System.in);
    private char[][] field = new char[10][10];
    private final int SHIPS_COUNT = 17;
    private int successfulHitCounter = 0;

    public GameField() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = '~';
            }
        }
    }

    public boolean takeShot(GameField enemyField) {
        Point2D shot;

            do {
                String pointToAttack = scan.next();
                shot = parseLocation(pointToAttack);
            } while (!checkShot(shot));

            int x = (int) shot.getX();
            int y = (int) shot.getY();

            if (enemyField.field[x][y] == 'O') {

                field[x][y] = 'X'; 
                enemyField.field[x][y] = 'X'; 
                successfulHitCounter++;

                if (successfulHitCounter == SHIPS_COUNT) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    return true;
                } else if (isSankAShip(x, y, enemyField.field)) {
                    System.out.println("You sank a ship!");
                } else {
                    System.out.println("You hit a ship!");
                }
            } else if (enemyField.field[x][y] == 'X') {
                System.out.println("You hit a ship!");
            } else {
                field[x][y] = 'M';
                enemyField.field[x][y] = 'M';
                System.out.println("You missed.");
            }

        return false;
    }

    private boolean isSankAShip(int x, int y, char[][] btlField) {
        return !checkNeighbors(x, y, btlField); 
    }

    public void fillWithShips() {
        for (ShipType ship : ShipType.values()) {
            System.out.printf("%nEnter the coordinates of the %s (%d cells):%n", ship.getName(), ship.getLength());
            int[] coords = getInput();

            while (!checkInput(coords, ship)) {
                coords = getInput();
            }

            addShip(coords, ship);
        }
    }

    private void addShip(int[] coords, ShipType ship) {

        for (int i = 0; i < ship.getLength(); i++) {
            field[coords[0]][coords[1]] = 'O';

            if (coords[2] != coords[0]) {
                coords[0]++;
            } else {
                coords[1]++;
            }
        }
        printField();
    }

    private int[] getInput() {
        String p1 = scan.next();
        String p2 = scan.next();

        return parseLocations(p1, p2);
    }

    private int[] parseLocations(String p1, String p2) { 
        int[] locations = new int[4];

        //if reverse input
        int x1 = p1.charAt(0) - 65;
        int y1 = Integer.parseInt(p1.replaceAll("\\D", "")) - 1;
        int x2 = p2.charAt(0) - 65;
        int y2 = Integer.parseInt(p2.replaceAll("\\D", "")) - 1;

        locations[0] = Math.min(x1, x2); 
        locations[1] = Math.min(y1, y2); 
        locations[2] = Math.max(x1, x2); 
        locations[3] = Math.max(y1, y2); 

        return locations;
    }

    private Point2D parseLocation(String p) {
        Point2D point = new Point();
        int x = p.charAt(0) - 65;
        int y = Integer.parseInt(p.replaceAll("\\D", "")) - 1;
        point.setLocation(x, y);
        return point;
    }

    private boolean checkShot(Point2D shot) {
        if (shot.getX() > 9 || shot.getY() > 9 || shot.getX() < 0 || shot.getY() < 0) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
        return true;
    }

    private boolean checkInput(int[] coords, ShipType ship) {

        if (coords[0] != coords[2] && coords[1] != coords[3]) { 
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }

        int shipLength = (coords[2] - coords[0]) + (coords[3] - coords[1]);
        if (shipLength != ship.getLength() - 1) { 
            System.out.printf("Error! Wrong length of the %s! Try again:%n", ship.getName());
            return false;
        }
        if (isHaveNeighbors(coords, ship.getLength())) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            return false;
        }
        return true;
    }

    private boolean isHaveNeighbors(int[] coords, int len) { 
        boolean isXRotation = false;
        int x = coords[0];
        int y = coords[1];

        if (coords[0] != coords[2]) { 
            isXRotation = true;
        }

        for (int i = 0; i < len; i++) { 

            if (checkNeighbors(x, y, field)) {
                return true;
            }

            if (isXRotation) {
                x++;
            } else {
                y++;
            }
        }

        return false;
    }

    private boolean checkNeighbors(int i, int j, char[][] btlField) {

        for (int xoff = -1; xoff <= 1; xoff++) {
            for (int yoff = -1; yoff <= 1; yoff++) {

                
                if (xoff == 0 && yoff == 0) {
                    continue;
                }

                int x = i + xoff;
                int y = j + yoff;

                
                if (x < 0 || y < 0 || x > btlField.length - 1 || y > btlField.length - 1) {
                    continue;
                }
                
                if (btlField[x][y] == 'O') {
                    return true;
                }
            }
        }

        return false;
    }

    public void printField() {
        String rows = "ABCDEFGHIJ";
        System.out.println();
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int j = 0; j < field.length; j++) {
            char[] row = field[j];
            System.out.print(rows.charAt(j) + " ");
            for (char col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }
}
