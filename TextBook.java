/**
	 * Creates the textbook objects which manages all the posts in an arraylist, as well as the post id file. 
     * This allows the program to access and recover posts from previous sessions.
     * 
	 *
	 * @author Davis Garman
	 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TextBook {
    private ArrayList<Post> postsList = new ArrayList<>();
    private int lastID = 0;
    public static final String POST_LIST_FILENAME = "postIDs.txt";

    public TextBook() {
        File postIDsFile = new File(POST_LIST_FILENAME);
        try {
            Scanner fileScan = new Scanner(postIDsFile);
            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine();
                Scanner lineScan = new Scanner(line);
                int id = Integer.parseInt(lineScan.next());
                Post recovery = new Post(id);
                postsList.add(recovery);
            }
            // Set lastID based on the last loaded post
            if (!postsList.isEmpty()) {
                lastID = postsList.get(postsList.size() - 1).getPostID();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File could not be opened");
        }
    }

    public int getLastID() {
        // Return lastID directly without trying to access an index
        return lastID;
    }

    // Returns the post array list size as an int

    public int getPostCount() {
        return postsList.size();
    }

    // Returns the indexed post as a string

    public String getPostString(int index) {
        return postsList.get(index).toString();
    }

    // Adds a post to Textbook

    public void addPost(String author, String text) {
        Post newpost = new Post(getLastID() + 1, author, text);
        postsList.add(newpost); // Add the new post to the list
        lastID = newpost.getPostID(); // Update lastID to the new post ID
        
        File postIDsFile = new File(POST_LIST_FILENAME);
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(postIDsFile, true))) {
            pw.println(newpost.getPostID());
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    // Removes a post from the arraylist, and also removes its id from the postids file

    public Post removePost(int index) {
        if (index < 0 || index >= postsList.size()) {
            System.out.println("Invalid index");
            return null;
        }

        Post removedPost = postsList.remove(index);

        // Update lastID after removal
        if (!postsList.isEmpty()) {
            lastID = postsList.get(postsList.size() - 1).getPostID();
        } else {
            lastID = 0;
        }

        // Re-write the post IDs file to reflect the current posts
        File postIDsFile = new File(POST_LIST_FILENAME);
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(postIDsFile))) {
            for (Post p : postsList) {
                pw.println(p.getPostID());
            }
        } catch (IOException e) {
            System.out.println("IO Exception");
        }

        return removedPost;
    }

    // add a comment to a post

    public void addComment(int postIndex, String author, String text) {
        if (postIndex < 0 || postIndex >= postsList.size()) {
            System.out.println("Invalid index");
            return;
        }
        Post target = postsList.get(postIndex);
        target.addComment(author, text);
    }

    // Tostring method for all of the posts in textbook

    @Override
    public String toString() {
        String postsCollection = "Textbook contains " + postsList.size() + " posts:\n";
        for (Post p : postsList) {
            postsCollection += p.toStringPostOnly() + "\n";
        }
        return postsCollection;
    }

    // returns a copy of the arraylists

    public ArrayList<Post> getPosts() {
        return new ArrayList<>(postsList);
    }
}
