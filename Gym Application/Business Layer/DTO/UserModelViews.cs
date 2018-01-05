using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace Business_Layer.DTO
{
    public class RegistrationModelView
    {
        public string Username { get; set; }
        public string Name { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
    }

    public class BaseUserModelView
    {
        public int Id { get; set; }
        public string Username { get; set; }
        public string Name { get; set; }
    }

    public class LoginModelView
    {
        public string Username { get; set; }
        public string Password { get; set; }
    }
}
