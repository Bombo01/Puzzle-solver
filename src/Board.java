public class Board {
    private final int[][] _board;

    /**
     * Costruttore della tabella
     *
     * @param tiles l'array bidimensionale rappresentante la tabella
     */
    public Board(int[][] tiles) {
        if (tiles.length == tiles[1].length) {
            _board = tiles.clone();
        } else
            throw new IllegalArgumentException();
    }

    /**
     * Classe privata che descrive un nodo.
     *
     * Il nodo è, in questo caso, una tabella che deriva dallo
     * spostamento dello zero dalla tabella genitore
     */
    private static class node {
        public int level = 0; //livello del nodo
        int[][] nodeBoard = new int[4][4];
        node parent = null;

        public node(int _level, int[][] board, node _parent) {
            int level = _level;
            nodeBoard = board.clone();
            parent = _parent;
            node up = move(1, 0);
            node right = move(0, 1);
            node down = move(-1, 0);
            node left = move(0, -1);
        }

        /**
         * Funzione che sposta lo zero in base a due parametri
         * x ed y che possono assumere solo valori 0, 1 o -1
         * @param x spostamento dello 0 nell'asse x (0 non sposta, 1 abbassa, -1 alza)
         * @param y spostamento dello 0 nell'asse y (0 non sposta, 1 sposta a destra, -1 sposta a sinistra)
         * @return il nodo con lo zero spostato
         */
        public node move(int x, int y){
            int[][] _tempBoard = nodeBoard.clone();

            int[] pos0 = Board.linearSearch(0, _tempBoard);
            if((x > 0 && pos0[0] != 3) || (x < 0 && pos0[0] != 0)){
                int temp = _tempBoard[pos0[0]+x][pos0[1]];
                _tempBoard[pos0[0]][pos0[1]] = temp;
                _tempBoard[pos0[0]+x][pos0[1]] = 0;

                //verificare distanza mah

                return new node(level+1, _tempBoard, ); //<-- trovare modo per dire del parentNode
            } else if((y > 0 && pos0[1] != 3) || (y < 0 && pos0[1] != 0)){
                int temp = _tempBoard[pos0[0]][pos0[1]+y];
                _tempBoard[pos0[0]][pos0[1]] = temp;
                _tempBoard[pos0[0]][pos0[1]+y] = 0;

                //verificare distanza mah

                return new node(_tempBoard);
            } else{
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Funzione per calcolare la distanza di manhattan:
     * di una cella da dove a dove dovrebbe essere
     *
     * @return la distanza di manhattan
     */
    public int manhattan() {
        int _manhattan = 0;
        int maxNumber = (_board.length * _board.length) - 1;
        for (int i = 1; i <= maxNumber; i++) {
            int verticalDistance = Math.abs(linearSearch(i, _board)[0] - ((i - 1) / 4));  //calcolo distanza righe
            int horizontalDistance = Math.abs(linearSearch(i, _board)[1] - ((i - 1) % 4));  //calcolo distanza colonne
            _manhattan += verticalDistance + horizontalDistance;    //calcolo distanza totale
        }
        return _manhattan;
    }

    /**
     * Funzione per cercare linearmente dove si trova una
     * cella nella tabella
     *
     * @param n il valore della cella da cercare
     * @return array con numero di riga e di colonna
     */
    private static int[] linearSearch(int n, int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[i][j] == n)
                    return new int[]{i, j};
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Descrizione della tabella in riga
     *
     * @return la descrizione della tabella
     */

    public String toString() {
        StringBuilder _return = new StringBuilder();
        for (int i = 0; i < _board.length; i++) {
            for (int j = 0; j < _board[1].length; j++) {
                _return.append(_board[i][j]).append(" ");
            }
        }
        return _return.toString();
    }
}
