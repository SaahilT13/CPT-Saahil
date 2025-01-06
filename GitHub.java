import arc.*;

public class GitHub{
    public static void main(String[] args){
        Console con = new Console();
        
		String themesFileName = "themes.txt";
        TextInputFile themesFile = new TextInputFile(themesFileName);

        // Read themes from file
        String[] themes = new String[10]; 
        int themeCount = 0;
        while (themesFile.eof() == false){
            themes[themeCount] = themesFile.readLine();
            themeCount++;
        }
        themesFile.close();
        
        // Main menu
        boolean play = true;
        while (play){
            con.println("Welcome to Hangman!");
            con.println("1. Play Game");
            con.println("2. Quit");
            con.print("Enter your choice: ");
            int choice = con.readInt();

            if (choice == 1){
				playGame(con, themes, themeCount);
            } else if (choice == 2){
                con.println("Thanks for playing!");
                break;
            } else {
                con.println("Invalid choice. Try again.");
            
            }
            play = false;
        }
     }
       
      
        public static void playGame(Console con, String[] themes, int themeCount){
			// Display available themes
			con.println("Available themes:");
			for (int i = 0; i < themeCount; i++) {
				con.println((i + 1) + ". " + themes[i]);
			}
			con.print("Choose a theme (enter the number): ");
			int themeChoice = con.readInt();

			// Validate choice
			if (themeChoice < 1 || themeChoice > themeCount) {
				con.println("Invalid choice. Returning to main menu.");
				return;
			}

			// Load the selected theme file
			String themeFileName = themes[themeChoice - 1] + ".txt";
			TextInputFile themeFile = new TextInputFile(themeFileName);

			// Read words from the theme file
			String[] words = new String[100]; 
			int wordCount = 0;
			while (themeFile.eof() == false) {
				words[wordCount] = themeFile.readLine().toUpperCase();
				wordCount++;
			}
			themeFile.close();
    }
        
}

