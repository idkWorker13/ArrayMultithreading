package com.github.idkWorker13.ArrayMultithreading;

public class PartRun implements Runnable {
	
	MultithreadingAction action;
	
	// From where to where the Thread has to work on the Array (non inclusive end)
	int beginn;
	int end;
	
	
	public PartRun(AM action, int beginn, int end) {
		this.action = action; // Takes the user defined action from the object
		this.beginn = beginn;
		this.end = end;
	}
	
	
	// Run the defined Action ( that the user specifies when creating an am obj) for object in this list
	public void run() {
		for (int i = beginn; i < end; i++) {
			action.runForEach(i);
		}
	}


}
