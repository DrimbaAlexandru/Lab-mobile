import React from  'react';
import CurseTable from './Curse';
import './CurseApp.css'
import './CurseForm'
import CurseForm from "./CurseForm";
import {GetCurses, DeleteCurse, AddCurse} from './utils/rest-calls'


class CurseApp extends React.Component{
    constructor(props){
        super(props);
        this.state={curses:[{"idcursa":"10","capacitate_Generala":"15","nr_Participanti":"10"}],
            deleteFunc:this.deleteFunc.bind(this),
            addFunc:this.addFunc.bind(this),
        }
        console.log('CurseApp constructor')
    }

    addFunc(curse){
        console.log('insIDCursae add Func '+curse);
        AddCurse(curse)
            .then(res=> GetCurses())
            .then(curses=>this.setState({curses}))
            .catch(erorr=>console.log('eroare add ',erorr));
    }


    deleteFunc(curse){
        console.log('insIDCursae deleteFunc '+curse);
        DeleteCurse(curse)
            .then(res=> GetCurses())
            .then(curses=>this.setState({curses}))
            .catch(error=>console.log('eroare delete', error));
    }


    componentDIDCursaMount(){
        console.log('insIDCursae componentDIDCursaMount')
        GetCurses().then(curses=>this.setState({curses}));
    }

    render(){
        return(
            <div className="CurseApp">
                <h1>Curse Management</h1>
                <CurseForm addFunc={this.state.addFunc}/>
                <br/>
                <br/>
                 <CurseTable curses={this.state.curses} deleteFunc={this.state.deleteFunc}/>
            </div>
        );
    }
}

export default CurseApp;