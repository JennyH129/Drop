import java.util.ArrayList;
public class ExplodeGem extends SuperGem {
    public ArrayList <Integer[]> special (Gem [][] board, int row, int col) {
	ArrayList <Integer []> arr = new ArrayList <Integer[]> ();
	//If the special gem is in a corner of the board, add the three gems around it and the gem itself
	if (row == 0 && col == 0) {
	    for (int i = 0; i < 2; i ++) {
		for (int j = 0; j < 2; j ++) {
		    arr.add (new Integer [] {i, j});
		}
	    }
	}
	else if (row == 9 && col == 9) {
	    for (int i = 9; i > 7; i --) {
		for (int j = 9; j > 7; j --) {
		    arr.add (new Integer [] {i, j});
		}
	    }
	}
	else if (row == 0 && col == 9) {
	    for (int i = 0; i < 2; i ++ ) {
		for (int j = 0; j > 7; j --) {
		    arr.add (new Integer [] {i, j});
		}
	    }
	}
	else if (row == 9 && col == 0) {
	    for (int i = 9; i > 7; i --) {
		for (int j = 0; j < 2; j ++) {
		    arr.add (new Integer [] {i, j});
		}
	    }
	}
	
	//If the special gem is on the side of the board, add the 5 gems around it and the gem itself
	else if (row == 0) {
	    for (int i = 0; i < 2; i ++ ) {
		for (int j = -1; j < 2; j ++)  {
		    arr.add (new Integer [] {i, col + j}) ;
		}
	    }
	}

	else if (row == 9) {
	    for (int i = 9; i > 7; i --) {
		for (int j = -1; j < 2; j ++) {
		    arr.add (new Integer [] { i, col + j} );
		}
	    }
	}

	else if ( col == 0) {
	    for (int i = -1; i < 2; i ++ ) {
		for (int j = 0; j < 2; j ++ ) {
		    arr.add (new Integer [] { row + i, j} ) ;
		}
	    }
	}

	else if (col == 9) {
	    for (int i = -1; i < 2; i ++) {
		for (int j = 9; j > 7; j --)  {
		    arr.add (new Integer [] { row + i, j } );
		}
	    }
	}
	
	//If the special gem is in the middle of the board, add the 8 gems around it and the gem itself

	else {
	    for (int i = -1; i < 2; i ++) { 
		for (int j = -1;j < 2; j ++) {
		    arr.add (new Integer [] {row+ i, col + j} );
		}
	    }
	}
	return arr; 
    }

    public String toString () {
        return Screen.ESC + color + "mo" + Screen.ESC + "0m";
    }
    
}
	    
