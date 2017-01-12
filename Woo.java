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

    public static void main( String[] args ){
        System.out.print( esc + "2J" + esc + ";H" ); // 2J = Clear screen; ;H = move cursor to top left corner

        //Define 2d array for the gems and then print it
        Gem[][] board = new Gem[10][10];
        populate( board );
        System.out.println(arrToStr( board ) + "\n"  );
        
        //Declare a scanner and a variable to hold values
        Scanner sc = new Scanner( System.in );
        String s;

        //Initial cursor points
        int row = 2;
        int col = 2;

        while( true ){
            System.out.print( esc + "K" + "Input (w/a/s/d): " ); // K = Clear line; print prompt
            s = sc.nextLine(); //Get input
            System.out.print( esc + "1A" ); // 1A = Move cursor back up 1 row (since sc.nextLine() moves it down one row)
            
            System.out.print( esc + "s" ); // s = Save position of prompt for later
            //Todo: make a method that overwrites the gem at the current or given position. Should replace the next two lines
            System.out.print( esc + ( row + 1 ) + ";" + ( 2 * col + 1 ) + "H" ); // line|col|H = Move to position of current gem object
            System.out.print( esc + "0m" + board[row][col] ); //Overwrite it with no background color

            //This block handles input
            if( s.equals("w") && row > 0 ){
                row--;
            } else if( s.equals("s") && row < board.length - 1 ){
                row++;
            } else if( s.equals("a") && col > 0 ){
                col--;
            } else if( s.equals("d") && col < board[row].length - 1 ){
                col++;
            } else {
                System.out.print( esc + "u" + "Input or move invalid. Press enter to continue. " );
                sc.nextLine();
                System.out.print( esc + "1A" ); 
            }


            System.out.print( esc + ( row + 1 ) + ";" + ( 2 * col + 1 ) + "H" ); //Move to position of current gem object
            System.out.print( esc + "47m" + board[row][col] + esc + "0m" ); //Overwrite it with a background color
            
            System.out.print( esc + "u" ); //Move cursor back to prompt
        }
    }
}
