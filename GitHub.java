import arc.*;

public class GitHub {
    public static void main(String[] args) {
        Console con = new Console("Hangman", 1280, 720);
        
        String themesFileName = "themes.txt";
        String[] themes = new String[100]; 
        int themeCount = loadThemes(themesFileName, themes);
		
		boolean running = true;
        // Main menu
        while (running){
			con.clear();
            con.println("Welcome to Hangman!");
            con.println("1. Play Game");
            con.println("2. View High Scores");
            con.println("3. Add Theme");
            con.println("4. Help");
            con.println("5. Quit");
            con.print("Enter your choice: ");
            int choice = con.readInt();

			//Get user input to know what theeir choice is
			
            if (choice == 1){
                Game(con, themes, themeCount);
            } else if(choice == 2){
                viewHighScores(con);
            } else if(choice == 3){
                addTheme(con);
                themeCount = loadThemes(themesFileName, themes); // Reload themes after adding one
            } else if(choice == 4){
                displayHelp(con);
            } else if(choice == 5){
                con.println("Thanks for playing!");
                running = false;
            }else if(choice == 26){ //Year We Graduate
				funnyscreen(con);
            } else {
                con.println("Invalid choice. Try again.");
            }
        }
    }

    // Load themes from file
    public static int loadThemes(String fileName, String[] themes) {
        TextInputFile themesFile = new TextInputFile(fileName);
        int count = 0;
        while (!themesFile.eof() && count < themes.length) {
            themes[count] = themesFile.readLine();
            count++;
        }
        themesFile.close();
        return count;
    }

    public static void Game(Console con, String[] themes, int themeCount) {
        
        if (themeCount == 0) {
            con.println("No themes available. Add a theme first!");
            return;
        }

        con.clear();
        con.println("Available themes:");
        for (int i = 0; i < themeCount; i++) {
            con.println((i + 1) + ". " + themes[i]);
        }
        con.print("Choose a theme (enter the number): ");
        int themeChoice = con.readInt();

        if (themeChoice < 1 || themeChoice > themeCount) {
            con.println("Invalid choice. Returning to main menu.");
            return;
        }

        String themeFileName = themes[themeChoice - 1] + ".txt";
        String[] words = new String[100];
        int wordCount = loadWords(themeFileName, words);

        if (wordCount == 0) {
            con.println("No words found in this theme. Please add words to play.");
            return;
        }

        String secretWord = words[(int) (Math.random() * wordCount)];
        hangmanStart(con, secretWord);
    }

    // Load words from a theme file
    public static int loadWords(String fileName, String[] words) {
        TextInputFile themeFile = new TextInputFile(fileName);
        int count = 0;
        while (!themeFile.eof() && count < words.length) {
            words[count] = themeFile.readLine().toUpperCase();
            count++;
        }
        themeFile.close();
        return count;
    }

    public static void hangmanStart(Console con, String secretWord) {
        con.clear();
        con.print("What is your Name? ");
        String playerName = con.readLine();
        con.clear();
        int attemptsLeft = 6;
        char[] revealedWord = new char[secretWord.length()];
        for (int i = 0; i < revealedWord.length; i++) {
            revealedWord[i] = '_';
        }

        con.println("Let's play Hangman!");
        while (attemptsLeft > 0) {
            con.println("Word: " + new String(revealedWord));
            con.println("Attempts left: " + attemptsLeft);
            con.print("Guess the word: ");
            String guess = con.readLine().toUpperCase();

            if (guess.equals(secretWord)) {
                con.println("Congratulations! You guessed the word: " + secretWord);
                 saveHighScore(playerName, attemptsLeft);
                 win(con);
                 return;
            } else {
                attemptsLeft = attemptsLeft - 1;
                revealRandomLetter(secretWord, revealedWord);
                drawHangman(con, 6 - attemptsLeft);
            }
        }

        con.println("Game over! The word was: " + secretWord);
    }

      // Method to save high score
    public static void saveHighScore(String playerName, int attemptsLeft) {
        TextOutputFile scoreFile = new TextOutputFile("highscores.txt", true);
        scoreFile.println(playerName + " - " + attemptsLeft);
        scoreFile.close();
    }


    // Method to view high scores
    public static void viewHighScores(Console con) {
        con.clear();
        con.println("High Scores:");

        // Read and display the high scores
        TextInputFile scoreFile = new TextInputFile("highscores.txt");
        while (!scoreFile.eof()) {
            con.println(scoreFile.readLine());
        }
        scoreFile.close();

        // Ask the user if they want to return to the main menu
        con.println("\nPress any key to return to the main menu...");
        con.readLine();  // Wait for user input before going back to the main menu
    }

    public static void addTheme(Console con) {
        con.print("Enter a new theme name: ");
        String newTheme = con.readLine();
        TextOutputFile themesFile = new TextOutputFile("themes.txt", true);
        themesFile.println(newTheme);
        themesFile.close();

        TextOutputFile newThemeFile = new TextOutputFile(newTheme + ".txt");
        con.println("Enter words for this theme (enter 'DONE' to finish):");
        while (true) {
            con.print("Word: ");
            String word = con.readLine();
            if (word.equalsIgnoreCase("DONE")) break;
            newThemeFile.println(word);
        }
        newThemeFile.close();
    }
   
    //Help Screen
    public static void displayHelp(Console con) {
        con.clear();
        con.println("Hangman Help:");
        con.println("- Choose a theme and try to guess the word.");
        con.println("- You guess whole words, not letters.");
        con.println("- Each incorrect guess reveals a letter and adds a body part.");
        con.println("- Get it right before the hangman is complete!");
        con.println("\nPress any key to return to the main menu...");
        con.readLine();
    }
   
    //Reveal random letter
	 public static void revealRandomLetter(String secretWord, char[] revealedWord) {
		int index = (int) (Math.random() * secretWord.length());

		// Find an unrevealed letter explicitly
		while (revealedWord[index] != '_') {
			index = (int) (Math.random() * secretWord.length());
		}

		revealedWord[index] = secretWord.charAt(index);
	}
	
	public static void funnyscreen(Console con){
		con.clear();
		con.println("--------------------------------------");
		con.println("|        SECRET MENU UNLOCKED!       |");
		con.println("--------------------------------------");
		con.println("| Why don't programmers like nature? |");
		con.println("|                                    |");
		con.println("| Because it has too many bugs!      |");
		con.println("--------------------------------------");
		
		con.println("Since you were able to find this page.");
		con.println("Type this same number when you are guessing a word");
		con.println("Press any key to return to the main menu...");
		con.readLine();
	}

	//Win Screen
	public static void win(Console con){
		con.println("Congratulations You Won!!");
		con.println("\nPress any key to return to the main menu...");
		con.readLine(); // Wait for user input before returning
	}
     
    //drawing the hanging man using 2d array
    public static void drawHangman(Console con, int stage){
        con.clear();
        String[][] hangmanStages = {
			{ "  -----", "  |   |", "      |", "      |", "      |", "      |" },
			{ "  -----", "  |   |", "  O   |", "      |", "      |", "      |" },
			{ "  -----", "  |   |", "  O   |", "  |   |", "      |", "      |" },
			{ "  -----", "  |   |", "  O   |", " /|   |", "      |", "      |" },
			{ "  -----", "  |   |", "  O   |", " /|\\  |", "      |", "      |" },
			{ "  -----", "  |   |", "  O   |", " /|\\  |", " /    |", "      |" },
			{ "  -----", "  |   |", "  O   |", " /|\\  |", " / \\  |" }
		};

		// Print the selected stage
		for (int i = 0; i < hangmanStages[stage].length; i++) {
			con.println(hangmanStages[stage][i]);
		}
	}

}

    

