package com.github.idkWorker13.ArrayMultithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AM implements MultithreadingAction{
	
	private int i_lenght;
	private ExecutorService executorService;
	private int processorCount;
	
	private PartRun[] runnables;
	
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
		int threadSpectrumWidth = i_lenght / processorCount; // How much work does each thread have to do
		runnables = new PartRun[processorCount];
		
		int currentSpecBeginn = 0;
		int currentSpecEnd = 0;
		
		// Initialises the threads, and start running them on the executor
		for (int i = 0; i < runnables.length; i++) { 
			
			currentSpecEnd = currentSpecBeginn + threadSpectrumWidth;
			
			if ((i+1) == runnables.length) { // Ensures that the last thread takes everything from the array
				currentSpecEnd = i_lenght;
			}
			
			runnables[i] = new PartRun(this, currentSpecBeginn, currentSpecEnd);
			executorService.execute(runnables[i]);
			
			currentSpecBeginn = currentSpecBeginn + threadSpectrumWidth;
		}
		
		
		
		executorService.shutdown();
		
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (Exception e) {
			System.err.println("Your task took to long!");
		}
		
		
	}
	
	
}
