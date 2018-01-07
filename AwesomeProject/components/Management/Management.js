import  React from 'react';
import { AppRegistry, StyleSheet, Text, TextInput, Button, View, } from 'react-native';

/* ca idee, ai nevie de textfields si un button/doua/cate vrei/fara numar (nu stiu exact de cate ai nevoie) */
/* Pune sa fie trei fielduri si doua butoane, add si update. Si sa trimiti din lista "ceva" despre obiectul selectat, de preferat id-ul */ 
export default class Management extends React.Component {
  constructor(props) {
    super(props);
    this.state = { textUser: 'username...', textPasswd: 'password...' };
  }

  static navigationOptions = {
    title: 'Nice element becomes nicer',
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