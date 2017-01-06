import java.util.Scanner;
public class Woo{

    /*
        This stores the ANSI "Escape" character and the left bracket for use later
        Info on what you can do with it and how to use it below:
        http://ascii-table.com/ansi-escape-sequences.php
    */
    public static final String esc = (char)27 + "[";

    public static void main( String[] args ){
        System.out.print( esc + "2J" + esc + ";H" ); //Clear screen, then move cursor to top left corner

        //Define 2d array for the gems and then print it
        Gem[][] arr = new Gem[20][20];
        Gem.populate( arr );
        System.out.println( Gem.arrToStr( arr ) + "\n"  );
        
        //Declare a scanner and a variable to hold values
        Scanner sc = new Scanner( System.in );
        String s;

        int row = 2;
        int col = 2;
        while( true ){
            System.out.print( esc + "K" + "Input (w/a/s/d): " );
            s = sc.nextLine(); //Get input
            System.out.print( esc + "1A" ); //Move cursor back up 1 row since sc.nextLine() moves it down one row
            
            if( s.equals("w") && row > 0 ){
                row--;
            } else if( s.equals("s") && row < 20 ){
                row++;
            } else if( s.equals("a") && col > 0 ){
                col--;
            } else if( s.equals("d") && col < 20){
                col++;
            } else {
                System.out.print( "Input invalid. Press enter to continue. " );
                sc.nextLine();
                System.out.print( esc + "1A" ); 
            }

            System.out.print( esc + "s" );

            System.out.print( esc + ( row + 1 ) + ";" + ( 2 * col + 1 ) + "H" );
            System.out.print( esc + "46m" + arr[row][col] + esc + "0m" );
            
            System.out.print( esc + "u" );
        }

        /*
        System.out.println( "bobobobobobobobobobobobobobobobobbobobobobobobobobobo" );
        System.out.println( "bobobobobobobobobobobobobobobobobbobobobobobobobobobo" );
        System.out.println( "bobobobobobobobobobobobobobobobobbobobobobobobobobobo" );
        System.out.println( "bobobobobobobobobobobobobobobobobbobobobobobobobobobo" );
        //Testing out the usage of ANSI escape sequences
        System.out.print( esc + "s" ); //Saves the current position of the cursor on the screen.
        System.out.print( esc + "3A" ); //Moves the curson up four lines from the current position
        System.out.print( esc + "30C" ); //Moves the curson right 10 characters

        System.out.print( esc + "46m" ); //Makes are characters printed from now on have a cyan colored background
        System.out.print( "im printing up 3 lines and 40 characters right!" ); //Print stuff
        System.out.print( esc + "0m" ); //Resets the background color for future characters

        System.out.print( esc + "u" ); //Moves the cursor back to the saved position
        */
    }
}
