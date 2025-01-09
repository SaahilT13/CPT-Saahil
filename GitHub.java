import arc.*;

public class GitHub {
    public static void main(String[] args) {
        Console con = new Console();
        
        String themesFileName = "themes.txt";
        String[] themes = new String[100]; // Increased size to avoid indexing issues
        int themeCount = loadThemes(themesFileName, themes);

        // Main menu
        while (true) {
			con.clear();
            con.println("Welcome to Hangman!");
            con.println("1. Play Game");
            con.println("2. View High Scores");
            con.println("3. Add Theme");
            con.println("4. Help");
            con.println("5. Quit");
            con.print("Enter your choice: ");
            int choice = con.readInt();

            if (choice == 1) {
                Game(con, themes, themeCount);
            } else if (choice == 2) {
              
            } else if (choice == 3) {
                themeCount = loadThemes(themesFileName, themes); // Reload themes after adding one
            } else if (choice == 4) {
            } else if (choice == 5) {
                con.println("Thanks for playing!");
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
	}
}

