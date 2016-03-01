package diningPhilosophers;

import java.util.concurrent.CountDownLatch;

public class Philosopher implements Runnable {
	
	boolean leftFork;
	
	boolean rightFork;
	
	int id;
	
	int eatCounter;
	
	int thinkCounter;
	
	private final CountDownLatch countDownLatch;
	
	Guard guard;
	
	private boolean forks[];
	
	private boolean smartPhilosopher = false;
	

	public Philosopher(int id, CountDownLatch countDownLatch, boolean forks[]){
		this.id=id;
		this.leftFork = false;
		this.rightFork = false;
		this.countDownLatch = countDownLatch;
		this.eatCounter = 0;
		this.thinkCounter = 0;
		this.forks = forks;
		
	}
	
	public Philosopher(int id, CountDownLatch countDownLatch, boolean forks[], Guard guard){
		this.id=id;
		this.leftFork = false;
		this.rightFork = false;
		this.countDownLatch = countDownLatch;
		this.eatCounter = 0;
		this.thinkCounter = 0;
		this.forks = forks;
		this.guard = guard;
	}
	
	public void startEating() throws InterruptedException{
		pickupFork();
		if(leftFork && rightFork){
			System.out.println(id + " - Started Eating");
			Thread.sleep(5000);
			this.eatCounter++;
			System.out.println(id + " - Finished Eating " + this.eatCounter + " times.");
			dropFork();
			this.thinkCounter=0;
		}
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
		Thread.sleep(5000);
		this.thinkCounter++;
		if(this.thinkCounter>=5)
			System.out.println(id+" - I am starving!!");
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setSmartPhilosopher(boolean smartPhilosopher) {
		this.smartPhilosopher = smartPhilosopher;
	}

	public void run() {
		countDownLatch.countDown();
		if(smartPhilosopher){
			while(true){
				try {
					startSmartEating();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		else{
			while(true){
				try {
					startEating();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	

	private void startSmartEating() throws InterruptedException{
		
		boolean state = guard.requestResource(getId());
		if(state){
			System.out.println(id + " - Started Eating");
			Thread.sleep(5000);
			this.eatCounter++;
			System.out.println(id + " - Finished Eating " + this.eatCounter + " times.");
			guard.submitResource(getId());
		}
		startThinking();
		
	}

}
