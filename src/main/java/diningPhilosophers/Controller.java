package diningPhilosophers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.google.common.collect.Lists;

public class Controller {

	public static final int NO_OF_PHILOSOPHERS = 5;

	public static void main(String args[]){

		List<Thread> philosopherList=Lists.newArrayList();
		
		boolean[] forks = new boolean[NO_OF_PHILOSOPHERS];
		
		Arrays.fill(forks, true);

		final CountDownLatch countDownLatch = new CountDownLatch(NO_OF_PHILOSOPHERS);

		for(int i=0;i<NO_OF_PHILOSOPHERS;i++){
			Philosopher philosopher = new Philosopher(i,countDownLatch, forks);
			Thread thread = new Thread(philosopher);
			philosopherList.add(thread);
		}

		philosopherList.forEach(Thread::start);

		try{
			countDownLatch.await();  
		}catch(InterruptedException e){
			e.printStackTrace();
		}

	}

}
