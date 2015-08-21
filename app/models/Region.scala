package models

import java.util.Date
import org.squeryl.annotations.Transient
import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl.{ManyToOne, OneToMany}
import play.api.libs.json._
import models.Database.tx
import scala.annotation.meta.getter
import models.format.RegionJsonFormat
import models.Database._

case class Region(
    @Transient id: Long,
    creationTimestamp: Option[Date],
    name: Option[String],
    description: Option[String],
    status: Option[String],
    quotasJson: Option[String],
    deprecatedJson: Option[String],
    projectId: Option[Long]) extends KeyedEntity[Long] {
  @(Transient @getter) var quotas: Option[List[Quota]] = None
  @(Transient @getter) var deprecated: Option[Deprecated] = None
  lazy val zones: OneToMany[Zone] = Database.regionToZones.left(this)
  lazy val project: ManyToOne[Project] = Database.projectToRegions.right(this)
}

object Region {
  
  implicit var regionFormat: Format[Region] = RegionJsonFormat
  
  def findAll(projectName: Option[String]): Option[List[Region]] = tx {
    val results = from(regionsTable, projectsTable)((rt, pt) =>
       where(rt.projectId === pt.id and pt.name === projectName).select(rt) orderBy(rt.id asc))
    if (results.isEmpty) None
    else Some(results.toList)
  }
  
  def find(projectName: Option[String], name: Option[String]): Option[Region] = tx {
    val results = from(regionsTable, projectsTable)((rt, pt) => 
      where(rt.projectId === pt.id and pt.name === projectName and rt.name === name).select(rt))
    if (results.isEmpty) None
    else Some(results.single)
  }
  
}