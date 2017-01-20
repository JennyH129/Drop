import java.util.ArrayList;

public class ChainItems{

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
                                //If the added gem is a superGem, also add the gems the supergem would destroy.
                                if( arr[row][j] instanceof SuperGem ){
                                    //Typecast to SuperGem to access method special()
                                    for( Integer[] k: ( (SuperGem)arr[row][j] ).special( arr, row, j ) ){
                                        chain.add( k );
                                    }
                                }
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
                                if( arr[j][col] instanceof SuperGem ){
                                    for( Integer[] k: ( (SuperGem)arr[j][col] ).special( arr, j, col ) ){
                                        chain.add( k );
                                    }
                                }
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

    //Main method only for testing
    public static void main(String[] args){
        Gem[][] board = new Gem[10][10];
        Woo.populate(board);
        for( int i = 0; i < board.length; i++ ){
            board[i][4] = new Gem(31);
            board[4][i] = new Gem(31);
        }
        System.out.println( Woo.arrToStr(board) );
        ArrayList<Integer[]> chain = chainItems( board );
        System.out.println( "Row\tColumn" );
        for( Integer[] i: chain ){
            for( Integer j: i ){
                System.out.print( j + "\t" );
            }
            System.out.println();
        }
    }

}
