/**
 * 
 */
package battleship;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ian Chow  
 *
 */
public class board {


	static char[][] grid = new char[8][8]; //board size

	public board() {
		// TODO Auto-generated constructor stub

	}

	/**
	 * method used to split the grids to separate each grid displayed for a more 
	 * aesthetically pleasing design
	 */
	public static void splitter() {
		System.out.println("_____________________________________");
		System.out.println(""); // creates an extra line of space between the
								// splitter line & grid
	}
 /**
  * creates the grid to be populated with ships 
  * @param grid
  */
	public static void creategrid(char[][] grid) {

		for (int row = 0; row < grid.length; row++) {

			for (int column = 0; column < grid.length; column++) {

				grid[row][column] = '~';
			}
			System.out.print("");
		}

	}
	/**
	 * hides the ships in the grid to be displayed to the user 
	 * @param grid
	 * @return
	 */

	public static char[][] displaygrid(char[][] grid) {
		splitter();
		System.out.print("  1 2 3 4 5 6 7 8"); // spacing must not be altered as
												// it is aligned with the grid
		System.out.println();
		for (int row = 0; row < grid.length; row++) {
			System.out.print(row + 1); // prints grids row number before the
										// actual grid; +1 is used to start
										// interval from 1
			// hides the type of ship on the grid
			for (int column = 0; column < grid.length; column++) {
				if (grid[row][column] == 'S') {
					System.out.print(" " + "~");
				} else if (grid[row][column] == 'B') {
					System.out.print(" " + "~");
				} else if (grid[row][column] == 'C') {
					System.out.print(" " + "~");
				} else if (grid[row][column] == 'D') {
					System.out.print(" " + "~");
				} else {
					System.out.print(" " + grid[row][column]);
				}
			}
			System.out.println("");
		}
		return grid;

	}

	/**
	 * Method is used to show the position of the ships
	 * 
	 * @param grid
	 */
	public static void displayShipPosition(char[][] grid) {
		splitter();
		System.out.print("  1 2 3 4 5 6 7 8"); // spacing must not be altered as
												// it is aligned with the grid
		System.out.println();
		for (int row = 0; row < grid.length; row++) {
			System.out.print(row + 1); // prints s row number before the
										// actual ; +1 is used to start
										// interval from 1

			for (int column = 0; column < grid[0].length; column++) {
				if (grid[row][column] == 'S') {
					System.out.print(" S");
				} else if (grid[row][column] == 'B') {
					System.out.print(" B");
				} else if (grid[row][column] == 'C') {
					System.out.print(" C");
				} else {
					System.out.print(" " + grid[row][column]);
				}
			}
			System.out.println(" ");
		}
	}
	/**
	 * save game method which save the board of the current new game as a text file
	 * @param grid
	 */

	public static void saveGame(char[][] grid) {

		try {
			FileOutputStream outputStream;
			PrintWriter printWriter = null;
			outputStream = new FileOutputStream("game.txt"); //creates this file if it is not in the directory or overwrites if it is present
			printWriter = new PrintWriter(outputStream);

			

			for (int row = 0; row < grid.length; row++) {
				
				

				for (int column = 0; column < grid.length; column++) {

					printWriter.print(grid[row][column]); // save exactly to prevent any white spaces when loading the game

				}

				printWriter.println(""); // loads second row on the next line

			}
			printWriter.close(); // closes the stream
		} catch (Exception e) {

			System.out.println(e);
		}
	}
	/**
	 * loads the save board from the text file 
	 * @param grid
	 */

	public static void loadGame(char[][] grid) {
		char[][] boardArray = new char[8][8];
		ArrayList<char[]> boardList = new ArrayList<>(); //array list used for a dynamic array as board size is undeclared.

		try {
			FileReader fileReader;
			BufferedReader bufferedReader;
			String nextLine;

			fileReader = new FileReader("game.txt");  //loads the predetermined text file 
			bufferedReader = new BufferedReader(fileReader);

			nextLine = bufferedReader.readLine();
			// while the next line is not empty
			while (nextLine != null) {

				// add the line to an array list
				boardList.add(nextLine.toCharArray());
				// get next line
				nextLine = bufferedReader.readLine();

			}

			fileReader.close();

			// convert array list to arrays
			boardArray = boardList.toArray(new char[8][8]);

			char[][] newBoardArray = new char[8][8];

			for (int row = 0; row < grid.length; row++) {

				for (int column = 0; column < grid.length; column++) {

					newBoardArray[row][column] = boardArray[row][column];
				}

			}

			// sets the grid(actual game board) with the save
			// board(newBoardArray)
			for (int row = 0; row < grid.length; row++) {

				for (int column = 0; column < grid.length; column++) {

					grid[row][column] = newBoardArray[row][column];

				}

			}
		} catch (Exception e) {

			System.out.println("Error:" + e);
			menu.processUserChoices();

		}

	}
}
