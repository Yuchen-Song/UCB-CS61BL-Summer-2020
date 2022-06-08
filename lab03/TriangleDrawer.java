public class TriangleDrawer{
    public static void main(String[] args){
        int row = 0;
        int SIZE = 10;
        while (row < SIZE) {
            int col = 0;
            while (col <= row) {
                System.out.print('*');//for Row row, print * row+1 times
                col = col + 1;
            }
            row = row + 1;
            System.out.println();//how do we cancel the last new line?
        }
    }
}