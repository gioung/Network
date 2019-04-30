package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	
	private static final int PORT= 8000;

	public static void main(String[] args) {
		ServerSocket serverSock=null;
		Socket socket=null;
		
			try {
				//1. 서버 소켓 생성
				serverSock=new ServerSocket();
				
				//2. 바인딩(binding)
				serverSock.bind(new InetSocketAddress("0.0.0.0",PORT));
				log("Server Starts...[PORT: " + PORT+"]");
				
				while(true) {
				//3. Accept : Client의 request를 기다림.
				socket=serverSock.accept(); //blocking 요청이 들어올때까지
				
				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
				
				}
				
				
				
			} 
			catch(SocketException e) {
				log("sudden closed");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if(serverSock!=null && !(serverSock.isClosed()))
						serverSock.close();
					if(socket!=null)
						socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
	
		
	

	}
	
	public static void log(String log) {
		System.out.println("[server# " + Thread.currentThread().getId()+ "] : "+log);
	}

}
