package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class KeyboardTest {

	public static void main(String[] args) {
		// 기반스트림 (표준입력 , 키보드, System.in)
		
		BufferedReader br=null;
		try {
			//보조스트림1 
			// byte|byte|byte -> char
			
			InputStreamReader input_stream=new InputStreamReader(System.in,"utf-8");
		
			//보조스트림2
			//char|char|char|\n -> "char1char2char3"
			br = new BufferedReader(input_stream);
			String line=null;
			while((line=br.readLine())!=null) {
				if(line.equals("exit"))
					break;
				
				System.out.println(">>"+line);
			
			
			}
		
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("error");
		}finally {
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


