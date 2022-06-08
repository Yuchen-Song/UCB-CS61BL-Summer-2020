public class CodingChallenges {

    /**
     * Return the missing number from an array of length N containing all the
     * values from 0 to N except for one missing number.
     */
    public static int missingNumber(int[] values) {
        /*int N = values.length;
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<=N; i++){
            if(list.contains(i)){
                continue;
            }
            return i;
        }
        return -1;*/

        int length = values.length;
        int flag;//no found
        for (int i = 0; i < length; i++) {
            flag = 0;
            for (int j : values) {
                if (j == i) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if and only if two integers in the array sum up to n.
     * Assume all values in the array are unique.
     */
    public static boolean sumTo(int[] values, int n) {

        for (int i : values) {
            for (int j : values) {
                if (i != j && i + j == n) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if and only if s1 is a permutation of s2. s1 is a
     * permutation of s2 if it has the same number of each character as s2.
     */
    public static boolean isPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        int flag;
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        int l = s1.length();
        for (char c_1 : c1) {
            flag = 0;
            for (char c_2 : c2) {
                if (c_1 == c_2) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                return false;
            }
        }
        return true;
        //Set<String> set = new HashSet<>(Arrays.asList(s1));
    }
}