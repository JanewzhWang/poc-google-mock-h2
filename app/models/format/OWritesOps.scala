package models.format

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Writes._

class OWritesOps[A](writes: OWrites[A]) {
  
  def addField[T: Writes](fieldName: String, field: A => T): OWrites[A] = 
    (writes ~ (__ \ fieldName).write[T])((a: A) => (a, field(a)))
  
  def addField[T: Writes](fieldName: String, value: T): OWrites[A] = 
    (writes ~ (__ \ fieldName).write[T])((a: A) => (a, value))

  def updateField[T: Writes](fieldName: String, newValue: T): OWrites[A] = {
    removeField(fieldName)
    addField(fieldName, newValue)
  }
    
  def removeField(fieldName: String): OWrites[A] = OWrites { a: A =>
    val transformer = (__ \ fieldName).json.prune
    Json.toJson(a)(writes).validate(transformer).get
  }
}

object OWritesOps {
  implicit def from[A](writes: OWrites[A]): OWritesOps[A] = new OWritesOps(writes)
}