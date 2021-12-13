import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Programma che, una volta aver ricevuto i dati in
 * ingresso da un file e averli organizzati sotto
 * forma di una matrice 2d 3x3, crea la Board ed
 * esegue il metodo "solver()" in modo da applicare
 * l'algoritmo A* per trovare la soluzione
 *
 * @author Luca Bombonati 2000247
 */
public class Solver {
    public static void main(String[] args) {
        // controllo se il numero di parametri passati dalla riga di comandi è giusto o no
        if (args.length != 1) {
            System.err.println("Uso: java Solver document.txt");
        } else {
            try {
                FileReader fileReader = new FileReader(args[0]);
                Scanner scanner = new Scanner(fileReader);

                ArrayList<Integer> cells = new ArrayList<>();
                while (scanner.hasNext()) { //finchè nel file sono presenti numeri li inserisco in boardArray
                    cells.add(scanner.nextInt());
                }

                int[][] boardArray = new int[cells.get(0)][cells.get(0)];
                int counter = 1;
                for (int i = 0; i < boardArray.length; i++) {
                    for (int j = 0; j < boardArray.length; j++) {
                        boardArray[i][j] = cells.get(counter);
                        counter++;
                    }
                }

                Board b = new Board(boardArray); //creo la tabella
                System.out.println(b.manhattan()); //stampo la distanza di manhattan

                /*
                ciò che ottengo è il percorso inverso, ma voglio visualizzare
                le mosse in ordine quindi mi salvo il percorso e lo stampo al contrario
                 */
                ArrayList<String> reversePath = new ArrayList<>();

                Node lastNode = b.solver(); //chiama l'algoritmo risolutivo

                while (lastNode != null) { //salvo tutte le mosse nell'arraylist
                    reversePath.add(lastNode.get_board().toString());
                    lastNode = lastNode.get_prev();
                }

                //stampo l'arraylist al contrario
                for (int i = reversePath.size() - 1; i >= 0; i--)
                    System.out.println(reversePath.get(i));
            } catch (IOException | NoSuchElementException | IllegalArgumentException ex) {
                // errore sulla lettura del file oppure sui dati inseriti
                System.err.println("Controllare i dati inseriti");
            }
        }
    }
}
