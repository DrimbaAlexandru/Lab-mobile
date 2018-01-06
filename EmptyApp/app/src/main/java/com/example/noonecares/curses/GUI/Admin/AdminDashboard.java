package com.example.noonecares.curses.GUI.Admin;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.noonecares.curses.Domain.Cursa;
import com.example.noonecares.curses.Domain.Motociclist;
import com.example.noonecares.curses.R;
import com.example.noonecares.curses.Repository.Local.AppDB;
import com.example.noonecares.curses.Service.CurseService;
import com.example.noonecares.curses.Service.DBCurseService;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 09.12.2017.
 */

public class AdminDashboard extends Activity {
    private CurseService srv;
    private List< View > tabs = new ArrayList<>();
    GraphView graph;
    ArrayAdapter< String > curse_adapter;

    ArrayList< String > curse_list = new ArrayList<>();
    ArrayList< Integer > curse_ids = new ArrayList<>();

    private final int anim_time = 300;

    private void setVisibleTab( int which )
    {
        try
        {
            View to_show = null;
            View to_hide = null;

            for( int i = 0; i < tabs.size(); i++ )
            {
                final View current_tab = tabs.get( i );

                if( current_tab.getVisibility() == View.VISIBLE )
                {
                    if( i != which )
                    {
                        to_hide = current_tab;
                    }
                }
                else
                {
                    if( i == which )
                    {
                        to_show = current_tab;
                    }
                    ( ( LinearLayout )findViewById( R.id.dashboard_views ) ).removeView( current_tab );
                }
            }

            if( to_hide != null )
            {
                final View f_to_hide = to_hide;
                final View f_to_show = to_show;

                //to_hide.setAlpha( 1 );
                AlphaAnimation anim = new AlphaAnimation( 1, 0 );
                anim.setDuration( anim_time );
                anim.setFillAfter( true );
                anim.setAnimationListener( new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart( Animation animation )
                    {

                    }

                    @Override
                    public void onAnimationEnd( Animation animation )
                    {
                        f_to_hide.setVisibility( View.GONE );
                        ( ( LinearLayout )findViewById( R.id.dashboard_views ) ).removeView( f_to_hide );
                        if( f_to_show != null )
                        {
                            try
                            {
                                ( ( LinearLayout )findViewById( R.id.dashboard_views ) ).addView( f_to_show );
                            }
                            catch( Exception e )
                            {
                                Log.e( "", e.getMessage() );
                            }
                            f_to_show.setAlpha( 0 );
                            f_to_show.setVisibility( View.VISIBLE );
                            f_to_show.animate().alpha( 1 ).setDuration( anim_time ).setListener( null );
                        }
                    }

                    @Override
                    public void onAnimationRepeat( Animation animation )
                    {

                    }
                } );
                to_hide.startAnimation( anim );
            }
            else
            {
                if( to_show != null )
                {
                    try
                    {
                        ( ( LinearLayout )findViewById( R.id.dashboard_views ) ).addView( to_show );
                    }
                    catch( Exception e )
                    {
                        Log.e( "", e.getMessage() );
                    }
                    to_show.setAlpha( 0 );
                    to_show.setVisibility( View.VISIBLE );
                    to_show.animate().alpha( 1 ).setDuration( anim_time ).setListener( null );
                }
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.admin_dashboard );

        final ListView curse = ( ListView )findViewById( R.id.list_curse );

        srv = new CurseService( getApplicationContext() );

        tabs = new ArrayList<>();
        tabs.add( findViewById( R.id.list_curse ) );
        tabs.add( findViewById( R.id.statistics_graph ) );

        graph = ( GraphView )findViewById( R.id.statistics_graph );

        curse_adapter = new ArrayAdapter< String >( AdminDashboard.this, android.R.layout.simple_list_item_1, curse_list );

        curse.setAdapter( curse_adapter );

        curse.setOnItemClickListener( ( AdapterView< ? > adapterView, View view, int i, long l ) ->
                                        {
                                            try
                                            {
                                                Intent intent = new Intent( this, EditCurseActivity.class );
                                                intent.putExtra( "cursaId", curse_ids.get( i ) );
                                                intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME );
                                                startActivity( intent );
                                            }
                                            catch( Exception e )
                                            {
                                                Log.e( "", e.getMessage() );
                                                e.printStackTrace();
                                            }
                                        } );

        ( ( Button )findViewById( R.id.btn_statistics ) ).setOnClickListener( e ->
                                                                                  {
                                                                                      setVisibleTab( 1 );
                                                                                  } );
        ( ( Button )findViewById( R.id.btn_curse ) ).setOnClickListener( e ->
                                                                                  {
                                                                                      setVisibleTab( 0 );
                                                                                  } );

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        populate();
    }

    private void populate()
    {
        curse_list.clear();
        curse_ids.clear();

        curse_list.add( "Create new" );
        curse_ids.add( -1 );
        for( Cursa c : srv.getCurse() )
        {
            curse_list.add( c.getId() + ". " + c.getNume() );
            curse_ids.add( c.getId() );
        }
        curse_adapter.notifyDataSetChanged();

        HashMap< Integer, Integer > capacities = new HashMap<>();
        for( Motociclist m : srv.getMotociclisti() )
        {
            if( capacities.get( m.getCapacitate_motor() ) == null )
            {
                capacities.put( m.getCapacitate_motor(), 1 );
            }
            else
            {
                capacities.put( m.getCapacitate_motor(), capacities.get( m.getCapacitate_motor() ) + 1 );
            }
        }
        DataPoint[] dps = new DataPoint[capacities.keySet().size()];
        int i=0;
        for( Map.Entry< Integer, Integer > e : capacities.entrySet() )
        {
            dps[ i ] = new DataPoint( e.getKey(), e.getValue() );
            i++;
        }

        BarGraphSeries< DataPoint > series = new BarGraphSeries< DataPoint >( dps );
        graph.removeAllSeries();
        graph.addSeries( series );

        graph.getViewport().setYAxisBoundsManual( true );
        graph.getViewport().setMinY( 0 );

        graph.getViewport().setXAxisBoundsManual( true );
        graph.getViewport().setMinX( 0 );

        series.setSpacing( 30 );

        // draw values on top
        series.setDrawValuesOnTop( true );
        series.setValuesOnTopColor( Color.RED );
        //series.setValuesOnTopSize(50);

    }

}
