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
		boolean optimized = false;
		boolean close = false;
		while(!close){
			System.out.println("Please enter a file name:");

			String filename = input.next();
			if(filename.equalsIgnoreCase("Close")){
				System.out.println("Goodbye!");
				close = true;
				continue;
			}
			if(filename.equalsIgnoreCase("Optimize")){
				optimized = !optimized;
				System.out.println("Optimized mode " + (optimized? "on" : "off"));
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
				int loopCount = 0;
				if(!optimized)
					if(Sudoku.Solve(SudokuMatrix))
					{
						long endTime = System.nanoTime();
						float duration = (endTime - startTime) / 1000000f;
						System.out.println("Solution found in " + duration + " milliseconds.");
						printSudoku(SudokuMatrix);
						loopCount = Sudoku.getAttempts();
					}
					else
						System.out.println("No solution found.");
				else
					if(OptimizedSudoku.Solve(SudokuMatrix)) //Code reptition for the ultra-lazy! 
					{
						long endTime = System.nanoTime();
						float duration = (endTime - startTime) / 1000000f;
						System.out.println("Solution found in " + duration + " milliseconds.");
						printSudoku(SudokuMatrix);
						loopCount = OptimizedSudoku.getAttempts();
					}
					else
						System.out.println("No solution found.");
				System.out.println("Algorythm went through " + loopCount + " cycles.");
				System.out.println("-----------------------");
			}



		}
	}


	private static void printSudoku(byte[][] Sudoku)
	{
		for(int line = 0; line < 9; line++)
		{
			System.out.print(line + ": [");
			for(int col = 0; col < 9; col++)
			{
				System.out.print(Sudoku[col][line] + " ");
			}
			System.out.print("]\n");
			System.out.flush();
		}
	}

}
