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

    //================CHAIN CHECKING METHODS START================/*
    //Returns a list of the positions of chains
    public static ArrayList<Integer[]> chainItems( Gem[][] arr ){
        ArrayList<Integer[]> chain = chainItemsRow( arr );
        for( Integer[] i: chainItemsColumn( arr ) ){
            chain.add( i );
        }
        return chain;
    }

    //Returns horizontal chains
    private static ArrayList<Integer[]> chainItemsRow( Gem[][] arr ){
        ArrayList<Integer[]> chain = new ArrayList<Integer[]>();

        //Iterate through each row looking for chains
        for( int row = 0; row < arr.length; row++ ){
            for( int col = 0; col < arr[row].length; col++ ){
                int chainLen = 1; //Length of current chain
                //While the next gem matches the current gem, increase the size of our chain
                for( int i = col; i < arr[row].length; i++ ){
                    if( (i < arr[row].length - 1 ) && arr[row][i].equals( arr[row][i + 1] ) ){
                        chainLen++;
                    } else{
                        if( chainLen >= 3 ){ //if the chain length is greater than or equal to 3, add the chain
                            for( int j = col; j <= i; j++ ){
                                chain.add( new Integer[] { row, j } );
                            }
                        }
                        col = i;
                        break;
                    }
                }
            }
        }
        return chain;
    }

    //Returns vertical chains
    private static ArrayList<Integer[]> chainItemsColumn( Gem[][] arr ){
        ArrayList<Integer[]> chain = new ArrayList<Integer[]>();

        //Iterate through each column looking for chains
        for( int col = 0; col < arr[0].length; col++ ){
            for( int row = 0; row < arr.length; row++ ){

                int chainLen = 1;
                for( int i = row; i < arr.length; i++ ){
                    if( (i < arr.length - 1) && arr[i][col].equals( arr[i + 1][col] ) ){
                        chainLen++;
                    } else {
                        if( chainLen >= 3 ){
                            for( int j = row; j <= i; j++ ){
                                chain.add( new Integer[] { j, col } );
                            }
                        }
                        row = i;
                        break;
                    }
                }

            }
        }
        return chain;
    }
    //================CHAIN CHECKING METHODS END================*/

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
                gemType = (int) (Math.random() * 50);
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
        ArrayList<Integer[]> toDestroy = chainItems( game );

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
            game [i[0]] [i[1]] = new Gem(30);
        }
    }

    public static boolean hasSuperGems( Gem[][] game, ArrayList<Integer[]> chain ){
        for( Integer[] i: chain ){
            if( game[ i[0] ][ i[1] ] instanceof SuperGem ){
                return true;
            }
        }
        return false;
    }

    //Adds all the gems that a supergem would destroy to the given chain.
    //Returns the added gems
    public static ArrayList<Integer[]> expandSuperGems( Gem[][] game, ArrayList<Integer[]> chain ){
        ArrayList<Integer[]> newChains = new ArrayList<Integer[]>();
        //If a gem is a superGem, also add the gems that the supergem would destroy.
        int size = chain.size();
        for( int i = 0; i < size; i++ ){
            Integer[] gem = chain.get(i);
            if( game[ gem[0] ][ gem[1] ] instanceof SuperGem ){
                ArrayList<Integer[]> specialChain = ( (SuperGem)game[ gem[0] ][ gem[1] ] ).special( game, gem[0], gem[1] );
                for( Integer[] j: specialChain ){
                    chain.add( j );
                    newChains.add( j );
                }
                game[ gem[0] ][ gem[1] ] = new Gem(30); //remove special gem after it's been expanded
            }
        }
        return newChains;
    }

    //This method DROPS the gems down when the gems underneath them are destroyed
    public static void fall( Gem[][] game){
        for(int row = 0; row < game.length - 1; row++){
            for(int col = 0; col < game[0].length; col++){
                if(game[row + 1][col].color == 30){
                    swap(game, row, col, row + 1, col);
                }
            }
        }
    }

    //Fills the voids in the game board with new random gems
    //Doesn't fill the voids in me
    public static void replaceTheVoid(Gem[][] game){
        int gemType; 
        for(int col = 0; col < game[0].length; col++){
            if(game[0][col].color == 30){
                gemType = (int) (Math.random() * 75);
                if (gemType == 1) {
                    game [0][col] = new CrossGem();
                }
                else if (gemType == 2) {
                    game[0][col] = new ColorGem();
                }
                else if (gemType == 3) {
                    game[0][col] = new ExplodeGem ();
                } 
                else {
                    game[0][col] = new Gem();
                }
            }
        }
    }

    public static void printHelp(){
        Screen.clear();
        String help =   "================ BEJEWELED ================ \n"
            +           "\n"
            +           "CONTROLS: \n" 
            +           "  w e     Enter w, a, s, or d to move the cursor.\n"
            +           "a s d     Press e to select/deselect the current gem. \n"
            +           "\n"
            +           "GAMEPLAY: Select two gems to swap them. \n"
            +           "          Swaps are only allowed if they result in a chain of\n"
            +           "          3 or more gems. \n"
            +           "          Get a chain of 3 gems in a row to destroy the chain.\n"
            +           "\n"
            +           "SUPER GEMS: \n"
            +           "   # regular gem \n" 
            +           "   o exploding gem (destroys all gems in a 3x3 area around this gem) \n"
            +           "   + cross gem (destroys all the gems in the same column and row) \n"
            +           "   x color gem (destroys all gems of the same color) \n"
            +           "\n"
            +           "Press enter to continue.";
        System.out.print( help );
    }

    public static void main( String[] args ){
        //Declare a scanner and a variable to hold values
        Scanner sc = new Scanner( System.in );
        String s;

        printHelp();
        Screen.promptUser( sc ); //Press enter to continue

        boolean playAgain = true;
        while( playAgain ){
            //Initial values
            int numMoves = 0;
            int numSelectedGems = 0;
            int points = 0;
            //Initial cursor points
            int row = 0;
            int col = 0;


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
                    s = Screen.promptUser( "Input (h for help): ", sc );

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
                    } else if( s.equals("h") ){
                        printHelp();
                        Screen.promptUser( sc );
                        Screen.updateBoard( game, numMoves, points );
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
                    ArrayList<Integer[]> toDestroy = chainItems( game );
                    //Add 1 point for each gem to be destroyed
                    points += toDestroy.size();

                    //If chain formed
                    if( toDestroy.size() >= 3 ){
                        //Highlight the gems that will be destroyed
                        Gem.highlight( game, toDestroy, true );
                        Screen.updateBoard( game, numMoves, points );
                        wait( 750 ); //wait .75 seconds (750 milliseconds) before moving on to destroying the gems

                        while( hasSuperGems( game, toDestroy ) ){
                            Gem.highlight( game, expandSuperGems( game, toDestroy ), true );       
                            Screen.updateBoard( game, numMoves, points );
                            wait( 750 );
                        }

                        //Destroy chain and increment move counter
                        points += toDestroy.size();
                        destroyChain( game, toDestroy );
                        numMoves++;

                        //Makes gems succumb to the inevitable force of Gravity
                        for(int i = 0; i < 20; i++){
                            fall(game);
                            replaceTheVoid(game);
                        }

                        //This block handles new chains formed by the destruction of old chains
                        while( ( toDestroy = chainItems( game ) ).size() >= 3  ){
                            //Highlight gems that will be destroyed
                            Gem.highlight( game, toDestroy, true );
                            Screen.updateBoard( game, numMoves, points );
                            wait( 750 );

                            while( hasSuperGems( game, toDestroy ) ){
                                Gem.highlight( game, expandSuperGems( game, toDestroy ), true );       
                                Screen.updateBoard( game, numMoves, points );
                                wait( 750 );
                            }
                            points += toDestroy.size();
                            destroyChain( game, toDestroy );
                            for(int i = 0; i < 20; i++){
                                fall(game);
                                replaceTheVoid(game);
                            }
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
            System.out.println( "\nGame over!\nYour score was: " + points );
            s = "";
            while( !( s.equals("y") || s.equals("n") ) ){
                s = Screen.promptUser( "Do you wish to play again (y/n)? ", sc );
                if( s.equals("y") ){ //do nothing
                } else if( s.equals("n") ){
                    playAgain = false;
                } else {
                    System.out.print( "Input invalid. " );
                }
            }
        }
        System.out.println("");
    }
}


