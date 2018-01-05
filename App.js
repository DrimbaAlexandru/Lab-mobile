import React from 'react';
import LogIn from './components/LogIn/LogIn';
import Elems from './components/Elems/Elems';
import { StackNavigator } from 'react-navigation';
import { Router } from './Router';

export default class App extends React.Component {
	render() { return <Router />; }
}