package models.format

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import models.Project

object ProjectJsonFormat extends Format[Project] {

  override def reads(json: JsValue): JsResult[Project] = {
    Json.reads[Project].reads(json)
  }
      
  override def writes(project: Project): JsValue = {
    Json.writes[Project].writes(project)
  }
  
}