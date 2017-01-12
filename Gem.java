public class Gem{

    private int color;
    private boolean locked;
    
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
