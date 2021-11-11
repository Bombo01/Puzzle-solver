public class Board {
    private final int[][] _board;
    public Board(int[][] tiles){
        if(tiles.length == tiles[1].length) {
            _board = tiles.clone();
        }else
            throw new IllegalArgumentException();
    }
    public int manhattan(){
        int _manhattan = 0;
        int maxNumber = (_board.length*_board.length)-1;
        for (int i = 1; i <= maxNumber; i++) {
            int verticalDistance = Math.abs(linearSearch(i)[0] - ((i-1)/4));
            int horizontalDistance = Math.abs(linearSearch(i)[1] - ((i-1)%4));
            _manhattan += verticalDistance + horizontalDistance;
        }
        return _manhattan;
    }
    private int[] linearSearch(int n){
        for (int i = 0; i < _board.length; i++) {
            for (int j = 0; j < _board.length; j++) {
                if(_board[i][j] == n)
                    return new int[]{i, j};
            }
        }
        throw new IllegalArgumentException();
    }
    public String toString(){
        StringBuilder _return = new StringBuilder();
        for (int i = 0; i < _board.length; i++) {
            for (int j = 0; j < _board[1].length; j++) {
                _return.append(_board[i][j]).append(" ");
            }
        }
        return _return.toString();
    }
}
