using Business_Layer.DTO;
using Business_Layer.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using System.Web.Http.Cors;
using DAL.Model;

namespace Gym_Application.Controllers
{
    public class MotociclistController : ApiController
    {
        [Route( "api/Motociclist" )]
        [EnableCors( origins: "*", headers: "*", methods: "*" )]
        [HttpPost]
        public IHttpActionResult PostMotociclist( [FromBody]Motociclist motociclist )
        {
            if( !ModelState.IsValid )
            {
                return BadRequest( ModelState );
            }

            var service = new MotociclistServices();
            MotociclistDTO model = service.addMotociclist( motociclist );
            return Ok( model );
        }

        [Route( "api/Motociclist/{id}" )]
        [EnableCors( origins: "*", headers: "*", methods: "*" )]
        [HttpDelete]
        public IHttpActionResult DeleteMotociclist( int id )
        {
            var service = new MotociclistServices();
            try
            {
                MotociclistDTO model = service.deleteMotociclist( id );
                return Ok( model );
            }
            catch( Exception e )
            {
                return NotFound();
            }
        }


        [Route( "api/Motociclist" )]
        [EnableCors( origins: "*", headers: "*", methods: "*" )]
        [HttpGet]
        public IQueryable<MotociclistDTO> GetCurse()
        {
            var service = new MotociclistServices();
            return service.getAllMotociclist();
        }

        [Route( "api/Motociclist/{id}" )]
        [EnableCors( origins: "*", headers: "*", methods: "*" )]
        [HttpGet]
        public IHttpActionResult GetMotociclist( int id )
        {
            var service = new MotociclistServices();
            try
            {
                MotociclistDTO model = service.getByID( id );
                return Ok( model );
            }
            catch( Exception e )
            {
                return NotFound();
            }
        }

    }
}
