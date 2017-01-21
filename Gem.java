public class Gem{

    protected int color;
    protected boolean locked;
    protected String character;
    
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
        return Screen.retColor( color ) + character + Screen.retColor( 0 );
    }
    
    public boolean equals(Gem other){
	return color == other.color;
    }

    public int getColor(){
	return color;
    }
}
