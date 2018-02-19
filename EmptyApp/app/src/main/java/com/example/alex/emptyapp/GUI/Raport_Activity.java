package com.example.alex.emptyapp.GUI;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.alex.emptyapp.Controller.ObserverMessage;
import com.example.alex.emptyapp.Controller.ProductController;
import com.example.alex.emptyapp.Controller.ProductControllerSingleton;
import com.example.alex.emptyapp.R;

import java.util.Observable;
import java.util.Observer;

public class Raport_Activity extends Activity implements Observer
{
    private ProductController controller = ProductControllerSingleton.getInstance();

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_raport_layout );
        findViewById( R.id.progressBar ).setVisibility( View.VISIBLE );
    }

    @Override
    public void update( Observable observable, Object o )
    {
        if( o instanceof ObserverMessage )
        {
            runOnUiThread( () ->
                           {
                               switch( ( ObserverMessage )o )
                               {
                                   case Raport_Succes:
                                       findViewById( R.id.progressBar ).setVisibility( View.INVISIBLE );
                                       ( ( TextView )( this.findViewById( R.id.raport_text ) ) ).setText( controller.getReport_text() );
                                       break;
                                   case Raport_Eroare_Retea:
                                       findViewById( R.id.progressBar ).setVisibility( View.INVISIBLE );
                                       ( ( TextView )( this.findViewById( R.id.raport_text ) ) ).setText( "Eroare retea" );
                                       break;
                                   case Raport_Refuzat:
                                       findViewById( R.id.progressBar ).setVisibility( View.INVISIBLE );
                                       ( ( TextView )( this.findViewById( R.id.raport_text ) ) ).setText( "Cerere refuzata de server" );
                                       break;
                               }
                           } );
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if( controller != null )
        {
            controller.deleteObserver( this );
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if( controller != null )
        {
            controller.addObserver( this );
        }
    }
}
