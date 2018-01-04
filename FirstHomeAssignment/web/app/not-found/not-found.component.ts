import {Component, OnInit}      from '@angular/core';

@Component({
    moduleId: "not-found-id", 
    templateUrl: 'app/not-found/not-found.component.html'
})
export class NotFoundComponent implements OnInit {

    msg = '200ok';
 
    constructor() {
        this.msg = "404";
    }

    ngOnInit() {
        console.log("Landing on init callback");
    }
}
