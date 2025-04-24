// -------------------- Klassen --------------------

open class Produkt(val name: String, val preis: Double, var bewertung: Double) {
    open fun getProduktDetails(): String {
        return "$name - Preis: €$preis, Bewertung: $bewertung/5"
    }
}

class Mode(name: String, preis: Double, bewertung: Double, val typ: String) :
    Produkt(name, preis, bewertung) {
    override fun getProduktDetails(): String {
        return "Modeartikel ($typ): $name - Preis: €$preis, Bewertung: $bewertung/5"
    }
}

class Schmuck(name: String, preis: Double, bewertung: Double, val marke: String) :
    Produkt(name, preis, bewertung) {
    override fun getProduktDetails(): String {
        return "Modeschmuck ($marke): $name - Preis: €$preis, Bewertung: $bewertung/5"
    }
}

open class Account(val benutzername: String, private var passwort: String, val alter: Int) {
    val id: Int = benutzername.hashCode()

    open fun istErlaubt(): Boolean {
        return alter >= 12
    }

    open fun login(passwortInput: String): Boolean {
        return passwortInput == passwort
    }

    fun passwortAendern(neuesPasswort: String) {
        passwort = neuesPasswort
    }
}

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
}

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

class Store {
    val produkte: MutableList<Produkt> = mutableListOf()
    val accounts: MutableList<Account> = mutableListOf()

    init {
        produkte.add(Mode("T-Shirt", 24.99, 4.5, "Oberteil"))
        produkte.add(Mode("Jacke", 149.99, 3.8, "Jacke"))
        produkte.add(Schmuck("Halskette", 89.99, 4.2, "Sun"))
        produkte.add(Schmuck("Ring", 79.99, 4.3, "Love"))
    }

    fun sortierenNachPreisen() {
        produkte.sortBy { it.preis }
    }

    fun sortiereAlphabetisch() {
        produkte.sortBy { it.name }
    }

    fun filterNachKatalog(kategorie: String): List<Produkt> {
        return produkte.filter {
            when (kategorie.lowercase()) {
                "mode" -> it is Mode
                "schmuck" -> it is Schmuck
                else -> false
            }
        }
    }

    fun benutzerAnlegen(account: Account) {
        accounts.add(account)
    }

    fun login(benutzername: String, passwort: String): Account? {
        return accounts.find {
            it.benutzername == benutzername && it.login(passwort)
        }
    }
}

// -------------------- Hauptprogramm --------------------

fun main() {
    val store = Store()

    val betreiber = BetreiberAccount("Lena", "Lena123", 34)
    val kunde = KundenAccount("Tara", "passwort123", 26)

    store.benutzerAnlegen(betreiber)
    store.benutzerAnlegen(kunde)

    var eingeloggterAccount: Account? = null

    while (true) {
        println("\n--- Hauptmenü ---")
        println("1. Einloggen")
        println("2. Alle Produkte anzeigen")
        println("3. Produkte nach Kategorie anzeigen (Mode/Schmuck)")
        println("4. Passwort ändern")
        println("5. Beenden")
        print("Wähle eine Option: ")

        when (readLine()?.trim()) {
            "1" -> {
                print("Benutzername: ")
                val benutzername = readLine()?.trim() ?: ""
                print("Passwort: ")
                val passwort = readLine()?.trim() ?: ""

                eingeloggterAccount = store.login(benutzername, passwort)
                if (eingeloggterAccount != null) {
                    println("Erfolgreich eingeloggt als ${eingeloggterAccount.benutzername}")
                } else {
                    println("Login fehlgeschlagen.")
                }
            }

            "2" -> {
                println("\n--- Alle Produkte ---")
                store.produkte.forEach { println(it.getProduktDetails()) }
            }

            "3" -> {
                print("Kategorie eingeben (Mode oder Schmuck): ")
                val kategorie = readLine()?.trim() ?: ""
                val gefiltert = store.filterNachKatalog(kategorie)
                println("\nProdukte der Kategorie \"$kategorie\":")
                gefiltert.forEach { println(it.getProduktDetails()) }
            }

            "4" -> {
                if (eingeloggterAccount != null) {
                    print("Neues Passwort eingeben: ")
                    val neuesPasswort = readLine()?.trim() ?: ""
                    eingeloggterAccount.passwortAendern(neuesPasswort)
                    println("Passwort erfolgreich geändert.")
                } else {
                    println("Bitte zuerst einloggen.")
                }
            }

            "5" -> {
                println("Programm wird beendet.")
                break
            }

            else -> println("Ungültige Eingabe.")
        }
    }
}
