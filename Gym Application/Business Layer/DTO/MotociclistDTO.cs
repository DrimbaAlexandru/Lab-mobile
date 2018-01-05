using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DAL.Model;

namespace Business_Layer.DTO
{
    public class MotociclistDTO
    {
        public int Id { get; set; }
        public String nume;
        public int capacitate_motor;
        public String echipa;

        public MotociclistDTO()
        {
        }

        public MotociclistDTO(Motociclist m)
        {
            Id = m.Id;
            nume = m.nume;
            capacitate_motor = m.capacitate_motor;
            echipa = m.echipa;
        }

    }
}
