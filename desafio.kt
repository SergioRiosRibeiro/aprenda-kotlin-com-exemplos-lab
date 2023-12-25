import java.text.Normalizer.Form

enum class Level { BASICO, INTERMEDIARIO, AVANCADO }

data class EducationalContent(val name: String, val duration: Int = 60)

open class Formations(val name: String, val level: Level, val contents: List<EducationalContent>)

data class Registered (val name: String, val formation: Formations) {
    override fun toString(): String {
        val contentDetails = formation.contents.joinToString(", ") { "${it.name} (${it.duration} minutos)" }
        return "Lista de matrículas - (Pessoa matriculada: $name; Formação: ${formation.name}; Dificuldade: ${formation.level}. " +
                "Os conteúdos dessa formação são: $contentDetails )"
    }
}

object Enrolling {
    private var enrolledUser: MutableList<Registered> = mutableListOf()

    fun enroll(formations: List<Formations>): MutableList<Registered> {
        var answer: String?
        var keepRegistering: String?
        var formationsName = formations.map { it.name }
        var formationsDificult = formations.map { it.level }
        var formationsContents = formations.map { it.contents }

        do {
            var user = getName()
            do {
                println("$user, gostaria de realizar uma matrícula (S/N)?  ")
                answer = readLine()?.lowercase()
                if (answer != "s" && answer != "n") {
                    answer = null
                }
            }while (answer.isNullOrEmpty())

            if (answer == "s") {
                println("$user, no momento temos as formações em $formationsName. Em qual gostaria de se inscrever? ")
                var choose: String? = null

                do {
                    choose = readLine().toString()
                    when (choose) {
                        "Desenvolvedor Android" -> enrolledUser.add(Registered(user, formations[0]))
                        "Lógica de Programação" -> enrolledUser.add(Registered(user, formations[1]))
                        "Unity 3D Game Developer" -> enrolledUser.add(Registered(user, formations[2]))
                        else -> choose = null
                    }

                    if (choose == null) {println("Valor inválido! Digite uma formação válida: ")}
                } while (choose == null)

            } else if (answer == "n") { return mutableListOf() }

            println("Matrícula efetuada com sucesso!")

            do {
                println("Quer realizar mais matrículas?(S/N) ")
                keepRegistering = readLine()?.lowercase()

                if (keepRegistering != "s" && keepRegistering != "n") { keepRegistering = null }
                if (keepRegistering.isNullOrEmpty()) { println("Não entendi. Forneça uma resposta válida.") }
            }while (keepRegistering.isNullOrEmpty())

            println("Entendido. Processando...")

        } while (keepRegistering == "s")
        return enrolledUser
    }
}

object Android {
    val androidDeveloper = Formations("Desenvolvedor Android", Level.INTERMEDIARIO, listOf(EducationalContent("Kotlin", 120),
        EducationalContent("Android Studio"), EducationalContent("Android Jetpack", 120)))
}

object Logic {
    val programmingLogic = Formations("Lógica de Programação", Level.BASICO, listOf(EducationalContent("Operadores"),
        EducationalContent("Estruturas de Controle", 120), EducationalContent("Estrutura de Dados e Objetos", 120)))
}

object Unity {
    val unityDeveloper = Formations("Unity 3D Game Developer", Level.AVANCADO, listOf(EducationalContent("Introdução", 120),
        EducationalContent("Scripts", 120), EducationalContent("Técnicas Artísticas", 120)))
}

fun getName(): String {
    var enteredName: String?

    println("Por favor, informe o nome da pessoa matriculanda: ")
    enteredName = readLine()

    if (enteredName.isNullOrEmpty()) {
        do {
            println("Nome inválido! Por favor, informe um nome válido: ")
            enteredName = readLine()
        } while (enteredName.isNullOrEmpty())
    }

    println("Bem vinda(o) $enteredName")
    return enteredName
}


fun main() {

    val registered: MutableList<Registered> = Enrolling.enroll(listOf(Android.androidDeveloper, Logic.programmingLogic, Unity.unityDeveloper))
    val showRegistered: List<Registered> = registered
    var showEverything: String?

    do {
        println("Gostaria de ver a lista atualizada de matrículas?(S/N) ")
        showEverything = readLine()?.lowercase()

        if (showEverything != "s" && showEverything != "n") { showEverything = null }

        if (showEverything.isNullOrEmpty()) { println("Não entendi. Forneça uma resposta válida.") }
    }while (showEverything.isNullOrEmpty())
    if (showEverything == "s") {
        println("carregando...")
        println(showRegistered)
    } else { return }

}
