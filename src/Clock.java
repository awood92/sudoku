
import java.util.Timer;
import java.util.TimerTask;


/**
 * Clock.java
 * Keeps track of the time counter for the Sudoku puzzle.
 * @author Blake Ambrose (z3373623)
 */
public class Clock {

    private Timer timer;
    private int seconds;
    private boolean paused;
    
    /**
     * Constructs a Clock object
     */
    public Clock() {
    	seconds = 0;
    	paused = true;
    	timer = new Timer();
    	timer.schedule(new ClockTick(), 0, 1000);
    }

    /**
     * A scheduled timer task that increments the second counter every 1 second
     */
    private class ClockTick extends TimerTask {
    	public void run() {
    		if(!paused) {
    			seconds++;
    		}
    	}
    }
    
    /**
     * Formats the time to string format to HH:MM:SS and returns it
     * @return the formatted time
     */
    public String time() {
    	return String.format("%02d:%02d:%02d",(seconds/3600),((seconds/60)%60),(seconds%60));
    }
    
    /**
     * Resets the clock time back to 0 and sets it to the paused state
     */
    public void reset() {
    	seconds = 0;
    	paused = true;
    }
    
    /**
     * Stops the clock counter
     */
    public void stop() {
    	paused = true;
    }
    
    /**
     * Stops the clock counter
     */
    public void start() {
    	paused = false;
    }
    
    /**
     * Checks the current state of the Clock
     */
    public boolean paused() {
    	return paused;
    }
    
    /**
     * If a user uses a hint, this will incur a 30sec penalty
     */
    public void penalty() {
    	seconds += 30;
    }
    
    /**
     * Returns the amount of seconds that have passed
     */
    public int seconds() {
    	return seconds;
    }
}