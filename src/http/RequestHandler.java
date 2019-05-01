package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class RequestHandler extends Thread {
	private Socket socket;
	private static String documentRoot="";
	private static final String BAD_ROOT = "./webapp/error/400.html, 400 Bad Request\r\n";
	private static final String NOTFOUND_ROOT = "./webapp/error/404.html, 404 Not found\r\n";
	
	static {
		try {
			documentRoot = new File(RequestHandler.class.getProtectionDomain().getCodeSource().getLocation()
					.toURI()).getPath();
			documentRoot+="/webapp";
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("---->"+documentRoot);
	}
	public RequestHandler( Socket socket ) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = ( InetSocketAddress )socket.getRemoteSocketAddress();
			consoleLog( "connected from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() );
				
			// get IOStream
			OutputStream os = socket.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			
			String request = null;
			while(true) {
				String line = br.readLine();
				
				//브라우저가 연결을 끊으면 , //header만 읽음
				if(line==null || "".equals(line)) {
					System.out.println("".equals(line));
						break;	
				}
				
				// Header의 첫번째 라인만 처리
				if(request == null)
					request = line;
				
			}
		
			String[] tokens=request.split(" ");
			
			//GET 방식
			if("GET".equals(tokens[0])) {
				consoleLog("request: "+tokens[1]);
				responseStaticResource(os,tokens[1],tokens[2]);
			}
			else {
				//POST,PUB,DELETE,HEAD,CONNECT와 같은 METHODS는 무시
				
				/* 응답 예시
				 * -----------------header-------------------
				 *  HTTP/1.1 404 Bad Request\r\n 
				 *  Content-Type:text/html; charset=utf-8\r\n
				 *  \r\n
				 *  ----------------body---------------------
				 *  HTML 에러 문서(./webapp/error/404.html)	
				 *  		 
				 */
				//400응답코드
				responseError(os,tokens[2],BAD_ROOT);
				
				
			}
						
			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다. 
			//문자 스트림을 안쓴 이유는 bin,이미지같은 것들도 보내야 되기 때문이다.
			/*os.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ) );
			os.write( "Content-Type:text/html; charset=utf-8\r\n".getBytes( "UTF-8" ) );
			os.write( "\r\n".getBytes() ); //헤더와 바디를 나누는 부분
			os.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes( "UTF-8" ) );
			 */
		} catch( Exception ex ) {
			consoleLog( "error:" + ex );
		} finally {
			// clean-up
			try{
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
				
			} catch( IOException ex ) {
				consoleLog( "error:" + ex );
			}
		}			
	}

	public void consoleLog( String message ) {
	
		System.out.println( "[RequestHandler#" + getId() + "] " + message );
	}
	public void responseStaticResource(OutputStream os,String url,String protocol) throws IOException{
		
		if("/".equals(url)) {
			url = "/index.html";
		}
		
		File f = new File(documentRoot+url);
		if(f.exists()==false) {
			//404 응답코드
			responseError(os,protocol,NOTFOUND_ROOT);
			return ;
		}
		//nio
		
			byte[] body = Files.readAllBytes(f.toPath());
			String contentType = Files.probeContentType(f.toPath());
			
	   //응답
			
			os.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
			os.write(("Content-Type:"+contentType+"; charset=utf-8\r\n").getBytes("UTF-8") );
			os.write("\r\n".getBytes() ); //헤더와 바디를 나누는 부분
			os.write(body);
	}
	
	//에러 처리 메소드
	public void responseError(OutputStream os,String protocol,String msg) throws IOException {
		String[] msg_tokens = msg.split(",");
		File f = new File(msg_tokens[0]);
		if(f.exists()==false) {
			
			return ;
		}
		
		
			byte[] body = Files.readAllBytes(f.toPath());
			String contentType = Files.probeContentType(f.toPath());
			
	   
			os.write( (protocol+msg_tokens[1]).getBytes( "UTF-8" ) );
			os.write( ("Content-Type:"+contentType+"; charset=utf-8\r\n").getBytes( "UTF-8" ) );
			os.write( "\r\n".getBytes() ); //헤더와 바디를 나누는 부분
			os.write(body);
	}
}
