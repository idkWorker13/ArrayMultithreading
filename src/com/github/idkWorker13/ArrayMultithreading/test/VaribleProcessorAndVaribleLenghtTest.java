package com.github.idkWorker13.ArrayMultithreading.test;

import com.github.idkWorker13.ArrayMultithreading.AM;

// To test if everything gets called exactly 1 time
public class VaribleProcessorAndVaribleLenghtTest {
	
	
	static int maxCycles = 10000; // The maximum i lenght that gets tested
	static int maxProcessors = 64; // Till how much cpus sizes get tested, (how much threads will be spawned)
	
	// Test everything based on the above mentioned class
	public static void main(String[] args) {
		// Include the stated values
		maxCycles = maxCycles + 1;
		maxProcessors = maxProcessors + 1;
		
		// Run it for all stated professors
		for (int i = 1; i < maxProcessors; i++) { // Start wiht 0 Processor
			testProcessor(i);
		}
		
	}
	
	
	private static boolean testProcessor(int processorCount) {
		for (int i = 1; i < maxCycles; i++) { // Array may not be zero
			if (test(i, processorCount)) {
				// Everything fine
			} else {
				// Everything is on fire
				System.out.println("(X) Error at processorTest: "+ processorCount);
				return false;
			}
		}
		System.out.println("(Y) No Problem at processorTest: "+ processorCount);
		return true;
	}
	
	
	private static boolean test(int i_lenght, int processorCount) {
		
		// Create a new array of ints and fill them with zero
		int[] ints = new int[i_lenght]; 
		for (int i = 0; i < ints.length; i++) {
			ints[i] = 0;
		}
		
		// run AM to add one to every number
		new AM(ints.length, processorCount) {
			
			@Override
			public void runForEach(int i) {
				ints[i] = ints[i] + 1;
			}
		};
		
		// Check if the runForEch was called once for every instruction
		// When every number in ints[] is 1 report true else false
		for (int i = 0; i < ints.length; i++) {
			if (ints[i]==1) {
				// Everything is ok
			} else {
				// Everything is on fire
				System.err.println(" -> proccesorCount: "+ processorCount + " i_lenght: " + i_lenght + " at: " + i + " with: " + ints[i]);
				return false;
			}
		}
		
		
		return true;
		
	}

}
