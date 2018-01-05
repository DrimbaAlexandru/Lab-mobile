using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DAL.Model;


namespace Business_Layer.DTO
{
    public class CursaDTO
    {        
        public int Id { get; set; }
        public int capacitate { set; get; }
        public int loc_ramas { set; get; }

        public CursaDTO()
        {
        }

        public CursaDTO( Cursa c )
        {
            Id = c.Id;
            capacitate = c.capacitate;
            loc_ramas = capacitate - c.Participanti.Count;
        }
    }
}
