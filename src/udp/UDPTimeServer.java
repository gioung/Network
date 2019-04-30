package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	private static final int PORT = 7500;
	private static final int BUFFER_SIZE=1024;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		
		try {
			//소켓 생성
			socket = new DatagramSocket(7500);
			while(true) {
			//데이터 수신
			DatagramPacket recvpacket = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE); 
			socket.receive(recvpacket);//받을때까지 블락
			System.out.println("[server]: client ip="+recvpacket.getAddress()+" client port="+recvpacket.getPort());
			int length = recvpacket.getLength();
			byte[] data = recvpacket.getData();
			String message = new String(data,0,length,"utf-8");
			
			if(message.isEmpty()==false) {
			//데이터 전송 (시간 데이터)
				SimpleDateFormat format = new SimpleDateFormat();
				byte[] send_data = format.format(new Date()).getBytes("utf-8");
				DatagramPacket sendpacket = new DatagramPacket(send_data,send_data.length,
						recvpacket.getAddress(),
						recvpacket.getPort());
				socket.send(sendpacket);
			
			  }
		  }
			
			
		} catch (SocketException e) {
			
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		

		
	}

}
