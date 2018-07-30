package io.github.stack07142.kotlin_samples.fragments

import android.app.Fragment
import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

class CoffeeMakerFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Timber.d("Dagger with Kotlin")
        DaggerInjectorUtils.create().getCoffeeMaker().brew()
    }
}

// Heater & Pump
interface Heater {
    fun on()
    fun off()
    val isHot: Boolean
}

interface Pump {
    fun pump()
}

class ElectricHeater : Heater {
    var heating: Boolean = false

    override fun on() {
        Timber.d("ElectricHeater-On(): heating ~ ~ ~")
        this.heating = true
    }

    override fun off() {
        Timber.d("ElectricHeater-Off()")
        this.heating = false
    }

    override val isHot: Boolean
        get() = heating
}

class Thermosiphon @Inject constructor(private val heater: Heater) : Pump {
    override fun pump() {
        if (heater.isHot) {
            Timber.d("Thermosiphon-pump(): => => Pump => =>")
        }
    }
}

// Coffee Maker
class CoffeeMaker @Inject constructor(private val heater: Heater, private val pump: Pump) {
    fun brew() {
        heater.on()
        pump.pump()
        Timber.d("[_]P Coffee")
        heater.off()
    }
}

// DI Module
@Module
class CoffeeMakerModule {
    @Singleton
    @Provides
    fun provideHeater(): Heater = ElectricHeater()

    @Singleton
    @Provides
    fun providePump(heater: Heater): Pump = Thermosiphon(heater)
}

// DI Component
@Singleton
@Component(modules = [(CoffeeMakerModule::class)])
interface InjectorUtils {
    // Provision method
    fun getCoffeeMaker(): CoffeeMaker
}


