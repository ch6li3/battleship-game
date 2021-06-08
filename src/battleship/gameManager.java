package battleship;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Scanner;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * @author Ian Chow  
 *
 */
public class gameManager {

	Scanner input = new Scanner(System.in);

	/**
	 * Method is used to randomly create and place the fixed number of ships
	 * destroyer, battleship, cruisers and submarines.
	 * 
	 * @param grid
	 */
	public static void createShip(char[][] grid) {

		int subSize = 1; // SUBMARINE SIZE
		int battleshipSize = 4;
		int cruiserSize = 3;
		int destroyerSize = 2;
		int cruiserShip = 1; // cruiser ship count; starts from 1
		int row;
		int column;
		int subCount = 1; // submarine count; starts from 1
		int destroyer = 0; // destroyer count ; starts from 0

		if (Math.random() < 0.5) // determines if ships are place
									// horizontally or vertically(randomly
									// generates a double (between 0 and 0.9)

		{

			// horizontal placement of ship

			column = (int) (Math.random() * 5);
			row = (int) (Math.random() * 6);

			for (int i = 0; i < battleshipSize; i++) {

				grid[row][column + i] = 'B';

			}

		} else {
			// vertical placement of ship
			column = (int) (Math.random() * 6); // range of 0-6
			row = (int) (Math.random() * 5); // range of 0-5

			for (int i = 0; i < battleshipSize; i++) {

				grid[row + i][column] = 'B';

			}
		}

		// while loop used to create exactly 2 cruiserShips; count starts
		// from 1

		while (cruiserShip < 3)

		{

			// vertical placement of ship
			// column
			column = (int) (Math.random() * 6); // between 0-6
			row = (int) (Math.random() * 5); // between 0-5

			// to prevent overlapping of ships and ensure ships are not placed
			// outside the board.
			if ((grid[row + 1][column] == '~') && grid[row + 2][column] == '~' && (grid[row + 3][column] == '~')
					&& (grid[row][column] == '~')) {
				for (int x = 0; x < cruiserSize; x++) {

					grid[row + x][column] = 'C';

				}

				cruiserShip++; // increment of ships to keep track of number of
								// ships created.

			}
		}

		// creating 3 submarines

		while (subCount < 4) {
			column = (int) (Math.random() * 7); // range between 0-7

			row = (int) (Math.random() * 7); // range between 0-7

			for (int i = 0; i < subSize; i++) {
				if (grid[row + i][column] == '~') { // prevents overlapping of
													// ships

					grid[row][column + i] = 'S';
					subCount++;
				}

			}
		}
		// creating 3 destroyers
		while (destroyer < 3) {

			// horizontal
			column = (int) (Math.random() * 6);
			row = (int) (Math.random() * 7);

			// System.out.println("destroyer" + destroyer + ":" + (int) (column
			// + 1) + "," + (int) (row + 1));
			if ((grid[row][column] == '~') && ((row < 7) && (row >= 0)) && ((column < 7) && (column >= 0))
					&& grid[row][column + 1] == '~') {

				for (int i = 0; i < destroyerSize; i++) {

					grid[row][column + i] = 'D';

				}

				destroyer++;

			}
		}
	}
	/**
	 * Determines if the shot fired is a miss or a hit.
	 * @param grid
	 * @param hits
	 * @param torps
	 * @param playerScore
	 * @return
	 */

	public static int userFire(char[][] grid, int hits, int torps, int playerScore) {
		try {
			Scanner input = new Scanner(System.in);
			int row, column;
			System.out.println("You have: " + torps + " torpedos left!");
			System.out.println("Select a row to fire in: ");

			row = input.nextInt();
			while (row > 8 || row < 1) // Error checking for out of bounds row
										// input
			{
				System.out.println("Enter a valid row (1 to 8)");
				row = input.nextInt();
			}
			System.out.println("Select a column to fire in: ");
			column = input.nextInt();
			while (column > 8 || column < 1) // Error checking for out of bounds
												// column input
			{
				System.out.println("Enter a valid column (1 to 8)");
				column = input.nextInt();
			}
			if ((grid[row - 1][column - 1] == 'B') || (grid[row - 1][column - 1] == 'C')
					|| (grid[row - 1][column - 1] == 'S') || (grid[row - 1][column - 1] == 'D')) {
				hits++;
				System.out.println("~~~~~~~ HIT ~~~~~~~");
				grid[row - 1][column - 1] = '!';
				playerScore++;
			} else {
				System.out.println("~~~~~~~ MISS ~~~~~~~");
				grid[row - 1][column - 1] = 'M';
			}

		} catch (Exception e) {
			System.out.print("Invalid input");
			userFire(grid, hits, torps, playerScore);
		}
		return hits;
	}

	public static void endGame(int hits, int torps) {
		if (torps < 1) {
			System.out.println("You have lost all your torpedos");
		} else if (hits >= 19) {
			System.out.println("You have beaten the game battleship, Thanks for playing!");
		}
		System.out.println("Good game, well played!");

	}
	/**
	 * Places the battleship in the versus computer mode
	 * @param grid
	 */

	public static void placeBattleship(char[][] grid) {
		Scanner input = new Scanner(System.in);

		int battleshipSize = 4;

		int row = 0, column = 0;
		int shipCount = 1; // battleshipCount
		char pos; // determines horizontal or vertical position
		while (shipCount == 1) { // places a single battleship;count starts from
									// 1
			// placing battleship
			try {
				// will keep repeating until the valid coordinates are placed
				do {
					System.out.println("Enter a valid row to place battleship" + shipCount + "(1 to 8)");
					row = input.nextInt();
					row--;
					System.out.println("Enter a valid column to place battleship" + shipCount + "(1 to 8) ");
					column = input.nextInt();
					column--;
				} while ((row < 0) || (row > 7) && (column < 0) || (column > 7));

				Scanner input1 = new Scanner(System.in);
				System.out.println("Enter the orientation to place the ship(Vertical=v or Horizontal=h)");

				pos = input1.next(".").charAt(0); // only allows a single char
													// input

				if (pos == 'H' || pos == 'h') {
					System.out.println("hBattleship" + (int) (column + 1) + "," + (int) (row + 1));
					if ((column <= 7) && (column + 1 <= 7) && (column + 2 <= 7) && (column + 3 <= 7)) {
						for (int i = 0; i < battleshipSize; i++) {

							grid[row][column + i] = 'B';
						}
						shipCount++;
					}
				} else if (pos == 'v' || pos == 'V') {
					if ((row <= 7) && (row + 1 <= 7) && (row + 2 <= 7) && (row + 3 <= 7)) {
						for (int i = 0; i < battleshipSize; i++) {

							grid[row + i][column] = 'B';
						}
						shipCount++;
					}
				} else { // if any character other than v or h are inputed
					System.out.println("Invalid orientation entered");
					placeBattleship(grid);
				}

			} catch (Exception e) {
				System.out.println(e);
				placeBattleship(grid);
			}
		}
	}
	/**
	 * Places the cruiser in the versus computer mode
	 * @param grid
	 */

	public static void placeCruiser(char[][] grid) {
		Scanner input = new Scanner(System.in);
		int cruiserSize = 3;

		int cruiserShip = 1;

		int row = 0, column = 0;

		char pos; // determines horizontal or vertical position
		// while loop used to place exactly 2 cruiserShips; count starts
		// from 1
		try {
			while (cruiserShip < 3)

			{
				do {
					System.out.println("Enter a valid row to place cruisership" + cruiserShip + "(1 to 8)");
					row = input.nextInt();
					row--;
					System.out.println("Enter a valid column to place cruisership" + cruiserShip + "(1 to 8)");
					column = input.nextInt();
					column--;
					Scanner input1 = new Scanner(System.in);
					System.out.println("Enter the orientation to place the ship(Vertical=v or Horizontal=h)");

					pos = input1.next(".").charAt(0); // only allows a single
														// char input

					if (pos == 'H' || pos == 'h') {
						if (column <= 7 && column + 1 <= 7 && column + 2 <= 7 && grid[row][column] == '~'
								&& grid[row][column + 1] == '~' && grid[row][column + 2] == '~') {

							for (int x = 0; x < cruiserSize; x++) {

								grid[row][column + x] = 'C';
							}
							cruiserShip++;

						} else {
							System.out.println("Invalid coordinates ");
							placeCruiser(grid);
						}
					} else if (pos == 'v' || pos == 'V') {
						if ((grid[row + 1][column] == '~') && grid[row + 2][column] == '~' && (grid[row][column] == '~')
								&& (row <= 7) && (row + 1 <= 7) && (row + 2 <= 7)) {

							for (int x = 0; x < cruiserSize; x++) {

								grid[row + x][column] = 'C';

							}
							cruiserShip++;
						} else {
							System.out.println("Ships overlapping ");

						}
					} else { // if any character other than v or h are
								// inputed
						System.out.println("Invalid orientation entered");

					}

					board.displayShipPosition(grid);

				} while ((row < 0) || (row > 7) && (column < 0) || (column > 7));// will
																					// keep
																					// asking
																					// for
																					// coordinates
																					// till
																					// valid
																					// coordinates
																					// are
																					// inputed

			}

		} catch (Exception e) {
			System.out.println("Invalid coordinates ");
			menu.processUserChoices();
		}
	}
	/**
	 * Places the Destroyer in the versus computer mode
	 * @param grid
	 */

	public static void placeDestroyer(char[][] grid) {
		Scanner input = new Scanner(System.in);
		int destroyerSize = 2;

		int destroyer = 1; // destroyer count
		int row = 0, column = 0;
		char pos; // determines horizontal or vertical position

		try {
			while (destroyer < 4) // creating 3 destroyers; count starts from 1

			{
				do {
					System.out.println("Enter a valid row to place destroyer" + destroyer + "(1 to 8)");
					row = input.nextInt();
					row--;
					System.out.println("Enter a valid column to place destroyer" + destroyer + "(1 to 8)");
					column = input.nextInt();
					column--;
					Scanner input1 = new Scanner(System.in);
					System.out.println("Enter the orientation to place the ship(Vertical=v or Horizontal=h)");

					pos = input1.next(".").charAt(0); // only allows a single
														// char input

					if (pos == 'H' || pos == 'h') {
						if (column <= 7 && column + 1 <= 7 && grid[row][column] == '~'
								&& grid[row][column + 1] == '~') {

							for (int x = 0; x < destroyerSize; x++) {

								grid[row][column + x] = 'D';
							}
							destroyer++;

						} else {
							System.out.println("Invalid coordinates ");

						}
					} else if (pos == 'v' || pos == 'V') {
						if ((grid[row + 1][column] == '~') && (grid[row][column] == '~') && (row <= 7)
								&& (row + 1 <= 7)) {

							for (int x = 0; x < destroyerSize; x++) {

								grid[row + x][column] = 'D';

							}
							destroyer++;
						} else {
							System.out.println("Ships overlapping ");

						}
					} else { // if any character other than v or h are
								// inputed
						System.out.println("Invalid orientation entered");
						menu.processUserChoices();
					}

					board.displayShipPosition(grid);

				} while ((row < 0) || (row > 7) && (column < 0) || (column > 7));// will
																					// keep
																					// asking
																					// for
																					// coordinates
																					// till
																					// valid
																					// coordinates
																					// are
																					// inputed

			}

		} catch (Exception e) {
			System.out.println("Invalid coordinates ");
			placeDestroyer(grid);
		}
	}
	/**
	 * Places the Submarine in the versus computer mode
	 * @param grid
	 */

	public static void placeSubmarines(char[][] grid) {
		Scanner input = new Scanner(System.in);
		int subCount = 1; // submarine count; starts from 1
		int subSize = 1; // SUBMARINE SIZE
		char pos; // determines horizontal or vertical position
		int row, column;

		try {
			while (subCount < 5) // creating 4 submarines; count starts from 1

			{
				do {
					System.out.println("Enter a valid row to place submarine" + subCount + "(1 to 8)");
					row = input.nextInt();
					row--;
					System.out.println("Enter a valid column to place submarine" + subCount + "(1 to 8)");
					column = input.nextInt();
					column--;

					if (grid[row][column] == '~') {

						for (int x = 0; x < subSize; x++) {

							grid[row][column + x] = 'S';
						}
						subCount++;

					} else {
						System.out.println("Ships overlapping. ");

					}

					board.displayShipPosition(grid);

				} while ((row < 0) || (row > 7) && (column < 0) || (column > 7)); // will
																					// keep
																					// asking
																					// for
																					// coordinates
																					// till
																					// valid
																					// coordinates
																					// are
																					// inputed

			}

		} catch (Exception e) {
			System.out.println("Invalid coordinates ");

		}

	}
	/**
	 * Determines the gameplay in the versus computer mode.
	 * Shots are randomly fired by the program, which are then determined if their a hit or a miss.
	 * The method compares the score of both the user and the program to determine the winner.
	 * @param grid
	 */

	public static void AI(char[][] grid1, int compScore, int playerScore) {
		char[][] grid = new char[8][8];
		// determines the number of torpedos left and hits made
		int comTorps = 0;
		int comHits = 0;
		int hit = 0; // hits needed for the AI to win

		for (int row = 0; row < grid1.length; row++) {

			for (int column = 0; column < grid1.length; column++) {
				if (grid1[row][column] == 'B' || grid1[row][column] == 'C' || grid1[row][column] == 'S'
						|| grid1[row][column] == 'D') {
					comTorps++;

				}
				if (grid1[row][column] == 'B' || grid1[row][column] == 'C' || grid1[row][column] == 'S'
						|| grid1[row][column] == 'D') {
					hit++;

				}
			}
		}

		// continues the game when the is remaining torpedoes and there
		// are still ships unsunk.
		while (comTorps > 0 && comHits <= hit) {

			board.displaygrid(grid1);

			try {

				int row, column;
				System.out.println("Computer have: " + comTorps + " torpedos left!");
				row = (int) (Math.random() * 6 + 1);
				System.out.println("Computer has fired in row: " + row);

				column = (int) (Math.random() * 6 + 1);
				System.out.println("Computer has fired in column: " + column);

				if ((grid1[row - 1][column - 1] == 'B') || (grid1[row - 1][column - 1] == 'C')
						|| (grid1[row - 1][column - 1] == 'S') || (grid1[row - 1][column - 1] == 'D')) {
					comHits++;
					System.out.println("~~~~~~~ HIT ~~~~~~~");
					grid1[row - 1][column - 1] = '!';
					comTorps--;
					compScore++;

				} else {
					System.out.println("~~~~~~~ MISS ~~~~~~~");
					grid1[row - 1][column - 1] = 'M';
					comTorps--;
				}

				board.displaygrid(grid1);
				board.splitter();

			} catch (Exception e) {
				System.out.print("Invalid input");

			}

		}
		if (comTorps < 1) {
			System.out.println("Computer has lost all their torpedos");
		} else if (comHits >= hit) {
			System.out.println("You have lost to the computer, Thanks for playing!");
		}

		board.creategrid(grid);
		gameManager.createShip(grid);
		int torps = 21;
		int hits = 0;
		// continues the game when the is remaining torpedoes and there
		// are still ships unsunk.
		while (torps > 0 && hits <= 21) {
			board.displaygrid(grid);
			hits = gameManager.userFire(grid, hits, torps, playerScore);

			torps--;

		}

		if (compScore < playerScore) {
			System.out.println("You have beaten the computer, Thanks for playing!");
		} else {
			System.out.println("You have lost to the computer, Thanks for playing!");
		}
		board.displaygrid(grid);

	}
	
}
