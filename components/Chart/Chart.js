import { StyleSheet, View, Component, Text, ListView } from 'react-native';
import  React from 'react';
import Row from '../Elems/Row';
//var { Axes, BarChart, LineChart, makeRange, generateScale } = require('react-native-vs-charts');

export default class ObjectsChart extends React.Component {
    constructor(props) {
        super(props);
        const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this.state = { textUser: 'username...', textPasswd: 'password...', dataSource: ds.cloneWithRows([{idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}, {idclient: 1, name: 'asdf'}]) };
    }

    render() {
        alert(this.props.navigation.state.datatosend);
        return(<View>
          <Text>{this.props.navigation.state.datatosend}</Text>
        </View>);
    }
}