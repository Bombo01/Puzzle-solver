public class solver {
    public static void main(String[] args) {
        int[][] _temp = {
                {15, 6, 2, 4},
                {1, 9, 11, 12},
                {3, 14, 8, 13},
                {0, 10, 5, 7}
        };
        Board b = new Board(_temp);
        System.out.println(b);
        System.out.println(b.manhattan());
    }
}
