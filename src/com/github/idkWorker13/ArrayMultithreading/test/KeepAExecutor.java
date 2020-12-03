package com.github.idkWorker13.ArrayMultithreading.test;

import com.github.idkWorker13.ArrayMultithreading.AM;
import com.github.idkWorker13.ArrayMultithreading.PartRunHolder;

public class KeepAExecutor {
	
	private static int crewMembers = 3;
	
	public static void main(String[] args) {
		
		PartRunHolder partRunHolder = new PartRunHolder();
		
		
		while(true) {
			String[] strings = new String[crewMembers];
			new AM(crewMembers, partRunHolder) {
				
				@Override
				public void runForEach(int i) {
					strings[i] = "	-> Aye ("+i+" CrewMember)";
				}
			};
			
			for (int i = 0; i < strings.length; i++) {
				System.out.println(strings[i]);
			}
			
			System.out.println("");
			
			try {
				Thread.sleep(1200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
