package ai;

import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, endNode, currentNode;
    boolean endReached = false;
    int step = 0;

    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instantiateNodes();
    }

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

            // set solid node
            // check tiles
            int tileNum = gp.tm.mapArray[gp.currentMap][col][row];
            node[col][row].solid = gp.tm.tile[tileNum].collision;

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
            if (openList.size() == 0) {break;}

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

    public void openNode(Node node) {

        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void pathBacktracking() {
        Node current = endNode;

        while (current != startNode) {
            pathList.add(0, current); // always adding to the first slot, so last added node is in index [0]
            current = current.parent;
        }
    }
}
