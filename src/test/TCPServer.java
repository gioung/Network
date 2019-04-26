package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPServer {

	public static void main(String[] args) {
		
		ServerSocket serverSock=null;
			try {
				//1. 서버 소켓 생성
				serverSock=new ServerSocket();
				
				//2. 바인딩(binding)
				// : socket에 SocketAddress(IPAddress + Port)를 바인딩한다.
				//InetAddress inetAddress = InetAddress.getLocalHost();
				//String localhost = inetAddress.getHostAddress();
				//serverSock.bind(new InetSocketAddress("localhost",5000));
				//serverSock.bind(new InetSocketAddress(InetAddress,5000));
				
				serverSock.bind(new InetSocketAddress("0.0.0.0",7000));
				
				//3. Accept : Client의 request를 기다림.
				Socket socket=serverSock.accept(); //blocking 요청이 들어올때까지
				
				
				InetSocketAddress inetRemoteAddress=(InetSocketAddress)socket.getRemoteSocketAddress();
				String remoteHostAddress=inetRemoteAddress.getAddress().getHostAddress();
				int remotePort = inetRemoteAddress.getPort();
				
				System.out.println("connected by client["+remoteHostAddress+" : "+
						remotePort+"]");
				
				try {
				//4. IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				while(true) {
					//5. 데이터 읽기
					byte[] buffer = new byte[256];
					int readByteCount=is.read(buffer);
					
					if(readByteCount == -1) {
						//클라이언트 정상종료 한 경우
						//close() 메소드 호출을 통해서
						System.out.println("[server] closed by client");
						break;
					}
					
					String data = new String(buffer, 0,readByteCount,"utf-8");
					System.out.println("[server] received "+data );
					
						//6.데이터 쓰기
						os.write(data.getBytes("utf-8"));
							
				 	}
				}catch(IOException e) {
					e.printStackTrace();
				}finally {
					try {
					if(socket!=null)
					socket.close();
						
					}catch(IOException e) {
						e.printStackTrace();
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if(serverSock!=null && !(serverSock.isClosed()))
						serverSock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
	
		
	}

}
