import play.api.{Application, GlobalSettings}
import org.squeryl.SessionFactory
import play.api.db.DB
import org.squeryl.adapters.H2Adapter
import org.squeryl.Session

object Global extends GlobalSettings {
  
  override def onStart(application: play.api.Application) {
    SessionFactory.concreteFactory = Some(() =>
      Session.create(DB.getConnection()(application), new H2Adapter) )
  }
  
}