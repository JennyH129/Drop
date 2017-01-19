import java.util.ArrayList;
public class ColorGem extends SuperGem {
    
    
    public abstract ArrayList <Integer[]> special (Gem[][] board, Gem selected){
	ArrayList <Integer[]> arr = new ArrayList <Integer[]> ();
	for ( i = 0; i < board.size () -1; i ++ ) {
	    for (j = 0; j <board.get(i).length -1; j ++) {
		if ((board.get(i))[j] == selected ) {
		    arr.add (//[row, column]
			     );
		}
	    }
	}
    }
    
} 
    
