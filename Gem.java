import java.util.ArrayList;
public class Gem{

    protected int color;
    protected String character;
    protected boolean highlighted = false;
    protected boolean selected = false;
    
    public Gem(){
        color = (int)(Math.random() * 6 + 31);
        character="#";
    }
    /*
       newColor should be in range [31,36]
       Colors:
        31 -- Red
        32 -- Green
        33 -- Yellow
        34 -- Blue
        35 -- Magenta
        36 -- Cyan
    
     */
    public Gem(int newColor){
        color = newColor;
        character="#";
    }

    public String toString(){
        String retStr = "";
        if( highlighted ){
            retStr += Screen.retColor( 47 );
        } 
        retStr += Screen.retColor( color ) + character + Screen.retColor( 0 );
        return retStr;
    }
    
    public boolean equals(Gem other){
	if (color != 30){
	    return color == other.color;
	}
	return false;
    }

    public int getColor(){
	return color;
    }

    //If a gem is selected, it will always be highlighted
    //Unselecting a gem wont unhighlight it
    public void select( boolean state ){
        selected = state;
        highlighted = highlighted || state;
    }
    
    //Makes a gem (un)highlighted. Selected gems cant be unhighlighted.
    public void highlight( boolean state ){
        highlighted = selected || state;
    }

    //Deselects and unhighlights a gem
    public void turnOff(){
        highlighted = false;
        selected = false;
    }

    public static void highlight( Gem[][] game, ArrayList<Integer[]> chain, boolean state ){
        for( Integer[] i: chain ){
            game[ i[0] ][ i[1] ].highlight( state );
        }
    }

}
