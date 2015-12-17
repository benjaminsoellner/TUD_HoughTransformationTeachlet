package appLogic;

/**
 * This Class is responsible for the events of the hough- animation.
 */
public interface Simulatable {
	
	/**
	 * This method inializes the simulation and calls some methods to 
	 * control the hough- animation.
	 * @param s
	 */
	public void init(Simulation s);

	public void start();
	public void pause();
	public void resume();
	public void reset();
	
	public void update();
	
	
}
