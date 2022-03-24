import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MazeSolver {

    //transfers file elements into an array of multidimensional array (array of char arrays)
    private static char[][] fileToArray(String file) throws Exception {
        try {
            int rows = 0;
            int cols = 0;

            char[][] maze = new char[rows][cols];
            int rowNum = 0;
            int colNum = 0;
            Scanner scan = new Scanner(new File(file));
            while(scan.hasNextLine()) {
                if (rowNum == 0) {
                    String[] curLineArr = scan.nextLine().split(" ");
                    rows = Integer.parseInt(curLineArr[0]);
                    cols = Integer.parseInt(curLineArr[1]);
                    maze = new char[rows][cols];
                    rowNum++;
                }
                else {
                    String curLine = scan.nextLine();
                    if (curLine.length() == cols) {
                        for (char c : curLine.toCharArray()) {
                            maze[rowNum-1][colNum] = c;
                            colNum++;
                        }
                        colNum = 0;
                        rowNum++;
                    }
                    else {
                        rowNum++;
                    }
                }
            }

            return maze;
        }
        catch(FileNotFoundException e){
            System.out.println("file not found.");
            return null;
        }
    }

    //finds starting point in maze
    private static int[] findStart(char[][] maze){
        int[] start = {-1, -1};
        int rowNum = 0;
        int colNum = 0;

        for(char[] line : maze){
            for(char c : line){
                if(c == '+'){
                    start[0] = rowNum;
                    start[1] = colNum;
                    return start;
                }
                colNum++;
            }
            rowNum++;
            colNum = 0;
        }

        return start;
    }

    //method searches for a spot
    private static int[] searchSpot(char[][] maze, int row, int col, char c){
        int[] spot = {row,col};

        if(col != maze[row].length-1 && maze[row][col+1]==c){//right
            spot[0] = row;
            spot[1] = col+1;
            return spot;
        }
        else if(row != maze.length-1 && maze[row+1][col]==c){//down
            spot[0] = row+1;
            spot[1] = col;
            return spot;
        }
        else if(col != 0 && maze[row][col-1]==c){//left
            spot[0] = row;
            spot[1] = col-1;
            return spot;
        }
        else if(row != 0 && maze[row-1][col]==c){//up
            spot[0] = row-1;
            spot[1] = col;
            return spot;
        }
        else{
            return spot;
        }
    }


    //method moves toward finish
    private static void traverse(char[][] maze, int row, int col, int[] start){
            int[] endPos = searchSpot(maze, row, col, '-');

            if(!notEnd(endPos[0] == row, endPos[1] == col)){
                System.out.println("solution found.");
                return;
            }

            int[] pos = searchSpot(maze, row, col, ' ');
            if (pos[0] == row && pos[1] == col) {//dead end

                if(noSolution(start[0] == row, start[1] == col)){
                    System.out.println("no solution.");
                    return;
                }
                else {
                    int[] back = searchSpot(maze, row, col, '+');
                    maze[pos[0]][pos[1]] = '.';
                    traverse(maze, back[0], back[1], start);
                }
            } else {
                maze[pos[0]][pos[1]] = '+';
                traverse(maze, pos[0], pos[1], start);
            }

    }

    private static boolean noSolution(boolean b, boolean b2) {
        return b && b2;
    }//better readability

    private static boolean notEnd(boolean b, boolean b1) {
        return b && b1;
    }//better readability

    //method prints maze
    private static void printMaze(char[][] maze) {
        if(maze != null) {
            for (int i = 0; i < maze.length; i++) {
                System.out.println(maze[i]);
            }
        }
    }

    public static void solve(String file) throws Exception {
        char[][] maze = fileToArray(file);

        try{
            assert maze != null;
            int[] start = findStart(maze);
            traverse(maze, start[0], start[1], start);
        }
        catch (Exception e){
            printMaze(maze);
            System.out.println("no solution.");
        }
        printMaze(maze);
        System.out.println("solution found.");
    }
}
