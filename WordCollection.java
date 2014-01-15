package WordQuizz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import acm.program.ConsoleProgram;
import acm.util.RandomGenerator;

public class WordCollection extends ConsoleProgram  implements WordQuizzConstants {
	/**
	 * Simple, console based word quiz program.
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, String> dictionary = new HashMap<String, String>();
	private ArrayList<String> keys = new ArrayList<String>();
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	public static void main(String[] args){
		new WordCollection().start(args);
	}
	
	public void run(){
		println("Welcome to the word quiz program version 1.0");
		println("Here you can create your own dictionaries and play words game.");
		println("Idea of program is to help memorize new words while learning foreign language.");
		
		while(true){
			int selection = getSelection(); 
			if (selection == QUIT) break;
			switch (selection){
			case CREATE_DICTIONARY:
				createDictionary();
				break;
			case USE_OLD_DICTIONARY:
				readDictionary();
				break;
			case UPDATE_DICTIONARY:
				updateDictionary();
				break;
			case SAVE_DICTIONARY:
				saveDictionary();
				break;
			case CLEAR_DICTIONARY:
				crearCurrent();
				break;
			case WORD_QUIZ:
				wordquiz();
				break;
			default: 
				println("Invalid selection"); 
				break; 
			}
		}
	}
	
	private void wordquiz() {
		if (!dictionary.isEmpty()){
			println("Welcome to Word quiz game.");
			println("We will display deffinition of random word and you must guess the word itself.");
			println("hit Enter to quit.");
			String correctWord = selectRandomWord();
			String deffinition = dictionary.get(correctWord);
			println(deffinition);
			while (true){
				String word = readLine("Enter word : ");
				if(word.equals(""))break;
				if(correctWord.equals(word)){
					println("Congratulations! your guess is correct!");
					break;
				} else {
					println("Sorry, your guess is incorrect. You don't know this word.");
				}
			}
		} else {
			println("Error, no dictionary found! Load or create dictionary first!");
		}
	}


	private String selectRandomWord() {
		int max = keys.size() - 1;
		int rand = rgen.nextInt(0, max);
		String word = keys.get(rand);
		return word;
	}

	private void readDictionary() {
		println("To load exiting dictionary, enter it's file name.");
		String file = readLine("fileName > ");
		readFile(file);
		
	}

	private void readFile(String file) {
		try{
			BufferedReader rd = new BufferedReader(new FileReader(file));
			while (true){
				String line = rd.readLine();
				if(line == null) break;
				int a = line.indexOf("-") - 1;
				int b = line.indexOf(">") + 1;
				String word = line.substring(0, a);
				String deffinition = line.substring(b, line.length());
				dictionary.put(word, deffinition);
				keys.add(word);
				
			}
			println("Dictionary " + file + " was loaded successfuly");
			rd.close();
			
		} catch(Exception e)
		{
		    e.printStackTrace();
		    println("Error! File not found or couldn't be read.");
		}
		
	}

	private void updateDictionary() {
		while(true){
			String word = readLine("Enter word to update: ");
			if(word.equals("")) break;
			if(dictionary.containsKey(word)){
				String oldDeff = dictionary.get(word);
				println("Current deffinition of -> " + word + " <- is - > " + oldDeff);
				println("If you want to change it enter new deffinition. If you want to leave it as it is - just hit Enter.");
				String newDeff = readLine("-> ");
				if(newDeff.equals(""))break;
				dictionary.put(word, newDeff);
			} else {
				println("Dictionary doesn't contain such word. Let's create new entry.");
				String deffinition = readLine("Deffinition: ");
				dictionary.put(word, deffinition);
				keys.add(word);
			}
		}
	}

	private void createDictionary() {
		println("To create new dictionary file, enter word first and then it's deffinition.");
		println("To finish, do not enter any word, just hit Enter.");
		println("Do not enter same word with different deffinitions several times.");
		while(true){
			String word = readLine("Word: ");
			word = word.toLowerCase();
			if(word.equals("")) break;
			String deffinition = readLine("Deffinition: ");
			dictionary.put(word, deffinition);
			keys.add(word);
		}
		
	}

	private void crearCurrent() {
		dictionary.clear();
		keys.clear();
		println("Dictionary was cleared successfuly.");
	}

	private void saveDictionary() {
		try {
			String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String name = "dictionaryFile" + time + ".txt";
			File dictionaryFile = new File(name);
			BufferedWriter writer = new BufferedWriter(new FileWriter(dictionaryFile));
			Iterator<String> it = dictionary.keySet().iterator();
			while (it.hasNext()){
				String line = it.next();
				String entryLine = line + " -> " + dictionary.get(line);
				writer.write(entryLine);
				writer.newLine();
			}
			writer.close();
			println("Dictionary was saved successfuly.");
		} catch(Exception e)
		{
		    e.printStackTrace();
		    println("Error! File couldn't be saved!");
		}
		
	}

	private int getSelection() {
		println(); 
		println("Please make a selection (0 to quit):");
		println("1. Create new Dictionary or add words to the current one.");
		println("2. Load exiting dicionary.");
		println("3. Update word deffinition in Dictionary.");
		println("4. Save dictionary to the file.");
		println("5. Clear current dictionary");
		println("6. Start Word quiz.");
		int choice = readInt("Selection: ");
		return choice;
	}

}
