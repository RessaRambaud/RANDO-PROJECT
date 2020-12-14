package com.RandoDam.rando.ActivityClass.CritereDeRecherche;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.RandoDam.rando.R;
// -------------------------------------------------------------------------------------------------

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZoomInZoomOut#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZoomInZoomOut extends Fragment implements OnTouchListener {

    Context myCtx;
    // ---------------------------------------------------------------------------------------------
    private float latitude;
    private float longitude;

    public float getLatitude() {
        return latitude;
    }
    public float getLongitude() {
        return longitude;
    }

    // --------------------------------------------------------------------------
    //          X Y                             X Y
    // --------------------------------------------------------------------------
    // Hatherleight -4,0737 : 50,801    Giessen 8,65 : 50,588
    // Palencia  -4.55 : 42,018         Mediterrannée 8,042 : 42,490
    // ---------------------------------------------------------------------------
    // gauche haut = 0,0                droite haut = 900,0         Delta X = 900
    // bas gauche = 0,900               bas droit = 900,900         Delta Y = 900
    // ----------------------------------------------------------------------------
    // gauche haut = 50:-4              droite haut = 50:8          Delta X = 12
    // bas gauche = 42:-4               bas droit = 42:8            Delta Y = 8
    //  ---------------------------------------------------------------------------
    // longitude coef X = 12 / 900      decalage X = -4
    // Latitude coef Y = -8 / 900       decalage Y = 50
    // Formule longitude = X * (12/900) - 4
    // Formule latidute = Y * (-8/900) + 50
    // ----------------------------------------------------------------------------

    private void calculeLonLat() {
        float coefX = 12f/900f;
        float coefY = -8f/900f;
        this.longitude = (start.x * (coefX)) - 4.0f ;
        this.latitude = (start.y * (coefY)) + 50.0f;

        // System.out.println( "--- x --- x --- x --- x ---");
        // System.out.println("start x = " + start.x);
        // System.out.println("start y = " + start.y);
        // System.out.println("longitude = " + this.longitude);
        // System.out.println("latitude = " + this.latitude);
    }

    // ---------------------------------------------------------------------------------------------
    private static final String TAG = "Touch";
    //@SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f,MAX_ZOOM = 1f;
    //private static final float MIN_ZOOM = 2,MAX_ZOOM = 10;
    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();  //PointF holds two float coordinates
    PointF mid = new PointF();
    float oldDist = 1f;
    // ---------------------------------------------------------------------------------------------
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    // ---------------------------------------------------------------------------------------------
    public ZoomInZoomOut() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZoomInZoomOut.
     */
    // TODO: Rename and change types and number of parameters
    public static ZoomInZoomOut newInstance(String param1, String param2) {
        ZoomInZoomOut fragment = new ZoomInZoomOut();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ContentView(R.layout.activity_main);
        //ImageView view = (ImageView) view.findViewById();
        //view.setOnTouchListener(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_zoom_in_zoom_out, container, false);
        ImageView view = (ImageView) v.findViewById(R.id.france);
        view.setOnTouchListener(this);
        return v;
    }

    // ---------------------------------------------------------------------------------------------

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        ImageView view = (ImageView) v;
        //view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        ((CriteresDeRecherche)getActivity()).boutonOk();

        // dumpEvent(event);
        // Handle touch events here...

        calculeLonLat();

        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                //Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;
                //Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(event);
                //Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG)
                {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                    // create the transformation in the matrix  of points
                    //matrix.setRotate(90);
                }
                else if (mode == ZOOM)
                {
                    // pinch zooming
                    float newDist = spacing(event);
                    //Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f)
                    {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y); // <-----------------------------------------
                    }
                }
                break;
        }

        view.setImageMatrix(matrix); // display the transformation on screen
        return true; // indicate event was handled
    }

    /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /** Show an event in the LogCat view, for debugging */
    private void dumpEvent(MotionEvent event)
    {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
        {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }
}