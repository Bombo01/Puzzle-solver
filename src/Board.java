import java.util.*;

/**
 * Classe che rappresenta la tabella del 8 puzzle
 *
 * @author Luca Bombonati 2000247
 */
public class Board {
    public final int[][] _board;

    /**
     * Costruttore della tabella
     *
     * @param tiles l'array bidimensionale rappresentante la tabella
     */
    public Board(int[][] tiles) {
        if (tiles.length == tiles[1].length)
            _board = tiles.clone();
        else
            throw new IllegalArgumentException();
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
            int[] numberPos = linearSearch(i, _board);
            int verticalDistance = Math.abs(numberPos[0] - ((i - 1) / _board.length));  //calcolo distanza verticale
            int horizontalDistance = Math.abs(numberPos[1] - ((i - 1) % _board.length));  //calcolo distanza colonne
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
                    return new int[]{i, j}; //return un array come [riga][colonna]
            }
        }
        throw new IllegalArgumentException(); //numero nella board non trovato
    }

    /**
     * Funzione per clonare un array board
     *
     * @param array l'array da copiare
     * @return l'array copiato
     */
    private int[][] copy2d(int[][] array) {
        int[][] _temp = new int[_board.length][_board.length];
        for (int i = 0; i < array.length; i++)
            System.arraycopy(array[i], 0, _temp[i], 0, array[1].length); //copio ogni linea nella nuova matrice
        return _temp;
    }

    /**
     * Funzione per spostare la casella 0 lungo
     * l'asse verticale e orizzontale
     *
     * @param vertical   > 0 per spostare lo zero verso destra
     *                   < 0 per spostare lo zero verso sinistra
     * @param horizontal > 0 per spostare lo zero verso il basso
     *                   < 0 per spostare lo zero verso l'alto
     * @return una nuova Board con lo zero traslato
     */
    public Board move(int vertical, int horizontal) {
        int[][] _tempBoard = copy2d(_board);
        int[] pos0 = Board.linearSearch(0, _tempBoard); //cerco lo zero nella tabella
        if ((vertical > 0 && pos0[0] != _board.length - 1) || (vertical < 0 && pos0[0] != 0)) { //controllo se posso spostare lo zero
            //sposto lo zero in verticale
            int temp = _tempBoard[pos0[0] + vertical][pos0[1]];
            _tempBoard[pos0[0]][pos0[1]] = temp;
            _tempBoard[pos0[0] + vertical][pos0[1]] = 0;
        } else if ((horizontal > 0 && pos0[1] != _board.length - 1) || (horizontal < 0 && pos0[1] != 0)) { //controllo se posso spostare lo zero
            //sposto lo zero in orizzontale
            int temp = _tempBoard[pos0[0]][pos0[1] + horizontal];
            _tempBoard[pos0[0]][pos0[1]] = temp;
            _tempBoard[pos0[0]][pos0[1] + horizontal] = 0;
        }
        return new Board(_tempBoard); //nuova tabella con lo zero spostato
    }

    /**
     * Funzione che applica l'algoritmo A* con la distanza
     * di manhattan per risolvere la Board
     *
     * @return il nodo rappresentante la board, il nodo successivo e il livello
     */
    public Node solver() {
        PriorityQueue<Node> unexpandedNodes = new PriorityQueue<>();    //priority queue per salvare i nodi trovati ma non espansi
        PriorityQueue<Node> expandedNodes = new PriorityQueue<>();  //priority queue per ricordarmi i nodi espansi

        unexpandedNodes.add(new Node(this, null, 0/*, null*/)); //aggiungo la tabella base

        while (true) {
            Node minimumPriorityNode = unexpandedNodes.poll();  //tolgo il nodo con priorità minore
            expandedNodes.add(minimumPriorityNode); //salvo il nodo appena tolto

            Board minimumPriorityBoard = minimumPriorityNode.get_board();
            int minimumPriorityLevel = minimumPriorityNode.get_level();

            if (minimumPriorityBoard.manhattan() == 0)
                return minimumPriorityNode; //se manhattan = 0 ho trovato la soluzione

            //calcolo tutti i nodi
            Node _up = new Node(minimumPriorityBoard.move(-1, 0), minimumPriorityNode, minimumPriorityLevel + 1);
            Node _right = new Node(minimumPriorityBoard.move(0, 1), minimumPriorityNode, minimumPriorityLevel + 1);
            Node _down = new Node(minimumPriorityBoard.move(1, 0), minimumPriorityNode, minimumPriorityLevel + 1);
            Node _left = new Node(minimumPriorityBoard.move(0, -1), minimumPriorityNode, minimumPriorityLevel + 1);

            /*
            se trovo un nodo che ho già espanso (ad esempio
            spostando lo zero in basso e subito dopo verso l'alto)
            non ha senso riaggiungerlo perchè significa che seguendo
            quella strada non troverei niente
            */
            if (!expandedNodes.contains(_up))
                unexpandedNodes.add(_up);
            if (!expandedNodes.contains(_right))
                unexpandedNodes.add(_right);
            if (!expandedNodes.contains(_down))
                unexpandedNodes.add(_down);
            if (!expandedNodes.contains(_left))
                unexpandedNodes.add(_left);
        }
    }

    /**
     * Descrizione della tabella in riga
     *
     * @return la descrizione della tabella
     */
    public String toString() {
        StringBuilder _return = new StringBuilder();
        for (int[] ints : _board) {
            for (int j = 0; j < _board[1].length; j++)
                _return.append(ints[j]).append(" "); //aggiungo ogni valore della matrice alla stringa
        }
        return _return.toString();
    }
}

/**
 * Classe che rappresenta un nodo
 *
 * @author Luca Bombonati 2000247
 */
class Node implements Comparable {
    private final Node _prev;
    private final int _level;
    private final Board _board;

    /**
     * Costruttore della classe nodo
     *
     * @param board la Board del nodo
     * @param prev  il nodo precedente
     * @param level l'altezza del nodo
     */
    public Node(Board board, Node prev, int level) {
        _prev = prev;
        _level = level;
        _board = board;
    }

    /**
     * Funzione che ritorna il nodo precendente
     *
     * @return il nodo precedente
     */
    public Node get_prev() {
        return _prev;
    }

    /**
     * Funzione che ritorna l'altezza del nodo
     *
     * @return l'altezza del nodo
     */
    public int get_level() {
        return _level;
    }

    /**
     * Funzione che ritorna la Board associata al nodo
     *
     * @return la Board associata
     */
    public Board get_board() {
        return _board;
    }

    /**
     * Funzione che compara 2 nodi e li mette in
     * ordine per la distanza di manhattan e l'altezza
     * del nodo
     *
     * @param o un altro nodo
     * @return -1 se il nodo "o" viene prima del nodo chiamante questa funzione
     * 0 se i due nodi sono uguali
     * 1 se il nodo "o" viene dopo  1000000
     */
    @Override
    public int compareTo(Object o) {
        Node otherNode = (Node) o;
        int thisNodePriority = this._level + _board.manhattan(); //calcolo la priorità del nodo
        int otherNodePriority = otherNode._level + otherNode.get_board().manhattan(); //calcolo la priorità dell'altro nodo
        return Integer.compare(thisNodePriority, otherNodePriority);
    }

    /**
     * Funzione che compara due tabelle dei nodi
     * e verifica se queste sono uguali. Viene utilizzato
     * quando viene chiamato il priorityqueue.contains(Object o)
     *
     * @param o un altro nodo
     * @return true o false se i due nodi hanno la board uguale
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        for (int i = 0; i < _board._board.length; i++) {
            for (int j = 0; j < _board._board[i].length; j++) {
                if(_board._board[i][j] != node._board._board[i][j])
                    return false;
            }
        }
        return true;
    }
}
