public class Gem{

    protected int color;
    protected boolean locked;
    
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
        return Screen.ESC + color + "m#" + Screen.ESC + "0m";
    }
    
    public boolean equals(Gem other){
	return color == other.color;
    }

    public int getColor(){
	return color;
    }
}
