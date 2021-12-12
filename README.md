# Puzzle solver

Puzzle solver is a Java app which implements A* algorithm for [numeric puzzles](https://en.wikipedia.org/wiki/15_puzzle).  
Using this app, you can solve 8 puzzle, 15 puzzle...

## Usage

First, you need to create a new text file as in the examples in [tables](tables).  
Then, you can use this app using these commands:

```bash
javac Solver.java
java Solver fileName.txt
```

## Result

This app will print something like:

```
4
0 1 3 4 2 5 7 8 6 
1 0 3 4 2 5 7 8 6 
1 2 3 4 0 5 7 8 6 
1 2 3 4 5 0 7 8 6 
1 2 3 4 5 6 7 8 0 
```

where ```4``` is the Manhattan distance of the given board and the other lines are the steps to do in order to find the solution:  
```
0 1 3       1 0 3       1 2 3       1 2 3       1 2 3
4 2 5       4 2 5       4 0 5       4 5 0       4 5 6
7 8 6       7 8 6       7 8 6       7 8 6       7 8 0 
```
