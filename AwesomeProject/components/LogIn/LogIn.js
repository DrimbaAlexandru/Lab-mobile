import React, { Component } from 'react';
import { AppRegistry, StyleSheet, Text, TextInput, Button, View, ActivityIndicator, Alert } from 'react-native';
import Native_bridge_test from '../DataLayer/Native_bridge_test';


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

export default class LogIn extends Component {
	
	init() 
	{
		Native_bridge_test.isOnline( 
			( isOnline ) =>
			{
				this.setState( {doneLoading: true } );
				if( isOnline==true )
				{
					Native_bridge_test.update_local(() =>
					{
					   alert( "Updated local DB");
					},
					( errmsg ) =>
					{
					   alert( 'PUSCAT' + errmsg );
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
				alert( errmsg );
			});
	}
	
  constructor(props) {
    super(props);
    this.state = { loggedin: false, textUser: 'user', textPasswd: 'user', doneLoading : false };
	setTimeout( function() { this.init(); }.bind(this), 1000);
  }

  static navigationOptions = {
    title: 'Welcome',
  };

  render() {
    const { navigate } = this.props.navigation;
    return (
      <View style={styles.container}>
		<LoadingCircleContainer doneLoading={this.state.doneLoading} />
        <TextInput
          style={{width: 200, height: 40, borderColor: 'gray', borderWidth: 1, textAlign: 'center'}}
          onChangeText={(textUser) => this.setState({textUser})}
          placeholder={'username...'}
        />
        <TextInput
          style = {styles.textInput}
          onChangeText={(textPasswd) => this.setState({textPasswd})}
          placeholder={'password...'}
        />
        <Button
          disabled={this.state.textUser==='' || this.state.textPasswd===''}
          style={styles.button}
          color='pink'
          styleDisabled={{color: 'grey'}}
          onPress={ () => {
							this.setState( {doneLoading: false } );
                            Native_bridge_test.login(
								this.state.textUser,
								this.state.textPasswd,
                                ( ret_val ) =>
                                {
									this.setState( {doneLoading: true } );
                                    var myObject = JSON.parse( ret_val );
                                    Alert.alert( "Got here " + myObject );
                                    if( myObject == null )
                                    {
                                        Alert(" Authentication failed! ");
                                    }
                                    else
                                    {
                                        navigate('Home');
                                    }
                                },
                                ( errmsg ) =>
                                {
									this.setState( {doneLoading: true } );

									if( this.state.textUser === 'user' && this.state.textPasswd === 'user' )
									{
										navigate('Home');
									}
									else
                                    {
										Alert.alert( errmsg );
									}
                                }
                            );
                        }
                    }
          title="Log In">
        </Button>
		<Button
          style={styles.button}
          color='pink'
          styleDisabled={{color: 'grey'}}
          onPress={ () => {
                            Native_bridge_test.sendEmail()
						  }
                  }
          title="Send mail">
        </Button>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  textInput: {
    width: 200, height: 40, borderColor: 'gray', borderWidth: 1, textAlign: 'center'
  },
  button: {
    fontSize: 20
  },
});
