package com.github.idkWorker13.ArrayMultithreading;

import java.util.concurrent.CountDownLatch;

public class PartRun implements Runnable {
	
	MultithreadingAction action;
	
	// From where to where the Thread has to work on the Array (non inclusive end)
	int beginn;
	int end;
	
	// Used in telling when the executor has finished
	private CountDownLatch threadsRemaining;
	
	public PartRun() {
	}
	
	// Used to set the thread up with its paremeters
	public void setUp(AM action, int beginn, int end, CountDownLatch threadsRemaining) {
		this.action = action; // Takes the user defined action from the object
		this.beginn = beginn;
		this.end = end;
		this.threadsRemaining = threadsRemaining;
	}
	
	
	// Run the defined Action ( that the user specifies when creating an am obj) for object in this list
	public void run() {
		
		for (int i = beginn; i < end; i++) {
			
			/**The following code is to test the VariableProssesor... .java test
			 * Here at i=42 a doble result is shown
			if (i == 42) {
				action.runForEach(i);
			} */
			
			action.runForEach(i);
			
		}
		
		threadsRemaining.countDown();
	}


}
