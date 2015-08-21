package controllers

import play.api.mvc.{Controller, Action}
import models.Project
import play.api.libs.json.Json

class ProjectController extends Controller {
  
  def get(project: String) = Action { request =>
    if (project != null && !project.isEmpty) {
      Project.find(Some(project)) match {
        case Some(result) => Ok(Json.toJson(result))
        case None => BadRequest("cannot find project with name '" + project + "'")
      }
    } else {
      BadRequest("find project failed for invalid parameter (project='" + project + "')")
    }
  }
  
  def list = Action {
    Project.findAll match {
      case Some(projects) => Ok(Json.toJson(projects))
      case None => BadRequest("find all projects failed")
    }
  }
  
}