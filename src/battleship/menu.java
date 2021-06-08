/**
 * 
 */
package battleship;

import java.util.Scanner;

/**
 * @author Ian Chow 
 *
 */
public class menu {

	private static int playerScore;

	/**
	 * 
	 */
	public menu() {
	}

	// TODO Auto-generated constructor stub
	private static void displayMenu() {
		System.out.println("\nPlease select one of the options below\n");
		System.out.println("1. New game");
		System.out.println("2. Load game");
		System.out.println("3. VS Com");
		System.out.println("0. Exit");
	}

	/**
	 * Determines user choice and reads user inputs to be passed to methods in
	 * choice. choice declared as string to allow for error checking by use of
	 * case default; try and catch error checking code is not required. Do while
	 * loop used to allow terminate of program when exit choice has been chosen.
	 * Try and catch within the cases are used for error checking user inputs.
	 */
	public static void processUserChoices() {
		char[][] grid = new char[8][8]; // user grid
		char[][] grid1 = new char[8][8]; // computer grid
		boolean decision = true; // used to determine if the user has made the
									// choice to exit the program
		do {
			String choice; // to determine which menu choice was chosen by the
							// user;leave as string to prevent any errors.
			menu.displayMenu();
			Scanner sc = new Scanner(System.in);
			choice = sc.nextLine();

			switch (choice) {
			case ("1"): {

				board.creategrid(grid);
				gameManager.createShip(grid);
				//board.displayShipPosition(grid);

				int torps = 30; // determines the number of torpedoes available
								// at the start of the game, 19 torpedoes are
								// needed to sink all the ships without missing
				int hits = 0;
				int playerScore = 0;
				// continues the gameManager when the is remaining torpedoes and
				// there
				// are still ships unsunk.
				while (torps > 0 && hits <= 21) {
					board.displaygrid(grid);
					hits = gameManager.userFire(grid, hits, torps, playerScore);
					torps--;

					Scanner sc1 = new Scanner(System.in);
					System.out.println("Save the game?(Yes=y/No=n)");
					String save = sc1.nextLine();
					if ((save.equals("Y")) || (save.equals("y"))) {
						board.saveGame(grid);
						System.out.println("game has been saved.");
						
						processUserChoices();
					} else if ((save.equals("n")) || (save.equals("N"))) {
						continue;
					} else {
						System.out.println("Invalid input.");

					}
				}
				gameManager.endGame(hits, torps);
				board.displaygrid(grid);

				break;
			}

			case ("2"): {

				board.loadGame(grid);
				//board.displayShipPosition(grid);
				// determines the number of torpedos left and hits made
				int torps = 21;
				int hits = 0;

				for (int row = 0; row < grid.length; row++) {

					for (int column = 0; column < grid.length; column++) {
						if (grid[row][column] == '!') {
							torps--;
							hits++;
						}
						if (grid[row][column] == 'M') {
							torps--;
						}
					}
				}

				// continues the gameManager when the is remaining torpedoes and
				// there
				// are still ships unsunk.
				while (torps > 0 && hits <= 21) {
					board.displaygrid(grid);

					hits = gameManager.userFire(grid, hits, torps, playerScore);
					torps--;

					Scanner sc1 = new Scanner(System.in);
					System.out.println("Save the game?(Yes=y/No=n)");
					String save = sc1.nextLine();
					if ((save.equals("Y")) || (save.equals("y"))) {
						board.saveGame(grid);
						System.out.println("game has been saved.");
						processUserChoices();
					} else if ((save.equals("n")) || (save.equals("N"))) {
						continue;
					} else {
						System.out.println("Invalid input.");

					}

				}
				gameManager.endGame(hits, torps);
				board.displaygrid(grid);
				break;
			}

			case ("3"): {
				board.creategrid(grid1);

				gameManager.placeBattleship(grid1);

				board.displayShipPosition(grid1);
				gameManager.placeCruiser(grid1);
				board.displayShipPosition(grid1);
				gameManager.placeDestroyer(grid1);
				board.displayShipPosition(grid1);
				gameManager.placeSubmarines(grid1);
				gameManager.AI(grid1, 0, 0);

				break;

			}
			case ("0"): {
				exit();
				decision = false;
				break;
			}
			default: {
				System.out.println("Invalid Option was selected,please select again.");
				break;
			}
			}
		} while (decision == true);

	}

	/**
	 * Method used to exit the program
	 */
	public static void exit() {

		System.out.println("\nBye! Thank you for playing.");
		return;
	}

	public static void main(String[] arg) {

		menu.processUserChoices();

	}

}
