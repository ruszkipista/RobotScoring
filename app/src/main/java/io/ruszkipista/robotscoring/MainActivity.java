package io.ruszkipista.robotscoring;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView mTextView_color_points;
    int mPointsColorFix = 0;

    int[] mPointsDistance = {0,0,0};
    TextView[] mTextView_distance_points = new TextView[mPointsDistance.length];
    EditText[] mEditTextDistance = new EditText[mPointsDistance.length];

    int mPointsWBballMission = 0;
    TextView mTextView_wb_points;

    TextView mTextView_total_points;
    int mPointsTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView_color_points       = findViewById(R.id.TextView_color_points);
        mTextView_distance_points[0] = findViewById(R.id.TextView_distance_0_points);
        mTextView_distance_points[1] = findViewById(R.id.TextView_distance_1_points);
        mTextView_distance_points[2] = findViewById(R.id.TextView_distance_2_points);
        mEditTextDistance[0]         = findViewById(R.id.EditText_distance_0);
        mEditTextDistance[1]         = findViewById(R.id.EditText_distance_1);
        mEditTextDistance[2]         = findViewById(R.id.EditText_distance_2);
        mTextView_wb_points          = findViewById(R.id.TextView_wb_points);
        mTextView_total_points       = findViewById(R.id.TextView_total_points);
        resetApp(null);

    }

    public void setColorFix0(View view){ calculateColorFixPoints(0); }
    public void setColorFix1(View view){ calculateColorFixPoints(1); }
    public void setColorFix2(View view){ calculateColorFixPoints(2); }
    public void setColorFix3(View view){ calculateColorFixPoints(3); }
    public void setDistance(View view){
        updateView();
    }
    public void setWBfailure(View view){ calculateWBballMissionPoints(0); }
    public void setWBsuccess(View view){ calculateWBballMissionPoints(1); }


    public void resetApp(View view){
        mPointsColorFix      = 0;
        for (int i = 0; i< mPointsDistance.length; i++) {
            mPointsDistance[i] = 0;
            mEditTextDistance[i].setText("");
        }
        mPointsWBballMission = 0;
        updateView();
    }

    private void calculateColorFixPoints(int numberOfColorFixes) {
        final int[] PointRangeColorFix = {150,75,25,0};
        if (numberOfColorFixes>=0 && numberOfColorFixes<PointRangeColorFix.length) {
            mPointsColorFix = PointRangeColorFix[numberOfColorFixes];
        } else {
            mPointsColorFix = 0;
        }
        updateView();
    }

    private void calculateDistancePoints(int line, String sDistance) {
        final double[] feetDistance = {5.0, 10.0, 20.0, 30.0, 45.0, Double.MAX_VALUE};
        final int[][] pointRangeDistance = {{110, 100, 80, 50, 10, 0},
                {220, 200, 160, 100, 20, 0},
                {110, 100, 80, 50, 10, 0}};
        int category;
        double distance;
        try {
            distance = Double.parseDouble(sDistance);
        } catch (java.lang.NumberFormatException e) {
            return;
        }

        if (line >= 0 && line < mPointsDistance.length) {
            for (category = 0; category <  feetDistance.length; category++) {
                if (distance <= feetDistance[category]) {
                    mPointsDistance[line] = pointRangeDistance[line][category];
                    break;
                }
            }
        }
    }

    private void calculateWBballMissionPoints(int button) {
        final int[] pointRangeWBballMission = {0,60};
        if (button>=0 && button<pointRangeWBballMission.length) {
            mPointsWBballMission = pointRangeWBballMission[button];
        } else {
            mPointsWBballMission = 0;
        }
        updateView();
    }

    private void updateView(){
        mTextView_color_points.setText(getString(R.string.color_points, mPointsColorFix));
        calculateDistancePoints(0,mEditTextDistance[0].getText().toString());
        calculateDistancePoints(1,mEditTextDistance[1].getText().toString());
        calculateDistancePoints(2,mEditTextDistance[2].getText().toString());
        mTextView_distance_points[0].setText(getString(R.string.distance_0_points, mPointsDistance[0]));
        mTextView_distance_points[1].setText(getString(R.string.distance_1_points, mPointsDistance[1]));
        mTextView_distance_points[2].setText(getString(R.string.distance_2_points, mPointsDistance[2]));
        mTextView_wb_points.setText(getString(R.string.wb_points, mPointsWBballMission));

        mPointsTotal = mPointsColorFix + mPointsWBballMission;
        for (int i = 0; i< mPointsDistance.length; i++) {
            mPointsTotal += mPointsDistance[i];
        }
        mTextView_total_points.setText(getString(R.string.total_points, mPointsTotal));
    }
}
