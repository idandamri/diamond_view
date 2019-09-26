package com.example.diamondview;


import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends Activity {

    private DiamondView diamondView;
    private int screenWidth;
    private int screenHeight;
    EditText et;
    EditText etPercent;
    Button btn;
    Button btnPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        diamondView = findViewById(R.id.pb);
        etPercent = findViewById(R.id.et_percent);
        et = findViewById(R.id.et);
        btnPercent = findViewById(R.id.btn_percent);
        btn = findViewById(R.id.btn);
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        etPercent.setInputType(InputType.TYPE_CLASS_NUMBER);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et.getText() != null && !et.getText().toString().isEmpty()) {
                    diamondView.setNumOfDivs(Integer.valueOf(et.getText().toString()));
                    diamondView.invalidate();
                    et.setText("");
                }
            }
        });

        final SeekBar skBrush = (SeekBar) findViewById(R.id.seek_gradient);
        skBrush.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                diamondView.setGradientBrushLength(progress);
                diamondView.invalidate();
            }
        });

        final SeekBar sk = (SeekBar) findViewById(R.id.seek);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                diamondView.setPercentFill((float) progress/100);
                etPercent.setText(String.valueOf(progress));
                diamondView.invalidate();

            }
        });

        btnPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPercent.getText() != null && !etPercent.getText().toString().isEmpty() &&
                        Integer.valueOf(etPercent.getText().toString()) < 101 &&
                        Integer.valueOf(etPercent.getText().toString()) >= 0) {
                    diamondView.setPercentFill(Float.valueOf(etPercent.getText().toString())/100);
                    diamondView.invalidate();
                    etPercent.setText("");
                }
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        diamondView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            diamondView.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        } else {
                            diamondView.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                    }
                });
        diamondView.setWidth(screenWidth - dpToPx(80));
        diamondView.setDiagonal(dpToPx(6));
        diamondView.setEnd(screenWidth - dpToPx(40));
        diamondView.setStart(0);
        diamondView.setGradientBrushLength(400);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
