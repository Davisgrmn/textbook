/**
	 * Creates the post object that allows the textbook to manipulate posts, with methods to obtain certain information about said post.
	 *
	 * @author Davis Garman
	 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

// Class Post gets declared

public class Post {
    private ArrayList<String> commentsList = new ArrayList<>();
    private int id;
    private String author;
    private String text;
    private Instant time;
    private static final DecimalFormat idFormat = new DecimalFormat("00000");

    // New Post constructor
    public Post(int id, String author, String text) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.time = Instant.now();
        String filename = getFilename();

        // Writing post details to a file
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(filename, true))) {
            pw.println(idFormat.format(id) + " " + time + " " + author + " " + text);
        } catch (IOException e) {
            System.out.println("Error: IO exception while creating post file");
        }
    }

    // Recovery constructor
    public Post(int postID) {
        this.id = postID;
        String filename = getFilename();
        try (Scanner fileScan = new Scanner(new File(filename))) {
            if (fileScan.hasNextLine()) {
                String line = fileScan.nextLine();
                try (Scanner lineScan = new Scanner(line)) {
                    lineScan.useDelimiter(" ");
                    
                    // Parse ID, timestamp, author, and text
                    id = Integer.parseInt(lineScan.next());
                    time = Instant.parse(lineScan.next());
                    author = lineScan.next();

                    // Collect the remaining text as the post content
                    StringBuilder postText = new StringBuilder();
                    while (lineScan.hasNext()) {
                        postText.append(lineScan.next()).append(" ");
                    }
                    this.text = postText.toString().trim();
                }
            }

            // Read additional lines as comments
            while (fileScan.hasNextLine()) {
                commentsList.add(fileScan.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File could not be opened: " + filename);
        }
    }


    public void addComment(String author, String text) {
        Instant timestamp = Instant.now();
        String comment = timestamp + " " + author + " " + text;
        commentsList.add(comment);
        
        // Append the comment to the file
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(getFilename(), true))) {
            pw.println(comment);
        } catch (IOException e) {
            System.out.println("Error: IO exception while adding comment");
        }
    }

    // tostring method prints post and its comments

    @Override
    public String toString() {
        StringBuilder comments = new StringBuilder();
        for (String comment : commentsList) {
            comments.append(comment).append("\n");
        }
        return "Post:\n" + id + " " + time + " " + author + " " + text + "\nComments:\n" + comments.toString();
    }

    // Puts the post, not the comments, in string form

   
    public String toStringPostOnly() {
        return "Post:\n" + id + " " + time + " " + author + " " + text;
    }

    // Returns the file name

   
    public String getFilename() {
        return "Post-" + idFormat.format(id) + ".txt";
    }

    // Returns post ID

   
    public int getPostID() {
        return id;
    }

    // Returns the text of a post

    
    public String getText() {
        return text;
    }

    // Returns the time stamp of the comment

    public Instant getTimestamp() {
        return time;
    }

    // Returns the author's name

    public String getAuthor() {
        return author;
    }
}
