package controllers

import models.Universo
import javax.inject._
import play.api._
import play.api.mvc._
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
 
  def index() = Action { 
    implicit request: Request[AnyContent] => Ok(views.html.index())
  }

  def melhorTrajeto(origem: String, destino: String) = Action { implicit request => 
    val universo = new Universo("exemplo.csv")
    Ok(universo.melhorTrajeto(List((0, List(origem))), destino, Set()).toString())
  } 

  def cadastrarRota() = Action { implicit request => 
    val content = request.body.asText.get
    if(content.split(",").length != 3) 
      BadRequest
    else {
      Files.write(Paths.get("exemplo.csv"), ("\n"+content.toString()).getBytes(), StandardOpenOption.APPEND)
      Ok(content) 
    }  
  }
}
