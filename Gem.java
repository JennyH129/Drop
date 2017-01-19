public class Gem{

    protected int color;
    private boolean locked;
    
    public Gem(){
        color = (int)(Math.random() * 6 + 31);
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
    }

    public String toString(){
        return Woo.esc + color + "m*" + Woo.esc + "0m";
    }
    
    public boolean equals(Gem other){
	return color == other.color;
    }

    public int getColor(){
	return color;
    }
}
