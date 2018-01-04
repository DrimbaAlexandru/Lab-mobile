/**
 * Created by grigo on 5/19/17.
 */
import React from  'react';
class CurseForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {id: '', capacitate:'', nrParticipanti:''};

      //  this.handleChange = this.handleChange.bind(this);
       // this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleCurseChange=(event) =>{
        this.setState({id: event.target.value});
    }

    handleCapacitateChange=(event) =>{
        this.setState({capacitate: event.target.value});
    }

    handleNrParticipantiChange=(event) =>{
        this.setState({nrParticipanti: event.target.value});
    }
    handleSubmit =(event) =>{

        var curse={id:this.state.id,
                capacitate:this.state.capacitate,
                nrParticipanti:this.state.nrParticipanti
        }
        console.log('A curse was submitted: ');
        console.log(curse);
        this.props.addFunc(curse);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Id:
                    <input type="text" value={this.state.id} onChange={this.handleCurseChange} />
                </label><br/>
                <label>
                    Capacitate:
                    <input type="text" value={this.state.capacitate} onChange={this.handleCapacitateChange} />
                </label><br/>
                <label>
                    NrParticipanti:
                    <input type="text" value={this.state.nrParticipanti} onChange={this.handleNrParticipantiChange} />
                </label><br/>

                <input type="submit" value="Submit" />
            </form>
        );
    }
}
export default CurseForm;