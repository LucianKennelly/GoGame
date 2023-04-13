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
import com.example.goframework.GoGameState;

public class GoSurfaceView extends FlashSurfaceView {

    protected GoGameState state;
    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_IN_PERILL = -4;
    private int BLACK_IN_PERILL = -5;
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
        Log.d("tag","state set: " + state.toString());
        this.state = state;
    }

    public void onDraw(Canvas g) {
        init();
        pixelDelta = pixelRatio(g);
        drawGrid(g,pixelDelta);
        if (state == null) {
            return;
        }
        drawStones(g,pixelDelta);
    }

    public void init() {
        setBackgroundColor(Color.parseColor("#E6D2B4"));
        invalidate();
    }
    Point translateToIndex(Point pos) {
        Log.d("tag","pixelDelta:"+pixelDelta);
        Log.d("tag","x:"+pos.x);
        Log.d("tag","y:"+pos.y);
        int x = -1;
        int y = -1;
        for (float i = 0; i < 9; i++) {
            if (pos.x >= (pixelDelta*(i+1))-pixelDelta/2F && pos.x <= (pixelDelta*(i+1)) +pixelDelta/2F) {
                y = (int)i;
            }
        }
        for (float j = 0; j < 9; j++) {
            if (pos.y >= (pixelDelta*(j+1))-pixelDelta/2F && pos.y <= (pixelDelta*(j+1))+pixelDelta/2F) {
                x = (int)j;
            }
        }
        if (x == -1 || y == -1) {
            return null;
        }
        else {
            state.setX(x);
            state.setY(y);
            return new Point(x, y);
        }
    }
    public void drawGrid(Canvas g, float pixelDelta) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        float boardLength = 9;
        for (float i = 0; i < 9; i++) {
            g.drawLine(pixelDelta, pixelDelta * (i + 1),
                    pixelDelta * boardLength, pixelDelta * (i + 1),paint);
        }
        paint.setColor(Color.BLACK);
        for (float j = 0; j < 9; j++) {

            g.drawLine(pixelDelta * (j + 1), pixelDelta,
                    pixelDelta * (j + 1), pixelDelta * (boardLength),paint);
        }
    }
    public void drawStones(Canvas g, float pixelDelta) {
        Paint paint = new Paint();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(state.getGameBoard(i, j) == WHITE) {
                    paint.setColor(Color.WHITE);
                    g.drawArc(pixelDelta / 2 + (pixelDelta * j),
                            pixelDelta / 2 + (pixelDelta * i),
                            3 * pixelDelta / 2 + (pixelDelta * j),
                            3 * pixelDelta / 2 + (pixelDelta * i),
                            0F,
                            360F,
                            false,
                            paint);
                }
                else if(state.getGameBoard(i, j) == BLACK) {
                    paint.setColor(Color.BLACK);
                    g.drawArc(pixelDelta / 2 + (pixelDelta * j), pixelDelta / 2 + (pixelDelta * i),
                            3 * pixelDelta / 2 + (pixelDelta * j),
                            3 * pixelDelta / 2 + (pixelDelta * i),
                            0F,
                            360F,
                            false,
                            paint);
                }
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
}
