import React from  'react';
import './CurseApp.css'

class CurseRow extends React.Component{

    handleClicke=(event)=>{
        console.log('delete button pentru '+this.props.curse.dcursa);
        this.props.deleteFunc(this.props.curse.idcursa);
    }

    render() {
        return (
            <tr>
                <td>{this.props.curse.idcursa}</td>
                <td>{this.props.curse.capacitate_Generala}</td>
                <td>{this.props.curse.nr_Participanti}</td>
                <td><button  onClick={this.handleClicke}>Delete</button></td>
            </tr>
        );
    }
}
/*<form onSubmit={this.handleClicke}><input type="submit" value="Delete"/></form>*/
/**/
class CurseTable extends React.Component {
    render() {
        var rows = [];
        var functieStergere=this.props.deleteFunc;
        this.props.curses.forEach(function(curse) {

            rows.push(<CurseRow curse={curse} key={curse.IDCursa} deleteFunc={functieStergere} />);
        });
        return (<div className="CurseTable">

            <table className="center">
                <thead>
                <tr>
                    <th>IDCursa</th>
                    <th>Capacitate_Generala</th>
                    <th>Nr_Participanti</th>

                    <th></th>
                </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>

            </div>
        );
    }
}

export default CurseTable;