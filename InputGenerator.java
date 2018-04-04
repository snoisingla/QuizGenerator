import java.util.*;
import java.io.*;

class InputGenerator{
	public static void createInput(){
		Random rn = new Random();
		String[] difficulty = new String[]{"HARD","MEDIUM","EASY"};
		for(int i = 1; i <=60; i++){
			int ans = rn.nextInt(6) + 1;
			int index = rn.nextInt(3);
			System.out.println("Q"+i+"|"+difficulty[index]+"|Tag"+ans);
		}				
	}
	public static void main(String[] args) {
		createInput();
	}
}