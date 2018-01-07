package com.awesomeproject.Native_Adapter;

import com.awesomeproject.Domain.Cursa;
import com.awesomeproject.Domain.Motociclist;
import com.awesomeproject.Domain.Participare;
import com.awesomeproject.Service.CurseService;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;

import java.util.List;

public class TestModule extends ReactContextBaseJavaModule
{
    private CurseService curseService;

    public TestModule( ReactApplicationContext reactContext )
    {
        super( reactContext );
        curseService = new CurseService( reactContext );
    }

    @Override
    public String getName()
    {
        return "Native_bridge_test";
    }
    
    @ReactMethod
    public void isOnline( Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( curseService.isOnline() );
        }
        catch( IllegalViewOperationException e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void update_local( Callback successCallback, Callback errorCallback )
    {
        try
        {
            curseService.update_local();
            successCallback.invoke();
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void login( String username, String password, Callback successCallback, Callback errorCallback ) throws Exception
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( curseService.login( username, password ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getLoggedUser ( Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( curseService.getLoggedUser() ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getCurse( Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( curseService.getCurse() ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getMotociclisti( Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( curseService.getMotociclisti() ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getParticipanti( int cursa_id, Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( curseService.getParticipanti( cursa_id ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getCursa( int id, Callback successCallback, Callback errorCallback  )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( curseService.getCursa( id ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getMotociclist( int id, Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( curseService.getMotociclist( id ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void addCursa( String cursa, Callback successCallback, Callback errorCallback ) throws Exception
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( curseService.addCursa( ObjectConverters.toObject( cursa, Cursa.class ) ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void addMotociclist( String m, Callback successCallback, Callback errorCallback ) throws Exception
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( curseService.addMotociclist( ObjectConverters.toObject( m, Motociclist.class ) ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void addParticipare( String p, Callback successCallback, Callback errorCallback ) throws Exception
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( curseService.addParticipare( ObjectConverters.toObject( p, Participare.class ) ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void deleteCursa( String el, Callback successCallback, Callback errorCallback ) throws Exception
    {
        try
        {
            curseService.deleteCursa( ObjectConverters.toObject( el, Cursa.class ) );
            successCallback.invoke();
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void deleteMotociclist( String el, Callback successCallback, Callback errorCallback ) throws Exception
    {
        try
        {
            curseService.deleteMotociclist( ObjectConverters.toObject( el, Motociclist.class ) );
            successCallback.invoke();
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void updateCursa( String el, Callback successCallback, Callback errorCallback ) throws Exception
    {
        try
        {
            curseService.updateCursa( ObjectConverters.toObject( el, Cursa.class ) );
            successCallback.invoke();
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void updateMotociclist( String el, Callback successCallback, Callback errorCallback ) throws Exception
    {
        try
        {
            curseService.updateMotociclist( ObjectConverters.toObject( el, Motociclist.class ) );
            successCallback.invoke();
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }
    
    @ReactMethod
    public void logout( Callback successCallback, Callback errorCallback )
    {
        try
        {
            curseService.logout();
            successCallback.invoke();
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

}