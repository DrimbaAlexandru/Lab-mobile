using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL.Model
{
    public partial class Motociclist : BaseModel
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }

        [Required]
        [StringLength( 255 )]
        public String nume{ get; set; }

        public int capacitate_motor{ get; set; }

        [Required]
        [StringLength( 255 )]
        public String echipa{ get; set; }

        [InverseProperty( "Participanti" )]
        public virtual ICollection<Cursa> Curse{ get; set; }

        public Motociclist()
        {
            Curse = new HashSet<Cursa>();
        }
    }
}
