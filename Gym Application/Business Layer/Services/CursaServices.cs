using Business_Layer.DTO;
using Business_Layer.Mappers;
using DAL.Model;
using DAL.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business_Layer.Services
{
    public class CursaServices
    {
        public CursaDTO addCursa( Cursa cursa )
        {
            using (var uow=new UnitOfWork())
            {
                uow.Repository<Cursa>().Save( cursa );
                uow.Save();
                return new CursaDTO( cursa );
            }
        }

        public CursaDTO deleteCursa( int id )
        {
            using (var uow = new UnitOfWork())
            {
                Cursa delCursa = uow.Repository<Cursa>().GetById( id );

                if( delCursa != null )
                {
                    uow.Repository<Cursa>().Delete( delCursa );
                    uow.Save();
                    return new CursaDTO( delCursa );
                }
                else
                {
                    throw ( new Exception( "Class not found" ) );
                }
                
            }
        }

        public IQueryable<CursaDTO> getAllCursa()
        {
            using (var uow = new UnitOfWork())
            {
                List<CursaDTO> curse = new List<CursaDTO>();
                IEnumerable<Cursa> cursa_all = uow.Repository<Cursa>().findAll();
                foreach( Cursa c in cursa_all )
                {
                    curse.Add( new CursaDTO( c ) );
                }
                return curse.AsQueryable();
            }
        }

        public CursaDTO getByID( int id )
        {
            using( var uow = new UnitOfWork() )
            {
                Cursa c = uow.Repository<Cursa>().GetById( id );
                if( c != null )
                {
                    return new CursaDTO( c );
                }
                else
                {
                    throw ( new Exception( "Class not found" ) );
                }
            }
        }

        public void addParticipant( int Cursa_Id, int Motociclist_Id )
        {
            using( var uow = new UnitOfWork() )
            {
                var repoC = uow.Repository<Cursa>();
                var repoM = uow.Repository<Motociclist>();
                Cursa c = repoC.GetById( Cursa_Id );
                Motociclist m = repoM.GetById( Motociclist_Id );
                if( c != null && m != null )
                {
                    c.Participanti.Add( m );
                    m.Curse.Add( c );
                    repoC.Update( c );
                    repoM.Update( m );
                    uow.Save();
                }
                else
                {
                    throw ( new Exception( "Cursa or Motociclist not found" ) );
                }
            }
        }

        public IEnumerable<MotociclistDTO> getParticipanti( int CursaId )
        {
            using( var uow = new UnitOfWork() )
            {
                Cursa c = uow.Repository<Cursa>().GetById( CursaId );
               
                if( c != null  )
                {
                    List<MotociclistDTO> motos = new List<MotociclistDTO>();
                    foreach( Motociclist m in c.Participanti )
                    {
                        motos.Add( new MotociclistDTO( m ) );
                    }
                    return motos;
                }
                else
                {
                    throw ( new Exception( "Cursa not found" ) );
                }
            }
        }

        public CursaDTO updateCursa( Cursa cursa )
        {
            using( var uow = new UnitOfWork() )
            {
                var repo = uow.Repository<Cursa>();
                Cursa found = repo.GetById( cursa.Id );
                if( found == null )
                {
                    uow.Repository<Cursa>().Save( cursa );
                    uow.Save();
                }
                else
                {
                    found.capacitate = cursa.capacitate;
                    found.nume = cursa.nume;
                    uow.Repository<Cursa>().Update( found );
                    uow.Save();
                }
                return new CursaDTO( cursa );
            }
        }
    }
}
