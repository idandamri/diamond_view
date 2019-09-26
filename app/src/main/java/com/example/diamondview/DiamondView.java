package com.example.diamondview;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;

public class DiamondView extends View {

    private final String FIFA_GRADIENT_COLOR_START = "#412E5B";
    private final String FIFA_GRADIENT_COLOR_END = "#FC5461";

    private Paint mBoarderPaint;
    Paint mInnerPaint;
    private int width = -1;
    private int height = -1;
    private int start = -1;
    private int end = -1;
    private int color1 = -1;
    private int color2 = -1;
    private int diagonal = -1;
    private int numOfDivs = 1;
    private int gradientBrushLength = 0;
    private float percentFill = 1;

    public DiamondView(Context context) {
        super(context);
        init();
    }

    public DiamondView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public DiamondView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setNumOfDivs(int numOfDivs) {
        this.numOfDivs = numOfDivs;
    }

    public void setDiagonal(int diagonal) {
        this.diagonal = diagonal;
    }

    public void setColor1(int color1) {
        this.color1 = color1;
    }

    public void setColor2(int color2) {
        this.color2 = color2;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setPercentFill(float percentFill) {
        this.percentFill = percentFill;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setGradientBrushLength(int gradientBrushLength) {
        try {
            this.gradientBrushLength = (int) (((float)(gradientBrushLength)/100) * width);
            mInnerPaint.setShader(new LinearGradient(0, 0, this.gradientBrushLength, 0,
                    color1, color2, Shader.TileMode.CLAMP));
            mBoarderPaint.setShader(new LinearGradient(0, 0, this.gradientBrushLength, 0,
                    color1, color2, Shader.TileMode.CLAMP));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void init() {
        try {

            setDefaults();

            initStrokeBrush();

            initFillBrush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFillBrush() {
        try {
            mInnerPaint = new Paint();
            mInnerPaint.setAntiAlias(true);
            mInnerPaint.setShader(new LinearGradient(0, 0, gradientBrushLength, 0,
                    color1, color2, Shader.TileMode.CLAMP));
            mInnerPaint.setStyle(Paint.Style.FILL);
            mInnerPaint.setStrokeJoin(Paint.Join.ROUND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initStrokeBrush() {
        try {
            mBoarderPaint = new Paint();
            mBoarderPaint.setAntiAlias(true);
            mBoarderPaint.setShader(new LinearGradient(0, 0, gradientBrushLength, 0,
                    color1, color2, Shader.TileMode.CLAMP));
            mBoarderPaint.setStyle(Paint.Style.STROKE);
            mBoarderPaint.setStrokeWidth(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDefaults() {
        try {
            if (color1 == -1 && color2 == -1) {
                setColor1(Color.parseColor(FIFA_GRADIENT_COLOR_START));
                setColor2(Color.parseColor(FIFA_GRADIENT_COLOR_END));
            }
            if (start == -1) {
                start = 0;
            }
            if (end == -1) {
                end = width;
            }
            if (diagonal == -1) {
                diagonal = 96;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        try {
            Path path = new Path();
            int smallDiamondViewWidth = width / numOfDivs;
            int end = smallDiamondViewWidth;
            int space = 2;
            boolean shoulFillDiamond = true;
            boolean specialCaseDiamond = true;
            for (int i = 1; i <= numOfDivs; i++) {
                if(percentFill >= (float)i/numOfDivs){
                    shoulFillDiamond = true;
                }
                else {
                    if(shoulFillDiamond && percentFill != (float)(i-1)/numOfDivs){
                        specialCaseDiamond = true;
                    }
                    else {
                        specialCaseDiamond = false;
                    }
                    shoulFillDiamond = false;
                }

                path.moveTo(end, 0);
                if (i == 1) {
                    path.lineTo(end - smallDiamondViewWidth + diagonal + space, 0);
                    path.lineTo(end - smallDiamondViewWidth + space, getHeight());
                    path.lineTo(end - diagonal, getHeight());
                    path.lineTo(end, 0);
                } else {
                    path.lineTo(end - smallDiamondViewWidth + diagonal - space, 0);
                    path.lineTo(end - smallDiamondViewWidth - space, getHeight());
                    path.lineTo(end - diagonal, getHeight());
                    path.lineTo(end, 0);
                }
                if (shoulFillDiamond) {
                    canvas.drawPath(path, mInnerPaint);
                }
                if(!shoulFillDiamond && specialCaseDiamond){
                    float numberOfDiffrenceToAdd = ((percentFill - (float)(i-1)/numOfDivs)/((float)1/numOfDivs));
                    float xsToMove = smallDiamondViewWidth * numberOfDiffrenceToAdd;
                    Path path2 = new Path();
                    path2.moveTo(end - smallDiamondViewWidth + diagonal - space, 0);
                    path2.lineTo(end - smallDiamondViewWidth - space, getHeight());
                    if ((isLeftEdge(xsToMove, 16) ||
                            isRightEdge((i-1)*smallDiamondViewWidth + xsToMove, end - diagonal))
                            && isEdges(end - smallDiamondViewWidth + xsToMove,
                            end - smallDiamondViewWidth + diagonal - space)) {
                        if (!isLeftEdge(xsToMove, 16)) {
                            path2.lineTo(end - smallDiamondViewWidth + xsToMove - diagonal, getHeight());
                            path2.lineTo(end - smallDiamondViewWidth + xsToMove - diagonal, 0);
                        } else {
                            path2.lineTo(end - smallDiamondViewWidth + xsToMove + diagonal + space, getHeight());
                            path2.lineTo(end - smallDiamondViewWidth + xsToMove + diagonal + space, 0);
                        }
                    } else {
                        path2.lineTo(end - smallDiamondViewWidth + xsToMove, getHeight());
                        path2.lineTo(end - smallDiamondViewWidth + xsToMove, 0);
                    }
                    path2.lineTo(end - smallDiamondViewWidth + diagonal - space, 0);
                    canvas.drawPath(path2, mInnerPaint);
                }
                canvas.drawPath(path, mBoarderPaint);
                end = (i + 1) * smallDiamondViewWidth;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isRightEdge(float xsToMove, int edge) {
        return xsToMove > edge;
    }

    private boolean isLeftEdge(float xsToMove, int edge) {
        return xsToMove < edge;
    }

    private boolean isEdges(float point, float endPlace) {
        try {
            if(point < endPlace){
                return true;
            }
            else if(point > endPlace){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}