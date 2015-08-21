package models

import java.util.Date
import play.api.libs.json._
import models.format.MaintenanceWindowJsonFormat

case class MaintenanceWindow(
    name: Option[String],
    description: Option[String],
    beginTime: Option[Date],
    endTime: Option[Date])
    
object MaintenanceWindow {
  
  implicit var maintenanceWindowFormat: Format[MaintenanceWindow] = MaintenanceWindowJsonFormat
  
}