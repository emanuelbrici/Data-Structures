
/**
 * Created by emanuel on 11/26/14.
 */
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeGraph implements Graph {


    private int width;
    private int height;

    public MazeGraph(int W, int H) {    // Constructor for the graph
        width = W;
        height = H;
    }

    public int numVerts() {
        return width*height;   //Number of vertices returned
    }

    public ArrayList<Integer> adjacents(int v) {
        ArrayList<Integer> adj = new ArrayList<Integer>();
        int row = v / width;                    // get row
        int col = v % width;                     // get col

        if (row < height - 1) { 				// top vertices
            int num = (row + 1) * width + col;
            adj.add(num);                            //add to list
        }
        if (row > 0) {						// bottom vertices
            int num = (row - 1) * width + col;
            adj.add(num);                         //add to list
        }
        if (col - 1 >= 0) {					// left vertices
            int num = row * width + (col - 1);
            adj.add(num);                         //add to list
        }
        if (col < width - 1) {				// right vertices
            int num = row * width + (col + 1);
            adj.add(num);                         //add to list
        }

        return adj;
    }

    public ArrayList<Integer> mazeDFS(Graph G, int s) {
        Stack<Integer> Graph = new Stack<Integer>();
        int v = numVerts();                 // Get total number of vertices

        ArrayList<Boolean> vist = new ArrayList<Boolean>();    //array to keep track or what has been visited
        ArrayList<Integer> parent = new ArrayList<Integer>(v + 1);  //array to keep track of parent

        for (int i = 0; i < v; i++) {
            parent.add(-1);     //parent is assigned -1
        }
        int visitCount;
        for (int i = 0; i < v; i++) {       // set all
              vist.add(i, false);
        }
        vist.set(s, true);      // when visited set to true
        parent.set(s, -1);          //parent is -1
        visitCount = 1;
        while (visitCount < v) {
            ArrayList<Integer> neighbors = G.adjacents(s); // array for neighbors
            Random randnum = new Random();
            ArrayList<Integer> unvist = new ArrayList<Integer>();  //array for unvisited neighbors
            for (int n: neighbors) {
                if (!vist.get(n) ) {
                    unvist.add(n);
                }
            }
            if (!unvist.isEmpty()) {
                int w = unvist.get(randnum.nextInt(unvist.size()));  //random visited neighbor
                vist.set(w , true);         //when visited
                visitCount++;               // increase count
                parent.set(w , s);
                Graph.push(w);               //push neighbor on stack
                s = w;
            } else {
                s = Graph.pop();            //pop so that you can backtrack
            }
        }
        return parent;
    }


    final static int EAST = 1; 	// 0001
    final static int NORTH = 2; // 0010
    final static int WEST = 4; 	// 0100
    final static int SOUTH = 8; // 1000

    public static void main(String[] args) {
        int W;
        int H;
        String fname;
        if (args.length != 3) {   //if the args given not correct
            W = 160;                 //auto 16 width
            H = 160;                 //auto 16 height
            fname = "maze160x160.txt";
        } else {
            W = Integer.parseInt(args[0]);
            H = Integer.parseInt(args[1]);
            if (W < 5 || H < 5) {           // if width and height are not greater the 5
                System.err.println("bogus size!");
                return;
            }
            fname = args[2];
        }

        int cells[][] = new int[H][W];
        for (int r = 0; r < H; r++) {     // make walls for all node rows
            for (int c = 0; c < W; c++) {   //make walls for all node cols
                cells[r][c] = 0xF;          // bit code = 1111
            }
        }

        MazeGraph Graph = new MazeGraph(W, H);
        ArrayList<Integer> parent = Graph.mazeDFS(Graph, 0);

        for (int r = 0; r < H; r++) {				// loop for rows
            for (int c = 0; c < W; c++) {			// loop for col
                int v = r * W + c;					// getting the node
                int par = parent.get(v);				// parent of the node
                if (par >= 0) { 						// fall in if there is a parent
                    int r0 = par / W;			// row of parent
                    int c0 = par % W;			// col of parent
                    if (r0 == r + 1) {				// parent on top
                        cells[r][c] &= ~SOUTH;      // clear SOUTH bit (~SOUTH = 1110)
                        cells[r0][c0] &= ~NORTH;    // clear NORTH bit (~NORTH = 1101)
                    }
                    if (r0 == r - 1) {				// parent on bottom
                        cells[r][c] &= ~NORTH;      // clear SOUTH bit (~SOUTH = 1110)
                        cells[r0][c0] &= ~SOUTH;    // clear NORTH bit (~NORTH = 1101)
                    }
                    if (c0 == c + 1) {				// parent is east
                        cells[r][c] &= ~EAST;       // clear EAST bit (~EAST = 1110)
                        cells[r0][c0] &= ~WEST;     // clear WEST bit (~WEST = 1011)
                    }
                    if (c0 == c - 1) {				// parent is west
                        cells[r][c] &= ~WEST;       // clear WEST bit (~WEST = 1011)
                        cells[r0][c0] &= ~EAST;     // clear EAST bit (~EAST = 1110)
                    }


                }
            }
        }
        Random randnum = new Random(W);

        int startRow=randnum.nextInt(H);  // random start
        int exitRow=randnum.nextInt(H);   // random end


        cells[startRow][0] &= ~WEST;        //remove random west wall for start
        cells[exitRow][W-1] &= ~EAST;       //remove random east wall for end

        try {
            PrintStream ps = new PrintStream(fname);
            ps.println(W + " " + H);
            for (int r = 0; r < H; r++) {
                for (int c = 0; c < W; c++) {
                    ps.print(cells[r][c] + " ");
                }
                ps.println();
            }
            ps.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
