namespace DAL.Model
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class User : BaseModel
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public User()
        {
        }

        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }

        [Required]
        [StringLength(40)]
        [Index(IsUnique = true)]
        public string Username { get; set; }

        [StringLength(200)]
        public string Name { get; set; }

        [Required]
        public byte[] PasswordSalt { get; set; }

        [Required]
        public byte[] PasswordHash { get; set; }

        [Required]
        [StringLength(255)]
        public string Email { get; set; }

    }
}
