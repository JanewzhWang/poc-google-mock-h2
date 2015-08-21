package controllers

import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.libs.json._
import play.api.libs.functional.syntax._
import models.Region
import play.api.libs.json.Json.toJsFieldJsValueWrapper

class RegionsController extends Controller {
  
  def get(project: String, region: String) = Action { request =>
    if (project != null && region != null &&
        !project.isEmpty && !region.isEmpty) {
      Region.find(Some(project), Some(region)) match {
        case Some(region) => Ok(Json.toJson(region))
        case None => BadRequest("cannot find the region (project='" + project + "', region='" + region + "')")
      }
    } else {
      BadRequest("find region failed for invalid paramter (project='" + project + "', region='" + region + "')")
    }
  }
  
  /*
   * TODO: not consider filter, maxResults, pageToken yet
   */
  def list(project: String) = Action {
    if (project != null && !project.isEmpty) {
      Region.findAll(Some(project)) match { 
        case Some(regions) => Ok(Json.obj(
          "kind" -> "compute#regionList",
          "selfLink" -> JsString("https://www.googleapis.com/compute/v1/projects/" + project + "/regions"),
          "id" -> JsString("projects/" + project + "/regions"),
          "items" -> Json.toJson(regions)))
        case None => BadRequest("no regions found for (project='" + project + "')")
      }
    } else {
      BadRequest("list regions failed for invalid parameter (project='" + project + "')")
    }
  }
}