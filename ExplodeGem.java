import java.util.ArrayList;
public class ExplodeGem extends SuperGem {
    public ArrayList <Integer[]> special (Gem [][] board, int row, int col) {
	ArrayList <Integer []> arr = new ArrayList <Integer[]> ();
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
	
		    

	else {
	    for (int i = -1; i < 2; i ++) { 
		for (int j = -1;j < 2; j ++) {
		    arr.add (new Integer [] {row+ i, col + j} );
		}
	    }
	}
	return arr; 
    }
}
	    
