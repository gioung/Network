package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PhoneList02 {

	public static void main(String[] args) {
		String filename ="phone.txt";
		File file = new File(filename);
		Scanner sc=null;
		
		try {
			sc=new Scanner(new FileInputStream(file));
			
			while(sc.hasNextLine()) {
				String name = sc.next();
				String phone01 = sc.next();
				String phone02 = sc.next();
				String phone03 = sc.next();
				
				System.out.println(name+":"+phone01+"-"+phone02+"-"+phone03);
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(sc!=null)
				sc.close();
		}

	}

}
