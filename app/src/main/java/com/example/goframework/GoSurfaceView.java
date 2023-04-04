package com.example.goframework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.example.GameFramework.utilities.FlashSurfaceView;

public class GoSurfaceView extends FlashSurfaceView {
    protected GoGameState state;
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
    float pixelRatio(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        int xNeed = 9;
        int yNeed = 9;
        return Math.min(w / xNeed, h / yNeed);
    }
    public void onDraw(Canvas g) {
        init();
        float pixelDelta = pixelRatio(g);
        drawGrid(g,pixelDelta);
    }
}
