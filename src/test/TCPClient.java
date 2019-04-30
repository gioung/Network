package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClient {
	private static final String SERVER_IP="192.168.1.13"; //final 키워드 : 값에 대입의 끝 (변경 불가) 
	private static final int SERVER_PORT= 7000;
	
	public static void main(String[] args) { 
		Socket sock = null;
		InputStream is = null;
		OutputStream os = null;
		try {
		// 1.소켓 생성
		sock = new Socket();
		//1-1.소켓 버퍼 사이즈 확인
		int receiveBufferSize = sock.getReceiveBufferSize();
		int sendBufferSize =sock.getSendBufferSize();
		System.out.println(receiveBufferSize+":"+sendBufferSize);				
		//1-2.소켓 버퍼사이즈 변경
		sock.setReceiveBufferSize(1024*10);
		sock.setSendBufferSize(1024*10);
		receiveBufferSize = sock.getReceiveBufferSize();
		sendBufferSize =sock.getSendBufferSize();
		System.out.println(receiveBufferSize+":"+sendBufferSize);
		
		//1-3. SO_NODELAY(Nagle Algorithm Off)
		sock.setTcpNoDelay(true);
		
		//1-4. SO_TIMEOUT
		sock.setSoTimeout(1000);
		
		//2. 서버 connect()
		sock.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
		System.out.println("[client] connectd by Server");
		
		//3. IOStream 받아오기
		is = sock.getInputStream();
		os = sock.getOutputStream();
		
		//4. write 
		String data = "Hello World\n"; //개행문자는 경계문자로써 응용단에서 경계를 만들어준다.
		os.write(data.getBytes("utf-8"));
		
		//5. read
		byte[] buffer = new byte[256];
		int readByteCount=is.read(buffer); //blocking

		if(readByteCount == -1) {
			System.out.println("[client] closed by Server");
		}
		data = new String(buffer,0,readByteCount,"utf-8");
		System.out.println("[client] received : " +data);
		
		//닫기는 서버쪽에서 닫지만 써준다.
		
		}
		catch (SocketTimeoutException e) {
			
			System.out.println("[client] time out");
			
		}catch (IOException e) {
			
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
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
