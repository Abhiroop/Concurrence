package diningPhilosophers;

import java.util.concurrent.CountDownLatch;

public class Philosopher implements Runnable {
	
	boolean leftFork;
	
	boolean rightFork;
	
	int i;
	
	private final CountDownLatch countDownLatch;
	
	public Philosopher(int i, CountDownLatch countDownLatch){
		this.i=i;
		this.leftFork = false;
		this.rightFork = false;
		this.countDownLatch = countDownLatch;
		
	}
	
	public void startEating() throws InterruptedException{
		System.out.println(i + " - Started Eating");
		Thread.sleep(5000);
		System.out.println(i + " - Finished Eating");
	}
	
	public void startThinking() throws InterruptedException{
		System.out.println(i + " - Started Thinking");
		Thread.sleep(5000);
		System.out.println(i + " - Finished Thinking");
	}
	
	public boolean isLeftFork() {
		return leftFork;
	}

	public void setLeftFork(boolean leftFork) {
		this.leftFork = leftFork;
	}

	public boolean isRightFork() {
		return rightFork;
	}

	public void setRightFork(boolean rightFork) {
		this.rightFork = rightFork;
	}

	public void run() {
		countDownLatch.countDown();
		while(true){
			try {
				startEating();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				startThinking();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
