package com.example.goframework;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.GameFramework.players.GameHumanPlayer;

/**
 * class GoHumanPlayer1
 *
 * This class partially controls the GUI. Additionally, it accepts inputs from the user
 * and updates the game state with each valid input.
 *
 * @author Lucian Kennelly, Connor Sisourath, Malissa Chen, Colin Miller
 * @date 28 April 2023
 */

public class GoHumanPlayer1 extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener {

    // UI components for the game screen
    private GoSurfaceView surfaceView;
    private Button skipButton = null;
    private int layoutId;
    public TextView playerOneScoreTextView;
    public TextView playerTwoScoreTextView;

    //game state variables
    private GoGameState state;
    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_DANGER = -4;
    private int BLACK_DANGER = -5;

    /**
     * Constructor
     * @param: String name
     * @param: int layoutId
     */
    public GoHumanPlayer1(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }


    /**
     * receiveInfo
     * @param: GameInfo info
     * @return: void
     * This method receives information of the game from a GameInfo object which is casted to a
     * GoGameState object. It then updates the GUI.
     */
    public void receiveInfo(GameInfo info) {
        if(surfaceView == null) {
            return;
        }
        // if the info is about an illegal move or not the player's turn, flash the surface view in red
        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            surfaceView.flash(Color.RED, 50);
        }
        // otherwise, update the game state and score displays
        else {
            state = (GoGameState) info;
            surfaceView.setState((GoGameState) info);
            playerOneScoreTextView.setText("White Piece Score: " + state.getWhiteScore());
            playerTwoScoreTextView.setText(("Black Piece Score: " + state.getBlackScore()));
        }

    }

    /**
     * setAsGui
     * @param: GameMainActivity activity
     * @return: void
     * This method initializes sets each GUI component to the one in the XML and
     * sets a listener for the various elements.
     */
    public void setAsGui(GameMainActivity activity) {
        activity.setContentView(layoutId);
        this.skipButton = (Button)activity.findViewById(R.id.button);
        surfaceView = (GoSurfaceView)myActivity.findViewById(R.id.goSurfaceViewXML);

        playerOneScoreTextView = (TextView)activity.findViewById(R.id.playerOneScoreXML);
        playerTwoScoreTextView = (TextView)activity.findViewById(R.id.playerTwoScoreXML);


        surfaceView.setOnTouchListener(this);
        skipButton.setOnClickListener(this);
    }

    /**
     * getTopView
     * @return: View
     * This method returns top_gui_layout.
     */
    @Override
    public View getTopView() {
       return myActivity.findViewById(R.id.top_gui_layout);
    }


    /**
     * onTouch
     * @param: View view
     * @param: MotionEvent event
     * @retyrn: boolean
     * This method is the listener for the user's touch. If the user touches a valid position
     * on the board during their turn then it translates their input to the coordinates relative
     * to the board and their piece appears.
     */
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(event.getAction() != MotionEvent.ACTION_UP) {
            return true;
        }
        // get the x and y coordinates of the touch event, and translate them to board indices
        int x = (int)event.getX();
        int y = (int)event.getY();
        Log.d("tag","x:"+x);
        Log.d("tag","y:"+y);
        Point p = new Point(x,y);
        Point finalP = surfaceView.translateToIndex(p, view);

        // if the move is illegal or not the player's turn, flash the surface view in red
        if(finalP == null) {
            surfaceView.flash(Color.RED, 50);
        }

        //else send a place piece action
        else {
            GoPlacePieceAction action = new GoPlacePieceAction(this, finalP.x, finalP.y);
                game.sendAction(action);
                surfaceView.invalidate();
        }
        return true;
    }

    /**
     * onClick
     * @param: View view
     * @retyrn: boolean
     * This method is the listener for the skip button
     */
    public void onClick(View view) {
        if (view != null) {
            GoSkipTurnAction action = new GoSkipTurnAction(this);
            game.sendAction(action);
        }
        surfaceView.invalidate();
    }
}
