package com.awesomeproject.Native_Adapter;

import android.widget.Toast;

import com.awesomeproject.Domain.ClassSchedule;
import com.awesomeproject.Domain.Feedback;
import com.awesomeproject.Domain.GymClass;
import com.awesomeproject.Service.GymService;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;

import java.util.Map;
import java.util.HashMap;

public class TestModule extends ReactContextBaseJavaModule
{
    private GymService gymService;

    public TestModule( ReactApplicationContext reactContext )
    {
        super( reactContext );
        gymService = new GymService( reactContext );
    }

    @Override
    public String getName()
    {
        return "Native_bridge_test";
    }

    @ReactMethod
    public void setString( Callback successCallback, Callback errorCallback )
    {
        String value = "Java says: the ting goes skrra, pap pap ka ka ka";
        try
        {
            successCallback.invoke( value );
        }
        catch( IllegalViewOperationException e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void isOnline( Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( gymService.isOnline() );
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
            gymService.update_local();
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
            successCallback.invoke( ObjectConverters.toJSon( gymService.login( username, password ) ) );
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
            successCallback.invoke( ObjectConverters.toJSon( gymService.getLoggedUser() ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void addClass( String gymClassJson, Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( gymService.addClass( ObjectConverters.toObject( gymClassJson, GymClass.class ) ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getClassSchedule( int class_id, Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( gymService.getClassSchedule( class_id ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void addClassSchedule( String classScheduleJSon, Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( gymService.addClassSchedule( ObjectConverters.toObject( classScheduleJSon, ClassSchedule.class ) ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void updateClassSchedule( String cs, Callback successCallback, Callback errorCallback )
    {
        try
        {
            gymService.updateClassSchedule( ObjectConverters.toObject( cs, ClassSchedule.class ) );
            successCallback.invoke();
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getClasses( Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( gymService.getClasses() ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getUsers ( Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( gymService.getUsers().toArray() ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getClassById( int id, Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( gymService.getClassById( id ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getUserById( int id, Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( gymService.getUserById( id ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getClassScheduleById( int id, Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( ObjectConverters.toJSon( gymService.getClassScheduleById( id ) ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void updateClass( String gymClass, Callback successCallback, Callback errorCallback )
    {
        try
        {
            gymService.updateClass( ObjectConverters.toObject( gymClass, GymClass.class ) );
            successCallback.invoke();
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void deleteClassSchedule( String cs, Callback successCallback, Callback errorCallback )
    {
        try
        {
            gymService.deleteClassSchedule( ObjectConverters.toObject( cs, ClassSchedule.class ) );
            successCallback.invoke();
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    /*
    @ReactMethod
    public void giveFeedback( Feedback feedback, Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( gymService.giveFeedback( feedback ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

    @ReactMethod
    public void getFeedback( int trainer_id, Callback successCallback, Callback errorCallback )
    {
        try
        {
            successCallback.invoke( gymService.getFeedback( trainer_id ) );
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }
*/
    @ReactMethod
    public void logout( Callback successCallback, Callback errorCallback )
    {
        try
        {
            gymService.logout();
            successCallback.invoke();
        }
        catch( Exception e )
        {
            errorCallback.invoke( e.getMessage() );
        }
    }

}