package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP="127.0.0.1"; //final 키워드 : 값에 대입의 끝 (변경 불가) 
	private static final int SERVER_PORT= 8000;

	public static void main(String[] args) {
		Scanner scanner = null;
		Socket sock = null;
		InputStream is = null;
		OutputStream os = null;
		try {
		// 0.스캐너 생성(std in)
		scanner = new Scanner(System.in);
		// 1.소켓 생성
		sock = new Socket();
		
		
		//2. 서버 connect()
		sock.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
		log("connectd by Server");
		
		//3. IOStream 받아오기
		is = sock.getInputStream();
		os = sock.getOutputStream();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,"utf-8"),true); //auto flush
		
		//4. write 
		
		while(true) {
			//5. 키보드 입력받기
			System.out.print(">>");
			String line=scanner.nextLine();
			
			if("quit".equals(line))
				break;
			
			//6. 데이터 쓰기
			pw.println(line);
			
			//7. 데이터 읽기
			String data = br.readLine();
			if(data == null) {
				log("closed by server");
				break;
			}
			
			//8.콘솔 출력
			System.out.println(">>"+data);
		}
		
		//닫기는 서버쪽에서 닫지만 써준다.
		
		} catch (IOException e) {
			
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
	public static void log(String log) {
		System.out.println("[client] " + log);
	}

}
