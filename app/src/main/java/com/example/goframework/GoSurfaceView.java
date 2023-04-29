package com.example.goframework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.GameFramework.utilities.FlashSurfaceView;

/**
 * class GoSurfaceView
 *
 * This class contains all the functions to translate a point to a coordinate and draws all
 * the GUI. It draws the grid and stones which are visible to the user.
 *
 * @author Lucian Kennelly, Connor Sisourath, Malissa Chen, Colin Miller
 * @date 28 April 2023
 */
public class GoSurfaceView extends FlashSurfaceView {

    protected GoGameState state;

    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_DANGER = -4;
    private int BLACK_DANGER = -5;

    private int boardLength;
    public float pixelDelta;

    private int centerX;
    private int centerY;

    /**
     * Constructor
     * @param: Context context
     */
    public GoSurfaceView(Context context) {
        super(context);
        init();
    }

    /**
     * Constructor
     * @param: Context context
     * @param: AttributeSet attrs
     */
    public GoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * setState
     * @param: GoGameState
     * This method sets the state and boardLength instance variables
     */
    public void setState(GoGameState state) {
        this.state = state;
        boardLength = state.boardSize;
    }

    /**
     * onDraw
     * @param: Canvas g
     * This method calls the helper methods to draw the grid and stones.
     */
    public void onDraw(Canvas g) {
        init();
        if (state == null) {
            return;
        }
        pixelDelta = pixelRatio(g);
        drawGrid(g, pixelDelta);
        drawStones(g, pixelDelta);
    }

    /**
     * init
     * @return: void
     * This method initializes the surface view and sets the background color
     */
    public void init() {
        invalidate();
        setBackgroundColor(Color.parseColor("#E6D2B4"));
    }

    /**
     * translateToIndex
     * @param: Point pos
     * @param: View v
     * This method translates a point to a coordinate on the board
     */
    Point translateToIndex(Point pos, View v ){
        Log.d("tag","pixelDelta:"+pixelDelta);
        Log.d("tag","x:"+pos.x);
        Log.d("tag","y:"+pos.y);

        int boardX = centerX + Math.round(pixelDelta)/2;
        int boardY = centerY + Math.round(pixelDelta)/2;

        int bX = pos.x - boardX;
        int bY = pos.y - boardY;

        int x = Math.round (bX / pixelDelta);
        int y = Math.round (bY / pixelDelta);

        Log.d("tag", "x coord" + x);
        Log.d("tag", "y coord" + y);
        if (x < 0 || y < 0) {
            return null;
        } else {
            state.setX(x);
            state.setY(y);
            return new Point(x, y);
        }

    }

    /**
     * drawGrid
     * @param: Canvas g
     * @param; float pixelDelta
     * This method draws the GO grid on the screen
     */
    public void drawGrid(Canvas g, float pixelDelta) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10f); // Set the stroke width to 10 pixels

        // Load the bitmap from the resources
        Bitmap background = BitmapFactory.decodeResource(getResources(),
                R.drawable.wooden_board_background);
        Bitmap sbackground = Bitmap.createScaledBitmap(background,
                (int) (boardLength * pixelDelta - pixelDelta), (int) (boardLength * pixelDelta -pixelDelta), false);

        //the position to center the board on the screen
        centerX =  150;
        centerY = 0;


        // Draw the bitmap as the background centered on the screen
        g.drawBitmap(sbackground, centerX + pixelDelta/2, centerY + pixelDelta/2, null);

        // Draw the grid lines
        for (float i = 0; i < boardLength; i++) {
            g.drawLine(centerX + pixelDelta - pixelDelta/2,
                    centerY + (pixelDelta * (i + 1))-pixelDelta/2,
                    centerX + (pixelDelta * boardLength)-pixelDelta/2,
                    centerY + (pixelDelta * (i + 1))-pixelDelta/2,paint);
        }
        paint.setColor(Color.BLACK);
        for (float j = 0; j < boardLength; j++) {
            g.drawLine(centerX + (pixelDelta * (j + 1))-pixelDelta/2,
                    centerY + pixelDelta-pixelDelta/2,
                    centerX + (pixelDelta * (j + 1))-pixelDelta/2,
                    centerY + (pixelDelta * (boardLength))-pixelDelta/2,paint);
        }
    }

    /**
     * drawStones
     * @param: Canvas g
     * @param; float pixelDelta
     * This method draws all the stones
     */
    public void drawStones(Canvas g, float pixelDelta) {
        float pieceDiameter = 2 * pixelDelta / 3;

        //getting all the stone graphics
        Bitmap whiteStone = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.white_stone);
        Bitmap scaledWhiteStone = Bitmap.createScaledBitmap(whiteStone,
                (int) (pieceDiameter), (int) (pieceDiameter), false);
        Bitmap blackStone = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.black_stone);
        Bitmap scaledBlackStone = Bitmap.createScaledBitmap(blackStone,
                (int) (pieceDiameter), (int) (pieceDiameter), false);
        Paint paint = new Paint();

        //traversing the board and drawing it on the screen
        for (int i = 0; i < state.boardSize; i++) {
            for (int j = 0; j < state.boardSize; j++) {
                if (state.getGameBoard(i, j) == WHITE) {
                    paint.setColor(Color.WHITE);
                    g.drawBitmap(scaledWhiteStone,
                            centerX + pieceDiameter + (pixelDelta * i - (pixelDelta/2)),
                            centerY + pieceDiameter /2 -15 +(pixelDelta * j),
                            new Paint());
                } else if (state.getGameBoard(i, j) == BLACK) {
                    paint.setColor(Color.BLACK);
                    g.drawBitmap(scaledBlackStone,
                            centerX + pieceDiameter + (pixelDelta * i - (pixelDelta/2)),
                            centerY + pieceDiameter /2 - 15 + (pixelDelta * j),
                            new Paint());
                }
            }
        }
    }


    /**
     * pixelRatio
     * @param: Canvas canvas
     * This returns the pixeldelta depending of the canvas.
     */
    public float pixelRatio(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        int xNeed = boardLength;
        int yNeed = boardLength;
        return Math.min(w / xNeed, h / yNeed);
    }
}