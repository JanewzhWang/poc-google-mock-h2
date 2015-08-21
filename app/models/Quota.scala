package models

import models.format.QuotaJsonFormat
import play.api.libs.json.Format

case class Quota (metric: Option[String], limit: Option[Double], usage: Option[Double])
    
object Quota {
  
  implicit var quotaFormat: Format[Quota] = QuotaJsonFormat
  
}