import java.util.Scanner;

public class Game {
    
    public static void main(String[] args) {
        
        /*
        Bugs to fix:

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

        while(grid.minesFound.size() < grid.getBombs().size()) {

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
            
            int x = input.nextInt();
                while(x > grid.getSize() || x < 1) {
                    System.out.println("Enter a number between 1 and " + grid.getSize() + ": ");
                    x = input.nextInt();  
                }

            System.out.println("\nEnter a y coordinate: ");
            int y = input.nextInt();  
            while(y > grid.getSize() || y < 1) {
                System.out.println("Enter a number between 1 and " + grid.getSize() + ": ");
                y = input.nextInt();  
            }

            Node selected = grid.select(x,y);

            if(flag == true) {
                if(selected.getIsHidden() == true) {
                    selected.willFlag = true;
                }
                else {
                    selected.willFlag = false;
                }
            }  
            
            grid.check(selected);

            if(selected.getIsFlagged().equals(true)) {
                grid.flagged.add(selected);
            }
            
            if(selected.getValue() == "B" && selected.getIsFlagged().equals(false)) {
                break;
            } else if(selected.getValue() == "B" && selected.getIsFlagged()) {
                grid.minesFound.add(selected);
            }
            grid.displayGrid();
    
        }

        input.close();

        System.out.println(grid.getGrid());

        System.out.println("Flagged: " + grid.flagged.size());
        System.out.println("Total mines: " + grid.getBombs().size());
        System.out.println("Mines Found: " + grid.minesFound.size());

        String message = "";

        if(grid.minesFound.size() == grid.getBombs().size() && grid.minesFound.size() == grid.flagged.size()) {
            message += "\nYou found all the mines!\nYou Win!\n";
        } else {
            if(grid.minesFound.size() < grid.getBombs().size()) {
                if(grid.minesFound.size() == 1 ) {
                    message += "\nYou found " + grid.minesFound.size() + " mine.\n";
                } else {
                    message += "\nYou found " + grid.minesFound.size() + " mines.\n";
                }
            } else if(grid.flagged.size() > grid.minesFound.size()) {
                message += "\nYou flagged too many nodes!\n";
            }
            message += "\nYou lose!\n";
        }

        System.out.println(message);

        System.out.println("\nThanks for playing!\n");
    }
}