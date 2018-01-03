/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Button,
  Alert
} from 'react-native';
import Native_bridge_test from './javaModuleWrapper';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
  android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

class TextThingy extends Component {
  render() {
    return (
      <Text>{this.props.text}</Text>
    );
  }
}

export default class App extends Component<{}>
{
    constructor( props )
    {
        super( props );
        this.state = {showtext: 'man\'s not hot',
                      dbReady : false };
        Native_bridge_test.isOnline( ( isOnline ) =>
                                      {
                                          if(isOnline==true)
                                          {
                                          Native_bridge_test.update_local(() =>
                                                                                   {
                                                                                       alert( "Updated local DB");
                                                                                   },
                                                                                  ( errmsg ) =>
                                                                                   {
                                                                                       alert( errmsg );
                                                                                   });
                                          }
                                          else
                                          {
                                            alert( "The server is not available" );
                                          }
                                      },
                                     ( errmsg ) =>
                                      {
                                          alert( errmsg );
                                      });

    }

    render()
    {
        return (
            <View>
                <Button
                    onPress = { () => {
                            Native_bridge_test.getClasses(
                                ( ret_val ) =>
                                {
                                    var myObject = JSON.parse( ret_val );
                                    Alert.alert( "Got here " + ret_val );
                                    if( myObject.length >= 0 )
                                    {
                                        this.setState( () => {
                                            return { showtext: myObject[0].Name };
                                        });
                                    }
                                    else
                                    {
                                        this.setState( () => {
                                            return { showtext: "Empty list" };
                                        });
                                    }
                                },
                                ( errmsg ) =>
                                {
                                    this.setState( () => {
                                            return { showtext: errmsg + '' };
                                          });
                                }
                            );
                        }
                    }
                    title="Press Me"
                />
                <Text>{this.state.showtext}</Text>
            </View>
        );
    }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
