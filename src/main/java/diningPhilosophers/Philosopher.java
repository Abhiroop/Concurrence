package diningPhilosophers;

import java.util.concurrent.CountDownLatch;

public class Philosopher implements Runnable {
	
	boolean leftFork;
	
	boolean rightFork;
	
	int id;
	
	int eatCounter;
	
	int thinkCounter;
	
	private final CountDownLatch countDownLatch;
	
	private boolean forks[];
	
	public Philosopher(int id, CountDownLatch countDownLatch, boolean forks[]){
		this.id=id;
		this.leftFork = false;
		this.rightFork = false;
		this.countDownLatch = countDownLatch;
		this.eatCounter = 0;
		this.thinkCounter = 0;
		this.forks=forks;
		
	}
	
	public void startEating() throws InterruptedException{
		if(leftFork && rightFork){
			pickupFork();
			System.out.println(id + " - Started Eating");
			Thread.sleep(5000);
			this.eatCounter++;
			System.out.println(id + " - Finished Eating " + this.eatCounter + " times.");
		}
		dropFork();
		startThinking();
	}
	
	private void dropFork() {
		if(!forks[id]){
			leftFork = false;
			forks[id] = true;
		}
		
		if(id<forks.length - 1 && !forks[id+1]){
			rightFork = false;
			forks[id+1] = true;
		}
		
		if(id==forks.length-1 && !forks[0]){
			rightFork = false;
			forks[0] = true;
		}
	}

	private void pickupFork() {
		
		if(forks[id]){
			leftFork = true;
			forks[id] = false;
		}
		
		if(id<forks.length - 1 && forks[id+1]){
			rightFork = true;
			forks[id+1] = false;
		}
		
		if(id==forks.length-1 && forks[0]){
			rightFork = true;
			forks[0] = false;
		}
	}

	public void startThinking() throws InterruptedException{
		System.out.println(id + " - Started Thinking");
		Thread.sleep(5000);
		this.thinkCounter++;
		System.out.println(id + " - Finished Thinking " + this.thinkCounter + " times.");
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
				e.printStackTrace();
			}
		}
		
	}

}
