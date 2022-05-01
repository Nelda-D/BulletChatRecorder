import kotlinx.coroutines.*

fun main(){
    runBlocking {
        coroutineScope {
            for (i in 1..10) {
                println(i)
                delay(100)
            }
//            launch {
//            }
        }
        println("coroutineScope finished")
    }
    println("runBlocking finished")
}

suspend fun printDot() = coroutineScope {
    launch {
        println(".")
        delay(1000)
    }
}