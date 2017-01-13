import java.util.Scanner;
public class Woo{

    /*
      This stores the ANSI "Escape" character and the left bracket for use later
      Info on what you can do with it and how to use it below:
      http://ascii-table.com/ansi-escape-sequences.php
    */
    public static final String esc = (char)27 + "[";


    public static String arrToStr( Gem[][] arr ){
        String retStr = "";
        for( Gem[] row: arr ){
            for( Gem gem: row ){
                retStr += gem.toString() + " ";
            }
            retStr += "\n";
        }
        return retStr;
    }

    public static void populate( Gem[][] arr ){
        for( int i = 0; i < arr.length; i++ ){
            for( int j = 0; j < arr[i].length; j++ ){
                arr[i][j] = new Gem((int)(Math.random() * 6 + 31));
            }
        }
    }

    public static Gem [] [] newGame () {
	
	Gem [] [] board = new Gem [10] [10];
	populate (board);
	return board;
    }

    public static boolean swap (Gem [] [] arr, int row1, int column1, int row2, int column2) {
        boolean bool = true; 
        Gem Gem1 = arr[row1][column1];
        Gem Gem2 = arr[row2][column2];
        arr[row1][column1] = Gem2;
        arr[row2][column2] = Gem1;
        if (chainCheck (arr, row1, column1, row2, column2) == false) {
            arr[row1][column1] = Gem1;
            arr[row2][column2] = Gem2;
            bool = false;
        }
        return bool;
    }

    public static boolean chainCheck (Gem [][] arr, int row1, int column1, int row2, int column2) {
        boolean bool = false;
        //checks for vertical chain around gem1
        if (arr[row1][column1].equals (arr[row1 + 1][column1])) {
            if (arr [row1][column1].equals (arr[row1 + 2][column1])){
                bool = true;
            }
            else if (arr [row1][column1].equals (arr[row1 -1] [column1])) {
                bool = true;
            }
        }
        if (arr[row1][column1].equals (arr[row1 -1][column1])) {
            if (arr [row1][column1].equals (arr[row1 -2][column1])){
                bool = true;
            }
        }
        //checks for horizontal chain around gem1
        if (arr[row1][column1].equals (arr[row1][column1+1])) {
            if (arr [row1][column1].equals (arr[row1][column1+2])){
                bool = true;
            }
            else if (arr [row1][column1].equals (arr[row1] [column1-1])) {
                bool = true;
            }
        }

        if (arr[row1][column1].equals (arr[row1][column1-1])) {
            if (arr [row1][column1].equals (arr[row1][column1-2])){
                bool = true;
            }
        }
        //checks for vertical chain around gem2
        if (arr[row2][column2].equals (arr[row2 + 1][column2])) {
            if (arr [row2][column2].equals (arr[row2 + 2][column2])){
                bool = true;
            }
            else if (arr [row2][column2].equals (arr[row2 -1] [column2])) {
                bool = true;
            }
        }
        if (arr[row2][column2].equals (arr[row2 -2][column2])) {
            if (arr [row2][column2].equals (arr[row2 -2][column2])){
                bool = true;
            }
        }

        //checks for horizontal chain around gem2
        if (arr[row2][column2].equals (arr[row2][column2+1])) {
            if (arr [row2][column2].equals (arr[row2][column2+2])){
                bool = true;
            }
            else if (arr [row2][column2].equals (arr[row2] [column2-1])) {
                bool = true;
            }
        }

        if (arr[row2][column2].equals (arr[row2][column2-1])) {
            if (arr [row2][column2].equals (arr[row2][column2-2])){
                bool = true;
            }
        }
        return bool;

    }
	

    //public static void swap (Gem[] [] arr) {
	

    public static void main( String[] args ){
        System.out.print( esc + "2J" + esc + ";H" ); // 2J = Clear screen; ;H = move cursor to top left corner

        //Define 2d array for the gems and then print it
        //Gem[][] board = new Gem[10][10];
        //populate( board );

        Gem [] [] game = newGame();
        System.out.println(arrToStr( game ) + "\n"  );
        
        //Declare a scanner and a variable to hold values
        Scanner sc = new Scanner( System.in );
        String s;

        //Initial cursor points
        int row = 0;
        int col = 0;

        while( true ){
            System.out.print( esc + "K" + "Input (w/a/s/d): " ); // K = Clear line; print prompt
            s = sc.nextLine(); //Get input
            System.out.print( esc + "1A" ); // 1A = Move cursor back up 1 row (since sc.nextLine() moves it down one row)
            
            System.out.print( esc + "s" ); // s = Save position of prompt for later
            //Todo: make a method that overwrites the gem at the current or given position. Should replace the next two lines
            System.out.print( esc + ( row + 1 ) + ";" + ( 2 * col + 1 ) + "H" ); // line|col|H = Move to position of current gem object
            System.out.print( esc + "0m" + game[row][col] ); //Overwrite it with no background color

            //This block handles input
            if( s.equals("w") && row > 0 ){
                row--;
            } else if( s.equals("s") && row < game.length - 1 ){
                row++;
            } else if( s.equals("a") && col > 0 ){
                col--;
            } else if( s.equals("d") && col < game[row].length - 1 ){
                col++;
            } else {
                System.out.print( esc + "u" + "Input or move invalid. Press enter to continue. " );
                sc.nextLine();
                System.out.print( esc + "1A" ); 
            }


            System.out.print( esc + ( row + 1 ) + ";" + ( 2 * col + 1 ) + "H" ); //Move to position of current gem object
            System.out.print( esc + "47m" + game[row][col] + esc + "0m" ); //Overwrite it with a background color
            
            System.out.print( esc + "u" ); //Move cursor back to prompt
	    }
    }
}
	
	
