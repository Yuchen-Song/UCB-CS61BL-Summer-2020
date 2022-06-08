package gitlet;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Commit implements Serializable {

    static final File COMMIT_FOLDER = new File(".gitlet/commit");

    private String commitID;
    private String parent;
    private String message;
    private String timeStamp;
    private HashMap<String, String> blobs;

    public Commit(){
        this.parent = null;
        this.message = "initial commit";
        this.timeStamp = stampTime();
        this.setID(message, timeStamp);
        this.blobs = new HashMap<String, String>();
    }

    public Commit(String parent, String message, HashMap<String, String> blobs){
        this.parent = parent;
        this.message = message;
        this.blobs = blobs;
        this.timeStamp = stampTime();
        this.setID(parent, message, timeStamp, blobs.toString());
    }

    public Commit(String parent, String message, String timeStamp, HashMap<String, String> blobs, String commitID){
        this.parent = parent;
        this.message = message;
        this.timeStamp = timeStamp;
        this.blobs = blobs;
        this.commitID = commitID;
    }

    public void saveCommit(){
        File commit_storage = Utils.join(COMMIT_FOLDER, commitID);
        Commit commit_data = new Commit(this.parent, this.message, this.timeStamp, this.blobs, this.commitID);
        Utils.writeObject(commit_storage, commit_data);
    }

    public static Commit fromFile(String id){
        File commit_retrieve = Utils.join(COMMIT_FOLDER, id);
        return Utils.readObject(commit_retrieve, Commit.class);
    }

    public void printInfo(){
        System.out.println("===");
        System.out.println("commit " + getCommitID());
        System.out.println("Date: " + getTimeStamp());
        System.out.println(getMessage());
        System.out.println();
    }

    public String stampTime(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z");
        return f.format(date);
    }

    public String getCommitID(){
        return commitID;
    }

    public String getParent(){
        return parent;
    }

    public String getMessage(){
        return message;
    }

    public String getTimeStamp(){
        return timeStamp;
    }

    public HashMap<String, String> getBlobs() {
        return blobs;
    }

    public void setID(Object... vals){
        this.commitID = Utils.sha1(vals);
    }
}