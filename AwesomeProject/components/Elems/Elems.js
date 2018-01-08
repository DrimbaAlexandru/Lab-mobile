import React, { Component } from 'react';
import Row from './Row';
import Native_bridge_test from '../DataLayer/Native_bridge_test';

import {
  AppRegistry,
  StyleSheet,
  Text,
  TextInput,
  Button,
  View,
  ListView,
} from 'react-native';

export default class Elems extends Component {
  constructor(props) {
    super(props);
    //this.state = { textUser: 'username...', textPasswd: 'password...', dataSource: ds.cloneWithRows([{idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}]) };
    
    this.state = { actualData: [{Id: -1, name: 'Add New'}] };
    //this.renderRow = this.renderRow.bind(this);
	  setTimeout( function() { this.populateData(); }.bind(this), 200);
  }
  
  populateData()
  {
	  Native_bridge_test.getClasses( 
		( response ) =>
		{
			var myObject = JSON.parse( response );
			var elements = [{Id: -1, name: 'Add New'}];
			if( myObject.length > 0)
			{
				myObject.forEach( ( item, index ) => 
					{
						elements.push(item);
					}
				);
			}
			else
			{
				elements.push( {Id: -1, name: 'Got an empty list'} );	
			}
			this.setState( { actualData: elements } );
			
		},
		( errmsg ) =>
		{
			alert( errmsg );
		});
  }
  

/*  renderRow (item) {
    return (
      <ListItem
        id={item.Id}
        name={item.name}
        onPress = {() => console.log(item.name)}
      />
    )
  }*/

  render() {
    const { navigate } = this.props.navigation;
    let { datatosend } = this.state.actualData;
    return (
      <View style={styles.container}>
        <Button 
          onPress={() => navigate('Chart')}
          title = 'Charts' >
        </Button>
        <ListView    
          dataSource={ new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2}).cloneWithRows(this.state.actualData)}
          renderRow={(data) => <Row {...{data, navigate}} />}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
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
