package com.github.idkWorker13.ArrayMultithreading.test;

import com.github.idkWorker13.ArrayMultithreading.AM;

public class HelloWorld {

	public static void main(String[] args) {
		
		String[] strings = new String[5];
		
		new AM(strings.length) {
			
			@Override
			public void runForEach(int i) {
				strings[i] = "Hello World";
				strings[i] = strings[i] + " \n";
				strings[i] = strings[i] + "from Number: " + i; 
				strings[i] = strings[i] + " \n";
			}
		};
		
		for (int i = 0; i < strings.length; i++) {
			System.out.println(strings[i]);
		}
	}

}
