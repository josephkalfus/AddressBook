/*
 * Author: Joseph Kalfus
 * Date: June-July 2019
 * This program is a simple address book console application.
 * You can add, edit, delete, and display an addressbook entry.
 * We will use Eclipse AspectJ to log the old info in a seperate "log" file 
 * when a record is updated or deleted.
 */

package com.josephkalfus;

import java.io.IOException;

public class AddressBookDriver { 

	public static void main(String[] args) throws IOException, InterruptedException, IOException {

		AddressBook.startProgram();

	}

}
/*
 * ******** LESSONS LEARNED********* 
 * (1) Using Scanner only ONCE: Use only one
 * scanner and only close one Scanner When you close scanner in any method, it
 * closes it for the entire program so you will get a NOSUCHELEMENT exception (if
 * using it within a single class).
 * The solution is to declare a Scanner (System.in) outside all methods at the
 * top of your program and close it outside all methods at the bottom of your
 * program. Within your methods, you can make a method variable to use scanner
 * within that method only. 
 * (2) Removing lines in file: You can't really remove
 * lines in a file using scanner. It only "scans". Duh. Use the Scanner to read
 * lines and use BufferedWriter to write lines into a temp file. If the line you
 * want to remove is in your input file (being read by Scanner) then just output
 * an empty string. Then do some file swaps with variableName.delete() [the
 * original file] and variableName.renameTo() [to original file name] and
 * variableName.createNewFile() [the temporary file you had set up] See
 * deleteEntry method for example.
 * (3) One method instead of two: Originally, I've had two methods for deleting
 * an entry, and another to edit an entry. The code was mostly the same so
 * we refactored it into one method, and changed the method signature to accept a
 * parameter list. See deleteOrEditEntry() method. 
 * (4) AspectJ and method variables: AspectJ can not look at method variables,
 * so we modified the signature and return type of deleteOrEditEntry() method
 * to return a string of the entry that was being deleted or edited. We can use
 * a point cut and after advise to look at the class variable. See the
 * AddressBookAspect.aj file for more info. 
 * 
 * 
 * ******** Bug Report ***************
 * For some reason, the aspect will not write the entry being edited or deleted to
 *  "addressbook-updates.txt" until AFTER you quit the program. It will write the 
 *  "Record Deleted: " and "Record Updated: " since it's before AspectJ advice. 
 */

