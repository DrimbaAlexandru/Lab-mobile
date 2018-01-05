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
    public class MotociclistServices
    {
        public MotociclistDTO addMotociclist( Motociclist motociclist )
        {
            using( var uow = new UnitOfWork() )
            {
                uow.Repository<Motociclist>().Save( motociclist );
                uow.Save();
                return new MotociclistDTO( motociclist );
            }
        }

        public MotociclistDTO deleteMotociclist( int id )
        {
            using( var uow = new UnitOfWork() )
            {
                Motociclist delMotociclist = uow.Repository<Motociclist>().GetById( id );

                if( delMotociclist != null )
                {
                    uow.Repository<Motociclist>().Delete( delMotociclist );
                    uow.Save();
                    return new MotociclistDTO( delMotociclist );
                }
                else
                {
                    throw ( new Exception( "Class not found" ) );
                }

            }
        }

        public IQueryable<MotociclistDTO> getAllMotociclist()
        {
            using( var uow = new UnitOfWork() )
            {
                List<MotociclistDTO> curse = new List<MotociclistDTO>();
                IEnumerable<Motociclist> motociclist_all = uow.Repository<Motociclist>().findAll();
                foreach( Motociclist c in motociclist_all )
                {
                    curse.Add( new MotociclistDTO( c ) );
                }
                return curse.AsQueryable();
            }
        }

        public MotociclistDTO getByID( int id )
        {
            using( var uow = new UnitOfWork() )
            {
                Motociclist c = uow.Repository<Motociclist>().GetById( id );
                if( c != null )
                {
                    return new MotociclistDTO( c );
                }
                else
                {
                    throw ( new Exception( "Class not found" ) );
                }
            }
        }
    }
}
