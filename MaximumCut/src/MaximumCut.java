import java.io.*;
import java.util.*;

public class MaximumCut {

	static int numVertices = 0, minNum=0,maxNum=0;
	static int [][] vertices; 
	static ArrayList<String> generatedSolutions = new ArrayList<String>(); 
	static int leftCost=0,rightCost=0,fullCost=0, maxCost=0;  // our solution will be stored here 
	static String answer; // and here
	static ArrayList<Integer> rightSide = new ArrayList<Integer>();
	static ArrayList<Integer> leftSide = new ArrayList<Integer>();
	
	

	public static void main(String[] args) throws IOException 
	{
		readFromFile(); 		
		PrintSolution();
	}	
	
	public static void PrintSolution() throws IOException 
	{
		SeperateVertices(answer);
	
	}
	
	public static void SeperateVertices(String sol) throws IOException {	
			
			String writeToFile = ""; 
		
			String temp = sol;		
			for(int b=0;b<temp.length();b++) {								
				if(temp.charAt(b)=='0') {					
					rightSide.add(b); 
				}else if(temp.charAt(b)=='1') {			
					leftSide.add(b); 				
				}				
			}				
			addCost(temp); 
			
			writeToFile = "Cost of partition 1 :"+leftCost+"\n"; 
			writeToFile = writeToFile+ "Cost of partition 2 :"+rightCost+"\n"; 
			writeToFile = writeToFile+ "Cost of both partations :"+fullCost+"\n"; 			
		
			int sumOFallCost = 0; 
			for(int a=0;a<vertices.length;a++) {
				for(int b=a+1;b<vertices.length;b++) {
					sumOFallCost = sumOFallCost+vertices[a][b]; 		
				}			
			}
			
			writeToFile = writeToFile+ "Max Cut is :"+ (sumOFallCost-fullCost)+"\n"; 	
			writeToFile = writeToFile+ "Weight of whole graph is :" +sumOFallCost+"\n"; 		
			
			String group1 =""; 
			String group2 ="";
			
			for(int a=0;a<leftSide.size();a++) {
				group1 = group1+"V"+(leftSide.get(a)+1)+"  "; 
			}
			for(int a=0;a<rightSide.size();a++) {
				group2 = group2+"V"+(rightSide.get(a)+1)+"  "; 
			}
			writeToFile = writeToFile+"The 2 partitions are : "+"\n"; 
			writeToFile = writeToFile+group1+"\n"; 
			writeToFile = writeToFile+group2+"\n"; 

			System.out.println(writeToFile);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("FileOut.txt"));
		    writer.write(writeToFile);
		    writer.close();
				
			
	}
	
	public static void calculateCost() {
		for(int a=0;a<generatedSolutions.size();a++) {
			String temp = generatedSolutions.get(a); 
			
			for(int b=0;b<temp.length();b++) {								
				if(temp.charAt(b)=='0') {					
					rightSide.add(b); 
				}else if(temp.charAt(b)=='1') {			
					leftSide.add(b); 				
				}				
			}				
			addCost(temp); 
			rightSide.clear();
			leftSide.clear();
		}
	}
	
	public static void addCost(String tempAns) {	
		leftCost =0; rightCost = 0; 
		for(int a=0; a<leftSide.size()-1;a++) {
			for(int b=a+1;b<leftSide.size();b++) {
				leftCost = leftCost+vertices[leftSide.get(a)][leftSide.get(b)];	
			}
		}		
		for(int a=0; a<rightSide.size()-1;a++) {
			for(int b=a+1;b<rightSide.size();b++) {
				rightCost = rightCost+vertices[rightSide.get(a)][rightSide.get(b)];	
			}
		} 
		fullCost = leftCost+rightCost; 
		
		if(fullCost>maxCost) {
			answer = tempAns; 
		}
	}
	
	
	
	public static void bruteForce() {
		for(int x=minNum ; x<=maxNum;x++) {			
			String temp = padding(convertTobinary(x)); 		
			if(validate(temp)) {
				generatedSolutions.add(temp); 
			}				
		}
	}

	public static String padding(String num){
		int x = num.length(); 		
		x = numVertices - x; 
		String ans = ""; 
		for(int a=0;a<x;a++) {
			ans = ans+"0"; 
		}
		ans = ans+num; 		
		return ans; 	
	}

	public static boolean validate(String str) { //return boolean to check the valid string
		int numOne = str.length()/2; 
		int counter =0; 
		for(int a=0;a<str.length(); a++) {
			if(str.charAt(a)=='1') {
				counter++; 
			}
		}
		return counter==numOne; 
	}
	
	public static String convertTobinary(int x) {	
		String ans = ""; 
		while(x > 0){
			ans = (x%2)+ans;
			x = x/2;
		}
		return ans; 
	}

	public static void getMinCout() {
		int x = numVertices; 
		if(x%2==1) {
			return; 
		} 	
		int half  = x/2; 
		int sum = 0; 
		for(int a=1; a<half;a++){
			sum = sum+(int)Math.pow(2,a); 
		}
		sum++; 
		minNum =  sum; 	
	}

	public static void getMaxCount() { //gets a input of number of vertices and creates the max count
		int x = numVertices; 
		if(x%2==1) {
			return; 
		}
		int half  = x/2; 
		int sum = 0; 
		for(int a=x-1; a>=half;a--) {
			sum = sum+(int)Math.pow(2,a); 
		}
		 maxNum = sum; 
	}
	
	public static void readFromFile() throws FileNotFoundException {	
		File file = new File("FileIn.txt"); 
		Scanner sc = new Scanner(file); 
		String temp = ""; 
		ArrayList<String[]> arrays = new ArrayList<String[]>(); 

		while (sc.hasNextLine()) {
			
			temp = sc.nextLine(); 
			String[] arr = temp.split(",");
			arrays.add(arr); 			  		   
		}
		vertices = new int[arrays.size()][arrays.size()]; 
		for(int b=0;b<arrays.size();b++) { //col
			for(int a=0; a<arrays.get(b).length;a++) {	//row		    		
				vertices[b][a] = Integer.parseInt(arrays.get(b)[a]); 		    				
			}
		}
		numVertices = vertices.length; 
		getMinCout();
		getMaxCount();
		bruteForce(); 
		calculateCost(); 
	}
}