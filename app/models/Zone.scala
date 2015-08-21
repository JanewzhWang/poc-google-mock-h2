package models

import org.squeryl.annotations.Transient
import java.util.Date
import play.api.libs.json._
import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl.ManyToOne
import models.Database._
import scala.annotation.meta.getter
import models.format.ZoneJsonFormat

/**
 * @author jwang7
 */
case class Zone(
    @Transient id: Long,
    creationTimestamp: Option[Date],
    name: Option[String],
    description: Option[String],
    status: Option[String],
    regionId: Option[Long],
    projectId: Option[Long],
    maintenanceWindowsJson: Option[String],
    deprecatedJson: Option[String]
  ) extends KeyedEntity[Long] {
  @(Transient @getter) var maintenanceWindows: Option[List[MaintenanceWindow]] = None
  @(Transient @getter) var deprecated: Option[Deprecated] = None
  lazy val region: ManyToOne[Region] = Database.regionToZones.right(this)
  lazy val project: ManyToOne[Project] = Database.projectToZones.right(this)
}

object Zone {
  
  implicit var zoneFormat: Format[Zone] = ZoneJsonFormat
  
  def findAll(projectName: Option[String]): Option[List[Zone]] = tx {
    val zones = from(zonesTable, projectsTable) { (zt, pt) => 
      where(zt.projectId === pt.id and pt.name === projectName).select(zt) orderBy(zt.id asc)}
    if (zones.isEmpty) None
    else Some(zones.toList)
  }
  
  def find(projectName: Option[String], name: Option[String]): Option[Zone] = tx {
    val zones = from(zonesTable, projectsTable)( (zt, pt) => 
      where(zt.projectId === pt.id and pt.name === projectName and zt.name === name).select(zt))
    if (zones.isEmpty) None
    else Some(zones.single)
  }
  
}

