package udp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPTimeClient {
	private static final String SERVER_IP = "127.0.0.1";
	private static final int PORT = 7500;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		
		DatagramSocket socket=null;
		Scanner sc = null;
		try {
			//소켓생성
			socket = new DatagramSocket();
			
			while(true) {
			//요청하기
			sc = new Scanner(System.in);
			System.out.print("아무키나 입력하세요 : ");
			byte[] send_data = sc.next().getBytes("utf-8");
			DatagramPacket sendPacket = new DatagramPacket(send_data,send_data.length,
					new InetSocketAddress(SERVER_IP,PORT));
			socket.send(sendPacket);
			
			//시간 읽기
			DatagramPacket recvPacket = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
			socket.receive(recvPacket);
			int length = recvPacket.getLength();
			byte[] data = recvPacket.getData();
			
			String timedata = new String(data,0,length,"utf-8");
			System.out.println(timedata);
		
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		

	}

}
