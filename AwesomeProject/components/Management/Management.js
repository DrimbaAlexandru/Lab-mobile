import  React from 'react';
import { AppRegistry, StyleSheet, Text, TextInput, Button, View, } from 'react-native';

/* ca idee, ai nevie de textfields si un button/doua/cate vrei/fara numar (nu stiu exact de cate ai nevoie) */
/* Pune sa fie trei fielduri si doua butoane, add si update. Si sa trimiti din lista "ceva" despre obiectul selectat, de preferat id-ul */ 
export default class Management extends React.Component {
  constructor(props) {
    super(props);
  }

  static navigationOptions = {
    title: 'Nice element becomes nicer',
  };

    render() {
    const { navigate } = this.props.navigation;
    const { idclient } = this.props.navigation.state.params; //nu doar teoretic anymore, se primeste un id frumos si asa se face retrieve
    const { buttonText } = { idclient } == -1 ? 'Add' : 'Update';
    return (
      <View style={styles.container}>
        <Text>
          Field 1
        </Text>
        <TextInput
          style={{width: 200, height: 40, borderColor: 'gray', borderWidth: 1, textAlign: 'center'}}
        />
        <Text>
          Name
        </Text>
        <TextInput
          style = {styles.textInput}
          value={ idclient }
        />
        <Text>
          Annual income bish
        </Text>
        <TextInput
          style = {styles.textInput}
        />
        <Button
          style={styles.button}
          color='pink'
          styleDisabled={{color: 'grey'}}
          onPress={() => alert()}
          title={{ idclient } == -1 ? 'Add' : 'Update'}>
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