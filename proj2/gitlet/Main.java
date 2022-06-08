package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Daniel Yun, Yuchen Song, Wei Li
 */
public class  Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if(args.length == 0){
            System.out.println("Please enter a command");
            System.exit(0);
        }

        Repository rep = new Repository();
        String command = args[0];
        File folder = new File(".gitlet");

        if(!command.equals("init")){
            if(!folder.exists()){
                System.out.println("Not in an initialized Gitlet directory.");
                System.exit(0);
            }
            rep = rep.fromFile();
            if(!command.equals("checkout") && !command.equals("log")
                    && !command.equals("global-log")
                    && !command.equals("status")
                    && args.length != 2){
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
        }

        switch(command){
            case "init":
                if(args.length != 1){
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }
                rep.init();
                break;
            case "add":
                rep.add(args[1]);
                break;
            case "commit":
                rep.commit(args[1]);
                break;
            case "rm":
                rep.remove(args[1]);
                break;
            case "log":
                rep.log();
                break;
            case "global-log":
                rep.globalLog();
                break;
            case "find":
                rep.find(args[1]);
                break;
            case "status":
                rep.status();
                break;
            case "checkout":
                if(args[1].equals("--")){
                    if(args.length != 3){
                        System.out.println("Incorrect operands.");
                        System.exit(0);
                    }
                    rep.checkout(args[2]);
                }else if(args.length == 4 && args[2].equals("--")){
                    rep.checkout(args[1], args[3]);
                }else if(args.length == 2){
                    rep.checkoutBranch(args[1]);
                }else{
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }
                break;
            case "branch":
                rep.branch(args[1]);
                break;
            case "rm-branch":
                rep.removeBranch(args[1]);
                break;
            case "reset":
                rep.reset(args[1]);
                break;
            case "merge":
                rep.merge(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
        }
    }
}