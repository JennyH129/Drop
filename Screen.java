//Class containing methods and variables related to changing the screen
import java.util.Scanner;
public class Screen{

    public static final String ESC = (char)27 + "[";

    //Save position of cursor for later
    public static void save(){
        System.out.print( ESC + "s" );
    }

    //Load saved cursor position
    public static void load(){
        System.out.print( ESC + "u" );
    }

    //Sets the color
    public static void setColor( int color ){
        System.out.print( ESC + color + "m" );
    }

    //Removes all formatting
    public static void resetColor(){
        System.out.print( ESC + "0m" );
    }

    //Returns a color
    public static String retColor( int color ){
        return ESC + color + "m";
    }

    //Clears screen and moves cursor to top left corner
    public static void clear(){
        System.out.print( ESC + "2J" + ESC + ";H" ); // "2J" = Clear screen; ";H" = move cursor to top left corner
    }

    public static void clearLine(){
        System.out.print( ESC + "K" );
    }

    //Move to given position in the board
    public static void moveToGem( int row, int col ){
        System.out.print( ESC + ( row + 1 ) + ";" + ( 2 * col + 1 ) + "H" ); 
    }

    //Move to given position in terminal
    public static void moveTo( int row, int col ){
        System.out.print( ESC + row + ";" + col + "H" );
    }
    
    //Move relative to current position
    public static void move( int row, int col ){
        if( row > 0 ){
            System.out.print( ESC + row + "B" );
        } else if( row < 0 ){
            row = -1 * row;
            System.out.print( ESC + row + "A" );
        }
        if( col > 0 ){
            System.out.print( ESC + col + "C" );
        } else if( col < 0 ){
            col = -1 * col;
            System.out.print( ESC + col + "D" );
        }
    }

    //Get input, move cursor appropriately, and return input
    public static String promptUser( Scanner sc ){
        String input = sc.nextLine();
        move( -1, 0 );
        return input;
    }

    //Print a prompt and get input
    public static String promptUser( String prompt, Scanner sc ){
        clearLine();
        System.out.print( prompt );
        return promptUser( sc );
    }

    //Updates the gem at a given position
    public static void updateGem( Gem[][] game, int row, int col ){
        Screen.save();
        moveToGem( row, col );
        System.out.print( game[row][col] );
        Screen.load();
    }

    public static void updateBoard( Gem[][] game, int numMoves, int points ){
        Screen.clear();
        System.out.print( Woo.arrToStr( game ) + "\n\n"  );
        System.out.println( "Moves left: " + (10 - numMoves) );
        System.out.println( "Points: " + points );
    }

}
