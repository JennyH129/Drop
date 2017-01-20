import java.util.ArrayList;
public class ColorGem extends SuperGem {
    
    
    public ArrayList <Integer[]> special (Gem[][] board, int row, int col){
	ArrayList <Integer[]> arr = new ArrayList <Integer[]> ();
	for ( int i = 0; i < board.length -1; i ++ ) {
	    for (int j = 0; j <board[i].length -1; j ++) {
		if (board[i][j].equals( board [row][col]) ) {
		    arr.add (new Integer [] {i, j}); 			    
		}
	    }
	}
	return arr; 

    }

    public String toString () {
	return Woo.esc + color + "mx" + Woo.esc + "0m";

    }
    
} 
    
