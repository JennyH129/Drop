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
                arr[i][j] = new Gem((int)(Math.random() * 6 + 31));
            }
        }
    }

    public static Gem [] [] newGame () {

        Gem [] [] board = new Gem [10] [10];
        populate (board);
        return board;
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
                arr[ i[0] ][ i[1] ] = new Gem((int)(Math.random() * 6 + 31));
            }
        } else {
            arr[row1][col1] = Gem1;
            arr[row2][col2] = Gem2;
            bool = false;
        }
        */
    }

    //Excludes current gem
    public static ArrayList<Integer[]> chainItemsH( Gem[][] arr, int row, int col ){
        
        ArrayList<Integer[]> retArr = new ArrayList<Integer[]>();
        //row and col of other gem
        int oRow = row;
        int oCol = col;

        //Check gems to left of current gem
        System.out.println( arr[row][col] );
        System.out.println( arr[row][col - 1] );
        while( oCol > 0 && arr[row][col].equals( arr[oRow][ oCol - 1 ] ) ){
            retArr.add( new Integer[]{ oRow, oCol - 1 } );
        }

        oRow = row;
        oCol = col;

        while( oCol < arr[oRow].length && arr[row][col].equals( arr[oRow][ oCol + 1 ] ) ){
            retArr.add( new Integer[]{ oRow, oCol + 1 } );
        }
        
        return retArr;
    }

    //Excludes current gem
    public static ArrayList<Integer[]> chainItemsV( Gem[][] arr, int row, int col ){
        
        ArrayList<Integer[]> retArr = new ArrayList<Integer[]>();
        //row and col of other gem
        int oRow = row;
        int oCol = col;

        while( oRow > 0 && arr[row][col].equals( arr[ oRow - 1 ][oCol] ) ){
            retArr.add( new Integer[]{ oRow - 1 , oCol } );
        }

        oRow = row;
        oCol = col;

        while( oRow < arr.length && arr[row][col].equals( arr[ oRow + 1 ][oCol] ) ){
            retArr.add( new Integer[]{ oRow + 1 , oCol } );
        }

        return retArr;
    }

    public static ArrayList<Integer[]> chainItems( Gem[][] arr, int row, int col ){
        ArrayList<Integer[]> arrH =  chainItemsH( arr, row, col );
        ArrayList<Integer[]> arrV =  chainItemsV( arr, row, col );

        ArrayList<Integer[]> retArr = new ArrayList<Integer[]>();
        retArr.add( new Integer[]{ row, col } );

        if( arrH.size() >= 2 ){
            for( Integer[] i: arrH ){ retArr.add( i ); }
        }

        if( arrV.size() >= 2 ){
            for( Integer[] i: arrV ){ retArr.add( i ); }
        }

        /*
        for( Integer[] j: retArr ){
            for( Integer i: j ){
                System.out.print( i + " " );
            }
            System.out.println();
        }
        */

        return retArr;
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
        

        Gem [] [] game = newGame();
        System.out.println(arrToStr( game ) + "\n"  );

        int numMoves = 0;
        int numSelectedGems = 0;

        //Declare a scanner and a variable to hold values
        Scanner sc = new Scanner( System.in );
        String s;

        //Initial cursor points
        int row = 0;
        int col = 0;

        //Coords of selected gems
        int[][] sGem = new int[2][2];


        while( numMoves < 50 ){


            while( numSelectedGems < 2 ){
                //Get user input
                System.out.print( esc + "K" + "Input (w/a/s/d): " ); // K = Clear line; print prompt
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

            swap( game, sGem[0][0], sGem[0][1], sGem[1][0], sGem[1][1] );
            
            //Update board
            System.out.print( esc + "2J" + esc + ";H" ); // 2J = Clear screen; ;H = move cursor to top left corner
            System.out.println(arrToStr( game ) + "\n"  );
            
            //chainItems goes into an infinite loop rn
            //ArrayList<Integer[]> toDestroy = chainItems( game, sGem[0][0], sGem[0][1] );

            /* 
            for( Integer[] j: toDestroy ){
                for( Integer i: j ){
                    System.out.print( i + " " );
                }
                System.out.println( "" );
            }
            */

        }
    }
}
	
	
