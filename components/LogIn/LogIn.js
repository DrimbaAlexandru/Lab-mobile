import React from 'react';
import { AppRegistry, StyleSheet, Text, TextInput, Button, View, } from 'react-native';

export default class LogIn extends React.Component {
  constructor(props) {
    super(props);
    this.state = { loggedin: false, textUser: 'username...', textPasswd: 'password...' };
  }

  static navigationOptions = {
    title: 'Welcome',
  };

  render() {
    const { navigate } = this.props.navigation;
    return (
      <View style={styles.container}>
        <TextInput
          style={{width: 200, height: 40, borderColor: 'gray', borderWidth: 1, textAlign: 'center'}}
          onChangeText={(textUser) => this.setState({textUser})}
          placeholder={this.state.textUser}
        />
        <TextInput
          style = {styles.textInput}
          onChangeText={(textPasswd) => this.setState({textPasswd})}
          placeholder={this.state.textPasswd}
        />
        <Button
          disabled={this.state.textUser==='username...' || this.state.textPasswd==='password...' || this.state.textUser==='' || this.state.textPasswd===''}
          style={styles.button}
          color='pink'
          styleDisabled={{color: 'grey'}}
          onPress={() => navigate('Home')}
          title="Log In">
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
