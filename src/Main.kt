fun main() {
    // Basisproduktklasse
    open class Produkt(val name: String, val preis: Double, var bewertung: Double) {
        open fun getProduktDetails(): String {
            return "$name - Preis: €$preis, Bewertung: $bewertung/5"
        }
    }

    // Modeprodukt als Unterklasse von Produkt
    class Mode(name: String, preis: Double, bewertung: Double, val typ: String) :
        Produkt(name, preis, bewertung) {
        override fun getProduktDetails(): String {
            return "Modeartikel ($typ): $name - Preis: €$preis, Bewertung: $bewertung/5"
        }
    }

    // Modeschmuckprodukt als Unterklasse von Produkt
    class Schmuck(name: String, preis: Double, bewertung: Double, val marke: String) :
        Produkt(name, preis, bewertung) {
        override fun getProduktDetails(): String {
            return "Modeschmuck ($marke): $name - Preis: €$preis, Bewertung: $bewertung/5"
        }
    }

    // Basis Account-Klasse
    open class Account(val benutzername: String, val passwort: String, val alter: Int) {
        val id: Int = benutzername.hashCode()

        open fun istErlaubt(): Boolean {
            return alter >= 12
        }

        open fun login(passwortInput: String): Boolean {
            return passwortInput == passwort
        }
    }

    // Kundenaccount, der von Account erbt
    class KundenAccount(benutzername: String, passwort: String, alter: Int) :
        Account(benutzername, passwort, alter) {
        val zahlungsmethode: String = "Kreditkarte"
        val warenkorb: MutableList<Produkt> = mutableListOf()

        fun fuegeZumWarenkorbHinzu(produkt: Produkt) {
            warenkorb.add(produkt)
        }

        fun gesamtpreis(): Double {
            return warenkorb.sumOf { it.preis }
        }

        fun bewertungAbgabe(produkt: Produkt, bewertung: Double) {
            produkt.bewertung = bewertung
        }

        override fun istErlaubt(): Boolean {
            return alter >= 12
        }
    }

    // Betreiberaccount, der von Account erbt
    class BetreiberAccount(benutzername: String, passwort: String, alter: Int) :
        Account(benutzername, passwort, alter) {
        val produkte: MutableList<Produkt> = mutableListOf()

        fun produktHinzufuegen(produkt: Produkt) {
            produkte.add(produkt)
        }

        fun produktEntfernen(produkt: Produkt) {
            produkte.remove(produkt)
        }

        fun nachbestellen(produkt: Produkt) {
            produkte.add(produkt)
        }
    }

    // Store-Klasse für das Management von Produkten und Accounts
    class Store {
        val produkte: MutableList<Produkt> = mutableListOf()
        val accounts: MutableList<Account> = mutableListOf()

        init {
            // Initialisierung von Produkten
            produkte.add(Mode("T-Shirt", 24.99, 4.5, "T-Shirt"))
            produkte.add(Mode("Jacke", 149.99, 3.8, "Jacke"))
            produkte.add(Schmuck("Halskette", 89.99, 4.2, "Sun"))
            produkte.add(Schmuck("Ring", 79.99, 4.3, "Love"))
        }

        // Methode, um Produkte nach Preisen zu sortieren
        fun sortierenNachPreisen() {
            produkte.sortBy { it.preis }
        }

        // Methode, um Produkte alphabetisch zu sortieren
        fun sortiereAlphabetisch() {
            produkte.sortBy { it.name }
        }

        // Filterfunktion nach Katalog
        fun filterNachKatalog(kategorie: String): List<Produkt> {
            return produkte.filter {
                when (kategorie) {
                    "Mode" -> it is Mode
                    "Schmuck" -> it is Schmuck
                    else -> false
                }
            }
        }

        // Benutzer anlegen
        fun benutzerAnlegen(account: Account) {
            accounts.add(account)
        }

        // Login-Funktion
        fun login(benutzername: String, passwort: String): Account? {
            return accounts.find {
                it.benutzername == benutzername && it.login(passwort)
            }
        }
    }

    // Hauptprogramm
    val store = Store()

    val betreiber = BetreiberAccount("Lena", "Lena123", 34)
    val kunde = KundenAccount("Tara", "passwort123", 26)

    store.benutzerAnlegen(betreiber)
    store.benutzerAnlegen(kunde)

    // Login als Betreiber
    val eingeloggterBetreiber = store.login("Lena", "Lena123") as? BetreiberAccount
    if (eingeloggterBetreiber != null) {
        // Produkte hinzufügen
        val neuesProdukt = Schmuck("Armband", 79.99, 4.8, "Sun1")
        eingeloggterBetreiber.produktHinzufuegen(neuesProdukt)
        println("Produkt hinzugefügt: ${neuesProdukt.getProduktDetails()}")
    }

    // Login als Kunde
    val eingeloggterKunde = store.login("Tara", "passwort123") as? KundenAccount
    if (eingeloggterKunde != null) {
        // Produkte zum Warenkorb hinzufügen
        eingeloggterKunde.fuegeZumWarenkorbHinzu(store.produkte[0])
        println("Gesamtpreis im Warenkorb: €${eingeloggterKunde.gesamtpreis()}")
    }

    // Produkte nach Preisen sortieren und anzeigen
    store.sortierenNachPreisen()
    println("\nProdukte nach Preis sortieren:")
    store.produkte.forEach { println(it.getProduktDetails()) }

    // Produkte nach Kategorie filtern und anzeigen
    println("\nGefilterte Produkte - Kategorie: Mode")
    val modeProdukt = store.filterNachKatalog("Mode")
    modeProdukt.forEach { println(it.getProduktDetails()) }
}
