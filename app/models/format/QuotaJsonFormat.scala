package models.format

import models.Quota
import play.api.libs.json._

object QuotaJsonFormat extends Format[Quota] {
  
  override def reads(json: JsValue): JsResult[Quota] = Json.reads[Quota].reads(json)
  
  override def writes(quota: Quota): JsValue = Json.writes[Quota].writes(quota)
  
}