System.register(["@angular/core", "./cursa.service"], function (exports_1, context_1) {
    "use strict";
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var __moduleName = context_1 && context_1.id;
    var core_1, trips_service_1, LandingComponent;
    return {
        setters: [
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (trips_service_1_1) {
                trips_service_1 = trips_service_1_1;
            }
        ],
        execute: function () {
            LandingComponent = class LandingComponent {
                constructor(service) {
                    this.service = service;
                    this.trips = [];
                    this.error = '';
                    this.modelAdd = {};
                    this.model = {};
                    this.openModalFor = null;
                }
                getTrips() {
                    this.trips = [];
                    this.service.getAllTrips()
                        .then(recievedTrips => {
                        this.trips = recievedTrips;
                    })
                        .catch(error => {
                    });
                }
                clearAddModel() {
                    this.modelAdd.id = '';
                    this.modelAdd.landmarkId = '';
                    this.modelAdd.transportId = '';
                    this.modelAdd.price = '';
                    this.modelAdd.totalSeats = '';
                    this.modelAdd.availableSeats = '';
                    this.modelAdd.leavingDate = '';
                }
                addTrip() {
                    this.error = '';
                    let leaving = new Date(this.modelAdd.leavingDate);
                    let month = leaving.getMonth() + 1;
                    let leavingDate = leaving.getFullYear() + "-" + month + "-" + leaving.getDate() + " " + leaving.getHours() + ":" + leaving.getMinutes() +
                        ":" + leaving.getSeconds() + "0.000";
                    let body = JSON.stringify({ id: this.modelAdd.id, landmarkId: this.modelAdd.landmarkId, transportId: this.modelAdd.transportId, price: this.modelAdd.price,
                        totalSeats: this.modelAdd.totalSeats, availableSeats: this.modelAdd.availableSeats, leavingDate: leavingDate });
                    this.service.addTrip(body)
                        .then(response => {
                        this.trips.push(response);
                        this.clearAddModel();
                    })
                        .catch(error => {
                        console.log(error);
                        this.error = "Error on adding trip: " + error._body;
                    });
                }
                editTrip(trip) {
                    this.error = '';
                    let body = JSON.stringify({ id: this.model.id, landmarkId: this.model.landmarkId, transportId: this.model.transportId, price: this.model.price,
                        totalSeats: this.model.totalSeats, availableSeats: this.model.availableSeats, leavingDate: trip.leavingDate });
                    this.service.editTrip(trip.id, body)
                        .then(response => {
                        let index = this.trips.indexOf(trip);
                        this.trips[index] = response;
                    })
                        .catch(error => {
                        this.error = "Error on editing trip: " + error._body;
                    });
                }
                setOpenModalFor(open) {
                    this.openModalFor = open;
                }
                clearOpenModalFor() {
                    this.openModalFor = null;
                }
                setActivityModelState(trip) {
                    this.model.id = trip.id;
                    this.model.landmarkId = trip.landmark.id;
                    this.model.transportId = trip.transport.id;
                    this.model.price = trip.price;
                    this.model.totalSeats = trip.totalSeats;
                    this.model.availableSeats = trip.availableSeats;
                }
                openModalForEditing(trip) {
                    this.setOpenModalFor(trip);
                    this.setActivityModelState(trip);
                }
                deleteTrip(trip) {
                    this.service.deleteTrip(trip.id)
                        .then(response => {
                        this.trips.splice(this.trips.indexOf(trip), 1);
                    })
                        .catch(error => {
                        this.error = "Error on deleting trip: " + error._body;
                    });
                }
                ngOnInit() {
                    console.log("Landing on init callback");
                    this.getTrips();
                }
            };
            LandingComponent = __decorate([
                core_1.Component({
                    moduleId: "landing-id",
                    selector: 'app-landing',
                    templateUrl: 'app/landing/landing.component.html',
                    providers: [trips_service_1.TripService]
                }),
                __metadata("design:paramtypes", [trips_service_1.TripService])
            ], LandingComponent);
            exports_1("LandingComponent", LandingComponent);
        }
    };
});
//# sourceMappingURL=landing.component.js.map