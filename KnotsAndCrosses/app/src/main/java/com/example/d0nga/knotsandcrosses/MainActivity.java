package com.example.d0nga.knotsandcrosses;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    // Global Variables for the game.

    /**
     * 0 = 'O' Knots
     * 1 = 'X' Crosses
     */
    int activePlayer = 1;
    boolean gameIsActive = true;

    // String for the winner
    String winner;


    // Intially all the board positions have not beeen played.
    int[] gameState = {3,3,3,3,3,3,3,3,3};
    int[][] winningStates = {{0,1,2},{3,4,5},{6,7,8},{0,3,7},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void selectPosition(View view){
        ImageView piece = (ImageView) view;
        // Getting the 'Tag' of the piece being tapped on. We then check
        // if this piece has been played before using the gamestate array.
        int pieceNumber = Integer.parseInt(piece.getTag().toString());
        if(gameState[pieceNumber] ==  3 && gameIsActive){
            if(activePlayer == 0){
                gameState[pieceNumber] = 0;
                activePlayer = 1;
                piece.setImageResource(R.drawable.o);
                piece.animate().alpha(1.0f).setDuration(200);
            }
            else{
                gameState[pieceNumber] = 1;
                activePlayer = 0;
                piece.setImageResource(R.drawable.x);
                piece.animate().alpha(1.0f).setDuration(200);
            }
            for(int[] winningState : winningStates){
                // If the gamestate of position winningState are the same in all three position, then we know that we have a win state so the game is
                // over.
                if(gameState[winningState[0]] ==  gameState[winningState[1]] && gameState[winningState[1]] == gameState[winningState[2]]
                        && gameState[winningState[0]] != 3){
                    gameIsActive = false;
                    if(gameState[winningState[0]] == 0){
                        winner = "Knots";
                    }
                    else{
                        winner = "Crosses";
                    }

                    // Setting the text to display the winner
                    TextView winnerMessage = (TextView) findViewById(R.id.gameMessage);
                    winnerMessage.setText(winner+"  Has won!");

                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
                else{
                    boolean gameOver = true;
                    for(int state : gameState){
                        if(state == 3){
                            gameOver = false;
                        }
                        else{
                            gameOver = true;
                        }
                    }
                    if(gameOver){
                        gameIsActive = false;
                        TextView winnerMessage = (TextView) findViewById(R.id.gameMessage);
                        winnerMessage.setText("DRAW!");

                        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }

        }

    }

    public void playAgain(View view){
        gameIsActive = true;
        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer = 1;
        for(int i = 0; i<gameState.length;i++){
            gameState[i] = 3;
        }
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        for(int i = 0;i<gridLayout.getChildCount();i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
