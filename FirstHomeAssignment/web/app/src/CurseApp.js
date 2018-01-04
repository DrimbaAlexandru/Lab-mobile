/**
 * Created by grigo on 5/18/17.
 */
import React from  'react';
import CurseTable from './Curse';
import './CurseApp.css'
import './CurseForm'
import CurseForm from "./CurseForm";
import {GetCurses, DeleteCurse, AddCurse} from './utils/rest-calls'


class CurseApp extends React.Component{
    constructor(props){
        super(props);
        this.state={curses:[{"id":"101","capacitate":"50","nrParticipanti":"30"}],
            deleteFunc:this.deleteFunc.bind(this),
            addFunc:this.addFunc.bind(this),
        }
        console.log('CurseApp constructor')
    }

    addFunc(curse){
        console.log('inside add Func '+curse);
        AddCurse(curse)
            .then(res=> GetCurses())
            .then(curses=>this.setState({curses}))
            .catch(erorr=>console.log('eroare add ',erorr));
    }


    deleteFunc(curse){
        console.log('inside deleteFunc '+curse);
        DeleteCurse(curse)
            .then(res=> GetCurses())
            .then(curses=>this.setState({curses}))
            .catch(error=>console.log('eroare delete', error));
    }


    componentDidMount(){
        console.log('inside componentDidMount')
        GetCurses().then(curses=>this.setState({curses}));
    }

    render(){
        return(
            <div className="CurseApp">
                <h1>Curse</h1>
                <CurseForm addFunc={this.state.addFunc}/>
                <br/>
                <br/>
                 <CurseTable curses={this.state.curses} deleteFunc={this.state.deleteFunc}/>
            </div>
        );
    }
}

export default CurseApp;