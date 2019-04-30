package thread;

public class DigitThread extends Thread {
	
	
	
	@Override  //완전 구현 (부모껄 쓰지 않음)
	public void run() {
		for(int i=0;i<10;i++) {
			System.out.print(i+" ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
		
	}
}
