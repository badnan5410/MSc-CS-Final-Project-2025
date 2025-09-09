package ai;

import main.GamePanel;

import java.util.ArrayList;

/**
 * Grid-based A* pathfinding over the world tile map.
 * Builds a reusable Node grid, marks solids from tiles/interactive tiles, then searches for a path and reconstructs it as tile coordinates.
 */
public class PathFinder {

    // game context for map size, tiles, and interactive tiles
    GamePanel gp;

    // node grid sized world columns/rows
    Node[][] node;

    // nodes to explore
    ArrayList<Node> openList = new ArrayList<>();

    // final from start to end nodes
    public ArrayList<Node> pathList = new ArrayList<>();

    // start, goal, and current node while search
    Node startNode, endNode, currentNode;

    // true when goal is reached
    boolean endReached = false;

    // safety cap to prevent infinite search
    int step = 0;

    /**
     * Creates a pathfinder bound to the given GamePanel.
     * Preallocates a Node for each world cell.
     * @param gp game panel
     */
    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instantiateNodes();
    }

    /**
     * Allocates the Node grid once for the whole world size.
     * Each Node stores its own (col,row).
     */
    public void instantiateNodes() {
        node = new Node[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        int col = 0;
        int row = 0;

        while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
            node[col][row] = new Node(col, row);
            col++;

            if (col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Resets transient state before each search.
     * Clears open/checked/solid flags across the grid,
     * empties lists, resets the stop flag and step counter.
     */
    public void resetNodes() {
        int col = 0;
        int row = 0;

        while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {

            // reset open, checked, and solid state
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            col++;

            if (col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }

        //reset other settings
        openList.clear();
        pathList.clear();
        endReached = false;
        step = 0;
    }

    /**
     * Prepares a search by setting start/end, marking solids,
     * and computing costs for every node.
     *
     * @param startCol start column
     * @param startRow start row
     * @param endCol goal column
     * @param endRow goal row
     */
    public void setNodes(int startCol, int startRow, int endCol, int endRow) {
        resetNodes();

        // set start and end nodes
        startNode = node[startCol][startRow];
        currentNode = startNode;
        endNode = node[endCol][endRow];
        openList.add(currentNode);
        int col = 0;
        int row = 0;

        while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {

            // check tiles
            int tileNum = gp.tManager.mapArray[gp.currentMap][col][row];
            node[col][row].solid = gp.tManager.tile[tileNum].collision;

            // check interactive tiles
            for (int i = 0; i < gp.iTile[1].length; i++) {

                if (gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible) {
                    int itCol = gp.iTile[gp.currentMap][i].worldX/gp.TILE_SIZE;
                    int itRow = gp.iTile[gp.currentMap][i].worldY/gp.TILE_SIZE;
                    node[itCol][itRow].solid = true;
                }
            }

            // set cost
            getCost(node[col][row]);
            col++;

            if (col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Computes A* costs for a node using Manhattan distance.
     * gCost: distance to start, hCost: distance to end, fCost: sum.
     *
     * @param node node to score
     */
    public void getCost(Node node) {
        int dx, dy;

        // G cost
        dx = Math.abs(node.col - startNode.col);
        dy = Math.abs(node.row - startNode.row);
        node.gCost = dx + dy;

        // H cost
        dx = Math.abs(node.col - endNode.col);
        dy = Math.abs(node.row - endNode.row);
        node.hCost = dx + dy;

        // F cost
        node.fCost = node.gCost + node.hCost;
    }

    /**
     * Runs the A* loop: expand current, open neighbors, pick lowest fCost.
     * Stops on goal, empty frontier, or step cap.
     *
     * @return true if a path to end was found
     */
    public boolean search() {
        int col, row, bestNodeIndex, bestNodefCost;

        while(!endReached && step < 500) {
            col = currentNode.col;
            row = currentNode.row;

            // check current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // open the up node
            if (row-1 >= 0) {
                openNode(node[col][row-1]);
            }

            // open the left node
            if (col-1 >= 0) {
                openNode(node[col-1][row]);
            }

            // open the down node
            if (row+1 >= 0) {
                openNode(node[col][row+1]);
            }

            // open the right node
            if (col+1 >= 0) {
                openNode(node[col+1][row]);
            }

            // find the best node
            bestNodeIndex = 0;
            bestNodefCost = 9999; // <-- arbitrary large number

            for(int i = 0; i < openList.size(); i++) {

                // check if this node's F cost is better
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }

                // if F cost the same, check G cost
                else if (openList.get(i).fCost == bestNodefCost) {

                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            // if there is no node in the openList, the loop ends
            if (openList.size() == 0) {
                break;
            }

            // after the loop, openList[bestNodeIndex] is the next step (the next currentNode)
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == endNode) {
                endReached = true;
                pathBacktracking();
            }

            step++;
        }

        return endReached;
    }

    /**
     * Adds a neighbor to the frontier if traversable and unseen.
     * Sets its parent to the current node.
     *
     * @param node neighbor node
     */
    public void openNode(Node node) {

        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    /**
     * Rebuilds path by following parent links from end to start.
     * Inserts at index 0 to produce a start→end order.
     */
    public void pathBacktracking() {
        Node current = endNode;

        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }
}
