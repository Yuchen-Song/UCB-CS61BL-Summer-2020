public class SieveOfEratosthenes {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("You need to enter an argument!" +
                    "\nIn IntelliJ, you can do this by clicking on the button with \n`SieveOfEratosthenes` on it" +
                    " > `Edit Configurations...` " +
                    "and provide a number in the `Program arguments` box.");
            return;
        }
        int upperBound = Integer.parseInt( args[0] );
        boolean[] isNotPrime = new boolean[upperBound];//they are all 0's by default
        isNotPrime[0] = true;
        for (int i = 0; i < upperBound; i++) {
            if (isNotPrime[i] == true) {
                continue;
            } else {
                if(i==1){
                    isNotPrime[i] = true;
                    continue;
                }
                for(int j=2; i*j < upperBound; j++){
                    isNotPrime[i*j] = true;
                }
            }
        }
        for (int i = 0; i < upperBound; i++) {
            if (!isNotPrime[i]) {
                System.out.println(i + " is a prime number.");
            }
        }
    }
}