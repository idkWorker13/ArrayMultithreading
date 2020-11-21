package com.github.idkWorker13.ArrayMultithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AM implements MultithreadingAction {
	
	private int i_lenght;
	private ExecutorService executorService;
	private int processorCount;
	
	private PartRun[] runnables;
	
	// Standart Run
	public AM(int i_lenght) {
		
		if (i_lenght <= 0) { // Checks if the programm got a good start
			System.err.println("AM should only be initialised normaly with integer highter than 0, " + i_lenght + " was tried.");
			Thread.dumpStack();
		};
		
		this.i_lenght = i_lenght;	
		this.processorCount = Runtime.getRuntime().availableProcessors();
		this.executorService = Executors.newFixedThreadPool(processorCount); // Create a Pool equal to the number of processors we have
		run();
	}
	
	
	
	
	// Creates a Stack of Threads and runs them in threads
	private void run() {
		
		if (i_lenght <= processorCount) { // We have a processor for each job (MultithreadingAction) 
			initLessAction(i_lenght);
		} else { // Multiple Jobs (MultithreadingAction) per thread
			initPlusActions();
		}
		
		
		
		
		// Shutdown Executot
		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (Exception e) {
			System.err.println("Your task took to long!");
		}
		
		
	}
	
	// Used when there are multiple jobs for each thread
	private void initPlusActions() {
		// start our threads and calculate variables that determine the 
		runnables = new PartRun[processorCount];
		int threadSpectrumWidth = i_lenght / processorCount; // How much work does each thread have to do
		int currentSpecBeginn = 0;
		int currentSpecEnd = 0;
		
		// Initialises the threads, and start running them on the executor
		for (int i = 0; i < runnables.length; i++) { 
			currentSpecEnd = currentSpecBeginn + threadSpectrumWidth;
					
			if (currentSpecEnd >= i_lenght) { // Ensures that no thread goes over the limit
				currentSpecEnd = i_lenght;
			}
			
			if ((i+1)==runnables.length) { // Ensures that the last thread does all remaining work
				currentSpecEnd = i_lenght;
			}
					
			runnables[i] = new PartRun(this, currentSpecBeginn, currentSpecEnd);
			executorService.execute(runnables[i]);
					
			currentSpecBeginn = currentSpecBeginn + threadSpectrumWidth;
		}
	}
	
	// Used when each Action gets its own Thread
	private void initLessAction(int nActions) {
		runnables = new PartRun[nActions]; // Create a runnable for each Action (i_lenght)
		
		for (int i = 0; i < nActions; i++) {
			runnables[i] = new PartRun(this, i, (i+1)); // Creta a Part run, thats only has one job
			executorService.execute(runnables[i]);
		}
	}
	
	/**
	 *  Constructor with more options (For testing)
	 */
	
	public AM(int i_lenght, int prossorCount) {
			
		if (i_lenght <= 0) { // Checks if the programm got a good start
			System.err.println("AM should only be initialised normaly with integer highter than 0, " + i_lenght + " was tried.");
			Thread.dumpStack();
		};
			
		this.i_lenght = i_lenght;	
		this.processorCount = prossorCount;
		this.executorService = Executors.newFixedThreadPool(processorCount); // Create a Pool with the number of proccessors from given to us
		run();
	}
	
	
}
