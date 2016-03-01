package diningPhilosophers;

public interface Guard {
	
	public boolean requestResource(int id);

	public void submitResource(int id);

}
