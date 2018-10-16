package io.github.stack07142.kotlin_samples.files

import org.koin.dsl.module.module
import org.koin.java.standalone.KoinJavaStarter.startKoin
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


fun main(args: Array<String>) {
    startKoin(listOf(CoffeeMakerKoinModule))
    CoffeeApplication().get().brew()
}

class CoffeeApplication : KoinComponent {
    private val coffeeMakerWithKoin: CoffeeMakerWithKoin by inject()

    fun get() = coffeeMakerWithKoin
}

// Heater, Pump
interface CoffeeHeater {
    fun on()
    fun off()
    val isHot: Boolean
}

interface CoffeePump {
    fun pump()
}

class ElectricCoffeeHeater : CoffeeHeater {
    private var heating: Boolean = false

    override fun on() {
        println("ElectricCoffeeHeater-On(): heating ~ ~ ~")
        heating = true
    }

    override fun off() {
        println("ElectricCoffeeHeater-Off()")
        heating = false
    }

    override val isHot: Boolean
        get() = heating
}

class ElectricCoffeePump(private val heater: CoffeeHeater) : CoffeePump {
    override fun pump() {
        if (heater.isHot) {
            println("Thermosiphon-pump(): => => Pump => =>")
        }
    }
}

// CoffeeMaker
class CoffeeMakerWithKoin(private val heater: CoffeeHeater, private val pump: CoffeePump) {
    fun brew() {
        heater.on()
        pump.pump()
        println("[_]P Coffee")
        heater.off()
    }
}

// DI
val CoffeeMakerKoinModule = module {
    single { CoffeeMakerWithKoin(get(), get()) }
    single<CoffeeHeater> { ElectricCoffeeHeater() }
    single<CoffeePump> { ElectricCoffeePump(get()) }
}
