package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPEchoServer {
	
	public static final int PORT = 7000;
	public  static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		
		DatagramSocket socket=null;
		try {
			//1. socket 생성
			socket = new DatagramSocket(PORT);
			
			while(true) {
				//2. 데이터 수신
				DatagramPacket recvPacket = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
				socket.receive(recvPacket);
				byte[] data=recvPacket.getData();
				int length = recvPacket.getLength();
				String message = new String(data,0,length,"UTF-8");
				System.out.println("[server] received:"+message);
				
				//3. 데이터 전송
				byte[] sendData = message.getBytes("utf-8");
				DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,recvPacket.getAddress(),recvPacket.getPort());
				socket.send(sendPacket);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(socket!=null || socket.isClosed()==false)
				socket.close();
		}
		

	}

}
