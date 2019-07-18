package com.josephkalfus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AddressBook {
	
	// Class Variables
	public static String[] anEntry = new String[7];
	public static boolean continueDoLoop = true;
	final static int SLEEP_DISPLAY = 3;
	public static String discardedEntry = new String();

	static Scanner input = new Scanner(System.in);
	
	/**
	 * Creates an addressbook entry and validates using
	 * regex. Once entry is valid, it saves to output file.
	 * 
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 * @throws IOException
	 */
		public static void createEntry() throws FileNotFoundException, InterruptedException, IOException {
		boolean goodInput = false;
		input.reset();
		do {
			goodInput = false;
			System.out.println("Enter first name:");
			String discard = input.nextLine();
			String inputs = input.nextLine();
			if (inputs.matches("[a-zA-Z]{2,}")) {
				anEntry[0] = inputs;
				goodInput = true;
			} else {
				System.out.println("Bad Input. Example: John");
				goodInput = false;
			}

		} while (goodInput == false);

		do {
			goodInput = false;
			System.out.println("Enter last name:");
			String inputs = input.nextLine();
			if (inputs.matches("[a-zA-Z]{2,}")) {
				anEntry[1] = inputs;
				goodInput = true;
			} else {
				System.out.println("Bad Input. Example: Smith");
				goodInput = false;
			}

		} while (goodInput == false);

		do {
			goodInput = false;
			System.out.println("Enter street address:");
			String inputs = input.nextLine();
			if (inputs.matches("[a-zA-Z_0-9\\s]{5,}")) {
				anEntry[2] = inputs;
				goodInput = true;
			} else {
				System.out.println("Bad Input. Example: 123 Main Street");
				goodInput = false;
			}

		} while (goodInput == false);

		do {
			goodInput = false;
			System.out.println("Enter city:");
			String inputs = input.nextLine();
			if (inputs.matches("[a-zA-Z\\s]{3,}")) {
				anEntry[3] = inputs;
				goodInput = true;
			} else {
				System.out.println("Bad Input. Example: Balimore");
				goodInput = false;
			}

		} while (goodInput == false);

		do {
			goodInput = false;
			System.out.println("Enter state: (2 Letter Code, uppercase)");
			String inputs = input.nextLine();
			if (inputs.matches("[A-Z]{2,2}")) {
				anEntry[4] = inputs;
				goodInput = true;
			} else {
				System.out.println("Bad Input. Example: MD");
				goodInput = false;
			}

		} while (goodInput == false);

		do {
			goodInput = false;
			System.out.println("Enter zip code (5 digit number)");
			String inputs = input.nextLine();
			if (inputs.matches("[0-9]{5}")) {
				anEntry[5] = inputs;
				goodInput = true;
			} else {
				System.out.println("Bad Input. Example: 21702");
				goodInput = false;
			}

		} while (goodInput == false);

		do {
			goodInput = false;
			System.out.println("Enter phone number (10 digit number)");
			String inputs = input.nextLine();
			if (inputs.matches("[0-9]{10}")) {
				anEntry[6] = inputs;
				goodInput = true;
			} else {
				System.out.println("Bad Input. Example: 2025051234");
				goodInput = false;
			}

		} while (goodInput == false);

		File file = new File("addressbook.txt");
		if (!file.exists()) {
			System.out.println("File does not exist");
			System.exit(1);
		}
		try {
			Writer fileOutput;
			fileOutput = new BufferedWriter(new FileWriter("addressbook.txt", true));

			fileOutput.write(anEntry[0] + "\t");
			fileOutput.write(anEntry[1] + "\t");
			fileOutput.write(anEntry[2] + "\t");
			fileOutput.write(anEntry[3] + "\t");
			fileOutput.write(anEntry[4] + "\t");
			fileOutput.write(anEntry[5] + "\t");
			fileOutput.write(anEntry[6]);
			fileOutput.write("\n");
			fileOutput.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Entry Added");
		TimeUnit.SECONDS.sleep(SLEEP_DISPLAY);

		AddressBook.startProgram();
	}

	/**
	 * Displays addressbook entries. Loops through array.
	 * 
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 * @throws IOException
	 */	
	public static void displayEntries() throws FileNotFoundException, InterruptedException, IOException {
		File displayEntryFile = new File("addressbook.txt");
		Scanner displayEntryInput = new Scanner(displayEntryFile);

		int count = 1;
		while (displayEntryInput.hasNextLine()) {
			String entry = displayEntryInput.nextLine();
			System.out.print(count + ": ");
			System.out.print(entry);
			System.out.println("\n");
			count++;
		}
		displayEntryInput.close();

		TimeUnit.SECONDS.sleep(SLEEP_DISPLAY);

		AddressBook.startProgram();
	}

	/**
	 * Deletes or edits and addressbook entry depending on the
	 * parameter passed.
	 * 
	 * @param editFlag
	 * @return
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static String deleteOrEditEntry(boolean editFlag)
			throws FileNotFoundException, InterruptedException, IOException {

		File deleteEntryFile = new File("addressbook.txt");
		File tempFile = new File("temp.txt");

		Scanner deleteEntryInput = new Scanner(deleteEntryFile);
		Scanner deleteEntryDisplay = new Scanner(deleteEntryFile);

		int counts = 1;
		while (deleteEntryDisplay.hasNextLine()) {
			String entry = deleteEntryDisplay.nextLine();
			System.out.print(counts + ": ");
			System.out.print(entry);
			System.out.println("\n");
			counts++;
		}

		if (editFlag == true) {
			System.out.println("Select which entry number you want to edit");
		} else {
			System.out.println("Select which entry number you want to delete");
		}

		int selection = input.nextInt();

		if (selection < 1 || selection >= counts) {
			System.out.println("Invalid Selection!");
			AddressBook.startProgram();
		}

		Writer fileOutput;
		fileOutput = new BufferedWriter(new FileWriter("temp.txt"));

		int count = 1;
		while (deleteEntryInput.hasNextLine()) {
			if (selection == count) {

				// Delete entry line

				discardedEntry = deleteEntryInput.nextLine();
				fileOutput.write("");

			} else {
				fileOutput.write(deleteEntryInput.nextLine());
				fileOutput.write("\n");
			}
			count++;
		}

		fileOutput.close();
		deleteEntryFile.delete();
		tempFile.renameTo(deleteEntryFile);
		tempFile.createNewFile();

		if (editFlag == true) {
			System.out.println("Editing Entry");
		} else {
			System.out.println("Entry Deleted");

		}

		TimeUnit.SECONDS.sleep(SLEEP_DISPLAY);

		deleteEntryInput.close();
		deleteEntryDisplay.close();

		if (editFlag == true) {
			AddressBook.createEntry();
		} else {
			AddressBook.startProgram();
		}

		return discardedEntry;
	}

	/**
	 * Start point of program. Presents a menu to user to pick
	 * what path they want to take. Continues execution until
	 * user decides to quit program.
	 * 
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void startProgram() throws FileNotFoundException, InterruptedException, IOException {
		System.out.println("Welcome to the Address Book. Choose a task number below:");
		System.out.println("1 Display Address Book Entries");
		System.out.println("2 Update Address Book Entry");
		System.out.println("3 Add Address Book Entry");
		System.out.println("4 Delete Address Book Entry");
		System.out.println("5 Quit Program");

		do {
			int topMenuSelection = input.nextInt();

			switch (topMenuSelection) {
			case 1:
				AddressBook.displayEntries();
				break;

			case 2:
				AddressBook.deleteOrEditEntry(true);
				break;

			case 3:
				AddressBook.createEntry();
				break;

			case 4:
				AddressBook.deleteOrEditEntry(false);
				break;

			case 5:
				System.out.println("Quitting");
				continueDoLoop = false;
				break;

			default:
				System.out.println("Invalid Input");
			}

		} while (continueDoLoop == true);

		input.close();
	}
}