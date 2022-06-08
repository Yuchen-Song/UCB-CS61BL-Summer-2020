package gitlet;

import java.io.Serializable;
import java.util.HashMap;

public class StagingArea implements Serializable {
    private HashMap<String, String> stagedAdd;
    private HashMap<String, String> stagedRem;

    public StagingArea(){
        stagedAdd = new HashMap<String, String>();
        stagedRem = new HashMap<String, String>();
    }

    public void addBlob(Blob newBlob){
        stagedAdd.put(newBlob.getFileName(), newBlob.getBlobID());
    }

    public void removeBlob(Blob rmBlob) {
        stagedRem.put(rmBlob.getFileName(), rmBlob.getBlobID());
    }

    public void printStageAdd(){
        for(String blobName : stagedAdd.keySet()){
            System.out.println(blobName);
        }
    }

    public void printStageRem(){
        for(String blobName : stagedRem.keySet()){
            System.out.println(blobName);
        }
    }

    public void clearAdd(){
        stagedAdd.clear();
    }

    public void clearRem() { stagedRem.clear(); }

    public HashMap<String, String> getStagedAdd(){
        return stagedAdd;
    }

    public HashMap<String, String> getStagedRem() { return stagedRem; }
}
