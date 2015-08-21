package models

import org.squeryl.KeyedEntity
import org.squeryl.annotations.Transient
import org.squeryl.PrimitiveTypeMode._
import models.Database.projectsTable
import play.api.libs.json._
import models.Database.tx
import models.format.ProjectJsonFormat

case class Project(
    @Transient id: Long,
    name: Option[String],
    description: Option[String]) extends KeyedEntity[Long]
    
object Project {
  
  implicit var projectFormats: Format[Project] = ProjectJsonFormat
  
  def findAll: Option[List[Project]] = tx {
    val results = from(projectsTable)( project => select(project) orderBy(project.id asc))
    if (results.isEmpty) None
    else Some(results.toList)
  }
  
  def find(name: Option[String]): Option[Project] = tx {
    val results = from(projectsTable)( project => where(project.name === name).select(project)) 
    if (results.isEmpty) None
    else Some(results.single)
  }
  
}