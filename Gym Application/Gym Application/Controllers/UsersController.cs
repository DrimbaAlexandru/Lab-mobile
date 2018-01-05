using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

using Business_Layer.DTO;
using Business_Layer.Services;
using System.Web.Http.Cors;
using DAL.Model;
using Business_Layer.Mappers;
using DAL.Repository;

namespace Gym_Application.Controllers
{
    public class UsersController : ApiController
    {
        //creeaza si salveaza un nou account
        [Route( "api/users/" )]
        [HttpPost]
        [EnableCors(origins: "*", headers: "*", methods: "*")]
        public IHttpActionResult Post([FromBody]RegistrationModelView account)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var service = new UserServices();
                    BaseUserModelView model = service.CreateAccount(account);
                    return Ok(model);
                }
                else
                    return BadRequest();
            }
            catch (Exception e)
            {
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.NotAcceptable, e.Message));
            }
        }

        //returns the user with username & password BaseAccountModelView
        [Route("api/users/login")]
        [EnableCors(origins: "*", headers: "*", methods: "*")]
        [HttpPost]
        public IHttpActionResult Login([FromBody]LoginModelView model)
        {
            try
            {
                var service = new UserServices();
                BaseUserModelView account = service.GetOneAccountWithPassword(model);
                return Ok(account);
            }
            catch (Exception)
            {
                return NotFound();
            }
        }

        [Route( "api/users" )]
        [EnableCors( origins: "*", headers: "*", methods: "*" )]
        [HttpGet]
        public IEnumerable<BaseUserModelView> GetUsers()
        {
            var service = new UserServices();
            List<BaseUserModelView> users = new List<BaseUserModelView>();
            foreach(User u in service.GetAll())
            {
                users.Add( UserMapper.UserToBaseUserMV( u ) );
            }
            return users;

        }
                  
    }
       
}
