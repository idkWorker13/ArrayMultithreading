package com.github.idkWorker13.ArrayMultithreading.test;

import com.github.idkWorker13.ArrayMultithreading.AM;
import com.github.idkWorker13.ArrayMultithreading.MultithreadingAction;
import com.github.idkWorker13.ArrayMultithreading.ActionExecutor;

public class KeepAExecutor {
	
	private static int crewMembers = 3;
	
	public static void main(String[] args) {
		
		ActionExecutor actionExecutor = new ActionExecutor();
		
		
		for(int i = 0; i <= 5; i++) {
			String[] strings = new String[crewMembers];
			new AM(crewMembers, actionExecutor) {
				
				@Override
				public void runForEach(int i) {
					strings[i] = "	-> Aye ("+i+" CrewMember)";
				}
			};
			
			for (int a = 0; a < strings.length; a++) {
				System.out.println(strings[a]);
			}
			
			System.out.println("");
			
			try {
				Thread.sleep(1200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		// You can use a Multithreading object and directly execute it on a partRunHolder
		String[] members = {"Joe", "Bill", "Tyler"};
		
		MultithreadingAction action = (int i) -> {
			System.out.println(members[i]);
		};
		
		actionExecutor.executeTasks(action, members.length);
		
		System.out.println("---------------");
		System.out.println(" And now again");
		System.out.println("---------------");
		
		actionExecutor.executeTasks(action, members.length);
		
		actionExecutor.executeTasks(action, 0);
		
		// Remember when using a custom ActionExecutor to shut it down
		actionExecutor.shutdown();
	}

}
