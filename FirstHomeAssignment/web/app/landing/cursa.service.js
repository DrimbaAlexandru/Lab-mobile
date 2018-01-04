System.register(["@angular/core", "@angular/http", "rxjs/Rx", "rxjs/add/operator/map"], function (exports_1, context_1) {
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
    var core_1, http_1, CursaService;
    return {
        setters: [
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (_1) {
            },
            function (_2) {
            }
        ],
        execute: function () {
            CursaService = class CursaService {
                constructor(http) {
                    this.http = http;
                    this.curseUrl = "http://localhost:8080/curse/curses/";
                }
                getAllCurse() {
                    return Promise.resolve(this.http.get(this.curseUrl).map((response) => {
                        return response.json();
                    })
                        .toPromise());
                }
                deleteCursa(id) {
                    return Promise.resolve(this.http.delete(this.curseUrl + id).map((response) => {
                        //return response;
                    })
                        .toPromise());
                }
                addCursa(body) {
                    console.log(body);
                    let headers = new http_1.Headers({ 'Content-Type': 'application/json' });
                    let options = new http_1.RequestOptions({ headers: headers });
                    return Promise.resolve(this.http.post(this.curseUrl, body, options).map((response) => {
                        return response.json();
                    })
                        .toPromise());
                }
                editCursa(id, body) {
                    let headers = new http_1.Headers({ 'Content-Type': 'application/json' });
                    let options = new http_1.RequestOptions({ headers: headers });
                    return Promise.resolve(this.http.put(this.curseUrl + id, body, options).map((response) => {
                        return response.json();
                    })
                        .toPromise());
                }
            };
            CursaService = __decorate([
                core_1.Injectable(),
                __metadata("design:paramtypes", [http_1.Http])
            ], CursaService);
            exports_1("CursaService", CursaService);
        }
    };
});
//# sourceMappingURL=cursa.service.js.map