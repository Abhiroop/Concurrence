package diningPhilosophers;

import java.util.concurrent.CountDownLatch;

public class Philosopher implements Runnable {
	
	boolean leftFork;
	
	boolean rightFork;
	
	int i;
	
	int eatCounter;
	
	int thinkCounter;
	
	private final CountDownLatch countDownLatch;
	
	public Philosopher(int i, CountDownLatch countDownLatch){
		this.i=i;
		this.leftFork = false;
		this.rightFork = false;
		this.countDownLatch = countDownLatch;
		this.eatCounter = 0;
		this.thinkCounter = 0;
		
	}
	
	public void startEating() throws InterruptedException{
		if(leftFork && rightFork){
			System.out.println(i + " - Started Eating");
			Thread.sleep(5000);
			this.eatCounter++;
			System.out.println(i + " - Finished Eating " + this.eatCounter + "times.");
			setLeftFork(false);
			setRightFork(false);
		}
	}
	
	public void startThinking() throws InterruptedException{
		System.out.println(i + " - Started Thinking");
		Thread.sleep(5000);
		this.thinkCounter++;
		System.out.println(i + " - Finished Thinking " + this.thinkCounter + "times.");
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
