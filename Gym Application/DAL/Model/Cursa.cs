using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL.Model
{
    public partial class Cursa : BaseModel
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }

        public int capacitate { set; get; }

        [InverseProperty("Curse")]
        public virtual ICollection<Motociclist> Participanti { get; set; }

        public Cursa()
        {
            Participanti = new HashSet<Motociclist>();
        }

        
    }
}
