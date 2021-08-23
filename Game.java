import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    
    public static void main(String[] args) {
        
        /*
        Bugs to fix:

            when flagging a 0, the other 0s still show...
            unflagging the flag
            integer validation for input

        */

        Scanner input = new Scanner(System.in);  

        System.out.println("\nAre you play easy, medium or hard mode: \n");
        String mode = input.nextLine();

        while(true) {
            Boolean correct_mode=false;
            if(mode.equals("easy") || mode.equals("medium") || mode.equals("hard")) {
                correct_mode = true;
            }
            if(correct_mode == true) {
                break;
            }
            System.out.println("Enter easy, medium or hard: ");
            mode = input.nextLine();  
        }

        Grid grid = new Grid(10,mode);

        grid.displayGrid();

        while(grid.flagged.size() < grid.getBombs().size()) {

            System.out.println("\nWould you like to flag (or unflag)? (yes or no) ");
            String choice = input.nextLine();
            while(true) {
                if(choice.equals("yes") || choice.equals("no")) {
                    break;
                }
                System.out.println("Enter yes or no ");
                choice = input.nextLine();  
            }


            Boolean flag=false;
            
            if(choice.equals("yes")) {
                flag = true;
            } else if(choice.equals("no")) {
                flag = false;
            }

                        
            System.out.println("\nEnter an x coordinate: ");
            int x = Integer.parseInt(input.nextLine());  
            while(x > grid.getSize() || x < 1) {
                System.out.println("Enter a number between 1 and " + grid.getSize() + ": ");
                x = input.nextInt();  
            }

            System.out.println("\nEnter a y coordinate: ");
            int y = Integer.parseInt(input.nextLine());  
            while(y > grid.getSize() || y < 1) {
                System.out.println("Enter a number between 1 and " + grid.getSize() + ": ");
                y = input.nextInt();  
            }

        
            Node selected = grid.select(x,y);

            if(flag == true) {
                if(selected.getIsHidden() == true) {
                    selected.setIsFlagged(true);
                    //selected.willFlag = true;
                    //System.out.println(selected.willFlag);
                }
            }  
            
            grid.check(selected);

            if(selected.getValue() == "B" && selected.getIsFlagged() == true) {
                grid.flagged.add(selected);
            }

            if(selected.getValue() == "B" && selected.getIsFlagged() == false) {
                break;
            } else {
                grid.displayGrid();
            }
            
        }
        input.close();

        System.out.println(grid.getGrid());

        ArrayList<Node> mines = new ArrayList<>();

        for(Node flagged_node : grid.flagged) {
            if(grid.getBombs().contains(flagged_node)) {
                mines.add(flagged_node);
            }
        }

        int n_mines = mines.size();
        String total = "You found " + n_mines;
        if(n_mines != 1) {
            total += " mines.";
        } else {
            total += " mine.";
        }
        System.out.println(total);

        if(grid.flagged.size() == mines.size() && mines.size() > 0) {
            System.out.println("\nYou win!");
        } else {
            System.out.println("\nYou lose!");
            if( grid.flagged.size() > mines.size()) {
                System.out.print(" (too many flags)");
            }
        }
        
        System.out.println("\nThanks for playing!\n");
    }
}