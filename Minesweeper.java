import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Minesweeper{
    private int rowNum;
    private int colNum;
    private int mineNum;

    private int secretBoard[][];
    private String playBoard[][];
    private int winningBoard[][];

    public Minesweeper(int rowNum, int colNum, int mineNum){
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.mineNum = mineNum;

        this.secretBoard = new int[rowNum][colNum];
        this.playBoard = new String[rowNum][colNum];
        this.winningBoard = new int[rowNum][colNum];

        this.populateSecretBoard(mineNum);

        this.populatePlayBoard();

        // this.winningBoard = (this.secretBoard).clone(); MAKES SHALLOW COPY
        this.populateWinningBoard();
    }

    public Minesweeper(int[][] twoDArray, int mineNum){
        this.rowNum = twoDArray.length;
        this.colNum = twoDArray[0].length;
        this.mineNum = mineNum;

        this.secretBoard = twoDArray;
        this.playBoard = new String[this.rowNum][this.colNum];
        this.winningBoard = new int[this.rowNum][this.colNum];

        this.populatePlayBoard();

        // this.winningBoard = (this.secretBoard).clone();
        this.populateWinningBoard();
    }

    public int[][] getSBoard(){
        return this.secretBoard;
    }

    public String[][] getPBoard(){
        return this.playBoard;
    }

    public int[][] getWBoard(){
        return this.winningBoard;
    }

    public int getRowNum(){
        return this.rowNum;
    }

    public int getColNum(){
        return this.colNum;
    }

    public void populateSecretBoard(int mineNum){
        for(int i = 0; i < mineNum; i++){
            this.putMinein();
            // this.fillAll();
        }
    }

    public void populateWinningBoard(){
        for(int row = 0; row < this.getRowNum(); row++){
            for(int col = 0; col < this.getColNum(); col++){
                if(this.getSBoard()[row][col] != 9){
                    this.getWBoard()[row][col] = -1;
                }
                else{
                    this.getWBoard()[row][col] = 9;
                }
            }
        }
    }

    public void populatePlayBoard(){
        for(int row = 0; row < this.getRowNum(); row++){
            for(int col = 0; col < this.getColNum(); col++){
                this.getPBoard()[row][col] = "-";
            }
        }
    }

    // public void fillAll(){
    //     int tempVal;
    //     int[][] tempB = this.getSBoard();
    //     for(int row = 0; row < this.getRowNum(); row++){
    //         for(int col = 0; col < this.getColNum(); col++){
    //             tempVal = tempB[row][col];
                
    //             if(tempVal == 9 || tempVal == ){
    //                 continue;
    //             }
    //             else{

    //             }
    //         }
    //     }
    // }

    public void putMinein(){
        int randRow;
        int randCol;
        
        while (true){
            Random rand = new Random();
            
            randRow = rand.nextInt(this.getRowNum());
            randCol = rand.nextInt(this.getColNum());

            if(this.getSBoard()[randRow][randCol] != 9){
                //System.out.println("row is " + randRow + " and col is " + randCol);
                this.getSBoard()[randRow][randCol] = 9;
                // System.out.println("random row and random col are " + randRow + " " + randCol);
                int[] neighbors = this.getadjNeigh(randRow, randCol);

                for(int i = 0; i < 8; i++){
                    if(neighbors[i] == 9 || neighbors[i] == -1){
                        continue;
                    }
                    else{
                        if(i == 0){
                            this.getSBoard()[randRow-1][randCol-1] += 1;
                        }
                        else if(i == 1){
                            this.getSBoard()[randRow-1][randCol] += 1;
                        }
                        else if(i == 2){
                            this.getSBoard()[randRow-1][randCol+1] += 1;
                        }
                        else if(i == 3){
                            this.getSBoard()[randRow][randCol-1] += 1;
                        }
                        else if(i == 4){
                            this.getSBoard()[randRow][randCol+1] += 1;
                        }
                        else if(i == 5){
                            this.getSBoard()[randRow+1][randCol-1] += 1;
                        }
                        else if(i == 6){
                            this.getSBoard()[randRow+1][randCol] += 1;
                        }
                        else{
                            this.getSBoard()[randRow+1][randCol+1] += 1;
                        }
                    }
                }
                break;
            }
        }
    }

    public int[] getadjNeigh(int row, int col){
        //in form NW-N-NE-W-E-SW-S-SE
        //index is 0-1-2 -3-4-5- 6-7

        int[] returnArr = new int[8];

        if(row < 1){
            if(col < 1){
                returnArr[4] = this.getSBoard()[row][col+1];
                returnArr[6] = this.getSBoard()[row+1][col];
                returnArr[7] = this.getSBoard()[row+1][col+1];

                returnArr[0] = -1;
                returnArr[1] = -1;
                returnArr[2] = -1;
                returnArr[3] = -1;
                returnArr[5] = -1;
            }
            else if(col == this.getColNum()-1){
                returnArr[3] = this.getSBoard()[row][col-1];
                returnArr[5] = this.getSBoard()[row+1][col-1];
                returnArr[6] = this.getSBoard()[row+1][col];

                returnArr[0] = -1;
                returnArr[1] = -1;
                returnArr[2] = -1;
                returnArr[4] = -1;
                returnArr[7] = -1;
            }
            else{
                returnArr[3] = this.getSBoard()[row][col-1];
                returnArr[4] = this.getSBoard()[row][col+1];
                returnArr[5] = this.getSBoard()[row+1][col-1];
                returnArr[6] = this.getSBoard()[row+1][col];
                returnArr[7] = this.getSBoard()[row+1][col+1];

                returnArr[0] = -1;
                returnArr[1] = -1;
                returnArr[2] = -1;
            }
        }

        else if(row == this.getRowNum()-1){
            if(col < 1){
                returnArr[1] = this.getSBoard()[row-1][col];
                returnArr[2] = this.getSBoard()[row-1][col+1];
                returnArr[4] = this.getSBoard()[row][col+1];

                returnArr[0] = -1;
                returnArr[3] = -1;
                returnArr[5] = -1;
                returnArr[6] = -1;
                returnArr[7] = -1;
            }
            else if(col == this.getColNum()-1){
                returnArr[0] = this.getSBoard()[row-1][col-1];
                returnArr[1] = this.getSBoard()[row-1][col];
                returnArr[3] = this.getSBoard()[row][col-1];

                returnArr[2] = -1;
                returnArr[4] = -1;
                returnArr[5] = -1;
                returnArr[6] = -1;
                returnArr[7] = -1;
            }
            else{
                returnArr[0] = this.getSBoard()[row-1][col-1];
                returnArr[1] = this.getSBoard()[row-1][col];
                returnArr[2] = this.getSBoard()[row-1][col+1];
                returnArr[3] = this.getSBoard()[row][col-1];
                returnArr[4] = this.getSBoard()[row][col+1];

                returnArr[5] = -1;
                returnArr[6] = -1;
                returnArr[7] = -1;
            }
        }

        else if(col < 1){
            returnArr[1] = this.getSBoard()[row-1][col];
            returnArr[2] = this.getSBoard()[row-1][col+1];
            returnArr[4] = this.getSBoard()[row][col+1];
            returnArr[6] = this.getSBoard()[row+1][col];
            returnArr[7] = this.getSBoard()[row+1][col+1];

            returnArr[0] = -1;
            returnArr[3] = -1;
            returnArr[5] = -1;
        }

        else if(col == this.getColNum()-1){
            returnArr[0] = this.getSBoard()[row-1][col-1];
            returnArr[1] = this.getSBoard()[row-1][col];
            returnArr[3] = this.getSBoard()[row][col-1];
            returnArr[5] = this.getSBoard()[row+1][col-1];
            returnArr[6] = this.getSBoard()[row+1][col];

            returnArr[2] = -1;
            returnArr[4] = -1;
            returnArr[7] = -1;
        }

        else{
            returnArr[0] = this.getSBoard()[row-1][col-1];
            returnArr[1] = this.getSBoard()[row-1][col];
            returnArr[2] = this.getSBoard()[row-1][col+1];
            returnArr[3] = this.getSBoard()[row][col-1];
            returnArr[4] = this.getSBoard()[row][col+1];
            returnArr[5] = this.getSBoard()[row+1][col-1];
            returnArr[6] = this.getSBoard()[row+1][col];
            returnArr[7] = this.getSBoard()[row+1][col+1];
        }
        return returnArr;
    }

    public static void representBoard(int[][] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.println(Arrays.toString(arr[i]));
        }
    }

    public static void representBoard(String[][] arr){
        int row = 0;
        int col = 0;
        int numRows = arr.length;
        int numCols = arr[0].length;
        String cols = "";

        System.out.println("\nrow");
        for(int i = 0; i < numRows; i++){
            System.out.println(Integer.toString(i) + "  " + Arrays.toString(arr[i]));
        }

        for(int i = 0; i < numCols; i++){
            cols += Integer.toString(i) + "  ";
        }
        System.out.println("col " + cols + "\n");
    }

    public boolean reveal(int row, int col){
        // System.out.println("row is " + Integer.toString(row) + " and col is " + Integer.toString(col));
        if(this.getPBoard()[row][col] == "x"){
            System.out.println("You've blocked this square. Unblock first!");
        }

        else if(this.getSBoard()[row][col] == 9){
            System.out.println("YOU HIT A MINE, BETTER LUCK NEXT TIME!");
            return false;
        }

        else if(this.getSBoard()[row][col] == -1){
            //do nothing
        }

        else if(this.getSBoard()[row][col] == 0){
            int[] neighbors = this.getadjNeigh(row, col);
            // System.out.println("should come in here to update player board");
            this.getPBoard()[row][col] = "o";
            this.getSBoard()[row][col] = -1;
            // representBoard(this.getPBoard(), this.getRowNum());
            // System.out.println("array is " + Arrays.toString(neighbors));

            for(int i = 0; i < 8; i++){
                // System.out.println("i is " + Integer.toString(i));
                if(neighbors[i] == -1){
                    continue;
                }

                else{
                    if(i == 0){
                        this.reveal(row-1, col-1);
                    }
                    else if(i == 1){
                        this.reveal(row-1, col);
                    }
                    else if(i == 2){
                        this.reveal(row-1, col+1);
                    }
                    else if(i == 3){
                        this.reveal(row, col-1);
                    }
                    else if(i == 4){
                        // System.out.println("should come in here first!");
                        this.reveal(row, col+1);
                    }
                    else if(i == 5){
                        this.reveal(row+1, col-1);
                    }
                    else if(i == 6){
                        this.reveal(row+1, col);
                    }
                    else if(i == 7){
                        this.reveal(row+1, col+1);
                    }
                }

                // else{
                //     // System.out.println("the value is inside here instead and is " + Integer.toString(this.getSBoard()[row][col]) + " for row and col " + Integer.toString(row) + " " + Integer.toString(col));
                //     this.getPBoard()[row][col] = Integer.toString(this.getSBoard()[row][col]);
                //     this.getSBoard()[row][col] = -1;
                // }
            }
        }

        else{
            // System.out.println("the value is " + Integer.toString(this.getSBoard()[row][col]));
            this.getPBoard()[row][col] = Integer.toString(this.getSBoard()[row][col]);
            this.getSBoard()[row][col] = -1;
        }
        return true;
    }

    public void block(int row, int col){
        if(this.getSBoard()[row][col] == -1){
            System.out.println("Cannot block what has already been revealed!");
        }
        else{
            this.getPBoard()[row][col] = "x";
        }
    }

    public void unblock(int row, int col){
        if(this.getPBoard()[row][col] == "x"){
            this.getPBoard()[row][col] = "-";
        }
        else{
            System.out.println("Cannot unblock what has not been blocked!");
        }
    }

    public boolean winner(){
        for(int row = 0; row < this.getRowNum(); row++){
            for(int col = 0; col < this.getColNum(); col++){
                if(this.getSBoard()[row][col] == this.getWBoard()[row][col]){
                    continue;
                }
                else{
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String [] args){
        // Minesweeper ms = new Minesweeper(4, 5, 5);
        // representBoard(ms.getSBoard(), ms.getRowNum());
        // representBoard(ms.getPBoard(), ms.getRowNum());

        // ms.reveal(2, 2);

        // representBoard(ms.getSBoard(), ms.getRowNum());
        // representBoard(ms.getPBoard(), ms.getRowNum());

        // System.out.println(Arrays.toString(ms.getadjNeigh(0, 2)));
        // System.out.println(Arrays.deepToString(ms.getPBoard()));

        System.out.println("Enter number of rows for grid: ");
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        System.out.println("Enter number of cols for grid: ");
        int col = scanner.nextInt();
        System.out.println("Enter number of mines inside grid: ");
        int mines = scanner.nextInt();

        Minesweeper ms = new Minesweeper(row, col, mines);
        String string;

        while (true){
            representBoard(ms.getPBoard());
            // representBoard(ms.getSBoard());
            System.out.println("Would you like to reveal, block or unblock? \nType 'reveal', 'block' or 'unblock'.");
            string = scanner.next();
            // System.out.println("string is " + string + " with length " + string.length());

            System.out.println("Pick a row: ");
            row = scanner.nextInt();
            System.out.println("Pick a column: ");
            col = scanner.nextInt();

            if( (row > ms.getRowNum()-1) || (col > ms.getColNum()-1)){
                System.out.println("That number lies outside the grid!");
                try{
                    Thread.sleep(3000);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }

            else if(string.equals("block")){
                ms.block(row, col);
            }

            else if(string.equals("unblock")){
                ms.unblock(row, col);
            }


            else if(string.equals("reveal")){
                if( ms.reveal(row, col) ){
                    // representBoard(ms.getWBoard());
                    // representBoard(ms.getSBoard());
                    if( ms.winner() ){
                        System.out.println("YOU WON!");
                        representBoard(ms.getPBoard());
                        break;
                    }
                    continue;
                }
                else{
                    break;
                }
            }

            else{
                System.out.println("You chose to " + string + " which is not a valid option. Please input valid option!");
                try{
                    Thread.sleep(3000);
                }
                catch (Exception e){
                    System.out.println(e);
                }
                continue;
            }
            // System.out.println("Pick a row: ");
            // row = scanner.nextInt();
            // System.out.println("Pick a column: ");
            // col = scanner.nextInt();

            // if( ms.reveal(row, col) ){
            //     continue;
            // }
            // else{
            //     break;
            // }

        }
    //     int[][] arr = new int[][]{
    //         {0, 0, 1, 9},
    //         {1, 1, 1, 1},
    //         {9, 1, 0, 0},
    //         {1, 1, 0, 0}
    //     };

    //     Minesweeper ms = new Minesweeper(arr, 2);

    //     representBoard(ms.getPBoard(), ms.getRowNum());
    //     representBoard(ms.getSBoard(), ms.getRowNum());

    //     if(ms.reveal(0,0)){
    //         System.out.println("came in here succesfully!");
    //     };

    //     if(ms.reveal(2,2)){
    //         System.out.println("came in here succesfully! ROUND 2");
    //     };

    //     representBoard(ms.getPBoard(), ms.getRowNum());
    //     representBoard(ms.getSBoard(), ms.getRowNum());
    }

}