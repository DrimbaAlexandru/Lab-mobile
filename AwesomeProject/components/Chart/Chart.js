import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View
} from 'react-native';

import Native_bridge_test from '../DataLayer/Native_bridge_test';
import GraphView from '../JavaChartView/GraphView';

export default class ObjectsChart extends React.Component {
    constructor(props) {
        super(props);
        const ds = [[1,3],[4,5],[7,2]];
        this.state = { dataSource: ds };
    }

    render() {
        return(
			<View>
				<GraphView style={{width: 300, height: 300}} dataSource={this.state.dataSource}/>
			</View>
	  ) ;
    }
}