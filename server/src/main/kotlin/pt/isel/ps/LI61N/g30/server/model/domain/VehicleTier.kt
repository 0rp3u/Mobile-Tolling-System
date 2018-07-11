package pt.isel.ps.LI61N.g30.server.model.domain

//enum class VehicleTier{
//    A,      //Motos e Triciclos
//    B,      //Carros de passeio
//    C,      //Veiculos de carga acima de 3,5t
//    D,      //Veiculos com mais de 8 passageiros
//    E       //Veiculos com unidade acoplada acima de 6t
//}

enum class VehicleType(val type: Int){
    TYPE1(1),
    TYPE2(2),
    TYPE3(3),
    TYPE4(4),
    TYPE5(5)
}