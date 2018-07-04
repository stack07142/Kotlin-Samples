package io.github.stack07142.kotlin_samples.calculator

import android.util.Log
import java.util.*

object Calculator : Stack<Any>() {

    fun calc(expression: List<String>): Double {
        val postfixExpression = changeToPostfix(expression)

        for (item in postfixExpression) {
            if (isOperator(item)) {
                val second = pop() as Double
                val first = pop() as Double

                when (item) {
                    "+" -> first + second
                    "-" -> first - second
                    "*" -> first * second
                    "÷" -> first / second
                    else -> 0.0
                }.let {
                    push(it)
                }
            } else {
                push(item.toDouble())
            }
        }

        return pop() as Double
    }

    private fun changeToPostfix(expression: List<String>): List<String> {
        val postfixExpression = MutableList(0) { "" }

        for (item in expression) {
            if (isOperator(item)) {
                while (!isEmpty()
                        && getPrecedence(peek() as String) >= getPrecedence(item)) {
                    postfixExpression.add(pop() as String)
                }
                push(item)
            } else {
                postfixExpression.add(item)
            }
        }

        while (!isEmpty()) {
            postfixExpression.add(pop() as String)
        }

        Log.d("Calculator", "postfixExpression= $postfixExpression")
        return postfixExpression
    }

    private fun getPrecedence(op: String): Int {
        return when (op) {
            "+", "-" -> {
                0
            }
            "*", "÷" -> {
                1
            }
            else -> 2
        }
    }

    private fun isOperator(s: String): Boolean {
        return when (s) {
            "+", "-", "*", "÷" -> true
            else -> false
        }
    }
}