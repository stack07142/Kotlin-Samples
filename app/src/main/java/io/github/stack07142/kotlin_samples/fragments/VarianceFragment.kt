package io.github.stack07142.kotlin_samples.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.stack07142.kotlin_samples.R
import timber.log.Timber

class VarianceFragment : Fragment() {

    open class Unit
    open class GroundUnit : Unit()
    open class AirUnit : Unit()
    class Marin : GroundUnit()
    class Dropship : AirUnit()

    interface Action<T> {
        fun myAction()
    }

    private fun testInvariant(parameter: Action<Marin>) {
        parameter.myAction()
    }

    private fun testCovariant(parameter: Action<out GroundUnit>) {
        parameter.myAction()
    }

    private fun testContravariant(parameter: Action<in Dropship>) {
        parameter.myAction()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_variance, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val marin = object : Action<Marin> {
            override fun myAction() {
                Timber.d("Attack!")
            }
        }

        val dropship = object : Action<Dropship> {
            override fun myAction() {
                Timber.d("Transport!")
            }
        }

        val someGroundUnit = object : Action<GroundUnit> {
            override fun myAction() {
                Timber.d("Walk")
            }
        }

        val someAirUnit = object : Action<AirUnit> {
            override fun myAction() {
                Timber.d("Fly!")
            }
        }

        /*
         * 1. 무공변성(invariant): 상속 관계와 상관없이 명시한 타입만 허용
         */
        // testInvariant(someGroundUnit) // error
        testInvariant(marin) // ok

        // testInvariant(someAirUnit) // error
        // testInvariant(dropship) // error


        /*
         * 2. 공변성(covariant): 자기 자신과 자식 객체 허용
         */
        testCovariant(someGroundUnit)
        testCovariant(marin)

        // testCovariant(someAirUnit) // error
        // testCovariant(dropship) // error

        /*
         * 3. 반공변성(contravariant): 자기 자신과 부모 객체 허용
         */
        // testContravariant(someGroundUnit) // error
        // testContravariant(marin) // error

        testContravariant(someAirUnit)
        testContravariant(dropship)
    }
}