package models.format

import models.Zone
import models.Database.tx
import OWritesOps._
import java.util.Date
import java.text.SimpleDateFormat
import play.api.libs.json._
import models.{MaintenanceWindow, Deprecated}

object ZoneJsonFormat extends Format[Zone] {
  
  override def reads(json: JsValue): JsResult[Zone] = tx {
    Json.reads[Zone].reads(json)
  }
  
  override def writes(zone: Zone): JsValue = tx {
    val customizedZoneWrites = Json.writes[Zone]
      .addField("selfLink", "https://www.googleapis.com/compute/v1/projects/" + 
          zone.project.toList(0).name.get + "/zones/" + zone.name.get)
      .addField("kind", "compute#zone")
      .addField("region", zone.region.toList(0).name.get)
      .updateField("creationTimestamp", 
          new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(zone.creationTimestamp.get))
      .removeField("projectId")
      .removeField("regionId")
    if (zone.maintenanceWindowsJson.isDefined) {
      customizedZoneWrites.addField("maintenanceWindows", zone.maintenanceWindowsJson.get).removeField("maintenanceWindowsJson")
    } else if (zone.maintenanceWindows.isDefined) {
      var mwJsonArr: Option[JsArray] = None
      zone.maintenanceWindows.get.foreach { maintenanceWindow => 
        mwJsonArr match {
          case Some(mw) => mwJsonArr = Some(mw ++ Json.arr(MaintenanceWindow.maintenanceWindowFormat.writes(maintenanceWindow)))
          case None => mwJsonArr = Some(Json.arr(MaintenanceWindow.maintenanceWindowFormat.writes(maintenanceWindow)))
        }
      }
      customizedZoneWrites.addField("maintenanceWindows", mwJsonArr.get).removeField("maintenanceWindowsJson")
    }
    if (zone.deprecatedJson.isDefined) {
      customizedZoneWrites.addField("deprecated", zone.deprecatedJson.get).removeField("deprecatedJson")
        .removeField("deprecatedJson")
    } else if (zone.deprecated.isDefined) {
      customizedZoneWrites.addField("deprecated", Deprecated.deprecatedFormat.writes(zone.deprecated.get))
        .removeField("deprecatedJson")
    }
    customizedZoneWrites.writes(zone)
  }
  
}