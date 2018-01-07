package com.awesomeproject.Native_Adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Alex on 03.01.2018.
 */

public class ObjectConverters
{
    private static final Gson gson = new GsonBuilder().create();
    /*
    private static boolean isGetter( Method method )
    {
        if( !method.getName().startsWith( "get" ) )
        {
            return false;
        }
        if( method.getParameterTypes().length != 0 )
        {
            return false;
        }
        if( void.class.equals( method.getReturnType() ) )
        {
            return false;
        }
        return true;
    }

    private static boolean isSetter( Method method )
    {
        if( !method.getName().startsWith( "set" ) )
        {
            return false;
        }
        if( method.getParameterTypes().length != 1 )
        {
            return false;
        }
        return true;
    }

    public static Object fromReadableMap( ReadableMap map, Class obj_class ) throws ClassCastException
    {
        try
        {
            Constructor constructor = obj_class.getConstructor();
            Object obj = obj_class.getConstructor().newInstance();
            Method[] methods = obj_class.getMethods();
            HashMap< String, Object > hashMap = map.toHashMap();

            for( Method method : methods )
            {
                if( isSetter( method ) )
                {
                    System.out.println( "getter: " + method );
                    String field_name = method.getName();
                    for( String key : hashMap.keySet() )
                    {
                        if( key.equalsIgnoreCase( field_name ) )
                        {
                            method.invoke( obj, hashMap.get( key ) );
                            break;
                        }
                    }
                }
                if( isGetter( method ) )
                {
                    System.out.println( "setter: " + method );
                }
            }
            return obj;
        }
        catch( Exception e )
        {
            e.printStackTrace();
            throw new ClassCastException( e.getMessage() );
        }
    }
*/

    public static String toJSon( Object object )
    {
        return gson.toJson( object );
    }

    public static < T > T toObject( String JSon, Class< T > _class )
    {
        return gson.fromJson( JSon, _class );
    }

}
