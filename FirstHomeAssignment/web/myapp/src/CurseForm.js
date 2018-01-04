import React from  'react';
class CurseForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {idcursa:'',capacitate_Generala: '',nr_Participanti:''};

      //  this.handleChange = this.handleChange.bind(this);
       // this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleCurseChange=(event)=>{
        this.setState({idcursa: event.target.value});
    }
    handleCapacitate_GeneralaChange=(event) =>{
        this.setState({capacitate_Generala: event.target.value});
    }

    handleNr_ParticipantiChange=(event) =>{
        this.setState({nr_Participanti: event.target.value});
    }
    handleSubmit =(event) =>{

        var curse={idcursa:this.state.idcursa,
                capacitate_Generala:this.state.capacitate_Generala,
                nr_Participanti:this.state.nr_Participanti
        }
        console.log('A race was submitted: ');
        console.log(curse);
        this.props.addFunc(curse);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    IDCursa:
                    <input type="text" value={this.state.idcursa} onChange={this.handleCurseChange} />
                </label><br/>
                <label>
                    Capacitate_Generala:
                    <input type="text" value={this.state.capacitate_Generala} onChange={this.handleCapacitate_GeneralaChange} />
                </label><br/>
                <label>
                    Nr_Participanti:
                    <input type="text" value={this.state.nr_Participanti} onChange={this.handleNr_ParticipantiChange} />
                </label><br/>

                <input type="submit" value="Submit" />
            </form>
        );
    }
}
export default CurseForm;