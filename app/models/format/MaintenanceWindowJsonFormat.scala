package models.format

import java.util.Date
import java.text.SimpleDateFormat
import play.api.libs.json._
import models.MaintenanceWindow
import OWritesOps._

object MaintenanceWindowJsonFormat extends Format[MaintenanceWindow] {
  
  override def reads(json: JsValue): JsResult[MaintenanceWindow] = 
    Json.reads[MaintenanceWindow].reads(json)
  
  override def writes(maintenanceWindow: MaintenanceWindow): JsValue = {
    val customizedMaintenanceWindowWrites = Json.writes[MaintenanceWindow].updateField("creationTimestamp", 
          new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(maintenanceWindow.beginTime))
    customizedMaintenanceWindowWrites.writes(maintenanceWindow)
  }
  
}