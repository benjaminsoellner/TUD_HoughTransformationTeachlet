package appLogic;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The Simulation contains the control elements for the hough transformation and
 * the functionality behind them.
 * 
 */
public class Simulation {

	private List<Simulatable> simulatable;
	
	private int time;
	
	//speed of update calls (per second)
	private double speed;
	
	private int nrOfUpdates;
	

	
	private boolean pause;
	
	private Timer timer;

	/**
	 * Standard constructor
	 * @param simulatable
	 */
	public Simulation(List<Simulatable> simulatable) {
		this.simulatable = simulatable;
		time=0;
		speed=0.001;
		pause=true;
		timer=new Timer();
		
		Iterator<Simulatable> it = simulatable.iterator();
		while(it.hasNext()){
			it.next().init(this);
		}
	}
	
	/**
	 * Configuration - sets the speed and the number of updates. 
	 * @param nrOfUpdates
	 * @param speed
	 */
	public void configure(int nrOfUpdates,double speed){
		boolean playedBefore=!pause;
		if(playedBefore) pause();
		this.speed=Math.max(speed,0.001);
		this.nrOfUpdates=nrOfUpdates;
		if(playedBefore) play();
	}
	
	/**
	 * Configuration - sets the speed 
	 * @param speed
	 */
	public void configure(double speed){
		boolean playedBefore=!pause;
		if(playedBefore) pause();
		this.speed=Math.max(speed,0.001);
		if(playedBefore) play();
	}

	/**
	 * Returns the number of updates
	 * @return nrOfUpdates
	 */
	public int getNrOfUpdates() {
		return nrOfUpdates;
	}

	/**
	 * Starts the simulation 
	 */
	public void play(){
		if(!pause) return;
		pause=false;
		Iterator<Simulatable> it = simulatable.iterator();
		while(it.hasNext()){
			if(time==0.0)
				it.next().start();
			else
				it.next().resume();
		}		
		
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				update();			
			}		
		},(int)(1000/speed)+1,(int)(1000/speed)+1);

		
	}

	/**
	 * Returns the time
	 * @return time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * If the simulation has not finished yet, this method increments the time 
	 * and updates the timer
	 */
	private synchronized void update(){
		time++;
		if(time>nrOfUpdates){
			timer.cancel();
			timer=new Timer();

			pause=true;
			return;
		}
		Iterator<Simulatable> it = simulatable.iterator();
		while(it.hasNext()){
			it.next().update();
		}	
		
	}
	
	/**
	 * Stop the simulation
	 */
	public void pause(){
		if(pause) return;
		timer.cancel();
		timer=new Timer();
		pause=true;
		Iterator<Simulatable> it = simulatable.iterator();
		while(it.hasNext()){
			it.next().pause();
		}		
	}

	/**
	 * Set the timer back to 0 and clear the simulation
	 */
	public void reset(){
		if(time==0) return;
		time=0;
		timer.cancel();

		timer=new Timer();
		pause=true;
		Iterator<Simulatable> it = simulatable.iterator();
		while(it.hasNext()){
			it.next().reset();
		}		
	}
	
	/**
	 * Go one step forward
	 */
	public void step(){
		update();
	}
	
	
	/**
	 * Returns the speed
	 * @return speed
	 */
	public double getSpeed() {
		return speed;
	}




	/**
	 * Return if the simulation is paused
	 * @return true if the simulation is paused or false otherwise
	 */
	public boolean isPause() {
		return pause;
	}
	


}
