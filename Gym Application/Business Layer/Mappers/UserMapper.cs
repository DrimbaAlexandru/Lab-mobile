using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Business_Layer.DTO;
using DAL.Model;

namespace Business_Layer.Mappers
{
    public class UserMapper
    {
        public static User RegistrationMVToUser(RegistrationModelView model)
        {
            User user = new User();
            user.Username = model.Username;
            user.Name = model.Name;
            user.Email = model.Email;
            return user;
        }

        public static BaseUserModelView UserToBaseUserMV( User user )
        {
            BaseUserModelView model = new BaseUserModelView();
            model.Id = user.Id;
            model.Username = user.Username;
            model.Name = user.Name;
            return model;
        }
    }
}
