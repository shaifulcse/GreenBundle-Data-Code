package anupam.acrylic;

import java.util.ArrayList;

public class TouchRecords {
    float startX;
    float startY;
    ArrayList<Float> xAxis;
    ArrayList<Float> yAxis;
    EasyPaint.MyView.LinePath touchLinePath=null;
    boolean isCancalled=false;
    boolean sameTouch=false;

    public TouchRecords() {
        xAxis=new ArrayList<Float>();
        yAxis=new ArrayList<Float>();
    }

}

