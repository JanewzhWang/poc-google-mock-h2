package models

import java.util.Date
import play.api.libs.json._
import play.api.libs.functional.syntax._
import models.format.DeprecatedJsonFormat

case class Deprecated(
    state: String,
    replacement: Date,
    deprecated: Date,
    obsolete: Date,
    deleted: Date)
    
object Deprecated {
  
  implicit val deprecatedFormat: Format[Deprecated] = DeprecatedJsonFormat
  
  val deprecateds = List(
      Deprecated("replacement", new Date, null, null, null),
      Deprecated("deprecated", null, new Date, null, null),
      Deprecated("obsolete", null, null, new Date, null),
      Deprecated("deleted", null, null, null, new Date))
  
  def find(state: String): Deprecated = {
    deprecateds.find(_.state == state).getOrElse(null)
  }
}