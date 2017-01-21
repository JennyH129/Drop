import java.util.ArrayList;
public class CrossGem extends SuperGem{
    public ArrayList<Integer[]> special (Gem [] [] board, int row, int col) {
	ArrayList<Integer []> arr = new ArrayList <Integer[]> ();

        for (int i = 0; i < 10; i ++) {
	    //adds all the gems in the same row as the special gem 
	    arr.add(new Integer [] {row, i});
	    //adds all the gems in the same column as the special gem
	    arr.add (new Integer[] {i, col}); 
	}
	return arr;
    }


    public String toString () {
	return Screen.ESC + color + "m+" + Screen.ESC + "0m";
    } 

}
	
	    
	
