namespace DAL.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class cursecunume : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Cursas", "nume", c => c.String(nullable: false, maxLength: 255));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Cursas", "nume");
        }
    }
}
