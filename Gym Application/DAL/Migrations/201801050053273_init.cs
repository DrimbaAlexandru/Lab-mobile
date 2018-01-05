namespace DAL.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class init : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Cursas",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        capacitate = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Motociclists",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        nume = c.String(nullable: false, maxLength: 255),
                        capacitate_motor = c.Int(nullable: false),
                        echipa = c.String(nullable: false, maxLength: 255),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Users",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Username = c.String(nullable: false, maxLength: 40),
                        Name = c.String(maxLength: 200),
                        PasswordSalt = c.Binary(nullable: false),
                        PasswordHash = c.Binary(nullable: false),
                        Email = c.String(nullable: false, maxLength: 255),
                    })
                .PrimaryKey(t => t.Id)
                .Index(t => t.Username, unique: true);
            
            CreateTable(
                "dbo.Participari",
                c => new
                    {
                        CursaId = c.Int(nullable: false),
                        MotoId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.CursaId, t.MotoId })
                .ForeignKey("dbo.Cursas", t => t.CursaId, cascadeDelete: true)
                .ForeignKey("dbo.Motociclists", t => t.MotoId, cascadeDelete: true)
                .Index(t => t.CursaId)
                .Index(t => t.MotoId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Participari", "MotoId", "dbo.Motociclists");
            DropForeignKey("dbo.Participari", "CursaId", "dbo.Cursas");
            DropIndex("dbo.Participari", new[] { "MotoId" });
            DropIndex("dbo.Participari", new[] { "CursaId" });
            DropIndex("dbo.Users", new[] { "Username" });
            DropTable("dbo.Participari");
            DropTable("dbo.Users");
            DropTable("dbo.Motociclists");
            DropTable("dbo.Cursas");
        }
    }
}
