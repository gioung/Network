package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSLookup {

	public static void main(String[] args) {
		String hostname=""; //도메인 이름
		BufferedReader bf = null; 
		
		
		
		
		while(true) {
		
			try {
				//입력받기
				System.out.print("도메인을 입력하세요: ");
				bf=new BufferedReader(new InputStreamReader(System.in,"utf-8"));
				hostname=bf.readLine();
			
				if(hostname.equals("exit")) //NSLOOKUP 종료
					break;
			
				//도메인을 ip 주소로 변환
				InetAddress[] inetAddresses;
				inetAddresses = InetAddress.getAllByName(hostname);
				for(InetAddress ht_name:inetAddresses) {
					System.out.println(ht_name.getHostAddress());
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	
		
		
		
		
		
		

	}

}
