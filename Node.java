public class Node {

    private int x_coord;
    private int y_coord;
    private String value;
    private Boolean isFlagged = false;
    private Boolean isHidden = true;
    private Boolean isBomb = false;
    public Boolean willFlag = false;

    public Node() {}

    public Node(int x_coord, int y_coord) {
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }

    public void setX(int x_coord) {this.x_coord = x_coord;}
    public void setY(int y_coord) {this.y_coord = y_coord;}
    public void setValue(String value) {this.value = value;}
    public void setBomb() {
        value = "B";
        isBomb = true;
    }
    public void setIsFlagged(Boolean isFlagged) {this.isFlagged = isFlagged;}
    public Boolean getIsBomb() {return isBomb;}
    public int getX() {return x_coord;}
    public int getY() {return y_coord;}
    public String getValue() {return value;}
    public Boolean getIsFlagged() {return isFlagged;}
    public Boolean getIsHidden() {return isHidden;}

    public void reveal() {
        isHidden = false;
    }
}