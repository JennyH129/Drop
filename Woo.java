import java.util.Scanner;
import java.util.ArrayList;
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
                arr[i][j] = new Gem();
            }
        }
    }

    public static Gem [] [] newGame () {

        Gem [] [] board = new Gem [10] [10];
        populate (board);
        return board;
    }

    public static boolean isNextTo (int row1, int col1, int row2, int col2) {
	boolean bool = false;
	if (row1 == row2) {
	    if ((col1 == col2 +1) || (col1 == col2-1)) {
		bool = true;
	    }
	}
	if (col1==col2) {
	    if ((row1 == row2+1) || (row1 == row2-1)) {
		bool = true;
	    }
	}
	return bool;
    }

    public static void swap (Gem [] [] arr, int row1, int col1, int row2, int col2) {
        boolean bool = true; 
        Gem Gem1 = arr[row1][col1];
        Gem Gem2 = arr[row2][col2];
        arr[row1][col1] = Gem2;
        arr[row2][col2] = Gem1;
    }

    public static void destroyChain(){
        /*
        ArrayList<Integer[]> toDestroy = chainItems( arr, row1, col1 );

        if( toDestroy.size() >= 3 ){
            for( Integer[] i: toDestroy ){
                arr[ i[0] ][ i[1] ] = new Gem();
            }
        } else {
            arr[row1][col1] = Gem1;
            arr[row2][col2] = Gem2;
            bool = false;
        }
        */
    }

    public static void main( String[] args ){
        //Declare a scanner and a variable to hold values
        Scanner sc = new Scanner( System.in );
        String s;

        //Print help info
        System.out.print( esc + "2J" + esc + ";H" ); // 2J = Clear screen; ;H = move cursor to top left corner
        String help =   " CONTROLS \n" 
            +           "   w e     Press w, a, s, or d and then hit enter to move your cursor.\n"  
            +           " a s d     Press e to select the current gem. Select two gems to swap them.\n"
            +           "           \n"
            +           " GAMEPLAY: Swaps are only allowed if they result in a chain of 3 gems*.\n"
            +           "           Get a chain of 3 gems in a row to destroy the chain. The gems will be replaced by new random gems.\n"
            +           "           Moves are only counted if they result in a valid swap. You have 4 swaps before the game ends.\n"
            +           "           \n"
            +           " Press enter to continue.";
        System.out.print( help );
        //Press enter to continue
        sc.nextLine();

        //Clear screen and initialize 2d Gem array
        System.out.print( esc + "2J" + esc + ";H" ); 
        Gem [] [] game = newGame();
        System.out.println(arrToStr( game ) + "\n"  );

        int numMoves = 0;
        int numSelectedGems = 0;
        //Initial cursor points
        int row = 0;
        int col = 0;

        //2d array holding the coordinates of selected gems
        //First levem elements
        int[][] sGem = new int[2][2];

        while( numMoves < 10 ){


            while( numSelectedGems < 2 ){
                //Get user input
                System.out.print( esc + "K" + "Input (w/a/s/d/e): " ); // K = Clear line; print prompt
                s = sc.nextLine(); //Get input
                System.out.print( esc + "1A" ); // 1A = Move cursor back up 1 row (since sc.nextLine() moves it down one row)

                //Update screen
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
                } else if( s.equals("e") ){
                    sGem[ numSelectedGems ][0] = row;
                    sGem[ numSelectedGems ][1] = col; 
                    numSelectedGems++;
                }
                else {
                    System.out.print( esc + "u" + "Input or move invalid. Press enter to continue. " );
                    sc.nextLine();
                    System.out.print( esc + "1A" ); 
                }

                System.out.print( esc + ( row + 1 ) + ";" + ( 2 * col + 1 ) + "H" ); //Move to position of current gem object
                System.out.print( esc + "47m" + game[row][col] + esc + "0m" ); //Overwrite it with a background color

                System.out.print( esc + "u" ); //Move cursor back to prompt
            }
            numSelectedGems = 0;

            if( isNextTo( sGem[0][0], sGem[0][1], sGem[1][0], sGem[1][1] ) ){
                swap( game, sGem[0][0], sGem[0][1], sGem[1][0], sGem[1][1] );

                //Build an array of the positions of the gems that will be destroyed later
                ArrayList<Integer[]> toDestroy = ChainItems.chainItems( game );

                if( toDestroy.size() >= 3 ){
                    numMoves++;
                    for( Integer[] i: toDestroy ){
                        int newCol = (int)(Math.random() * 6 + 31);
                        game[ i[0] ][ i[1] ] = new Gem();
                    }
                } else {
                    swap( game, sGem[0][0], sGem[0][1], sGem[1][0], sGem[1][1] );
                }

                //Update board
                System.out.print( esc + "2J" + esc + ";H" ); // 2J = Clear screen; ;H = move cursor to top left corner
                System.out.println(arrToStr( game ) + "\n"  );

            } else {
                System.out.print( esc + "u" + "Selected gems not next to each other. Press enter to continue." );
                sc.nextLine();
                System.out.print( esc + "1A" );
            }


        }
        System.out.print( "\nGame over! Press enter to exit. " );
        sc.nextLine();
    }
}
	
	
