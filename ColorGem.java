import java.util.ArrayList;
public class ColorGem extends SuperGem {
    
    public ColorGem(){
        color = (int)(Math.random() * 6 + 31);
        character="x";
    }

    public ColorGem(int newColor){
        color = newColor;
        character="x";
    }

    public ArrayList <Integer[]> special (Gem[][] board, int row, int col){
	ArrayList <Integer[]> arr = new ArrayList <Integer[]> ();
	//traverses the entire board to find gems of the same color as the special gem 
	for ( int i = 0; i < board.length -1; i ++ ) {
	    for (int j = 0; j <board[i].length -1; j ++) {
		if (board[i][j].equals( board [row][col]) ) {
		    arr.add (new Integer [] {i, j}); 			    
		}
	    }
	}
	return arr; 

    }
    
} 
    
