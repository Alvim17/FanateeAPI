package models

import scala.io.Source
import scala.collection.mutable.Map

class Universo(nomeArquivo: String) {
  
  val ligacoesPlanetas : Map[String, List[(Int, String)]] = Map()  
  var dadosArquivo = new Array[String](3)  

  for(line <- Source.fromFile(nomeArquivo).getLines()){
    dadosArquivo = line.split(",") 

    if(!ligacoesPlanetas.contains(dadosArquivo(0))){
      ligacoesPlanetas += (dadosArquivo(0) -> List((dadosArquivo(2).toInt, dadosArquivo(1))))
    }
    else {
      ligacoesPlanetas(dadosArquivo(0)) = (dadosArquivo(2).toInt, dadosArquivo(1)) :: ligacoesPlanetas(dadosArquivo(0))
    }
    if(!ligacoesPlanetas.contains(dadosArquivo(1)))
      ligacoesPlanetas += (dadosArquivo(1) -> Nil)
  }  

  def melhorTrajeto(fronteira: List[(Int, List[String])], planetaDestino: String, planetasVisitados: Set[String]): (Int, List[String]) = fronteira match {
    case (custoAcumulado, trajeto) :: fronteira_rest => trajeto match {
      case planeta :: trajeto_rest =>
        if (planeta == planetaDestino) (custoAcumulado, trajeto.reverse)
        else {
          if(!ligacoesPlanetas.contains(planeta)) return (0, List())
          val caminhos = ligacoesPlanetas(planeta).flatMap {
            case (custo, planeta) => 
              if (!planetasVisitados.contains(planeta)) List((custoAcumulado + custo, planeta :: trajeto)) 
              else Nil
          }
          val fronteiraOrdenada = (caminhos ++ fronteira_rest).sortWith {
            case ((custo1, _), (custo2, _)) => custo1 < custo2
          }
          melhorTrajeto(fronteiraOrdenada, planetaDestino, planetasVisitados + planeta)
        }
    }
    case Nil => (0, List())
  }  
}