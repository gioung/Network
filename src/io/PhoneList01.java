package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PhoneList01 {

	public static void main(String[] args) {
		BufferedReader br=null;
		try {
		
			//기반, 보조 스트림1(bytes -> char)
	
			br=new BufferedReader(new InputStreamReader(new FileInputStream("phone.txt"),"UTF-8"));
			String line = null;
			while((line=br.readLine())!=null) {
				StringTokenizer tokenizer=new StringTokenizer(line,"\t, ");
				
				int index=0;
				while(tokenizer.hasMoreElements()) {
					String token=tokenizer.nextToken();
					System.out.print(token);
					if(index==0)
						System.out.print(":");
					else if(index<3) 
						System.out.print("-");
					index++;
				}
				System.out.println();
			}
		
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
				try {
					if(br!=null)
						br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
