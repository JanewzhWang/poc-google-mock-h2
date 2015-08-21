package models.format

import play.api.libs.json._
import java.text.SimpleDateFormat
import models.Database.tx
import scala.math.BigDecimal.long2bigDecimal
import models.{Region, Deprecated, Quota}
import OWritesOps._

object RegionJsonFormat extends Format[Region] {
  
  def reads(json: JsValue): JsResult[Region] = Json.reads[Region].reads(json)
  
  def writes(region: Region): JsValue = tx {
    val customizedRegionWrites = Json.writes[Region]
      .addField("selfLink", "https://www.googleapis.com/compute/v1/projects/" + 
          region.project.toList(0).name.get + "/regions/" + region.name.get)
      .addField("kind", "compute#region")
      .updateField("creationTimestamp", 
          new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(region.creationTimestamp.get))
      .addField("zones", region.zones)
    if (region.deprecatedJson.isDefined) {
      customizedRegionWrites.addField("deprecated", JsString(region.deprecatedJson.get)).removeField("deprecatedJson")
    } else if (region.deprecated.isDefined) {
      customizedRegionWrites.addField("deprecated", Deprecated.deprecatedFormat.writes(region.deprecated.get))
        .removeField("deprecatedJson")
    }
    if (region.quotasJson.isDefined) {
      customizedRegionWrites.addField("quotas", JsString(region.quotasJson.get)).removeField("quotasJson")
    } else if (region.quotas.isDefined) {
      var quotasJsonArr: Option[JsArray] = None
      region.quotas.get.foreach { quota => 
        quotasJsonArr match {
          case Some(qJsonArr) => quotasJsonArr = Some(qJsonArr ++ Json.arr(Quota.quotaFormat.writes(quota)))
          case None => quotasJsonArr = Some(Json.arr(Quota.quotaFormat.writes(quota)))
        }
      }
      customizedRegionWrites.addField("quotas", quotasJsonArr.get).removeField("quotasJson")
    }
    customizedRegionWrites.writes(region)
  }
  
}