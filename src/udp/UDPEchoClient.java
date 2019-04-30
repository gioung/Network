package udp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import java.util.Scanner;

public class UDPEchoClient {
	private static final String SERVER_IP="127.0.0.1"; //final 키워드 : 값에 대입의 끝 (변경 불가) 
	private static final int SERVER_PORT= 7000;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		Scanner scanner = null;
		DatagramSocket sock = null;
		InputStream is = null;
		OutputStream os = null;
		try {
		// 0.스캐너 생성(std in)
		scanner = new Scanner(System.in);
		// 1.소켓 생성
		sock = new DatagramSocket();
		
		
		
		
		while(true) {
			//3. 키보드 입력받기
			System.out.print(">>");
			String line=scanner.nextLine();
			
			if("quit".equals(line))
				break;
			
			//4. 데이터 쓰기
			byte[] sendData = line.getBytes("utf-8");
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,new InetSocketAddress(SERVER_IP,SERVER_PORT));
			sock.send(sendPacket);
			
			//5. 데이터 읽기
			DatagramPacket recvPacket = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
			sock.receive(recvPacket);
			byte[] data=recvPacket.getData();
			int length = recvPacket.getLength();
			String message = new String(data,0,length,"UTF-8");
			System.out.println("[client] received:"+message);
			//6.콘솔 출력
			System.out.println(">>"+data);
			}
			
			
		}
		 catch (IOException e) {
			
			e.printStackTrace();
		}
		finally {
				try {
					if(is!=null)
						is.close();
					if(os!=null)
						os.close();
					if(sock!=null && !(sock.isClosed()))
						sock.close();
					if(scanner!=null)
						scanner.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	}


