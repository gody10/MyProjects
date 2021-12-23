package com.company;

import java.util.ArrayList;
import java.lang.*;

public class Board {
    protected char[][] gameboard;
    private final char[] indexes = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private int userColor;

    public Board(int color) {
        gameboard = new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', 'W', 'B', '_', '_', '_',},
                {'_', '_', '_', 'B', 'W', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
        };
        this.userColor = color;
    }

    /**
     *
     * @return usercolor
     */
    public int getUserColor() {
        return userColor;
    }

    public Board(Board b) {
        gameboard = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.gameboard[i][j] = b.gameboard[i][j];
            }
        }
        this.userColor = b.userColor;
    }

    /**
     *
     * @param player
     * @return total pieces for the given player
     */
    public int CalculatePieces(int player) {
        char ATMPlayer = player == 1 ? 'B' : 'W';
        int pieces = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameboard[i][j] == ATMPlayer)
                    pieces++;
            }
        }
        return pieces;
    }

    /**
     *
     * @return difference between player's and AI's pieces
     */
    public int CalculateAiPieceDifference() {
        int diff = CalculatePieces(1) - CalculatePieces(2);
        return userColor == 1 ? diff : -diff;
    }

    public void displayBoard() {
        System.out.print("\n  ");
        for (int i = 0; i < 8; ++i) System.out.print(indexes[i] + " ");
        System.out.println();
        for (int i = 0; i < 8; ++i) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; ++j)
                System.out.print(gameboard[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    /**
     *
     * @param gameboard
     * sets current gameboard as the one given
     */
    public void setGameboard(char[][] gameboard) {
        this.gameboard = gameboard;
    }


    /**
     * @param X
     * @param Y
     * @param player
     * @return True if the move is possible, false otherwise
     * also makes the move
     */

    public boolean makeMove(int X, int Y, int player) {
        char playerPiece = player == 1 ? 'B' : 'W';
        char OpponentPiece = playerPiece == 'B' ? 'W' : 'B';
        boolean validMove = false;
        Board temp = new Board(this);
        if (gameboard[X][Y] == '_') {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0)
                        continue;
                    boolean piecesToFlip = false, passedOpponent = false;
                    int k = 1;
                    while ((X + j * k >= 0 && X + j * k < 8) && (Y + i * k >= 0 && Y + i * k < 8)) {
                        if ((gameboard[X + j * k][Y + i * k] == '_') || (gameboard[X + j * k][Y + i * k] == playerPiece && !passedOpponent)) {
                            break;
                        }
                        if (gameboard[X + j * k][Y + i * k] == playerPiece && passedOpponent) {
                            piecesToFlip = true;
                            break;
                        } else if (gameboard[X + j * k][Y + i * k] == OpponentPiece) {
                            passedOpponent = true;
                            k++;
                        }
                    }
                    if (piecesToFlip) {

                        gameboard[X][Y] = playerPiece;

                        for (int h = 1; h <= k; h++) {
                            gameboard[X + j * h][Y + i * h] = playerPiece;
                        }

                        validMove = true;
                    }
                }
            }
        }
        return validMove;
    }

    /**
     * @param player
     * @return an  arraylist with boards
     * that contain all possible moves
     */
    public ArrayList<Board> findValidMoves(int player) {
        ArrayList<Board> moves = new ArrayList<>();
        Board b = new Board(this);
        int current = 1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (b.makeMove(i, j, player)) {
                    moves.add(b);
                    b = new Board(this);
                }
            }
        }
        return moves;
    }

    /**
     *
     * boolean function
     * @return True if the game is over, false elsewhere
     */
    public boolean IsTerminal(){
        //boolean endgame = false;
        ArrayList <Board> moves1 = new ArrayList<>();
        ArrayList <Board> moves2 = new ArrayList<>();
        moves1 = findValidMoves(1);
        moves2 = findValidMoves(2);
        return moves1.isEmpty() && moves2.isEmpty();
    }
    /*
     * displayValidMoves
     */

    /**
     *
     * @param move
     * @param player
     * @return true if the string given by the user is a valid move
     * also makes the move
     */
    public boolean placeMove(String move, int player) {
        int Y = Integer.parseInt(String.valueOf(move.charAt(0) - 96)) - 1;
        int X = Integer.parseInt(String.valueOf(move.charAt(1))) - 1;
        return  makeMove(X, Y, player) ;
    }


    /**
     *
     * @return the current gameboard
     */
    public char[][] getGameboard() {
        return gameboard;
    }

}

