package com.example.goframework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

import com.example.GameFramework.utilities.FlashSurfaceView;
import com.example.goframework.GoGameState;

public class GoSurfaceView extends FlashSurfaceView {

    protected GoGameState state;
    private int boardLength;
    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_IN_PERIL = -4;
    private int BLACK_IN_PERIL = -5;
    public float pixelDelta;

    public GoSurfaceView(Context context) {
        super(context);
        init();
    }

    public GoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setState(GoGameState state) {
        this.state = state;
        boardLength = state.boardSize;
    }

    public void onDraw(Canvas g) {
        init();
        if (state == null) {
            return;
        }
        pixelDelta = pixelRatio(g);
        drawGrid(g, pixelDelta);
        drawStones(g, pixelDelta);
    }

    public void init() {
        setBackgroundColor(Color.parseColor("#E6D2B4"));
    }

    Point translateToIndex(Point pos) {
        int x = -1;
        int y = -1;
        for (float i = 0; i < (float)boardLength; i++) {
            if (pos.x >= pixelDelta * i - pixelDelta/2F  && pos.x <= pixelDelta * i + pixelDelta/2F) {
                y = (int) i;
            }
        }
        for (float j = 0; j <  (float)boardLength; j++) {
            if (pos.y >= pixelDelta * j - pixelDelta/2F && pos.y <= pixelDelta * j+ pixelDelta/2F) {
                x = (int) j;
            }
        }
        if (x == -1 || y == -1) {
            return null;
        } else {
            state.setX(x);
            state.setY(y);
            return new Point(x, y);
        }
    }

    public void drawGrid(Canvas g, float pixelDelta) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10f); // Set the stroke width to 10 pixels

        // Load the bitmap from the resources
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.wooden_board_background);

        // Draw the bitmap as the background
        g.drawBitmap(background, null, new RectF(0, 0, getWidth(), getHeight()), null);

        for (float i = 0; i <  (float)boardLength; i++) {
            g.drawLine(pixelDelta - pixelDelta / 2, pixelDelta * (i + 1) - pixelDelta / 2,
                    pixelDelta * boardLength - pixelDelta / 2, pixelDelta * (i + 1) - pixelDelta / 2, paint);
        }
        paint.setColor(Color.BLACK);
        for (float j = 0; j <(float) boardLength; j++) {

            g.drawLine(pixelDelta * (j + 1) - pixelDelta / 2, pixelDelta - pixelDelta / 2,
                    pixelDelta * (j + 1) - pixelDelta / 2, pixelDelta * (boardLength) - pixelDelta / 2, paint);
        }
    }

    public void drawStones(Canvas g, float pixelDelta) {
        Bitmap whiteStone = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.white_stone);
        Bitmap scaledWhiteStone = Bitmap.createScaledBitmap(whiteStone, (int) (2 * pixelDelta / 3), (int) (2 * pixelDelta / 3), false);
        Bitmap blackStone = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.black_stone);
        Bitmap scaledBlackStone = Bitmap.createScaledBitmap(blackStone, (int) (2 * pixelDelta / 3), (int) (2 * pixelDelta / 3), false);
        Paint paint = new Paint();
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                if (state.getGameBoard(i, j) == WHITE) {
                    paint.setColor(Color.WHITE);
                    g.drawBitmap(scaledWhiteStone,
                            pixelDelta * j + pixelDelta/5,
                            pixelDelta * i + pixelDelta/5,
                            new Paint());
                } else if (state.getGameBoard(i, j) == BLACK) {
                    paint.setColor(Color.BLACK);
                    g.drawBitmap(scaledBlackStone,
                            pixelDelta * j + pixelDelta/5,
                            pixelDelta * i + pixelDelta/5,
                            new Paint());
                }
            }
        }
    }

    public float pixelRatio(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        int xNeed = boardLength;
        int yNeed = boardLength;
        return Math.min(w / xNeed, h / yNeed);
    }
}