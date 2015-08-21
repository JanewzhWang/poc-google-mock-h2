package models.format

import models.Deprecated
import play.api.libs.json._

object DeprecatedJsonFormat extends Format[Deprecated] {
  
  override def reads(json: JsValue): JsResult[Deprecated] = Json.reads[Deprecated].reads(json)
  
  override def writes(deprecated: Deprecated): JsValue = Json.writes[Deprecated].writes(deprecated)
  
}