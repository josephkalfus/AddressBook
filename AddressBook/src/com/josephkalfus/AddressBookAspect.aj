package com.josephkalfus;

import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;

public aspect AddressBookAspect {

	pointcut uniqueLog(boolean b) : call (public static String deleteOrEditEntry(boolean)) && args (b);
	
	before(boolean b) : uniqueLog(b) {
		if (b == true) {

			try {
				Writer fileOutput;
				fileOutput = new BufferedWriter(new FileWriter("addressbook-updates.txt", true));
				fileOutput.write("Record Updated: ");
				fileOutput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			try {
				Writer fileOutput;
				fileOutput = new BufferedWriter(new FileWriter("addressbook-updates.txt", true));
				fileOutput.write("Record Deleted: ");
				fileOutput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	pointcut getNamePointcut() : get(String AddressBook.discardedEntry);  
	// Advice declaration
	after( ) returning(String value) : getNamePointcut( )   {
		
		
		try {
			Writer fileOutput;
			fileOutput = new BufferedWriter(new FileWriter("addressbook-updates.txt", true));
			fileOutput.write(value + "\n");
			fileOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
