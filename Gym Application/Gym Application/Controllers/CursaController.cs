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
    public class CursaController : ApiController
    {
        [Route( "api/Cursa" )]
        [EnableCors( origins: "*", headers: "*", methods: "*" )]
        [HttpPost]
        public IHttpActionResult PostCursa( [FromBody]Cursa cursa )
        {
            if( !ModelState.IsValid )
            {
                return BadRequest( ModelState );
            }

            var service = new CursaServices();
            CursaDTO model = service.addCursa( cursa );
            return Ok( model );
        }

        [Route( "api/Cursa" )]
        [EnableCors( origins: "*", headers: "*", methods: "*" )]
        [HttpPut]
        public IHttpActionResult PutCursa( [FromBody]Cursa cursa )
        {
            if( !ModelState.IsValid )
            {
                return BadRequest( ModelState );
            }

            var service = new CursaServices();
            CursaDTO model = service.updateCursa( cursa );
            return Ok( model );
        }

        [Route( "api/Cursa/{id}" )]
        [EnableCors( origins: "*", headers: "*", methods: "*" )]
        [HttpDelete]
        public IHttpActionResult DeleteCursa( int id )
        {
            var service = new CursaServices();
            try
            {
                CursaDTO model = service.deleteCursa( id );
                return Ok( model );
            }
            catch( Exception e )
            {
                return NotFound();
            }
        }


        [Route( "api/Cursa" )]
        [EnableCors( origins: "*", headers: "*", methods: "*" )]
        [HttpGet]
        public IQueryable<CursaDTO> GetCurse()
        {
            var service = new CursaServices();
            return service.getAllCursa();
        }

        public IHttpActionResult GetCursa( int id )
        {
            var service = new CursaServices();
            try
            {
                CursaDTO model = service.getByID( id );
                return Ok( model );
            }
            catch( Exception e )
            {
                return NotFound();
            }
        }

        [Route( "api/Cursa/{id}/Participants" )]
        [EnableCors( origins: "*", headers: "*", methods: "*" )]
        [HttpGet]
        public IHttpActionResult GetCursaParticipants( int id )
        {
            var service = new CursaServices();
            try
            {
                return Ok( service.getParticipanti( id ) );
            }
            catch( Exception e )
            {
                return BadRequest( e.Message );
            }
        }

        [Route( "api/Cursa/{id_c}/Participants/{id_m}" )]
        [EnableCors( origins: "*", headers: "*", methods: "*" )]
        [HttpPost]
        public IHttpActionResult GetCursaParticipants( int id_c, int id_m )
        {
            var service = new CursaServices();
            try
            {
                service.addParticipant( id_c, id_m );
                return Ok();
            }
            catch( Exception e )
            {
                return BadRequest( e.Message );
            }
        }
    }
}
