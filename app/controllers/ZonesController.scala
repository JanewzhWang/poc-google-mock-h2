package controllers

import models.Zone
import play.api.mvc.{Action, Controller}
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Json.toJsFieldJsValueWrapper

class ZonesController extends Controller {
  
  def get(project: String, zone: String) = Action { request =>
    if (project != null && zone != null &&
        !project.isEmpty && !zone.isEmpty) {
      Zone.find(Some(project), Some(zone)) match {
        case Some(zone) => 
          println(Json.toJson(zone)) 
          Ok(Json.toJson(zone))
        case None => BadRequest("cannot find the zone (project='" + project + "', zone='" + zone + "')")
      }
    } else {
      BadRequest("find zone failed for invalid paramter (project='" + project + "', zone='" + zone + "')")
    }
  }
  
  /*
   * TODO: not consider filter, maxResults, pageToken yet
   */
  def list(project: String) = Action {
    if (project != null && !project.isEmpty) {
      Zone.findAll(Some(project)) match { 
        case Some(zones) => Ok(Json.obj(
              "kind" -> "compute#zoneList",
              "selfLink" -> JsString("https://www.googleapis.com/compute/v1/projects/" + project + "/zones"), 
              "id" -> JsString("projects/" + project + "/zones"),
              "items" -> Json.toJson(zones)))
        case None => BadRequest("no zones found for (project='" + project + "')")
      }
    } else {
      BadRequest("list zones failed for invalid parameter (project='" + project + "')")
    }
  }
}