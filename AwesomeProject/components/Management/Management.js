import  React from 'react';
import { AppRegistry, StyleSheet, Text, TextInput, Button, View, } from 'react-native';
import Native_bridge_test from '../DataLayer/Native_bridge_test';

/* ca idee, ai nevie de textfields si un button/doua/cate vrei/fara numar (nu stiu exact de cate ai nevoie) */
/* Pune sa fie trei fielduri si doua butoane, add si update. Si sa trimiti din lista "ceva" despre obiectul selectat, de preferat id-ul */ 
export default class Management extends React.Component {
	
  getMyObject( id )
  {
	  Native_bridge_test.getClassById( 
		id,
		( response ) =>
		{
			var myObject2 = JSON.parse( response );
			this.setState( { myObject: myObject2 } );
			if( myObject2!=null )
				this.setState( { objectToSend: myObject2 } );
		},
		( errmsg ) =>
		{
			alert( errmsg );
			this.setState( { myObject: null } );
		});
  }
  
  constructor(props) 
{
    super(props);
	this.state={myObject: null, objectToSend: { Id: 0, Name: "" }};
	this.getMyObject( this.props.navigation.state.params.Id );
  }
  
  onAdd( object )
  {
	  Native_bridge_test.addClass( 
		JSON.stringify(object),
		( response ) =>
		{
			var resp = JSON.parse( response );
			this.setState( { myObject: resp } );
			this.setState( { objectToSend: resp } );
		},
		( errmsg ) =>
		{
			alert( errmsg );
			this.setState( { myObject: null } );
		});
  }
  
  onUpdate( object )
  {
	  Native_bridge_test.updateClass( 
		JSON.stringify( object ),
		() =>
		{
			this.setState( { myObject: object } );
			this.setState( { objectToSend: object } );
		},
		( errmsg ) =>
		{
			alert( errmsg );
			this.setState( { myObject: null } );
		});
  }
  
  
  static navigationOptions = {
    title: 'Nice element becomes nicer',
  };

    render() {
    const { navigate } = this.props.navigation;

    const buttonText = this.state.myObject == null ? 'Add' : 'Update';

    return (
      <View style={styles.container}>
        <Text>
          Name
        </Text>
        <TextInput
          style = {styles.textInput}
		  value={ this.state.objectToSend.Name.toString() }
		  onChangeText = { (newVal) => { var newObject = this.state.objectToSend; newObject.Name=newVal; this.setState( { objectToSend: newObject } );} }
        />
        
        <Text>
          Id
        </Text>
        <TextInput
          style = {styles.textInput}
		  value={ this.state.myObject == null ? 'invalid id' : this.state.objectToSend.Id.toString() }
        />
        <Button
          style={styles.button}
          color='pink'
          styleDisabled={{color: 'grey'}}
          onPress={() => {
			  if(this.state.myObject == null)
			  {
				  this.onAdd( this.state.objectToSend );
			  }
			  else
			  {
				  this.onUpdate( this.state.objectToSend );
			  }
		  }}
          title={buttonText}>
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