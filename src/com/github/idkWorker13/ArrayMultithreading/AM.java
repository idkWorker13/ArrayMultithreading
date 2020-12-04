package com.github.idkWorker13.ArrayMultithreading;

import com.github.idkWorker13.ArrayMultithreading.ActionExecutor;
import com.github.idkWorker13.ArrayMultithreading.exceptions.NoWorkException;;

public abstract class AM implements MultithreadingAction {
	
	// how often ::runForEach is called
	private int nTasks;
	
	private ActionExecutor actionExecutor;
	
	/*
	 * Runs a task, a specified number of times
	 * creates its own Runnables and ExecutorService
	 */
	public AM(int nTasks) {
		
		if (nTasks <= 0) { // Checks if the programm got a good start
			throw new NoWorkException(new String("AM should only be initialised with integer highter than 0, " + nTasks + " was tried."));
		};
		
		this.actionExecutor = new ActionExecutor();
		this.nTasks = nTasks;	
		run();
		
		// As we created our own we need to shutdown the actionExecutor again
		this.actionExecutor.shutdown();
	}
	
	/*
	 * Runs a task, a specified number of times,
	 * uses a ActionExecutor to keep an executor and Runnables
	 */
	public AM(int nTasks, ActionExecutor actionExecutor) {
		
		if (nTasks <= 0) { // Checks if the programm got a good start
			System.err.println("AM should only be initialised normaly with integer highter than 0, " + nTasks + " was tried.");
			Thread.dumpStack();
		};
		
		this.actionExecutor = actionExecutor;
		this.nTasks = nTasks;	
		run();
	}
	
	
	private void run() {
		actionExecutor.executeTasks(this, nTasks);
	}
	
	/**
	 *  Constructor with more options (For testing)
	 */
	protected AM(int nTasks, int prossorCount) {
			
		if (nTasks <= 0) { // Checks if the programm got a good start
			throw new NoWorkException(new String("AM should only be initialised normaly with integer highter than 0, " + nTasks + " was tried."));
		};
		
		this.actionExecutor = new ActionExecutor(prossorCount);
		this.nTasks = nTasks;	
		run();
		
		// As we created our own we need to shutdown the actionExecutor again
		this.actionExecutor.shutdown();
	}
	
	/*
	 * Returns how many Tasks, how often ::runForEach is called
	 */
	protected int getTasksLenght() {
		return nTasks;
	}
	
	
}
