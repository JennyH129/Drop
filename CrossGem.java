import java.util.ArrayList;
public class CrossGem extends SuperGem{
    public ArrayList<Integer[]> special (Gem [] [] board, int row, int col) {
	ArrayList<Integer []> arr = new ArrayList <Integer[]> ();

        for (int i = 0; i < 10; i ++) {
	    arr.add(new Integer [] {row, i});
	    arr.add (new Integer[] {i, col}); 
	}
	return arr;
    }


    public String toString () {
	return Woo.esc + color + "m+" + Woo.esc + "0m";
    } 

}
	
	    
	
