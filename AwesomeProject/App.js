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
  Alert,
  ActivityIndicator
} from 'react-native';
import Native_bridge_test from './javaModuleWrapper';
import GraphView from './GraphView';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
  android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

class RCTGraphViewComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
	 //return <Text>AHA</Text>;
      return(
		<View>
			<Text>AHA</Text>
			<GraphView style={{width: 300, height: 300}} dataSource={this.props.dataSource}/>
			<Text>AHA</Text>
		</View>
	  ) ;
  }
}

class TextThingy extends Component {
  render() {
    return (
      <Text>{this.props.text}</Text>
    );
  }
}

class LoadingCircleContainer extends Component
{
	render()
	{
		let indicator = null;
		if( !this.props.doneLoading )
		{
			indicator = <ActivityIndicator size="large" color="#0000ff" />;
		}
		else
		{
			indicator = <Text> </Text>
		}
		return ( 
			<View>
				{indicator}
			</View>
		);
	}
}

export default class App extends Component<{}>
{
	
	init() 
	{
		Native_bridge_test.isOnline( 
			( isOnline ) =>
			{
				this.setState( {doneLoading: true } );
				this.setState( {showtext: "Gata incarcat"});
				if( isOnline==true )
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
				this.setState( {doneLoading: true } );
				this.setState( {showtext: "Gata incarcat"});
				alert( errmsg );
			});
	}
	
    constructor( props )
    {
        super( props );
        this.state = {showtext: 'man\'s not hot',
                      doneLoading : false };
		setTimeout( function() { this.init(); }.bind(this), 1000);
    }
	
    render()
    {
		var data=[[1,2],[3,4]];
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
				<LoadingCircleContainer doneLoading={this.state.doneLoading} />
				
				<Text>{data} gg</Text>
				<RCTGraphViewComponent dataSource={data}/>
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
