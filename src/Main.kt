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
    // Kundenaccount, der von Account erbt
    class KundenAccount(benutzername: String, passwort: String, alter: Int) :
            Account(benutzername, passwort, alter){
                val zahlungsmethode: String = "Kreditkarte"
        val warenkorb: MutableList<Produkt> = mutableListOf()
        fun fuegeZumWarenkorbHinzu(produkt: Produkt){
            warenkorb.add(produkt)
        }
        fun gesamtpreis(): Double
        return warenkorb.sumOf{ it.preis }
            }
    fun bewertungAbgabe(produkt: Produkt, bewertung: Double){produkt.bewertung = bewertung
    }
    override fun istErlaubt(): Boolean{
        return alter >=12
    }
}
//Betreiberaccount, der von Account erbt
class BetreiberAccount(benutzername: String, passwort: String, alter: Int):
        Account(benutzername, passwort, alter){
            val produkt: MutableList<Produkt> = mutableListOf()

    fun produktHinzufuegen(produkt: Produkt){
        produkt.add(produkt)
    }
    fun produktHinzufuegen(produkt: Produkt){
        produkt.remove(produkt)
    }

    fun nachbestellen(produkt: Produkt){
        produkt.add(produkt)
    }
        }

//Store-Klasse für das Management von Produkten und Accounts
class Store{
    val produkte: MutableList<Produkt> = mutableListOf()
    val accounts: MutableList<Account> = mutableListOf()

    init {
        //Initalisierung von Produkten
        produkte.add(Mode("T-Shirt", 24.99, 4.5, "T-Shirt"))
        produkte.add(Mode("Jacke", 149.99, 3.8, "Jacke"))
        produkte.add(Schmuck("Halskette", 89.99, 4.2,"Sun"))
        produkte.add(Schmuck("Ring", 79.99, 4.3, "Love"))
    }
    //Methode,um Produkte nach Preise zu sortiern
    fun sortierenNachPreise(){
        produkte.sortBy{ it.preis }
    }
   //Methode, um Produkte alphabetisch zu sortieren
   fun sortiereAphabetisch(){
       produkte.sortBy { it.name }
   }

}