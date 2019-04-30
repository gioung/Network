package thread;

public class UppercaseAlphabetRunnableImpl extends UppercaseAlphabet implements Runnable{

	@Override
	public void run() {
		super.print();
	}
	
}
