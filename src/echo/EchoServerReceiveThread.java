package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread{
	
	private Socket socket;
	
	public EchoServerReceiveThread(Socket socket) {
		this.socket=socket;
	}
	
	@Override
	public void run() {
		try {
		//4. IOStream 받아오기
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,"utf-8"),true); //auto flush
		
		
		while(true) {
			//5. 데이터 읽기
			String data = br.readLine();
			
			if(data == null) {
				//클라이언트 정상종료 한 경우
				//close() 메소드 호출을 통해서
				EchoServer.log("closed by client");
				
				break;
			}
			
			EchoServer.log("received "+data);
			
				//6.데이터 쓰기
				pw.println(data); //개행이 빠져서 읽는다.
					
		 	}
		}catch(SocketException e) {
			EchoServer.log("sudden closed");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				
				if(socket!=null && !(socket.isClosed()))
					socket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}


}
