package gitlet;

import java.io.File;
import java.io.Serializable;

public class Blob implements Serializable {
    static final File BLOB_FOLDER = new File(".gitlet/blob");
    private String fileName;
    private byte[] content;
    private String blobID;

    public Blob(String fileName, byte[] content){
        this.fileName = fileName;
        this.content = content;
        this.setID(content);
    }

    public Blob(String fileName, byte[] content, String blobID){
        this.fileName = fileName;
        this.content = content;
        this.blobID = blobID;
    }

    public void saveBlob(){
        File blob_storage = Utils.join(BLOB_FOLDER, blobID);
        Blob blob_data = new Blob(this.fileName, this.content, this.blobID);
        Utils.writeObject(blob_storage, blob_data);
    }

    public static Blob fromFile(String name){
        File blob_retrieve = Utils.join(BLOB_FOLDER, name);
        return Utils.readObject(blob_retrieve, Blob.class);
    }

    public String getFileName(){
        return fileName;
    }

    public byte[] getContent(){
        return content;
    }

    public String getBlobID(){
        return blobID;
    }

    public void setID(Object... vals){
        this.blobID = Utils.sha1(vals);
    }
}