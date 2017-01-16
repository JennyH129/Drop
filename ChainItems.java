import java.util.ArrayList;

public class ChainItems{

    public static void arrToStr(Integer[][] arr){
	for(Integer[] I: arr){
	    for(Integer i: I){
		System.out.print( i + " ");
	    }
	    System.out.println();
	}
    }
    
    public static void populate(Integer[][] arr){
	for(int i = 0; i < arr.length; i++){
	    for(int I = 0; I < arr.length; I++){
		arr[i][I] = (int)(Math.random() * 3);
	    }
	}
    }

    public static ArrayList<Integer[]> chainItemRight(Gem[][] arr, int row, int col){
	ArrayList<Integer[]> retArr = new ArrayList<Integer[]>();
	Gem orig = arr[row][col];
	for(int start = col; start < arr.length && arr[row][start].equals(orig); start++){
	    retArr.add(new Integer[] {row,start});
	}
	return retArr;
    }

    public static ArrayList<Integer[]> chainItemLeft(Gem[][] arr, int row, int col){
	ArrayList<Integer[]> retArr = new ArrayList<Integer[]>();
	Gem orig = arr[row][col];
	for(int start = col; start > 0 && arr[row][start].equals(orig); start--){
	    retArr.add(new Integer[] {row,start});
	}
	return retArr;
    }

    public static ArrayList<Integer[]> chainItemDown(Gem[][] arr, int row, int col){
	ArrayList<Integer[]> retArr = new ArrayList<Integer[]>();
	Gem orig = arr[row][col];
	for(int start = row; start < arr.length && arr[start][col].equals(orig); start++){
	    retArr.add(new Integer[] {start,col});
	}
	return retArr;
    }
    
    public static ArrayList<Integer[]> chainItemUp(Gem[][] arr, int row, int col){
	ArrayList<Integer[]> retArr = new ArrayList<Integer[]>();
	Gem orig = arr[row][col];
	for(int start = row; start > 0 && arr[start][col].equals(orig); start--){
	    retArr.add(new Integer[] {start,col});
	}
	return retArr;
    }

    public static ArrayList<Integer[]> chainItems(Gem[][] arr, int row, int col){
	ArrayList<Integer[]> rChain = chainItemRight(arr, col, row);
	ArrayList<Integer[]> lChain = chainItemLeft(arr, col, row);
	ArrayList<Integer[]> uChain = chainItemUp(arr, col, row);
	ArrayList<Integer[]> dChain = chainItemDown(arr, col, row);
	ArrayList<Integer[]> retArr = new ArrayList<Integer[]>();
	if(rChain.size() >=3){
	    for(Integer[] i: rChain){ retArr.add(i);}
	}
	if(lChain.size() >=3){
	    for(Integer[] i: lChain){ retArr.add(i);}
	}
	if(uChain.size() >=3){
	    for(Integer[] i: uChain){ retArr.add(i);}
	}
	if(dChain.size() >=3){
	    for(Integer[] i: dChain){ retArr.add(i);}
	}

	return retArr;
    }
}
