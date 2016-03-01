package diningPhilosophers;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Lists;

public class GuardImpl implements Guard {

	boolean forks[];
	
	List<Thread> philosopherList;
	
	AtomicInteger gate;
	
	List<Integer> bannedPhilosophers = Lists.newArrayList();
	
	GuardImpl(boolean[] forks, List<Thread> philosopherList){
		this.forks = forks;
		this.philosopherList = philosopherList;
		this.gate = new AtomicInteger(0);
	}
	
	//id is the id of the thread requesting
	@Override
	public boolean requestResource(int id) {
		
		gate.incrementAndGet();
		
		if(gate.get()==philosopherList.size()){
			gate.set(0);
		}
		
		synchronized(this){
			if(bannedPhilosophers.contains(id))
				return false;

			if(id == philosopherList.size() - 1){
				if(forks[0] && forks[philosopherList.size()-1]){
					forks[0] = false;
					forks[philosopherList.size()-1] = false;
					bannedPhilosophers.add(0);
					bannedPhilosophers.add(id-1);
					return true;
				}
			}
			else{
				if(forks[id] && forks[id+1]){
					forks[id] = false;
					forks[id+1] = false;
					bannedPhilosophers.add(id+1);
					if(id==0){
						bannedPhilosophers.add(philosopherList.size()-1);
					}
					else{
						bannedPhilosophers.add(id-1);
					}
					return true;
				}
			}

			return false;
		}
				
	}

	@Override
	public synchronized void submitResource(int id) {
		forks[id] = true;
		if(id==philosopherList.size()-1){
			forks[0] = true;
		}
		forks[id+1] = true;
		
	}

}
