import java.lang.StringBuilder;
import java.util.ArrayList;
import java.lang.Math;

public class Grid {

    private int size;
    private ArrayList<Node> bombs = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    public ArrayList<Node> flagged = new ArrayList<>();
    private String grid;
    private String mode;
    public int n_bombs;


    public Grid(int size, String mode) {
        this.size = size;
        setMode(mode);
        grid = generateGrid();
    }


    public String getMode() {return mode;}
    public String getGrid() {return grid;}
    public int getSize() {return size;}
    public void setSize(int size) {this.size = size;}
    public ArrayList<Node> getBombs() {return bombs;}
    public ArrayList<Node> getNodes() {return nodes;}


    public void setMode(String mode) {
        this.mode = mode;
        if(mode.equals("medium")) {
            int n_nodes = size*size;
            n_bombs = n_nodes / 5;
        }
        if(mode.equals("easy")) {
            int n_nodes = size*size;
            n_bombs = n_nodes / 10;
        } 
        if(mode.equals("hard")) {
            int n_nodes = size*size;
            n_bombs = n_nodes / 3;
        }
    }


    public void check(Node node) {

        if(node.getIsHidden() == false ) {
            /*if(node.willFlag == false) {
                System.out.println("This node has already been revealed!");
            }*/
            System.out.println("This node has already been revealed!");
        } else {
            /*if(node.willFlag == true && node.getIsFlagged() == false) {
                node.reveal();
            } else if(node.willFlag == true && node.getIsFlagged() == true) {
                node.setIsFlagged(true);
            }*/
            node.reveal();
        }

        if(node.getValue().equals("B") && node.getIsFlagged() == false) {
            System.out.println("\nGame over!");
        }

        if(node.getValue().equals("0")) {
            isZero(node);
        }
        /*
        if(node.willFlag == true && node.getIsFlagged() == true) {
            node.setIsFlagged(false);
        }
        */
    }
    

    public Node select(int x, int y) {

        int get = (x + size * (y-1))-1;
        Node node = nodes.get(get);

        return node;
    }


    public void isZero(Node node) {

        ArrayList<Node> neighbours = getAdjacNodes(node);

        for(int i=0; i<neighbours.size(); i++) {
            Node adj = neighbours.get(i);
            if(adj.getIsHidden() == true) {
                adj.reveal();
                if(adj.getValue().equals("0")) {
                    isZero(adj);
                }
            }   
        }
    }


    public void displayGrid() {

        StringBuilder builder = new StringBuilder();

        builder.append("\n\n");

        for(Node node: nodes) {
            
            if(node.getIsFlagged() == true) {
                builder.append("F | ");
            } else {
                if(!node.getIsHidden()) {
                    builder.append(node.getValue() + " | ");
                }   
                else {
                    builder.append("  | ");
                }   
            } 
            
            if(node.getX() == size) {
                builder.append("\n");
                for(int l=0; l<size; l++) {
                    String value = node.getValue().toString();
                    for(int m=0; m<value.length() + 2 ; m++) {
                        builder.append("_");
                    }
                    builder.append(" ");
                }
                builder.append("\n");
            }
        }
        System.out.println(builder.toString());
    }


    public String generateGrid() {

        StringBuilder builder = new StringBuilder();
        bombs = setBombs();
        for(int i=1; i<size+1; i++) {
            for(int j=1; j<size+1; j++) {
                Node node = new Node(j,i);
                        
                for(Node bomb: bombs) {
                    if(node.getX() == bomb.getX() && node.getY() == bomb.getY()) {
                        node.setBomb();
                    }
                }  
                nodes.add(node);
            }
        }

        ArrayList<Node> allNodes = addNumbers();

        for(int i=0; i<nodes.size(); i++) {
            Node node = allNodes.get(i);
            String value = node.getValue();
            nodes.get(i).setValue(value);
        }

        builder.append("\n\n");

        for(Node node: nodes) {

            builder.append(node.getValue() + " | ");

            if(node.getX() == size) {
                builder.append("\n");
                for(int l=0; l<size; l++) {
                    String value = node.getValue().toString();
                    for(int m=0; m<value.length() + 2 ; m++) {
                        builder.append("_");
                    }
                    builder.append(" ");
                }
                builder.append("\n");
            }
        }
        return builder.toString();
    }


    public ArrayList<Node> setBombs() {

        int number = n_bombs;        

        while(number >= 0) {

            Boolean isValid = true;
            int x = (int)(Math.random() * ((size - 1) + 1)) + 1;
            int y = (int)(Math.random() * ((size - 1) + 1)) + 1;

            Node newBomb = new Node(x,y);
            newBomb.setValue(" ");

            for(Node bomb: bombs) {
                if((bomb.getX() == newBomb.getX() && bomb.getY() == newBomb.getY())) {
                    isValid = false;
                }   
            }

            if(isValid == true) {
                bombs.add(newBomb);
                number--;
            }
        } 
        return bombs;
    }


    public ArrayList<Node> getAdjacNodes(Node node) {
        
        int x = node.getX();
        int y = node.getY();
        ArrayList<Node> p_nodes = new ArrayList<>();
        ArrayList<Node> toRemove = new ArrayList<>();
        ArrayList<Node> toReturn = new ArrayList<>();

        p_nodes.add(new Node(x-1,y-1));
        p_nodes.add(new Node(x,y-1));
        p_nodes.add(new Node(x+1,y-1));
        p_nodes.add(new Node(x-1,y));
        p_nodes.add(new Node(x+1,y));
        p_nodes.add(new Node(x-1,y+1));
        p_nodes.add(new Node(x,y+1));
        p_nodes.add(new Node(x+1,y+1));

        for(Node newNode: p_nodes) {
            if(newNode.getX() <1 || newNode.getY() <1 || newNode.getX() > size || newNode.getY() > size) {
                toRemove.add(newNode);
            } 
        }

        p_nodes.removeAll(toRemove);

        while(true) {
            if(toReturn.size() == p_nodes.size()) {
                break;
            }
            for(Node p_node: p_nodes ) {
                for(Node adj: nodes) {
                    if(p_node.getX() == adj.getX() && p_node.getY() == adj.getY()) {
                        toReturn.add(adj);
                    }
                }
            }
        }
        return toReturn;
    }

    
    public ArrayList<Node> addNumbers() {

        for (Node node : nodes) {
            if(node.getValue() != "B") {
                int num = 0;
                ArrayList<Node> adj = getAdjacNodes(node);
                for (Node node2 : adj) {
                    if(node2.getValue() == "B") {
                        num++;
                    }
                }
                node.setValue(Integer.toString(num));
            }
        }
        return nodes;
    }
}