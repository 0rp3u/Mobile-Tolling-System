package pt.isel.ps.LI61N.g30.server.model.domain

//enum class VehicleTier{
//    A,      //Motos e Triciclos
//    B,      //Carros de passeio
//    C,      //Veiculos de carga acima de 3,5t
//    D,      //Veiculos com mais de 8 passageiros
//    E       //Veiculos com unidade acoplada acima de 6t
//}

enum class VehicleType {
    Classe_1, //veículos com altura inferior a 1,1m.
    Classe_2, //Veículos com 2 eixos com altura superior a 1,1m.
    Classe_3, //Veículos com 3 eixos com altura superior a 1,1m.
    Classe_4, //Veículos com 4 eixos ou mais, com altura superior a 1,1m.
    Classe_5 //Motociclos são considerados Classe 5, beneficiando de um desconto de 30% (excepto Ponte Vasco da Gama).
}