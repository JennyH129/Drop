public class Gem{


    public String toString(){
        return "*";
    }
    
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


}
