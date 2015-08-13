import java.util.Random;


public class asdfsdf {
	
	static int[] location_Limits = new int[6];
	static int[] lim = {1,25,26,75,76,100};
	static int[] randomz= {1,6,12,62,72};
	public static void main(String[] args) {
		//Randomic(randomz);
		Sort(randomz);
	}
	
	private static void Randomic(int[] randomz) {
		Random randomGenerator = new Random();
		for (int i = 0; i < randomz.length; i++){
			randomz[i] = randomGenerator.nextInt(100);
//			System.out.println(i + "  " +randomz[i]);
		}
		print_randomz();
	}
	
	
	private static void Sort(int[] randomz) {
		int i;
		boolean flag = true;
		int temp;
		if (randomz.length > 0){
			
			while(flag){
				flag = false;
				for(i = 0; i < randomz.length - 1; i++){
					if (randomz[i]>randomz[i+1]){
						temp = randomz[i];
						randomz[i] = randomz[i+1];
						randomz[i+1] = temp;
						flag = true;
					}
				}
			}
			print_randomz();
			
			
			
			location_Limits[0]= 1;
			location_Limits[5]= randomz.length-1;
			boolean is2set = false;
			boolean is4set = false;
			
			for (i=0;i<randomz.length; i++){
	
				if ((randomz[i]>lim[1]) && (is2set == false)){
					location_Limits[1] = i-1;
					location_Limits[2] = i;
					is2set = true;
				}
				else if ((randomz[i]>lim[3]) && (is4set == false)){
					location_Limits[3] = i-1;
					location_Limits[4] = i;
					is4set = true;
				}

			}

			boolean swapped = false;
			boolean one = false;
			boolean two = false;
			boolean three = false;
			
			one = randomz[0]< lim[1]; //first index is greater than 25 therefore nothing in row 1
			two = is2set;
			three = is4set;
			
		
			if ((!one) && (!two) && (!three)){	
			}
			else if((!one) && (!two) && (three)){	
			}
			else if((!one) && (two) && (!three)){	
			}
			else if((one) && (!two) && (!three)){
			}
			else if((one) && (two) && (!three)){
				check_flip(1,swapped);
			}
			else if((!one) && (two) && (three)){
				check_flip(3,swapped);
			}
			else if((one) && (!two) && (three)){
				check_flip(2,swapped);
			}
			else if((one) && (two) && (three)){
				check_flip(1,swapped);
				check_flip(3,swapped);
			};

			print_randomz();
				
			
		}
	}
	
	
	public static void print_randomz(){
		System.out.println("");
		for (int i=0; i<randomz.length; i++){
			System.out.print(randomz[i] + ", ");
		}
	}
	
	public static boolean check_flip(int fromRow, boolean swapped){

		int bound = 0;
		int a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0;
		
		switch(fromRow){
			case 1:
				a = 1; b = 1; c = 2; d = 2; e = 1; f = 0; g = 3; h = 3; 
				bound = 3;
				break;
			case 2:
				a = 1; b = 1; c = 5; d = 5; e = 1; f = 0; g = 4; h = 4; 
				bound = 3;
				break;
			case 3:
				if (swapped){
					a = 3; b = 2; c = 5; d = 5; e = 3; f = 3; g = 4; h = 4; 
					bound = 4;
				}
				else{
					a = 2; b = 2; c = 5; d = 5; e = 3; f = 3; g = 4; h = 4; 
					bound = 4;
				}
				break;
			default:
				System.out.println("Error: the fromRow to the command is wrong");
				break;
		}

		
		
		if (((Math.abs((randomz[location_Limits[a]])-lim[b])) + (Math.abs((randomz[location_Limits[c]])-lim[d]))) > ((Math.abs((randomz[location_Limits[e]])-lim[f])) + (Math.abs((randomz[location_Limits[g]])-lim[h])))){
			int[] flip = new int[location_Limits[bound+1]-location_Limits[bound]+1];	
			swapped = true;
			for (int j = location_Limits[bound]; j <=location_Limits[bound+1]; j++){
				flip[j-location_Limits[bound]]=randomz[j];
			}
			
			for (int k = 0; k < flip.length/2; k++) {
			    int temp2 = flip[k];
			    flip[k] = flip[flip.length-(1+k)];
			    flip[flip.length-(1+k)] = temp2;
			}
			
			for (int j = location_Limits[bound]; j <=location_Limits[bound+1]; j++){
				randomz[j]=flip[j - location_Limits[bound]];
			}
		}
		return swapped;
	}
	
}
