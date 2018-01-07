import React from 'react';
import Row from './Row';
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
    this.state = { actualData: [{idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}], textUser: 'username...', textPasswd: 'password...', dataSource: ds.cloneWithRows([{idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}]) };
    this.renderRow = this.renderRow.bind(this);
  }

  renderRow (item) {
    return (
      <ListItem
        id={item.idclient}
        name={item.name}
        onPress = {() => console.log(item.name)}
      />
    )
  }

  render() {
    const { navigate } = this.props.navigation;
    let { datatosend } = this.state.actualData;
    return (
      <View style={styles.container}>
        <Button 
          onPress={() => navigate('Chart', { datatosend })}
          title = 'Charts' >
        </Button>
        <ListView
          dataSource={this.state.dataSource}
          renderRow={(data) => <Row {...data} />}
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
