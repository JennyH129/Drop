import java.util.ArrayList;

public class ChainItems{

    //Returns a list of the positions of chains
    public static ArrayList<Integer[]> chainItems( Gem[][] arr ){
        ArrayList<Integer[]> chain = chainItemsRow( arr );
        /*
        for( Integer[] i: chainItemsColumn( arr ) ){
            chain.add( i );
        }
        */
        return chain;
    }

    //Returns horizontal chains
    private static ArrayList<Integer[]> chainItemsRow( Gem[][] arr ){
        ArrayList<Integer[]> chain = new ArrayList<Integer[]>();

        //Iterate through the 2d gem array checking for chains of Gems
        //First check for chains in rows
        for( int row = 0; row < arr.length; row++ ){
            for( int col = 0; col < arr[row].length; col++ ){
                int chainLen = 1; //Length of current chain

                for( int i = col; i < arr[row].length; i++ ){
                    if( i == arr[row].length - 1 ){
                        //do nothing
                    } else if( arr[row][i].equals( arr[row][i + 1] ) ){
                        chainLen++;
                    } else{
                        if( i - col + 1 >= 3 ){ //if the chain length is greater than or equal to 3, add the chain
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

    //Main method only for testing
    public static void main(String[] args){
        Gem[][] board = new Gem[10][10];
        Woo.populate(board);
        //BUG: Chains are not detected if one of the gems is in the last column
        //This chain is not detected
        board[5][9] = new Gem(31);
        board[5][8] = new Gem(31);
        board[5][7] = new Gem(31);
        board[5][6] = new Gem(31);
        board[5][5] = new Gem(31);
        board[5][4] = new Gem(31);
        board[5][3] = new Gem(31);
        board[5][2] = new Gem(31);
        //This chain is detected
        board[6][9] = new Gem(32);
        board[6][8] = new Gem(31);
        board[6][7] = new Gem(31);
        board[6][6] = new Gem(31);
        board[6][5] = new Gem(31);
        board[6][4] = new Gem(31);
        board[6][3] = new Gem(31);
        board[6][2] = new Gem(31);
        System.out.println( Woo.arrToStr(board) );
        ArrayList<Integer[]> chain = chainItems( board );
        for( Integer[] i: chain ){
            for( Integer j: i ){
                System.out.print( j + " " );
            }
            System.out.println();
        }
    }

}
