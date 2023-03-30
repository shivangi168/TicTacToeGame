import java.util.*;

import java.util.Random;


import java.util.Scanner;

public class TicTacToeGame {

    public static void main(String[] args) {
        try (Scanner userInputReader = new Scanner(System.in)) {
            Board board = new Board();
            board.instructionBoard();
            System.out.println("Please enter a position:");
            int position = Integer.parseInt(userInputReader.nextLine());
            board.placePiece(position, "X");

            boolean isGameInProgress = GameResolver.resolve(board.getBoard())==GameResolver.GameState.IN_PROGRESS;

            while( isGameInProgress && !board.isFull()){

                board.placePieceRandomly("O");
                board.displayBoard();
                System.out.println("Please enter a position:");
                position = Integer.parseInt(userInputReader.nextLine());
                board.placePiece(position, "X");

                isGameInProgress = GameResolver.resolve(board.getBoard())==GameResolver.GameState.IN_PROGRESS;

            }
            if(!isGameInProgress) {
                board.displayBoard();
                System.out.print(" GAME OVER!!!");
            }
            else board.displayBoard();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class Board {
    // 0 means empty
    // 1 means X
    // 2 means O
    private int[][] board= new int[3][3];

    public void instructionBoard(){
        System.out.println("| - | - | - |");
        System.out.println("| 1 | 2 | 3 |");
        System.out.println("| - | - | - |");
        System.out.println("| 4 | 5 | 6 |");
        System.out.println("| - | - | - |");
        System.out.println("| 7 | 8 | 9 |");
        System.out.println("| - | - | - |");
    }

    public void displayBoard(){
        System.out.println("| - | - | - |");
        System.out.println(printBoardRow(0));
        System.out.println("| - | - | - |");
        System.out.println(printBoardRow(1));
        System.out.println("| - | - | - |");
        System.out.println(printBoardRow(2));
        System.out.println("| - | - | - |");

    }

    public boolean placePiece(int position, String pieceType){
        int row = (position-1)/3;
        int col = (position - (row*3))-1;
        if(board[row][col] == 0) {
            if (pieceType.equals("X")) board[row][col] = 1;
            if (pieceType.equals("O")) board[row][col] = 2;
            return true;
        }
        return false;

    }

    public boolean placePieceRandomly(String pieceType){
        int row = new Random().nextInt(3);
        int col = new Random().nextInt(3);
        boolean wasPiecePlace = false;
        while(!wasPiecePlace && !isFull()){
            if(board[row][col]==0){
                wasPiecePlace=true;
                if (pieceType.equals("X")) board[row][col] = 1;
                if (pieceType.equals("O")) board[row][col] = 2;
            }else{
                row = new Random().nextInt(3);
                col = new Random().nextInt(3);
            }
        }
        return wasPiecePlace;
    }

    public boolean isFull(){

        for(int row=0;row<board.length;row++){
            for(int col=0;col<board[0].length;col++){
                if(board[row][col]==0) return false;
            }
        }
        return true;
    }


    private String printBoardRow(int row){
        StringBuilder rowBuilder = new StringBuilder("| ");
        for(int i=0;i<board[0].length;i++){
            if(board[row][i] == 0) rowBuilder.append(" ");
            if(board[row][i] == 1) rowBuilder.append("X");
            if(board[row][i] == 2) rowBuilder.append("O");
            rowBuilder.append(" | ");
        }
        rowBuilder.deleteCharAt(rowBuilder.lastIndexOf(" "));
        return rowBuilder.toString();
    }

    public int[][] getBoard(){
        return board;
    }

    public static class Cell{
        public int row;
        public int col;

        public Cell(int row, int col){
            this.row = row;
            this.col = col;
        }
    }

}
 class GameResolver {

    public static GameState calculateGameState(int[][] board, Board.Cell position){

        int gamePiece = board[position.row][position.col];
        if (gamePiece == 0) return GameState.IN_PROGRESS;
        // DOWN DIRECTION
        boolean isCellOutOfBoard = (position.row-1 < 0);
        if (!isCellOutOfBoard && (board[position.row-1][position.col] == gamePiece)){
            isCellOutOfBoard = (position.row-2<0);
            if(!isCellOutOfBoard && (board[position.row-2][position.col] == gamePiece)){
                return gamePiece==1?GameState.X_WON:GameState.O_WON;
            }
        }
        // RIGHT UP DIRECTION
        isCellOutOfBoard = (position.row-1<0) || (position.col+1>board[0].length-1);
        if ( !isCellOutOfBoard && (board[position.row-1][position.col+1] == gamePiece)){
            isCellOutOfBoard = (position.row-2<0) || (position.col+2>board[0].length-1);
            if(!isCellOutOfBoard && (board[position.row-2][position.col+2] == gamePiece)){
                return gamePiece==1?GameState.X_WON:GameState.O_WON;
            }
        }

        // RIGHT DIRECTION
        isCellOutOfBoard = (position.col+1>board[0].length-1);
        if ( !isCellOutOfBoard && (board[position.row][position.col+1] == gamePiece)){
            isCellOutOfBoard = (position.col+2>board[0].length-1);
            if(!isCellOutOfBoard && (board[position.row][position.col+2] == gamePiece)){
                return gamePiece==1?GameState.X_WON:GameState.O_WON;
            }
        }

        // RIGHT DOWN DIRECTION
        isCellOutOfBoard =position.row+1>board.length-1 || (position.col+1>board[0].length-1);
        if ( !isCellOutOfBoard && (board[position.row+1][position.col+1] == gamePiece)){
            isCellOutOfBoard = position.row+2>board.length-1 || (position.col+2>board[0].length-1);
            if(!isCellOutOfBoard && (board[position.row+2][position.col+2] == gamePiece)){
                return gamePiece==1?GameState.X_WON:GameState.O_WON;
            }
        }

        // DOWN DIRECTION
        isCellOutOfBoard =position.row+1>board.length-1;
        if ( !isCellOutOfBoard && (board[position.row+1][position.col] == gamePiece)){
            isCellOutOfBoard = position.row+2>board.length-1;
            if(!isCellOutOfBoard && (board[position.row+2][position.col] == gamePiece)){
                return gamePiece==1?GameState.X_WON:GameState.O_WON;
            }
        }

        // LEFT DOWN DIRECTION
        isCellOutOfBoard =position.row+1>board.length-1 || (position.col-1<0);
        if ( !isCellOutOfBoard && (board[position.row+1][position.col] == gamePiece)){
            isCellOutOfBoard =position.row+2>board.length-1 || (position.col-2<0);
            if(!isCellOutOfBoard && (board[position.row+2][position.col-2] == gamePiece)){
                return gamePiece==1?GameState.X_WON:GameState.O_WON;
            }
        }

        // LEFT DIRECTION
        isCellOutOfBoard = (position.col-1<0);
        if ( !isCellOutOfBoard && (board[position.row][position.col-1] == gamePiece)){
            isCellOutOfBoard =(position.col-2<0);
            if(!isCellOutOfBoard && (board[position.row][position.col-2] == gamePiece)){
                return gamePiece==1?GameState.X_WON:GameState.O_WON;
            }
        }

        // LEFT UP DIRECTION
        isCellOutOfBoard = (position.row-1<0) || (position.col-1<0);
        if ( !isCellOutOfBoard && (board[position.row-1][position.col-1] == gamePiece)){
            isCellOutOfBoard = (position.row-2<0) || (position.col-2<0);
            if(!isCellOutOfBoard && (board[position.row-2][position.col-2] == gamePiece)){
                return gamePiece==1?GameState.X_WON:GameState.O_WON;
            }
        }

        return GameState.IN_PROGRESS;
    }

    public static GameState resolve(int[][] board) {
        for(int row=0;row<board.length;row++){
            for(int col=0;col<board[0].length;col++){
                GameState gameState = calculateGameState(board,new Board.Cell(row,col));
                if(gameState!=GameState.IN_PROGRESS){
                    return gameState;
                }
            }
        }

        return GameState.IN_PROGRESS;
    }

    public enum GameState{
        IN_PROGRESS,X_WON,O_WON;
    }
}

