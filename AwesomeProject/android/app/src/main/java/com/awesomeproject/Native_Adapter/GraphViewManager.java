package com.awesomeproject.Native_Adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.ReactImageView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by Alex on 07.01.2018.
 */

public class GraphViewManager extends SimpleViewManager<GraphView>
{
    public static final String REACT_CLASS = "RCTGraphView";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public GraphView createViewInstance(ThemedReactContext context ) {
        GraphView view = new GraphView( context );
        return view;
    }

    @ReactProp( name = "dataSource" )
    public void setdataSource( GraphView view, @Nullable ReadableArray sources )
    {
        if( sources == null )
        {
            return;
        }
        try
        {
            DataPoint[] dataPoints = new DataPoint[ sources.size() ];
            int i;
            double maxX = 5;
            double maxY = 5;
            double minX = 0;
            double minY = 0;
            double X, Y;
            for( i = 0; i < sources.size(); i++ )
            {
                X = sources.getArray( i ).getDouble( 0 );
                Y = sources.getArray( i ).getDouble( 1 );
                dataPoints[ i ] = new DataPoint( X, Y );
                maxX = Math.max( maxX, X );
                maxY = Math.max( maxY, Y );
                minX = Math.min( minX, X );
                minY = Math.min( minY, Y );
            }

            BarGraphSeries< DataPoint > series = new BarGraphSeries<>( dataPoints );
            view.removeAllSeries();
            view.addSeries( series );

            view.getViewport().setYAxisBoundsManual( true );
            view.getViewport().setMinY( minY - Math.abs( maxY - minY ) * 0.1f );
            view.getViewport().setMaxY( maxY + Math.abs( maxY - minY ) * 0.1f );

            view.getViewport().setXAxisBoundsManual( true );
            view.getViewport().setMinX( minX - Math.abs( maxX - minX ) * 0.1f );
            view.getViewport().setMaxX( maxX + Math.abs( maxX - minX ) * 0.1f );

            series.setSpacing( 20 );

            series.setDrawValuesOnTop( true );
            series.setValuesOnTopColor( Color.BLACK );
        }
        catch( Exception e )
        {
            Log.e( "GraphView", e.getMessage() );
        }
    }

}