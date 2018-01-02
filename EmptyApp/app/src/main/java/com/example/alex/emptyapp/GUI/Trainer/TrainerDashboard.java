package com.example.alex.emptyapp.GUI.Trainer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.alex.emptyapp.Domain.Feedback;
import com.example.alex.emptyapp.Domain.User;
import com.example.alex.emptyapp.GUI.EditUserFragment;
import com.example.alex.emptyapp.R;
import com.example.alex.emptyapp.Repository.Local.AppDB;
import com.example.alex.emptyapp.Service.DBGymService;
import com.example.alex.emptyapp.Service.GymService;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 09.12.2017.
 */

public class TrainerDashboard extends Activity {
    private GymService srv;
    private User user;
    private LinearLayout edit_user_container;
    private List< View > tabs = new ArrayList<>();

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
                    ( ( LinearLayout )findViewById( R.id.trainer_dashboard_views ) ).removeView( current_tab );
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
                        ( ( LinearLayout )findViewById( R.id.trainer_dashboard_views ) ).removeView( f_to_hide );
                        if( f_to_show != null )
                        {
                            try
                            {
                                ( ( LinearLayout )findViewById( R.id.trainer_dashboard_views ) ).addView( f_to_show );
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
                        ( ( LinearLayout )findViewById( R.id.trainer_dashboard_views ) ).addView( to_show );
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
        setContentView( R.layout.trainer_dashboard );

        srv = new GymService( getApplicationContext() );
        user = srv.getLoggedUser();

        tabs = new ArrayList<>();
        tabs.add( findViewById( R.id.rating_chart ) );
        tabs.add( findViewById( R.id.edit_user_trainer ) );

        edit_user_container = findViewById( R.id.edit_user_trainer );
        edit_user_container.removeAllViews();

        Bundle bundle = new Bundle();

        bundle.putSerializable( "loggedUser", user );
        // set Fragmentclass Arguments
        EditUserFragment fragment = new EditUserFragment();
        fragment.setArguments( bundle );

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add( R.id.edit_user_trainer, fragment );
        transaction.commit();

        ( ( Button )findViewById( R.id.btn_see_ratings ) ).setOnClickListener( e ->
                                                                               {
                                                                                   setVisibleTab( 0 );
                                                                               } );
        ( ( Button )findViewById( R.id.btn_edit_user_trainer ) ).setOnClickListener( e ->
                                                                                  {
                                                                                      setVisibleTab( 1 );
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
        int rating[] = new int[ 5 ];

        for( Feedback f : srv.getFeedback( user.getId() ) )
        {
            try
            {
                rating[ f.getRating() - 1 ]++;
            }
            catch( ArrayIndexOutOfBoundsException e )
            {
                Log.wtf( "Invalid rating value", e.getMessage() );
            }
        }

        GraphView graph = ( GraphView )findViewById( R.id.rating_chart );
        BarGraphSeries< DataPoint > series = new BarGraphSeries<>( new DataPoint[] {
                new DataPoint( 1, rating[ 0 ] ),
                new DataPoint( 2, rating[ 1 ] ),
                new DataPoint( 3, rating[ 2 ] ),
                new DataPoint( 4, rating[ 3 ] ),
                new DataPoint( 5, rating[ 4 ] )
        } );
        graph.addSeries( series );

        graph.getViewport().setYAxisBoundsManual( true );
        graph.getViewport().setMinY( 0 );

        graph.getViewport().setXAxisBoundsManual( true );
        graph.getViewport().setMinX( 0 );
        graph.getViewport().setMaxX( 6 );

        // styling
        series.setValueDependentColor( new ValueDependentColor< DataPoint >()
        {
            @Override
            public int get( DataPoint data )
            {
                return Color.rgb( ( int )( Math.min( 1.0, ( -( data.getX() - 3 ) / 2 + 1 ) ) * 230 ),
                                  ( int )( Math.min( 1.0, ( ( data.getX() - 3 ) / 2 + 1 ) ) * 230 ),
                                  0 );
            }
        } );

        series.setSpacing( 50 );

        // draw values on top
        series.setDrawValuesOnTop( true );
        series.setValuesOnTopColor( Color.RED );
        //series.setValuesOnTopSize(50);

    }
    private void show_error_dialog( String msg, String title )
    {
        AlertDialog alertDialog = new AlertDialog.Builder( TrainerDashboard.this ).create();
        alertDialog.setTitle( title );
        alertDialog.setMessage( msg );
        alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener()
        {
            public void onClick( DialogInterface dialog, int which )
            {
                dialog.dismiss();
            }
        } );
        alertDialog.show();
    }

}
