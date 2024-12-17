/**
	 * Driver class that gives the textbook life, using a while loop and switch to iterate through a menu,
     * giving the user a series of option on what to do with the textbook.
	 *
	 * @author Davis Garman
	 */
import java.util.Scanner;

public class TextBookDriver {

    public static void main(String[] args) {
        // Welcome the user and get their login name
        try (Scanner scanner = new Scanner(System.in)) {
            // Welcome the user and get their login name
            System.out.println("Welcome to your TextBook social media platform!");
            System.out.print("Enter your one-word login name: ");
            String authorName = scanner.nextLine().trim();
            
            // Create a TextBook instance
            TextBook textbook = new TextBook();
            
            // Display the menu
            displayMenu();
            
            boolean running = true;
            while (running) {
                System.out.print("\nEnter a menu option (or 'm' to show the menu): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                
                switch (choice) {
                    case "p" -> {
                        // Print the TextBook (list of posts)
                        System.out.println("TextBook Posts:");
                        System.out.println(textbook.toString());
                    }
                        
                    case "a" -> {
                        // Add a post to the TextBook
                        System.out.print("Enter the text for the new post: ");
                        String postText = scanner.nextLine();
                        textbook.addPost(authorName, postText);
                        System.out.println("Post added successfully.");
                    }
                        
                    case "d" -> {
                        // Delete a post from the TextBook
                        System.out.print("Enter the index of the post to delete: ");
                        int deleteIndex = getValidIndex(scanner, textbook.getPostCount());
                        if (deleteIndex != -1) {
                            textbook.removePost(deleteIndex);
                            System.out.println("Post deleted successfully.");
                        }
                    }
                        
                    case "c" -> {
                        // Comment on a post
                        System.out.print("Enter the index of the post to comment on: ");
                        int commentIndex = getValidIndex(scanner, textbook.getPostCount());
                        if (commentIndex != -1) {
                            System.out.print("Enter your comment: ");
                            String commentText = scanner.nextLine();
                            textbook.addComment(commentIndex, authorName, commentText);
                            System.out.println("Comment added successfully.");
                        }
                    }
                        
                    case "r" -> {
                        // Read a post, including comments
                        System.out.print("Enter the index of the post to read: ");
                        int readIndex = getValidIndex(scanner, textbook.getPostCount());
                        if (readIndex != -1) {
                            System.out.println("Reading Post:");
                            System.out.println(textbook.getPostString(readIndex));
                        }
                    }
                        
                    case "m" -> // Display the menu again
                        displayMenu();
                        
                    case "q" -> {
                        // Quit the application
                        System.out.println("Exiting the application. Goodbye!");
                        running = false;
                    }
                        
                    default -> // Handle invalid menu choice
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    /**
     * Display the menu options for the user.
     */
    private static void displayMenu() {
        System.out.println("\nMenu options:");
        System.out.println("(p)rint the TextBook (indexed list of posts)");
        System.out.println("(a)dd a post to the TextBook");
        System.out.println("(d)elete a post from the TextBook");
        System.out.println("(c)omment on a post");
        System.out.println("(r)ead a post (including comments)");
        System.out.println("(q)uit the application");
        System.out.println("Enter 'm' to display the menu again.");
    }

    /**
     * Helper method to get a valid index from the user.
     *
     * @param scanner The Scanner to use for input
     * @param postCount The number of posts in the TextBook
     * @return A valid index, or -1 if the input is invalid
     */
    private static int getValidIndex(Scanner scanner, int postCount) {
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine().trim());
            if (index < 0 || index >= postCount) {
                System.out.println("Invalid index. Please try again.");
                return -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric index.");
            return -1;
        }
        return index;
    }
}
