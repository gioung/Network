package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Localhost {

	public static void main(String[] args) {
		
		try {
			
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostname = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
		
			System.out.println("host name : "+hostname+" host address : "
					+ hostAddress);
			
			byte[] addresses = inetAddress.getAddress();
			for(byte address:addresses) {
				System.out.print(address & 0xff);
				System.out.print(".");
			}
			
			InetAddress[] allname = InetAddress.getAllByName(hostname);
			for(int i=0; i<allname.length;i++)
				System.out.println(allname[i].getHostName()+":"+allname[i].getHostAddress());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
