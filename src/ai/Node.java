package ai;

/**
 * Grid node for A* pathfinding.
 * Holds grid position, path costs, walkability flags,
 * and a parent link for path reconstruction.
 */
public class Node {

    // previous node in the rebuilt path (default null)
    Node parent;

    // grid coordinates (column, row)
    public int col, row;

    // gCost: cost from starting node
    // hCost: cost to end node
    // fCost = gCost + fCost
    int gCost, hCost, fCost;

    // solid: true if grid cell is blocked
    // open: true if node in open list
    // checked: true if node has been evaluated
    boolean solid, open, checked;

    /**
     * Creates a node at the given grid position.
     * 
     * @param col column index in the grid
     * @param row row index in the grid
     */
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
