package models

import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._
import java.sql.DriverManager
import org.squeryl.adapters.H2Adapter
import org.squeryl.Session

/**
 * @author jwang7
 */
object Database extends Schema {
  
  /*
   * region and zone
   */
  val projectsTable = table[Project]("projects")
  val zonesTable = table[Zone]("zones")
  val regionsTable = table[Region]("regions")
 
  on(projectsTable) { p => declare {
    p.id is (autoIncremented)
  }}
  on(zonesTable) { p => declare {
    p.id is (autoIncremented)
  }}
  on(regionsTable) { p => declare {
    p.id is (autoIncremented)
  }}
  
  val regionToZones = oneToManyRelation(regionsTable, zonesTable).via((r, z) => r.id === z.regionId)
  val projectToZones = oneToManyRelation(projectsTable, zonesTable).via((p, z) => p.id === z.projectId)
  val projectToRegions = oneToManyRelation(projectsTable, regionsTable).via((p, r) => p.id === r.projectId)

  private def init = {
    import org.squeryl.SessionFactory
    Class.forName("org.h2.Driver")
    if(SessionFactory.concreteFactory.isEmpty) {
      SessionFactory.concreteFactory = Some(() =>
        Session.create(
          DriverManager.getConnection("jdbc:h2:mem://localhost/~/default", "sa", "123"),
          new H2Adapter
        )
      )
    }
  }
  
  def tx[A](a: =>A): A = {
    init
    inTransaction(a)
  }
}