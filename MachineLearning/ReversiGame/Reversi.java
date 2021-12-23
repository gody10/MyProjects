package com.company;

import java.util.ArrayList;

public class Reversi {
    private int maxLevel;


    public Reversi(int lvl){
        this.maxLevel = lvl;
    }


    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     *
     * @param depth
     * @param board
     * @param maximizingPlayer
     * @param score1
     * @param score2
     * @return max or min score possible depending on the player
     */
    public int minimax(int depth, Board board, boolean maximizingPlayer, int score1, int score2) {
        if (depth > maxLevel) {
            return board.CalculateAiPieceDifference();
        }
        // get all the possible valid moves //
        // let it be moves//
        ArrayList<Board> moves = board.findValidMoves(board.getUserColor()%2+1);

        if (moves.size() == 0) {
            return maximizingPlayer ? 64 : -64;
        }

        if (maximizingPlayer) {
            int top = 0;
            for (int i = 0; i < moves.size(); i++) {
                int score = minimax(depth + 1, moves.get(i), false, score1, score2);

                if (score > score1) {
                    score1 = score;
                    top = i;
                }
                if (score1 > score2)
                    break;
            }
            if (depth == 1)
                board.setGameboard(moves.get(top).getGameboard());
            return score1;
        }
        else {
            for (Board b2 : moves){
                int score = minimax(depth + 1, b2, true, score1, score2);

                if (score < score2)
                    score2 = score;

                if (score1 > score2)
                    break;
            }
        return score2;
        }
    }
}