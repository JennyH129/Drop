import java.util.Scanner;
import java.util.ArrayList;
public class Woo{

    //Waits the given number of milliseconds
    public static void wait( int time ){
        try{ 
            Thread.sleep( time );
        } catch( Exception e ){

        }
    }

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

    //destroys the given chains
    public static void destroyChain( Gem[][] game, ArrayList<Integer[]> toDestroy ){
        for( Integer[] i: toDestroy ){
            game[ i[0] ][ i[1] ] = new Gem();
        }
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

        //Initial values
        int numMoves = 0;
        int numSelectedGems = 0;
        int points = 0;
        //Initial cursor points
        int row = 0;
        int col = 0;

        printHelp();
        Screen.promptUser( sc ); //Press enter to continue

        //Clear screen, initialize 2d Gem array, and print board
        Screen.clear();
        Gem [] [] game = newGame();
        Screen.updateBoard( game, numMoves, points );

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

                //Unhighlight the gem the cursor used to be on
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
                    //If you select the same gem again, deselect it
                    if( numSelectedGems == 1 && sGem[0][0] == row && sGem[0][1] == col ){ 
                        numSelectedGems--;
                        game[ sGem[0][0] ][ sGem[0][1] ].select( false );
                    } else { //Else add the selected gem and select it
                        sGem[ numSelectedGems ][0] = row;
                        sGem[ numSelectedGems ][1] = col; 
                        numSelectedGems++;
                        game[row][col].select( true ); //force the gem to stay highlighted
                    }
                } else { //Invalid input so print error message
                    Screen.load(); 
                    System.out.print( "Input or move invalid. Press enter to continue. " );
                    Screen.promptUser( sc );
                }
                
                //Highlight the gem the cursor is over
                game[row][col].highlight( true );
                //Update gem
                Screen.updateGem( game, row, col );
            }

            numSelectedGems = 0;
            //Deselect and unhighlight the selected gems
            game[ sGem[0][0] ][ sGem[0][1] ].turnOff();
            Screen.updateGem( game, sGem[0][0], sGem[0][1] );
            game[ sGem[1][0] ][ sGem[1][1] ].turnOff();
            Screen.updateGem( game, sGem[1][0], sGem[1][1] );

            if( isNextTo( sGem[0][0], sGem[0][1], sGem[1][0], sGem[1][1] ) ){

                swap( game, sGem[0][0], sGem[0][1], sGem[1][0], sGem[1][1] );
                //Array holding the coordinates of gems to be destroyed
                ArrayList<Integer[]> toDestroy = ChainItems.chainItems( game );
                //Add 1 point for each gem to be destroyed
                points += toDestroy.size();

                //If chain formed
                if( toDestroy.size() >= 3 ){
                    //Highlight the gems that will be destroyed
                    Gem.highlight( game, toDestroy, true );
                    Screen.updateBoard( game, numMoves, points );
                    wait( 1000 ); //wait 1 second (1000 milliseconds) before moving on to destroying the gems

                    //Destroy chain and increment move counter
		            destroyChain( game, toDestroy );
                    numMoves++;

                    //This block handles new chains formed by the destruction of old chains
                    while( ( toDestroy = ChainItems.chainItems( game ) ).size() >= 3  ){
                        //Highlight gems that will be destroyed
                        Gem.highlight( game, toDestroy, true );
                        Screen.updateBoard( game, numMoves, points );
                        wait( 1000 );

                        points += toDestroy.size();
                        destroyChain( game, toDestroy );
                    }
                } else { //If no chain formed, swap back the gems
                    swap( game, sGem[0][0], sGem[0][1], sGem[1][0], sGem[1][1] );   
                }

                //Highlight the new gem that the cursor is over
                game[row][col].highlight( true );
                //Update board
                Screen.updateBoard( game, numMoves, points );

            } else { //If selected gems not next to each other
                //Make sure that the last selected gem is highlighted so that the cursor is visible
                game[ sGem[1][0] ][ sGem[1][1] ].highlight( true );
                Screen.updateGem( game, sGem[1][0], sGem[1][1] );

                Screen.load();
                System.out.print( "Selected gems not next to each other. Press enter to continue." );
                Screen.promptUser( sc );
            }

        }
        System.out.print( "\nGame over! Press enter to exit. " );
        Screen.promptUser( sc );
    }
}
	
	
