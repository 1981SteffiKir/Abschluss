fun main() {
    //Basisproduktklasse
    open class Produkt (val name: String, val preis: Double,var bewertung: Double){
        open fun getProduktDetais():String{
            return"$ name - Preis:€$preis, Bewretung:$bewertung/5"
        }
    }
    Modeprodukt als Unterklasse von Produkten
    class Mode (name: String, preis: Double, bewertung: Double, val typ: String ) :
            Produkt(name, preis, bewertung){
        override fun getProduktDetais(): String {
            return "Modeartikel ($typ):$name - Preis: €$preis,Bewertung: $bewertung/5"
        }
}
    Modeschmuckprodukt als Unterklasse von Produkt
    class Schmuck (name: String, preis: Double, bewertung:Double, val marke: String):
    Produkt(name, preis, bewertung){
        override fun getProduktDetais(): String {
            return "Modeschmuck ($marke): $name - Preis: "$preis, Bewertung:$bewertung/5
        }
}
    //Basis Account - Klasse
    open class Account(val benutzername: String, val passwort: String, val alter:Int){
    val id: Int = benutzername.hashCode()

        open fun istErlaubt(): Boolean{
            return alter >=12 }
        open fun login(passwortInput: String): Boolean{
        return passwortInput==passwort
        }
    }
    //
}