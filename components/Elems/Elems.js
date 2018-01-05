import React from 'react';
import Row from './Row';
import Header from './Header';
import {
  AppRegistry,
  StyleSheet,
  Text,
  TextInput,
  Button,
  View,
  ListView,
} from 'react-native';

export default class Elems extends React.Component {
  constructor(props) {
    super(props);
    const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    this.state = { textUser: 'username...', textPasswd: 'password...', dataSource: ds.cloneWithRows([{idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}]) };
  }

  render() {
    return (
      <View style={styles.container}>
        <ListView
          dataSource={this.state.dataSource}
          renderRow={(data) => <Row {...data} />}
          renderHeader={() => <Header />}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 20,
    backgroundColor: '#fff',
    justifyContent: 'center',
  },
  textInput: {
    width: 200, height: 40, borderColor: 'gray', borderWidth: 1, textAlign: 'center'
  },
  button: {
    fontSize: 20
  },
});
