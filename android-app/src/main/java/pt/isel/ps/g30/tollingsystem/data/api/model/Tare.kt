package pt.isel.ps.g30.tollingsystem.data.api.model

enum class Tare {

    Classe_1, //veículos com altura inferior a 1,1m.
    Classe_2, //Veículos com 2 eixos com altura superior a 1,1m.
    Classe_3, //Veículos com 3 eixos com altura superior a 1,1m.
    Classe_4, //Veículos com 4 eixos ou mais, com altura superior a 1,1m.
    Classe_5 //Motociclos são considerados Classe 5, beneficiando de um desconto de 30% (excepto Ponte Vasco da Gama).

}