package thread;


public class ThreadEx03 {

	public static void main(String[] args) {
		Thread thread1 = new Thread(new UppercaseAlphabetRunnableImpl()); //만약 Thread extends면 그 Thread를 일일이 써야됨.
		//보다 더 유연함
		Thread thread2 = new AlphaThread();
		Thread thread3 = new DigitThread();
		
		thread1.start();
		thread2.start();
		thread3.start();
		
		

	}
}
