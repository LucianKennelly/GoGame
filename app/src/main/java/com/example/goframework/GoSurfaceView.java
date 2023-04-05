package com.example.goframework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

import com.example.GameFramework.utilities.FlashSurfaceView;

public class GoSurfaceView extends SurfaceView {
    protected GoGameState state;
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
    }
    public void init() {
        setBackgroundColor(Color.parseColor("#E6D2B4"));
        invalidate();
    }
    Point translateToIndex(Point pos) {
        Log.d("tag","pixelDelta:"+pixelDelta);
        Log.d("tag","x:"+pos.x);
        Log.d("tag","y:"+pos.y);
        //if (surfaceView != null) {
        //int myX8 = (int) (8 / ratio * (pos.x - ratio / 2)) + 8;
        //
        //int myY8 = (int) (8 / ratio * (pos.y - ratio / 2)) + 8;
        int x = -1;
        int y = -1;
        for (float i = 0; i < state.getGameBoard().length; i++) {
            if (pos.x >= (pixelDelta*(i+1))-pixelDelta/2F && pos.x <= (pixelDelta*(i+1)) +pixelDelta/2F) {
                y = (int)i;
            }
        }
        for (float j = 0; j < state.getGameBoard().length; j++) {
            if (pos.y >= (pixelDelta*(j+1))-pixelDelta/2F && pos.y <= (pixelDelta*(j+1))+pixelDelta/2F) {
                x = (int)j;
            }
        }
        //if ((myX8 + 7) % 8 >= 6 || (myY8 + 7) % 8 >= 6) {
        //   return null;
        //} else {
        if (x == -1 || y == -1) {
            return null;
        }
        else {
            return new Point(x, y);
        }
        //}
        //else {
        //    return null;
        //}
    }
    public void drawGrid(Canvas g, float pixelDelta) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        float boardLength = 9;
        for (float i = 0; i < state.getGameBoard().length; i++) {
            g.drawLine(pixelDelta, pixelDelta * (i + 1),
                    pixelDelta * boardLength, pixelDelta * (i + 1),paint);
        }
        paint.setColor(Color.BLACK);
        for (float j = 0; j < state.getGameBoard().length; j++) {

            g.drawLine(pixelDelta * (j + 1), pixelDelta,
                    pixelDelta * (j + 1), pixelDelta * (boardLength),paint);
        }
    }
    public void drawStones(Canvas g, float pixelDelta) {
        Paint paint = new Paint();
        int[][] board = state.getGameBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                switch (board[i][j]) {
                    case 1:
                        paint.setColor(Color.WHITE);
                        break;
                    case 2:
                        paint.setColor(Color.BLACK);
                        break;
                    //case GoFrame.WHITE_IN_PERIL:
                    //    g.setColor(Color.PINK);
                    //    break;
                    //case GoFrame.BLACK_IN_PERIL:
                    //    g.setColor(Color.RED);
                    //    break;
                    default:
                        continue;
                }

                g.drawArc(pixelDelta / 2 + (pixelDelta * j),
                        pixelDelta / 2 + (pixelDelta * i),
                        3 * pixelDelta / 2 + (pixelDelta * j),
                        3 * pixelDelta / 2 + (pixelDelta * i),
                        0F,
                        360F,
                        false,
                        paint);
            }
        }
    }
    public float pixelRatio(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        int xNeed = 9;
        int yNeed = 9;
        return Math.min(w / xNeed, h / yNeed);
    }
    public void onDraw(Canvas g) {
        init();
        pixelDelta = pixelRatio(g);
        drawGrid(g,pixelDelta);
        drawStones(g,pixelDelta);
    }
}
