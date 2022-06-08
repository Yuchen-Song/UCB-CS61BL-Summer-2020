package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Repository implements Serializable {
    static final File GITLET_FOLDER = new File(".gitlet");
    static final File COMMIT_FOLDER = new File(".gitlet/commit");
    static final File BLOB_FOLDER = new File(".gitlet/blob");
    private String head;
    private String currentBranch;
    private ArrayList<String> allCommits;
    private HashMap<String, String> allBranches;
    private HashMap<String, String> allBlobs;
    private StagingArea stage;

    /* Initializes Repository by making the .gitlet folder.
     * The commit and blob folder are also made as a new directory
     * and all commits and blobs are serialized and saved in these folders.
     * The staging area is part of the repository and holds the blobs
     * that are staged for addition or removal. This init function also
     * makes an initial commit making the current Branch and head pointing to it.
     * he function serializes the state of the repository after adding the
     * initial commit.
     */
    public void init(){
        if(GITLET_FOLDER.exists()){
            System.out.println("A Gitlet version-control system already exists in the current directory");
            return;
        }
        GITLET_FOLDER.mkdir();
        COMMIT_FOLDER.mkdir();
        BLOB_FOLDER.mkdir();
        allCommits = new ArrayList<String>();
        allBranches = new HashMap<String, String>();
        allBlobs = new HashMap<String, String>();
        stage = new StagingArea();

        Commit initialCommit = new Commit();
        initialCommit.saveCommit();
        head = initialCommit.getCommitID();
        currentBranch = "master";
        allCommits.add(head);
        allBranches.put(currentBranch, head);

        saveRepository();
    }

    public void add(String fileName){
        File file = new File(fileName);
        if(!file.exists()){
            System.out.println("File does not exist.");
            return;
        }
        Blob blobFile = new Blob(fileName, Utils.readContents(file));
        File curFile = Utils.join(COMMIT_FOLDER, head);
        Commit curCommit = Utils.readObject(curFile, Commit.class);
        HashMap<String, String> stagedAdd = stage.getStagedAdd();
        HashMap<String, String> stagedRem = stage.getStagedRem();
        // 1st Case: same file in current commit and content is same
        // 2nd Case: same file in current commit but content is different
        // 3rd Case: file not in current commit but in staging area
        // 4th Case: file not in current commit and not in staging area
        // 5th Case: file staged in remove but added again

        if(curCommit.getBlobs().containsKey(fileName)){
            if(curCommit.getBlobs().get(fileName).equals(blobFile.getBlobID())){
                stagedAdd.remove(blobFile.getFileName());
                stagedRem.remove(fileName);
            }else{
                allBlobs.put(blobFile.getFileName(), blobFile.getBlobID());
                stage.addBlob(blobFile);
                blobFile.saveBlob();
            }
        }else{
            if(stagedAdd.containsKey(fileName)){
                stagedAdd.remove(blobFile.getFileName());
            }
            allBlobs.put(blobFile.getFileName(), blobFile.getBlobID());
            stage.addBlob(blobFile);
            blobFile.saveBlob();
        }
        saveRepository();
    }

    public void commit(String message){
        if(message.length() == 0 || message == null){
            System.out.println("Please enter a commit message");
            return;
        }
        File curFile = Utils.join(COMMIT_FOLDER, head);
        Commit curCommit = Utils.readObject(curFile, Commit.class);
        HashMap<String, String> blobsCurrentCommit = curCommit.getBlobs();
        HashMap<String, String> blobsForAdd = stage.getStagedAdd();
        if(blobsCurrentCommit.equals(blobsForAdd)){
            System.out.println("No changes added to the commit");
            return;
        }
        Commit newCommit = new Commit(head, message, blobsForAdd);
        newCommit.saveCommit();
        head = newCommit.getCommitID();
        allCommits.add(head);
        allBranches.put(currentBranch, head);
        stage.clearAdd();
        stage.clearRem();
        saveRepository();
    }

    public void remove(String fileName){
        HashMap<String, String> stagedAdd = stage.getStagedAdd();
        File curFile = Utils.join(COMMIT_FOLDER, head);
        Commit curCommit = Utils.readObject(curFile, Commit.class);
        // 1st Case: File is tracked in the current commit and staged for addition
        // 2nd Case: File is tracked in the current commit and not staged for addition
        // 3rd Case: File is not tracked and staged for addition
        // 4th Case: File is neither tracked nor staged for addition
        if(curCommit.getBlobs().containsKey(fileName)){
            if(stagedAdd.containsKey(fileName)){
                stagedAdd.remove(fileName);
            }
            File deleteFile = new File(fileName);
            Utils.restrictedDelete(deleteFile);
            String id = allBlobs.remove(fileName);
            stage.getStagedRem().put(fileName, id);
        }else if(stagedAdd.containsKey(fileName)){
            stagedAdd.remove(fileName);
        }else{
            System.out.println("No reason to remove the file.");
        }
        saveRepository();
    }

    public void log(){
        String curID = head;
        while(curID != null){
            File curFile = Utils.join(COMMIT_FOLDER, curID);
            Commit curCommit = Utils.readObject(curFile, Commit.class);
            curCommit.printInfo();
            curID = curCommit.getParent();
        }
    }

    public void globalLog(){
        for(String cm : allCommits){
            File curFile = Utils.join(COMMIT_FOLDER, cm);
            Commit curCommit = Utils.readObject(curFile, Commit.class);
            curCommit.printInfo();
        }
    }

    public void find(String message){
        boolean found = false;
        for(String cm : allCommits){
            File curFile = Utils.join(COMMIT_FOLDER, cm);
            Commit curCommit = Utils.readObject(curFile, Commit.class);
            if(curCommit.getMessage().equals(message)){
                System.out.println(curCommit.getCommitID());
                found = true;
            }
        }
        if(!found){
            System.out.println("Found no commit with that message.");
        }
    }

    public void status(){
        System.out.println("=== Branches ===");
        for(String b : allBranches.keySet()){
            if(b.equals(currentBranch)){
                System.out.println("*" + b);
            }else{
                System.out.println(b);
            }
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        stage.printStageAdd();
        System.out.println();
        System.out.println("=== Removed Files ===");
        stage.printStageRem();
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }

    public void checkout(String fileName){
        File commitFile = Utils.join(COMMIT_FOLDER, head);
        Commit curCommit = Utils.readObject(commitFile, Commit.class);
        if(!curCommit.getBlobs().containsKey(fileName)){
            System.out.println("File does not exist in that commit.");
            return;
        }
        String blobId = curCommit.getBlobs().get(fileName);
        File blobFile = Utils.join(BLOB_FOLDER, blobId);
        Blob curBlob = Utils.readObject(blobFile, Blob.class);
        File currFile = Utils.join(".", fileName);
        Utils.writeContents(currFile, curBlob.getContent());
        saveRepository();
    }

    public void checkout(String commitID, String fileName){
        ArrayList<String> shortenedCommit = new ArrayList<>();
        for (String id : allCommits) {
            shortenedCommit.add(id.substring(0,6));
        }
        if(!shortenedCommit.contains(commitID.substring(0,6))){
            System.out.println("No commit with that id exists.");
            return;
        }
        File commitFile = Utils.join(COMMIT_FOLDER, head);
        Commit curCommit = Utils.readObject(commitFile, Commit.class);
        while (curCommit != null) {
            if (curCommit.getCommitID().substring(0,6).equals(commitID.substring(0,6))) {
                break;
            }
            File temp = Utils.join(COMMIT_FOLDER, curCommit.getParent());
            curCommit = Utils.readObject(temp, Commit.class);
        }
        if(!curCommit.getBlobs().containsKey(fileName)){
            System.out.println("File does not exist in that commit.");
            return;
        }
        String blobId = curCommit.getBlobs().get(fileName);
        File blobFile = Utils.join(BLOB_FOLDER, blobId);
        Blob curBlob = Utils.readObject(blobFile, Blob.class);
        File currFile = Utils.join(".", fileName);
        Utils.writeContents(currFile, curBlob.getContent());
        saveRepository();
    }

    public void checkoutBranch(String branchName){
        if (!allBranches.containsKey(branchName)) {
            System.out.println("No such branch exists.");
            return;
        }
        if (currentBranch.equals(branchName)) {
            System.out.println("No need to checkout the current branch.");
            return;
        }
        File curFile = Utils.join(COMMIT_FOLDER, head);
        Commit curCommit = Utils.readObject(curFile, Commit.class);
        File changeFile = Utils.join(COMMIT_FOLDER, allBranches.get(branchName));
        Commit changeCommit = Utils.readObject(changeFile, Commit.class);

        List<String> files = Utils.plainFilenamesIn("./");
        for(String fileName : files){
            // checkout other error
            if(!curCommit.getBlobs().containsKey(fileName) && changeCommit.getBlobs().containsKey(fileName)){
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                return;
            }
            if(curCommit.getBlobs().containsKey(fileName) && !changeCommit.getBlobs().containsKey(fileName)) {
                File deleteFile = new File(fileName);
                Utils.restrictedDelete(deleteFile);
            }
        }
        for(String blob : changeCommit.getBlobs().keySet()){
            String blobId = changeCommit.getBlobs().get(blob);
            File blobFile = Utils.join(BLOB_FOLDER, blobId);
            Blob curBlob = Utils.readObject(blobFile, Blob.class);
            File newFile = Utils.join(".", blob);
            Utils.writeContents(newFile, curBlob.getContent());
        }
        currentBranch = branchName;
        head = changeCommit.getCommitID();
        stage.clearAdd();
        stage.clearRem();
        saveRepository();
    }

    public void branch(String branchName){
         if (allBranches.containsKey(branchName)) {
            System.out.println("A branch with that name already exists.");
        } else {
            allBranches.put(branchName, head);
        }
        saveRepository();
    }

    public void removeBranch(String branchName){
        if(!allBranches.containsKey(branchName)){
            System.out.println("A branch with that name does not exist.");
            return;
        }else if(currentBranch.equals(branchName)){
            System.out.println("Cannot remove the current branch.");
        }else{
            allBranches.remove(branchName);
        }
        saveRepository();
    }

    public void reset(String commitID){
        ArrayList<String> shortenedCommit = new ArrayList<>();
        for (String id : allCommits) {
            shortenedCommit.add(id.substring(0,6));
        }
        if(!shortenedCommit.contains(commitID.substring(0,6))){
            System.out.println("No commit with that id exists.");
            return;
        }
        File commitFile = Utils.join(COMMIT_FOLDER, head);
        Commit curCommit = Utils.readObject(commitFile, Commit.class);
        while (curCommit != null) {
            if (curCommit.getCommitID().substring(0,6).equals(commitID.substring(0,6))) {
                head = curCommit.getCommitID();
                break;
            }
            File temp = Utils.join(COMMIT_FOLDER, curCommit.getParent());
            curCommit = Utils.readObject(temp, Commit.class);
        }
        for (String fileName:curCommit.getBlobs().keySet()) {
            checkout(fileName);
        }
        File CWD = new File(".");
        for (File f: CWD.listFiles()) {
            if (!curCommit.getBlobs().containsKey(f.getName())) {
                if (!f.getName().equals(".gitignore") && !f.getName().equals("proj2.iml")) {
                    Utils.restrictedDelete(f);
                }
            }
        }
        stage.clearAdd();
        stage.clearRem();
        saveRepository();
    }

    public void merge(String branchName){
        if(stage.getStagedAdd().size() != 0 || stage.getStagedRem().size() != 0){
            System.out.println("You have uncommitted changes.");
            return;
        }
        if(!allBranches.containsKey(branchName)){
            System.out.println("A branch with that name does not exist.");
            return;
        }
        File curFile = Utils.join(COMMIT_FOLDER, allBranches.get(currentBranch));
        File compFile = Utils.join(COMMIT_FOLDER, allBranches.get(branchName));
        Commit curCommit = Utils.readObject(curFile, Commit.class);
        Commit compCommit = Utils.readObject(compFile, Commit.class);
        if(curCommit.getCommitID().equals(compCommit.getCommitID())){
            System.out.println("Cannot merge a branch with itself.");
            return;
        }

        // Untracked file in current commit would be overwritten or deleted error
        HashMap<String, String> currentBlob = curCommit.getBlobs();
        HashMap<String, String> otherBlob = compCommit.getBlobs();
        List<String> files = Utils.plainFilenamesIn("./");
        for(String fileName : files){
            if(!currentBlob.containsKey(fileName) && otherBlob.containsKey(fileName)){
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                return;
            }
        }


        // Compare each branch lengths first
        int curBranchLength = 0;
        int compBranchLength = 0;
        Commit curPtr = curCommit;
        Commit braPtr = compCommit;
        while(curPtr.getParent() != null){
            File tempFile = Utils.join(COMMIT_FOLDER, curPtr.getParent());
            curPtr = Utils.readObject(tempFile, Commit.class);
            curBranchLength += 1;
        }
        while(braPtr.getParent() != null){
            File tempFile = Utils.join(COMMIT_FOLDER, braPtr.getParent());
            braPtr = Utils.readObject(tempFile, Commit.class);
            compBranchLength += 1;
        }

        // Make both branch same length and start comparing each commit to find splitPoint
        int diff = curBranchLength - compBranchLength;
        curPtr = curCommit;
        braPtr = compCommit;
        if(diff > 0){
            while(diff > 0){
                File tempFile = Utils.join(COMMIT_FOLDER, curPtr.getParent());
                curPtr = Utils.readObject(tempFile, Commit.class);
                diff -= 1;
            }
        }else{
            while(diff < 0){
                File tempFile = Utils.join(COMMIT_FOLDER, braPtr.getParent());
                braPtr = Utils.readObject(tempFile, Commit.class);
                diff += 1;
            }
        }
        while(!curPtr.getCommitID().equals(braPtr.getCommitID())){
            File tempFile = Utils.join(COMMIT_FOLDER, curPtr.getParent());
            File tempFile2 = Utils.join(COMMIT_FOLDER, braPtr.getParent());
            curPtr = Utils.readObject(tempFile, Commit.class);
            braPtr = Utils.readObject(tempFile2, Commit.class);
        }

        // If splitPoint is the input branch || If splitPoint is the current branch
        if(curPtr.getCommitID().equals(compCommit.getCommitID())){
            System.out.println("Given branch is an ancestor of the current branch");
            return;
        }
        if(curPtr.getCommitID().equals(curCommit.getCommitID())){
            currentBranch = branchName;
            System.out.println("Current branch fast-forwarded.");
            return;
        }

        //Merge Step
        boolean mergeConflict = false;
        HashMap<String, String> splitBlob = curPtr.getBlobs();

        //Cases...


        if(mergeConflict){
            System.out.println("Encountered a merge conflict.");
        }else{
            System.out.println("Merged " + branchName + " into " + currentBranch + ".");
        }

        saveRepository();
    }


    public void saveRepository(){
        File repository_storage = Utils.join(GITLET_FOLDER, "project");
        Utils.writeObject(repository_storage, this);
    }

    public static Repository fromFile(){
        File retrieve_repository = Utils.join(GITLET_FOLDER, "project");
        return Utils.readObject(retrieve_repository, Repository.class);
    }
}