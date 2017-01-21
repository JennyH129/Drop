import java.util.Scanner;
import java.util.ArrayList;
public class Woo{

    //Returns string version of the game board
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

    //Populates the game board and make sures it doesn't start with chains
    public static void populate( Gem[][] arr ){
        //Initial population
        int gemType = 0; 
        for( int i = 0; i < arr.length; i++ ){
            for( int j = 0; j < arr[i].length; j++ ){
                gemType = (int) (Math.random() * 20);
                if (gemType == 1) {
                    arr[i][j] = new CrossGem();
                }
                else if (gemType == 2) {
                    arr [i][j] = new ColorGem();
                }
                else if (gemType == 3) {
                    arr [i][j] = new ExplodeGem ();
                } 
                else {
                    arr[i][j] = new Gem();
                }
            }
        }
        while( destroyChain(arr) ){
            //This will remove chains that the game starts with
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
        Gem Gem1 = arr[row1][col1];
        Gem Gem2 = arr[row2][col2];
        arr[row1][col1] = Gem2;
        arr[row2][col2] = Gem1;
    }

    /* destroyChain( Gem[][] game ) -- destroys the chains in a given board
     * Precond: game is not null
     *
     * Post: If chains found: destroy the chains by replacing them with new random gems. Then return true.
     *       Else: return false.
     */
    public static boolean destroyChain( Gem[][] game ){
        //Build an array of the positions of the gems that will be destroyed later
        ArrayList<Integer[]> toDestroy = ChainItems.chainItems( game );

        if( toDestroy.size() >= 3 ){
            for( Integer[] i: toDestroy ){
                game[ i[0] ][ i[1] ] = new Gem();
            }
            return true;
        }
        return false;
    }

    public static void printHelp(){
        Screen.clear();
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
    }

    public static void main( String[] args ){
        //Declare a scanner and a variable to hold values
        Scanner sc = new Scanner( System.in );
        String s;

        int numMoves = 0;
        int numSelectedGems = 0;
        int points = 0;
        //Initial cursor points
        int row = 0;
        int col = 0;

        printHelp();
        //Press enter to continue
        Screen.promptUser( sc );

        //Clear screen and initialize 2d Gem array
        Screen.clear();
        Gem [] [] game = newGame();
        System.out.print(arrToStr( game ) + "\n\n"  );
        System.out.println( "Moves left: " + (10 - numMoves) );
        System.out.println( "Points: " + points );


        /* sGem is a 2d array holding the coordinates of selected gems
           Each first level element represents the coordinates of a single gem. The 0th item is the row, the 1st item is the column.
           Example:
           [ 
            Gem 0: [ row, col ]
            Gem 1: [ row, col ]
           ]
        */
        int[][] sGem = new int[2][2];

        //Highlight starting point
        game[row][col].highlight( true );
        Screen.updateGem( game, row, col );

        while( numMoves < 10 ){


            while( numSelectedGems < 2 ){
                //Get user input
                s = Screen.promptUser( "Input (w/a/s/d/e): ", sc );

                game[row][col].highlight( false );
                Screen.updateGem( game, row, col );

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
                    if( numSelectedGems == 1 && sGem[0][0] == row && sGem[0][1] == col ){ //If you select the same gem again, deselect it
                        numSelectedGems--;
                        game[ sGem[0][0] ][ sGem[0][1] ].select( false );
                    } else {
                        sGem[ numSelectedGems ][0] = row;
                        sGem[ numSelectedGems ][1] = col; 
                        numSelectedGems++;
                        //force the gem to stay highlighted
                        game[row][col].select( true ); 
                    }
                }
                else {
                    Screen.load(); 
                    System.out.print( "Input or move invalid. Press enter to continue. " );
                    Screen.promptUser( sc );
                }
                
                if( !(s.equals("e")) ){
                    game[row][col].highlight( true );
                }
                Screen.updateGem( game, row, col );
            }

            numSelectedGems = 0;
            //Deselect and unhighlight selected gems
            game[ sGem[0][0] ][ sGem[0][1] ].turnOff();
            game[ sGem[1][0] ][ sGem[1][1] ].turnOff();
            Screen.updateGem( game, sGem[0][0], sGem[0][1] );
            Screen.updateGem( game, sGem[1][0], sGem[1][1] );

            if( isNextTo( sGem[0][0], sGem[0][1], sGem[1][0], sGem[1][1] ) ){

                swap( game, sGem[0][0], sGem[0][1], sGem[1][0], sGem[1][1] );
                points += ChainItems.chainItems( game ).size();

                if( destroyChain(game) ){
		    
                    numMoves++;
                    while( destroyChain(game) ){
                        //remove the new chains that are formed by old chains being replaced
                    }
                } else {
                    swap( game, sGem[0][0], sGem[0][1], sGem[1][0], sGem[1][1] );   
                }

                //Update board
                Screen.clear();
                System.out.print(arrToStr( game ) + "\n\n"  );
                System.out.println( "Moves left: " + (10 - numMoves) );
                System.out.println( "Points: " + points );

                game[row][col].highlight( true );
                Screen.updateGem( game, row, col );
            } else {
                Screen.load();
                System.out.print( "Selected gems not next to each other. Press enter to continue." );
                Screen.promptUser( sc );
            }


        }
        System.out.print( "\nGame over! Press enter to exit. " );
        Screen.promptUser( sc );
    }
}
	
	
