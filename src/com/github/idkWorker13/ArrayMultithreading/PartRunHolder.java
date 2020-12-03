package com.github.idkWorker13.ArrayMultithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * This Object is a reusable Holder for PartRun Runnables and the Executor service
 * 
 */
public class PartRunHolder {
	
	// initially setUp parameters
	private PartRun[] partRuns;
	private ExecutorService executorService;
	private int nRunnables;
	
	// parameter for the current run
	private int nTasks;
	private CountDownLatch threadsRemaining;
	
	/*
	 * does the init work based on Parameters form the Constructors
	 */
	private void setUp() {
		
		// Set up the Runnables
		partRuns = new PartRun[nRunnables];
		for (int i = 0; i < partRuns.length; i++) {
			partRuns[i] = new PartRun();
		}
		
		executorService = Executors.newFixedThreadPool(nRunnables);


	}
	
	/*  Constructors for diffrent use cases that call ::setUp(int nRunnables)
	 *  ||
	 *  \/
	 */
	
	/*
	 * Creates a PartRunHolder with a specified number of PartRuns Runnables
	 */
	public PartRunHolder(int nRunnables) {
		
		if (nRunnables <= 0) { // Checks if the programm got a good start
			System.err.println("PartRunHolder should only be initialised normaly with integer highter than 0, " + nRunnables + " was tried.");
			Thread.dumpStack();
			return;
		};
		
		this.nRunnables = nRunnables;
		setUp();
	}
	
	/*
	 * Creates a PartRunHolder dependant on the number of processors
	 */
	public PartRunHolder() {
		
		
		
		
		this.nRunnables = Runtime.getRuntime().availableProcessors();
		setUp();
	}
	
	
	/*
	 * Runs the tasks based on a AM Object and the number of tasks
	 */
	public void executeTasks(AM action) {
		
		// change how many tasks we have based on the AM Object
		nTasks = action.getTasksLenght();
		
		if (nTasks <= nRunnables) { // We have a processor for each job (MultithreadingAction) 
			initLessAction(action);
		} else { // Multiple Jobs (MultithreadingAction) per thread
			initPlusActions(action);
		}
		
			
		try {
			threadsRemaining.await();
		} catch (InterruptedException e) {
			System.err.println("could not wait");
			e.printStackTrace();
		}
	}
	
	
		
	// Used when there are multiple jobs for each thread
	private void initPlusActions(AM action) {
		
		// Create a latch corresponding to the number of runnables
		threadsRemaining = new CountDownLatch(nRunnables);
		
		int threadSpectrumWidth = nTasks / nRunnables; // How much work does each thread have to do
		int currentSpecBeginn = 0;
		int currentSpecEnd = 0;
			
		// Initialises the threads, and start running them on the executor
		for (int i = 0; i < partRuns.length; i++) { 
			currentSpecEnd = currentSpecBeginn + threadSpectrumWidth;
					
			if (currentSpecEnd >= nTasks) { // Ensures that no thread goes over the limit
				currentSpecEnd = nTasks;
			}
				
			if ((i+1)==partRuns.length) { // Ensures that the last thread does all remaining work
				currentSpecEnd = nTasks;
			}
						
			partRuns[i].setUp(action, currentSpecBeginn, currentSpecEnd, threadsRemaining);
			executorService.execute(partRuns[i]);
						
			currentSpecBeginn = currentSpecBeginn + threadSpectrumWidth;
		}
	}
	
	// Used when each Action gets its own Thread
	private void initLessAction(AM action) {	
		
		// Create a latch corresponding to the number of actually executed runnables
		threadsRemaining = new CountDownLatch(nTasks);
		
		for (int i = 0; i < nTasks; i++) {
			partRuns[i].setUp(action, i, (i+1), threadsRemaining); // Creta a Part run, thats only has one job
			executorService.execute(partRuns[i]);
		}
	}
	
	public void shutdown() {
		executorService.shutdown();
	}
	
	
	

}
