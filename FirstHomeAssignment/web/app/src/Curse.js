/**
 * Created by grigo on 5/17/17.
 */
import React from  'react';
import './CurseApp.css'

class CurseRow extends React.Component{

    handleClicke=(event)=>{
        console.log('delete button pentru '+this.props.curse.id);
        this.props.deleteFunc(this.props.curse.id);
    }

    render() {
        return (
            <tr>
                <td>{this.props.curse.id}</td>
                <td>{this.props.curse.capacitate}</td>
				<td>{this.props.curse.nrParticipanti}</td>
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

            rows.push(<CurseRow curse={curse} key={curse.id} deleteFunc={functieStergere} />);
        });
        return (<div className="CurseTable">

            <table className="center">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Capacitate</th>
					<th>Numar Participanti</th>

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