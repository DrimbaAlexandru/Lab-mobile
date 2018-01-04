import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CursaService } from './cursa.service'

@Component({
    moduleId: "landing-id", 
    selector: 'app-landing',
    templateUrl: 'app/landing/landing.component.html',
    providers: [ CursaService ]
})

export class LandingComponent implements OnInit {
    curse: Cursa[] = [];
    error = '';
    modelAdd: any = {};
    model: any = {};
    openModalFor: Cursa = null;
 
    constructor(private service: CursaService) { }

    getCursa(): void {
        this.curse = [];
        this.service.getAllCursa()
            .then(recievedCurse => {
                this.curse = recievedCurse;
            })
            .catch(error => { 
            });
    }

    clearAddModel(): void{
        this.modelAdd.id = '';
        this.modelAdd.capacitate = '';
        this.modelAdd.participanti = '';
    }

    addCursa(): void {
        this.error = '';
        
        let body = JSON.stringify({id : this.modelAdd.id, capacitate: this.modelAdd.capacitate, participanti: this.modelAdd.participanti});
        this.service.addCursa(body)
            .then(response => {
                this.curse.push(response);
                this.clearAddModel();
            })
            .catch(error => { 
                console.log(error);
                this.error = "Error on adding race: "+error._body;
            }); 
    }

    editCursa(cursa: Cursa): void {
        this.error = '';
        let body = JSON.stringify({id : this.modelAdd.id, capacitate: this.modelAdd.capacitate, participanti: this.modelAdd.participanti});

        this.service.editCursa(cursa.id, body)
                .then(response => {
                    let index = this.curse.indexOf(cursa);
                    this.curse[index] = response;
                })
                .catch(error => { 
                    this.error = "Error on editing race: "+error._body;
                }); 
    }

    setOpenModalFor(open: Cursa): void {
        this.openModalFor = open;
    }

    clearOpenModalFor(): void {
        this.openModalFor = null;
    }

    setActivityModelState(cursa: Cursa): void {
        this.model.id = cursa.id;
        this.model.capacitate = cursa.capacitate;
        this.model.participanti = cursa.participanti;
    }

    openModalForEditing(cursa: Cursa): void {
        this.setOpenModalFor(cursa);
        this.setActivityModelState(cursa);
    }

    deleteCursa(cursa: Cursa): void {
        this.service.deleteCursa(cursa.id)
            .then(response => {
                this.curse.splice(this.curse.indexOf(cursa),1);
            })
            .catch(error => { 
                this.error = "Error on deleting race: "+error._body;
            });
    }

    ngOnInit() {
        console.log("Landing on init callback");
        this.getCursa();
    }
}
