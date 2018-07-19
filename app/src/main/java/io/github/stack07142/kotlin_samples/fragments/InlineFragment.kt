package io.github.stack07142.kotlin_samples.fragments

import android.app.Fragment
import android.os.Bundle

class InlineFragment : Fragment() {

    /**
     * 코틀린이 람다를 보통 익명클래스로 컴파일하지만, 매번 새로운 클래스가 만들어지지는 않는다.
     * 그러나 익명클래스가 변수를 포획하면 매번 새로운 클래스가 생성된다. -> inline 함수로 성능 오버헤드 감소
     */

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        someSomeFunction()
    }

    fun calc(functionForMeasurement: () -> Unit): Long {
        val start = System.nanoTime()
        functionForMeasurement()
        return System.nanoTime() - start
    }

    inline fun calcInline(functionForMeasurement: () -> Unit): Long {
        val start = System.nanoTime()
        functionForMeasurement()
        return System.nanoTime() - start
    }

    fun doSomething() {}

    fun someSomeFunction() {
        val elapsedTime = calc {
            doSomething()
        }

        val elapsedTimeWithInline = calcInline {
            doSomething()
        }
    }

    /** 바이트 코드를 디컴파일
    public final void someSomeFunction() {

        // calc
        this.calc((Function0)(new Function0() {
            public final void invoke() {
                this.doSomething();
            }

            public Object invoke() {
                this.invoke();
                return Unit.INSTANCE;
            }
        }));


        // calcInline
        long start$iv = System.nanoTime();
        this.doSomething();
        long elapsedTimeWithInline = System.nanoTime() - start$iv;
    }
     */


}