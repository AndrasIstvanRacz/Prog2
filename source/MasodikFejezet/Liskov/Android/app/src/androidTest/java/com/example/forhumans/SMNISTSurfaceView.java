/*
 * SMNISTSurfaceView.java
 *
 * SMNIST for Humans
 *
 * Copyright (C) 2019, Dr. Bátfai Norbert
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Ez a program szabad szoftver; terjeszthető illetve módosítható a
 * Free Software Foundation által kiadott GNU General Public License
 * dokumentumában leírtak; akár a licenc 3-as, akár (tetszőleges) későbbi
 * változata szerint.
 *
 * Ez a program abban a reményben kerül közreadásra, hogy hasznos lesz,
 * de minden egyéb GARANCIA NÉLKÜL, az ELADHATÓSÁGRA vagy VALAMELY CÉLRA
 * VALÓ ALKALMAZHATÓSÁGRA való származtatott garanciát is beleértve.
 * További részleteket a GNU General Public License tartalmaz.
 *
 * A felhasználónak a programmal együtt meg kell kapnia a GNU General
 * Public License egy példányát; ha mégsem kapta meg, akkor
 * tekintse meg a <http://www.gnu.org/licenses/> oldalon.
 *
 * Version history:
 *
 * https://bhaxor.blog.hu/2019/03/10/the_semantic_mnist
 */
package com.example.forhumans;

/**
 * @author nbatfai
 */
public class SMNISTSurfaceView extends android.view.SurfaceView implements Runnable {

    private float startx = 150;
    private float starty = -700;
    private float width = 1000;
    private float height = 1000;
    protected float fromx;
    protected float fromy;

    private android.view.SurfaceHolder surfaceHolder;
    private android.view.ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;

    private boolean running = true;
    private static int decisionTimeLimit = 1000;

    private int semValue = 0;
    private static final int SEM_VALUE_LIMIT_START = 3;
    private static int semValueLimit = SEM_VALUE_LIMIT_START;
    private static final int NUM_OF_DIGITS = 10;
    private float[] semValueDots = new float[2 * NUM_OF_DIGITS];

    private float[] digitsCoords = new float[2 * NUM_OF_DIGITS];

    private int successCnt = 0;
    private boolean armed = true;
    private long armedTime = 0;

    int[] bgColor =
            {
                    android.graphics.Color.rgb(11, 180, 250),
                    android.graphics.Color.rgb(11, 250, 180)
            };
    int bgIdx = 0;

    private int decisionTimeDelaySum = 0;
    private int semValueSum = 0;

    private static String msg1 = "How many dots can you see?";
    private static String msg2 = "Touch the appropriate number.";

    android.graphics.Paint dotPaint = new android.graphics.Paint();
    android.graphics.Paint textPaint = new android.graphics.Paint();
    android.graphics.Paint msgPaint = new android.graphics.Paint();
    android.graphics.Paint fillPaint = new android.graphics.Paint();
    android.graphics.Paint borderPaint = new android.graphics.Paint();
    android.graphics.Rect textBounds = new android.graphics.Rect();

    private int failCnt = 0;
    private static int[] millisecs = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static int[] svmeans  =  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    static int[] getMillisecs() {
        return SMNISTSurfaceView.millisecs;
    }

    static void setMillisecs(int[] array) {
        System.arraycopy(array, 0, SMNISTSurfaceView.millisecs, 0, array.length);
        msg();
    }

    static int[] getSvmeans() {
        return SMNISTSurfaceView.svmeans;
    }

    static void setSvmeans(int[] array) {
        System.arraycopy(array, 0, SMNISTSurfaceView.svmeans, 0, array.length);
        msg();
    }

    static void set(int semValueLimit, int decisionTimeLimit) {
        SMNISTSurfaceView.semValueLimit = semValueLimit;
        SMNISTSurfaceView.decisionTimeLimit = decisionTimeLimit;
    }

    static int getSem() {
        return SMNISTSurfaceView.semValueLimit;
    }

    static int getDec() {
        return SMNISTSurfaceView.decisionTimeLimit;
    }

    @Override
    public void run() {
        long now, dnow = 0, rnow = 0;

        running = true;
        while (running) {

            now = System.currentTimeMillis();

            if (now - dnow > decisionTimeLimit) {

                dnow = now;

                semValue = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, semValueLimit);
                float semValueDots[] = drawSemValue(semValue);
                System.arraycopy(semValueDots, 0, this.semValueDots, 0, semValueDots.length);

                armed = true;
                armedTime = now;
                bgIdx = (bgIdx + 1) % 2;
            }

            if (now - rnow > 50) {

                rnow = now;

                repaint();

            }

        } // while

    }

    @Override
    public void onDraw(android.graphics.Canvas canvas) {

        if (surfaceHolder.getSurface().isValid()) {

            canvas.save();
            canvas.scale(scaleFactor, scaleFactor);

            canvas.drawColor(bgColor[bgIdx]);
            // nem jott be, nagyon zavaró
            //canvas.drawColor(android.graphics.Color.rgb(0, 50+bgIdx*cnt*20, 50+((bgIdx+1)%2)*cnt*20));

            drawMeasurement(canvas);

            canvas.restore();
        }
    }

    static StringBuilder sb = new StringBuilder();
    static StringBuilder sb2 = new StringBuilder();

    public static void msg() {
        sb.delete(0, sb.length());
        sb2.delete(0, sb2.length());

        sb.append("ms: (");
        sb.append(decisionTimeLimit);
        sb.append(") ");

        sb2.append("lvl/[...]: (");
        sb2.append(semValueLimit);
        sb2.append(") ");

        float ur = 0;
        for (int i = SEM_VALUE_LIMIT_START; i < NUM_OF_DIGITS; ++i) {

            sb.append(millisecs[i]);
            sb.append(" ");
            if (millisecs[i] != 0) {
                sb2.append(i + 1);
                ur += (((svmeans[i]+1.0)*(i + 1.0)) / millisecs[i]);
                sb2.append("/");
                sb2.append(svmeans[i]);
            }
            else {
                sb2.append(0);
                sb2.append("/");
                sb2.append(0);
            }
            sb2.append(" ");

        }

        sb2.append(" <");
        sb2.append(ur);
        sb2.append(">");

        msg1 = sb.toString();
        msg2 = sb2.toString();

    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {

        scaleGestureDetector.onTouchEvent(event);

        float x = event.getX() / scaleFactor;
        float y = event.getY() / scaleFactor;

        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {

            fromx = x;
            fromy = y;

            for (int i = 0; i < digitsCoords.length / 2; ++i) {

                if (
                        Math.abs(x - (-startx + digitsCoords[2 * i] + 800 / 2)) < 40
                                && Math.abs(y - (-starty + digitsCoords[2 * i + 1] - 800 / 2)) < 40
                                && armed
                        ) {

                    if (semValue == i) {
                        armed = false;

                        long decisionTimeDelay = System.currentTimeMillis() - armedTime;

                        decisionTimeDelaySum += decisionTimeDelay;
                        semValueSum += semValue;

                        if (++successCnt > 10) {
                            if (semValueLimit < NUM_OF_DIGITS) {
                                ++semValueLimit;

                                successCnt = 0;

                                decisionTimeLimit = decisionTimeDelaySum / 10;
                                decisionTimeDelaySum = 0;

                                svmeans[semValueLimit - 1] = semValueSum / 10;
                                semValueSum = 0;

                                millisecs[semValueLimit - 1] = decisionTimeLimit;
                                msg();
                            }
                        }

                    } else {

                        decisionTimeDelaySum = 0;
                        semValueSum = 0;

                        if (successCnt > 5) {
                            if (++failCnt > 1) {
                                decisionTimeLimit += 50;
                                failCnt = 0;
                            }
                        }

                        successCnt = 0;

                    }

                }
            }

        } else if (event.getAction() == android.view.MotionEvent.ACTION_POINTER_DOWN) {

        } else if (event.getAction() == android.view.MotionEvent.ACTION_CANCEL) {

        } else if (event.getAction() == android.view.MotionEvent.ACTION_MOVE) {

            startx += (fromx - x);
            starty += (fromy - y);

            fromx = x;
            fromy = y;

            repaint();

        } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {

            fromx = x;
            fromy = y;

        }

        return true;
    }


    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public SMNISTSurfaceView(android.content.Context context) {
        super(context);

        cinit(context);

    }

    public SMNISTSurfaceView(android.content.Context context,
                             android.util.AttributeSet attrs) {
        super(context, attrs);

        cinit(context);

    }

    public SMNISTSurfaceView(android.content.Context context,
                             android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        cinit(context);

    }

    @Override
    protected void onSizeChanged(int newx, int newy, int x, int y) {

        super.onSizeChanged(newx, newy, x, y);

        width = newx;
        height = newy;

    }

    private void cinit(android.content.Context context) {

        textPaint.setColor(android.graphics.Color.GRAY);
        textPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
        textPaint.setTextSize(50);

        msgPaint.setColor(android.graphics.Color.GRAY);
        msgPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        msgPaint.setAntiAlias(true);
        msgPaint.setTextAlign(android.graphics.Paint.Align.LEFT);
        msgPaint.setTextSize(40);

        dotPaint.setColor(android.graphics.Color.BLACK);
        dotPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        dotPaint.setAntiAlias(true);
        dotPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
        dotPaint.setTextSize(50);

        borderPaint.setStrokeWidth(2);
        borderPaint.setColor(android.graphics.Color.GRAY);
        fillPaint.setStyle(android.graphics.Paint.Style.FILL);
        fillPaint.setColor(android.graphics.Color.YELLOW);

        surfaceHolder = getHolder();

        surfaceHolder.addCallback(new SurfaceEvents(this));

        scaleGestureDetector = new android.view.ScaleGestureDetector(context, new ScaleAdapter(this));

        for (int i = 0; i < digitsCoords.length / 2; ++i) {

            digitsCoords[2 * i] = (float) (150 * Math.cos(-Math.PI / 2.0 + i * (12.0 / 10.0) * (Math.PI / 6.0)));
            digitsCoords[2 * i + 1] = (float) (150 * Math.sin(-Math.PI / 2.0 + i * (12.0 / 10.0) * (Math.PI / 6.0)));

        }

        msg();

    }

    public float[] drawSemValue(int semValue) {

        float[] xy = new float[semValue * 2];

        float sumx = 0;
        float sumy = 0;

        for (int j = 0; j < semValue; ++j) {

            boolean ok = true;

            do {

                xy[2 * j] = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 100);
                xy[2 * j + 1] = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 100);

                ok = true;
                for (int k = 0; k < j; ++k) {

                    if (xy[2 * k] == xy[2 * j] && xy[2 * k + 1] == xy[2 * j + 1]) {

                        ok = false;
                    }

                }

            } while (!ok);

            sumx += xy[2 * j];
            sumy += xy[2 * j + 1];
        }

        float meanx = sumx / semValue;
        float meany = sumy / semValue;

        float xshift = 100 / 2 - meanx;
        float yshift = 100 / 2 - meany;

        for (int j = 0; j < semValue; ++j) {

            xy[2 * j] = xy[2 * j] + xshift - 50;
            xy[2 * j + 1] = xy[2 * j + 1] + yshift - 50;
        }

        return xy;

    }

    public void drawMeasurement(android.graphics.Canvas canvas) {

        float x = -startx + 800 / 2;
        float y = -starty - 800 / 2;

        canvas.drawCircle(x, y,
                120, fillPaint);
        canvas.drawCircle(x, y,
                118, borderPaint);

        for (int i = 0; i < 10; ++i) {

            canvas.drawCircle(x + digitsCoords[2 * i], y + digitsCoords[2 * i + 1],
                    40, borderPaint);

            canvas.drawCircle(x + digitsCoords[2 * i], y + digitsCoords[2 * i + 1],
                    38, fillPaint);

            String s = "" + i;
            textPaint.getTextBounds(s, 0, s.length(), textBounds);
            canvas.drawText(s, x + digitsCoords[2 * i],
                    y + digitsCoords[2 * i + 1] - textBounds.exactCenterY(), textPaint);

        }

        for (int i = 0; i < semValue; ++i) {
            canvas.drawCircle(x + semValueDots[2 * i], y + semValueDots[2 * i + 1],
                    10, dotPaint);
        }

        textPaint.getTextBounds(msg1, 0, msg1.length(), textBounds);

        canvas.drawText(msg1, x-160, y + 380 - 40 - textBounds.height(), msgPaint);
        canvas.drawText(msg2, x-160, y + 380 , msgPaint);
    }

    public void repaint() {

        android.graphics.Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                onDraw(canvas);
            }

        } finally {

            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

        }
    }

    public void stop() {

        running = false;

    }

}
