import java.io.File;
import java.util.Scanner;
import java.util.Timer;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to the Sudoku solver!");
		Scanner input = new Scanner(System.in);
		boolean close = false;
		while(!close){
			System.out.println("Please enter a file name:");

			String filename = input.next();
			if(filename.equalsIgnoreCase("Close"))
			{
				System.out.println("Goodbye!");
				close = true;
				continue;
			}
			File file = new File(filename);
			if(!file.exists())
			{
				System.out.println("File not found: " + file.getAbsolutePath());
				continue;
			}
			else
			{
				SudokuLoader loader = new SudokuLoader(file);
				byte[][] SudokuMatrix = loader.read();
				if(SudokuMatrix == null)
				{
					System.out.println("Sudoku matrix invalid");
					continue;
				}
				//Load file!

				/*//Test array
				byte[][] TestSudoku = new byte[][]
						{
						{0,3, 0,6, 0,5, 0, 0, 0},
						{6, 0, 0, 0,9, 0, 0, 0,2},
						{ 0,7, 0,1, 0, 0, 0, 0,6},
						{ 0,9, 0, 0, 0, 0, 0, 0, 0},
						{8,1, 0, 0,5, 0, 0,6,9},
						{ 0, 0, 0, 0, 0, 0, 0,8, 0},
						{4, 0, 0, 0, 0,3, 0,2, 0},
						{9, 0, 0, 0,2, 0, 0, 0,5},
						{ 0, 0, 0,9, 0,8, 0,3, 0},
						};*/
				System.out.println("Loaded Sudoku: ");
				printSudoku(SudokuMatrix);
				System.out.println("Beginning solve.");
				long startTime = System.nanoTime();
				if(Sudoku.Solve(SudokuMatrix))
				{
					long endTime = System.nanoTime();
					long duration = endTime - startTime;
					System.out.println("Solution found in " + duration + " nanoseconds");

					printSudoku(SudokuMatrix);
				}
				else
					System.out.println("No solution found.");
			}}
	}


	private static void printSudoku(byte[][] Sudoku)
	{
		for(byte[] line : Sudoku)
		{
			System.out.print('[');
			for(byte b : line)
			{
				System.out.print(b + " ");
			}
			System.out.print("]\n");
			System.out.flush();
		}
	}

}
