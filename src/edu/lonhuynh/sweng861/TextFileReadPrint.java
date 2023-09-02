package edu.lonhuynh.sweng861;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class TextFileReadPrint {
	/**
	* main() accepts a single commmand line parameter.
	* @param arg[0] path to the input text file and perform the required program
	*/
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);//Create new scanner to get user input for continue or break the program
		//Main program will loop until user want to exit
		do {
			if (args.length != 1) {
				System.out.println("Missing command line argument w/ file name");
				System.exit(1);
			}
			//Request User input for maximum characters to read and return valid value
			int maxChar = inputValidation("Please enter maximum number of characters (including spaces) you want to read: ");
			// Read text file into a String
			String lines;
			lines = readTextFile(args[0]);
			//Compare the maximum to read with length of the lines (without line break) and return error if excess lines length
			if (lines.replaceAll("\n", "").length() < maxChar) {
	            System.out.println("Error: Number of characters (including spaces) in the file less than the specified maximum.");
	        } else {//when condition satisfied start the main program
	        	String trimLines = textTrim(lines,maxChar);//called textTrim method and return trimmed lines with specified maximum chars (included spaces)
	        	analyzeText(trimLines);//called analyzeText method to analyze lines on the trimmed lines
	        	String reversedLines = toReverseOrder(trimLines);//called analyzeText method to reverse the trimmed lines
	        	System.out.println("\nThe reversed-lower case version of the text with "+ maxChar+ " characters: \n");
	        	printTextFile(reversedLines.toLowerCase());//Display the final results to console
	        }
			System.out.print("\nDo you want to continue the program? (enter 'y' to continue or any key to exit): ");//Asking user to get their input on continue or exit the program
		} while (scanner.nextLine().equalsIgnoreCase("y"));//Catch user reply to exit program
		System.out.print("Thank you for using the program!");//Inform the program is stop
		System.exit(1);//exit the program
	}
	/**
	 * inputValidation() Get user message to display and asking user for an integer.
	 * It will keep asking user to retry until they provide a valid input
	 * @param message to prompt user input
	 * @return int a greater than 0 integer
	 */
	private static int inputValidation(String prompt) {
		//New scanner object to get user input stream
		Scanner scanner = new Scanner(System.in);
		//Default return value as 0
		int validInput = -1;
		//Valid flag to exit the loop default value as False
		boolean isValid = false;
		//While loop until user input meet expectation
		while(!isValid) {
			//Get user input
			try {
				System.out.println(prompt);
				//Try to get user input and convert to integer
				validInput = Integer.parseInt(scanner.nextLine());
				//Catch the case when user enter negative number
                if (validInput > 0) {
                	isValid = true;
                } else {
                    System.out.println("Invalid input, please enter a number that greater than 0!");}
				}catch (NumberFormatException e){
				System.out.println("Invalid input, please enter a number!");
			}
		}
		return validInput;
	}
	/**
	* readTextFile() reads the contents of a text file and returns a long string
	containing the lines of the file.
	* @param filePath path to the input file.
	* @return string with all lines from file.
	*/
	private static String readTextFile(String filePath) {
		String lines = "";
		String line = "";
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(filePath);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				// Don't forget to add the newline at the end.
				lines = lines + line + "\n";
			}
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filePath + "'");
			System.exit(2);
		} catch (IOException ex) {
			System.out.println("Error reading file '" + filePath + "'");
			System.exit(3);
		}
		return lines;
	}
	/**
	* printTextFile() prints the string line by line.
	* @param lines string with the lines of the input file. Each line is separated by
	a linefeed.
	*/
	private static void printTextFile(String lines) {
		BufferedReader bufferedReader = new BufferedReader(new StringReader(lines));
		String line;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.out.println("Error reading string");
			System.exit(4);
		}
	}
	/**
     * Analyzes the given text, counting various character types and statistics.
     * @param lines: The input text to be analyzed.
     */
	private static void analyzeText(String lines) {
		String noLineBreak = lines.replaceAll("\n", ""); //Remove line break
	    //Set initial value for each statistic count
		int wordCount = 0;
	    int letterCount = 0;
	    int numberCount = 0;
	    int charsCount = 0;
	    int uppercaseCount = 0;
	    int lowercaseCount = 0;
	    int spaceCount = 0;
	    boolean inWord = false;// This flag is used to tell whenever one word is completed to trigger word count
	    for (int i = 0; i < noLineBreak.length(); i++) {// Traverse each character in the line
	        char c = noLineBreak.charAt(i);// Record char to variable c
	        charsCount++;//character counted (included space)
	        if (Character.isLetter(c)) {
	            letterCount++;//if c defined as character increase letterCount
	            if (Character.isUpperCase(c)) {
	                uppercaseCount++; //count Upper case chars
	            } else {
	                lowercaseCount++; //count Lower case chars
	            }
	          //check whether the current char is still belong to the current count word,
	          //if not increase word count
	            if (!inWord) {
	                inWord = true;
	                wordCount++;
	            }
	        } else if (Character.isDigit(c)) {
	            numberCount++;//counting number
	            inWord = false;
	        } else if (Character.isWhitespace(c)) {
	            spaceCount++;//Counting space
	            inWord = false;
	        } else { //The current c still belong to its original word, no word count recored
	            inWord = false;
	        }
	    }
	  //Character does not offered isPunctuation so the Punctuation will be the remaining characters when everything else counted
	    int punctuationCount = charsCount - letterCount - numberCount - spaceCount;
	  //Present the statistic
	    System.out.println("Words: " + wordCount);
	    System.out.println("Numbers: " + numberCount);
	    System.out.println("Chars (incl. spaces): " + charsCount);
	    System.out.println("Spaces: " + spaceCount);
	    System.out.println("Punctuation chars: " + punctuationCount);
	    System.out.println("Uppercase chars: " + uppercaseCount);
	    System.out.println("Lowercase chars: " + lowercaseCount);
	}
	/**
     * Trim the given text to the maximum characters(included white spaces but not line break)
     * @param 
     * lines The input text to be trim
     * maxChar The maximum chars user want to read (included white spaces but not line break)
     * @return string trimmed version of the input without counting the line breaks
     */
	private static String textTrim(String lines,int maxChar) {
	    StringBuilder trimmedLine = new StringBuilder();//created new empty string builder to stored the trimmed lines
	    int currentCharCount = 0;//initial characters count as 0
	    for (char c : lines.toCharArray()) {//traverse through each chars in lines string
	        if (currentCharCount < maxChar) {//stop the string builder whenever char count surpassed the maximum chars
	        	trimmedLine.append(c);//append current c to trimmedLine string builder
	        	currentCharCount++;//increase the count when encountered chars (included white spaces)
	            if (c == '\n') {//Excluded the line break
	            	currentCharCount --; // Not count line break as characters
	            }
	        } else {
	            break;//end the method when condition met
	        }
	    }
	    return trimmedLine.toString();//returns the trimmed lines
	}
	/**
     * Reverse given lines into new order with last characters bring to front and first character bring to back.
     * @param lines The input text to be reversed.
     * @return string with reversed lines of the input
     */
	private static String toReverseOrder(String lines) {
        StringBuilder reversedLine = new StringBuilder();//created new empty string builder to store reversed lines
        //Traverse from the end of the paragraph going back to the beginning of the paragraph 
        for (int i = lines.length() - 1; i >= 0; i--) {
            char c = lines.charAt(i);//store current char to c
            reversedLine.append(c);//append current c to reversed lines string builder
        }
        return reversedLine.toString();//returns the reversed lines
    }
}

