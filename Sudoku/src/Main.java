import java.util.Timer;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Load file!

		//Test array
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
				};

		System.out.println("Beginning solve.");
		long startTime = System.nanoTime();
				if(Sudoku.Solve(TestSudoku))
		{
			long endTime = System.nanoTime();
			long duration = endTime - startTime;
			System.out.println("Solution found in " + duration + " nanoseconds");

			printSudoku(TestSudoku);
		}
		else
			System.out.println("No solution found.");

		//Sudoku.Solve(new Byte[][]); //Solves a col/line Sudoku double array.
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
