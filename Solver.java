public class Solver {
	
	int sudoku[][] = {
			{0,0,0, 0,0,0, 0,0,0},
			{0,0,0, 0,0,0, 0,0,0},
			{0,0,0, 0,0,0, 0,0,7},
			
			{0,4,0, 5,0,0, 0,8,2},
			{0,0,5, 0,0,0, 4,0,0},
			{8,7,0, 0,0,9, 0,3,0},
			
			{2,0,0, 0,0,7, 1,6,0},
			{3,6,0, 1,0,5, 0,0,4},
			{0,0,4, 0,0,0, 0,0,0}
	};
	
	int givenProb[][] = new int[9][9];
	
	int backTrackCount = 0;
	
	public Solver() {
		
		try{
			
			for(int i = 0 ; i < 9 ; i++){
				for(int j = 0 ; j < 9 ; j++){
						givenProb[i][j] = sudoku[i][j];
					}
				}
			
			if(validateSudoku()){
				sudoku = dieForSolution(sudoku);
				if(validateSudoku()){
					printSudoku(sudoku);
					System.out.println("Back track count "+backTrackCount);	
				}else{
					System.out.println("Not able to solve");
				}
				
			}else{
				System.out.println("Invalid sudoku given");
			}
			
		}catch(Exception error){
			System.out.println("Cann't be solved");
			error.printStackTrace();
		}
	}
	
	private int[][] dieForSolution(int[][] expsudoku) {
		
		while(true){
			
			// Check for 1st zeroth element
			int i = 0 , j = 0;
			for(i = 0; i < 9 ; i ++){
				for(j = 0 ; j < 9 ; j ++){
					if(expsudoku[i][j]==0) break;
				}
				if(j<9) break;
			}
			
			if(i==9 && j==9)  break; // Cool buddy u got it :)
			
			//System.out.println("i = "+i+" j = "+j);
			
			int possilbleOutCome = getPossibleOutcome(0,expsudoku,i,j);
			
			if(possilbleOutCome==-1){ // No possible out come found back track its previous assumed value
				expsudoku = backTrack(expsudoku,i,j);
			}else{
				expsudoku[i][j] = possilbleOutCome;
			}
			
			
		}
		
		return expsudoku;
		
			
	}
	
	public int[][] backTrack(int[][] expsudoku,int i, int j){
		
		while(true){
			backTrackCount++;
			
			do{
				j = j -1;
				if(j < 0){
					j = 8;
					i = i - 1;
					if(i < 0) i = 0;
				}
				
			}while(givenProb[i][j]!=0); // Step back to previous program filled assumed position
			
			int possilbleOutCome = getPossibleOutcome(expsudoku[i][j],expsudoku,i,j);
			
			if(possilbleOutCome==-1){ // No possible out come found back track its previous assumed value
				expsudoku[i][j] = 0;
				//return backTrack(expsudoku,i,j);
			}else{
				expsudoku[i][j] = possilbleOutCome;
				break;
			}
			
		}
		
		
		
		return expsudoku;
	}
	

	public void printSudoku(int sudoku[][]){
		for(int i = 0 ; i < 9 ; i++){
			for(int j = 0 ; j < 9 ; j++){
					System.out.print(sudoku[i][j]+" ");
				}
			System.out.print("\n");
			}
		
		
	}
	
	public boolean validateSudoku(){
		for(int i = 0 ; i < 9 ; i++){
			for(int j = 0 ; j < 9 ; j++){
				int value = sudoku[i][j]==0 ? -1 : sudoku[i][j]; 
				if(!validateRowsNColumns(value, i, j) || !validateSubMatrix(value, i, j)) return false;
			}
		
		}
		return true;
		
	}
	
	public boolean validateRowsNColumns(int value , int row, int column){
		
		int presence = 0;
		
		for(int i = 0 ; i < 9 ; i ++){
			if(sudoku[row][i] == value) presence++;
			if(sudoku[i][column] == value) presence++;
			if(presence>2) return false; // Given that the given value should be present only twice in both row & column wise
		}
		
		return true;
	}
	
	public boolean validateSubMatrix(int value , int row, int column){
		
		int presence = 0;
	
			
		for(int rowBound = (row/3) * 3 , i =  rowBound; i < rowBound+3 ; i ++){
			for(int colBound = (column/3) * 3, j = colBound ; j < colBound+3 ; j ++){
				if(sudoku[i][j]==value) presence++;
				if(presence>1) return false;
			}
		}
			
		
		
		return true;
	}
	
	public int getPossibleOutcome(int previousOutCome , int[][] expsudoku, int row , int column){
		
		boolean temp[] = new boolean[9];
		int possibleValue = -1;
		
		
		// Marking Rows and column values
		for(int i = 0 ; i < 9 ; i ++){
			if(expsudoku[row][i] !=0 ) temp[expsudoku[row][i] - 1] = true;
			if(expsudoku[i][column] !=0 ) temp[expsudoku[i][column] - 1] = true;
		}
		
		// Marking sub matrix values
		for(int rowBound = (row/3) * 3 , i =  rowBound; i < rowBound+3 ; i ++){
			for(int colBound = (column/3) * 3, j = colBound ; j < colBound+3 ; j ++){
				if(expsudoku[i][j]!=0) temp[expsudoku[i][j] - 1] = true;
			}
		}

		
		for(int i = 0 ; i < 9 ; i ++ ){
			
			if(temp[i]!=true && (i+1) > previousOutCome){
				possibleValue = i + 1 ;
				break;
			}
		}
		
		return possibleValue;
	}
	
	public static void main(String args[]){
		new Solver();
	}
	

}
