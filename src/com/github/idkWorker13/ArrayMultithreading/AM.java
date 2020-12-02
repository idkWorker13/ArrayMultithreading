package com.github.idkWorker13.ArrayMultithreading;

import com.github.idkWorker13.ArrayMultithreading.PartRunHolder;;

public abstract class AM implements MultithreadingAction {
	
	// how often ::runForEach is called
	private int nTasks;
	
	private PartRunHolder partRunHolder;
	
	/*
	 * Runs a task, a specified number of times
	 * creates its own Runnables and ExecutorService
	 */
	public AM(int nTasks) {
		
		if (nTasks <= 0) { // Checks if the programm got a good start
			System.err.println("AM should only be initialised normaly with integer highter than 0, " + nTasks + " was tried.");
			Thread.dumpStack();
			return;
		};
		
		this.partRunHolder = new PartRunHolder();
		this.nTasks = nTasks;	
		run();
		
		// As we created our own we need to shutdown the partRunHolder again
		this.partRunHolder.shutdown();
	}
	
	/*
	 * Runs a task, a specified number of times,
	 * uses a PartRunHolder to keep an executor and Runnables
	 */
	public AM(int nTasks, PartRunHolder partRunHolder) {
		
		if (nTasks <= 0) { // Checks if the programm got a good start
			System.err.println("AM should only be initialised normaly with integer highter than 0, " + nTasks + " was tried.");
			Thread.dumpStack();
		};
		
		this.partRunHolder = partRunHolder;
		this.nTasks = nTasks;	
		run();
	}
	
	
	private void run() {
		partRunHolder.executeTasks(this);
	}
	
	/**
	 *  Constructor with more options (For testing)
	 */
	public AM(int nTasks, int prossorCount) {
			
		if (nTasks <= 0) { // Checks if the programm got a good start
			System.err.println("AM should only be initialised normaly with integer highter than 0, " + nTasks + " was tried.");
			Thread.dumpStack();
			return;
		};
		
		this.partRunHolder = new PartRunHolder(prossorCount);
		this.nTasks = nTasks;	
		run();
		
		// As we created our own we need to shutdown the partRunHolder again
		this.partRunHolder.shutdown();
	}
	
	/*
	 * Returns how many Tasks, how often ::runForEach is called
	 */
	public int getTasksLenght() {
		return nTasks;
	}
	
	
}
