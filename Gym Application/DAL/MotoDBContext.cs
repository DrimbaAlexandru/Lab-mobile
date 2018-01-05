namespace DAL
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;
    using DAL.Model;

    public partial class MotoDBContext : DbContext
    {
        public MotoDBContext()
            : base("name=MotoDBContext")
        {
        }

        public virtual DbSet<Cursa> Curse { get; set; }
        public virtual DbSet<Motociclist> Motociclisti { get; set; }
        public virtual DbSet<User> Users { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {

            modelBuilder.Entity<Cursa>()
                .HasMany( e => e.Participanti )
                .WithMany( e => e.Curse )
                .Map( m => m.ToTable( "Participari" ).MapLeftKey( "CursaId" ).MapRightKey( "MotoId" ) );
            
        }
    }
}
