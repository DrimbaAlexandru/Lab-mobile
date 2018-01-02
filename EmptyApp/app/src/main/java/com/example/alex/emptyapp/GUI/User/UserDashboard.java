package com.example.alex.emptyapp.GUI.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;

import com.example.alex.emptyapp.Domain.Feedback;
import com.example.alex.emptyapp.Domain.GymClass;
import com.example.alex.emptyapp.Domain.Role;
import com.example.alex.emptyapp.Domain.User;
import com.example.alex.emptyapp.GUI.EditUserFragment;
import com.example.alex.emptyapp.R;
import com.example.alex.emptyapp.Repository.Local.AppDB;
import com.example.alex.emptyapp.Service.DBGymService;
import com.example.alex.emptyapp.Service.GymService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 09.12.2017.
 */

public class UserDashboard extends Activity {
    private GymService srv;
    private User user;
    private LinearLayout edit_user_container;
    private List< View > tabs = new ArrayList<>();

    ArrayAdapter< String > classes_adapter;
    ArrayAdapter< String > users_adapter;

    ArrayList< String > classes_list = new ArrayList<>();
    ArrayList< Integer > classes_ids = new ArrayList<>();
    ArrayList< String > users_list = new ArrayList<>();
    ArrayList< Integer > users_ids = new ArrayList<>();

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
                    ( ( LinearLayout )findViewById( R.id.user_dashboard_views ) ).removeView( current_tab );
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
                        ( ( LinearLayout )findViewById( R.id.user_dashboard_views ) ).removeView( f_to_hide );
                        if( f_to_show != null )
                        {
                            try
                            {
                                ( ( LinearLayout )findViewById( R.id.user_dashboard_views ) ).addView( f_to_show );
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
                        ( ( LinearLayout )findViewById( R.id.user_dashboard_views ) ).addView( to_show );
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
        setContentView( R.layout.user_dashboard );

        final ListView classes = ( ListView )findViewById( R.id.list_classes_user );
        final ListView trainers = ( ListView )findViewById( R.id.list_trainers_user );

        srv = new GymService( getApplicationContext() );

        user = srv.getLoggedUser();

        tabs = new ArrayList<>();
        tabs.add( findViewById( R.id.list_classes_user ) );
        tabs.add( findViewById( R.id.edit_user_user ) );
        tabs.add( findViewById( R.id.list_trainers_user ) );

        classes_adapter = new ArrayAdapter< String >( UserDashboard.this, android.R.layout.simple_list_item_1, classes_list );
        users_adapter = new ArrayAdapter< String >( UserDashboard.this, android.R.layout.simple_list_item_1, users_list );

        classes.setAdapter( classes_adapter );
        trainers.setAdapter( users_adapter );

        classes.setOnItemClickListener( ( AdapterView< ? > adapterView, View view, int i, long l ) ->
                                        {

                                        } );

        trainers.setOnItemClickListener( ( AdapterView< ? > adapterView, View view, int i, long l ) ->
                                        {
                                            show_give_rating( srv.getUserById( users_ids.get( i ) ) );
                                        } );

        edit_user_container = findViewById( R.id.edit_user_user );
        edit_user_container.removeAllViews();

        Bundle bundle = new Bundle();
        bundle.putSerializable( "loggedUser", user );
        // set Fragmentclass Arguments
        EditUserFragment fragment = new EditUserFragment();
        fragment.setArguments( bundle );

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add( R.id.edit_user_user, fragment );
        transaction.commit();

        ( ( Button )findViewById( R.id.btn_rate_trainers ) ).setOnClickListener( e ->
                                                                                 {
                                                                                     setVisibleTab( 2 );
                                                                                 } );
        ( ( Button )findViewById( R.id.btn_see_classes ) ).setOnClickListener( e ->
                                                                               {
                                                                                   setVisibleTab( 0 );
                                                                               } );
        ( ( Button )findViewById( R.id.btn_edit_user_user ) ).setOnClickListener( e ->
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
        classes_list.clear();
        classes_ids.clear();

        for( GymClass c : srv.getClasses() )
        {
            classes_list.add( c.getId() + ". " + c.getName() );
            classes_ids.add( c.getId() );
        }
        classes_adapter.notifyDataSetChanged();

        users_list.clear();
        users_ids.clear();

        for( User u : srv.getUsers() )
        {
            if( u.getRole() == Role.TRAINER )
            {
                users_list.add( u.getUsername() );
                users_ids.add( u.getId() );
            }
        }
        users_adapter.notifyDataSetChanged();

    }
    private void show_error_dialog( String msg, String title )
    {
        AlertDialog alertDialog = new AlertDialog.Builder( UserDashboard.this ).create();
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

    private void show_give_rating( User trainer )
    {
        LayoutInflater li = LayoutInflater.from( UserDashboard.this );
        LinearLayout someLayout = ( LinearLayout )li.inflate( R.layout.trainer_rating_lay, null );

        RatingBar rb = ( RatingBar )someLayout.findViewById( R.id.trainer_rating_bar );
        EditText et = ( EditText )someLayout.findViewById( R.id.txt_feedback_comment );

        AlertDialog alertDialog = new AlertDialog.Builder( UserDashboard.this ).create();
        alertDialog.setTitle( "Give rating" );
        alertDialog.setView( someLayout );

        alertDialog.setButton( AlertDialog.BUTTON_POSITIVE, "Rate!", new DialogInterface.OnClickListener()
        {
            public void onClick( DialogInterface dialog, int which )
            {
                Feedback f = new Feedback();
                f.setTrainerId( trainer.getId() );
                f.setUserId( user.getId() );
                f.setRating( ( short )rb.getRating() );
                f.setText( et.getText().toString() );
                try
                {
                    srv.giveFeedback( f );
                }
                catch( Exception e )
                {
                    show_error_dialog( e.getMessage(), "Error" );
                }
                dialog.dismiss();
            }
        } );
        alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "Close", new DialogInterface.OnClickListener()
        {
            public void onClick( DialogInterface dialog, int which )
            {
                dialog.dismiss();
            }
        } );
        alertDialog.requestWindowFeature( Window.FEATURE_OPTIONS_PANEL );
        alertDialog.show();
    }

}
