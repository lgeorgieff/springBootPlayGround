package com.example.fsm

import com.example.fsm.core.Event
import com.example.fsm.core.State
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.statemachine.StateMachine

@SpringBootApplication
class FsmApplication {
    @Autowired
    private lateinit var fsm: StateMachine<State, Event>

    @Bean
    fun cmd() = CommandLineRunner {
        println(""""+": ${processString("+")}""")
        println(""""-": ${processString("-")}""")
        println("""".": ${processString(".")}""")
        println(""""a": ${processString("a")}""")
        println("""" ": ${processString(" ")}""")
        println("""".123": ${processString(".123")}""")
        println(""""-.": ${processString("-.")}""")
        println(""""+.": ${processString("+.")}""")
        println(""""123.": ${processString("123.")}""")
        println(""""123.123.": ${processString("123.123.")}""")
        println(""""123-123.": ${processString("123-123.")}""")
        println(""""123+123.": ${processString("123+123.")}""")
        println(""""123+": ${processString("123+")}""")
        println(""""123+": ${processString("123+")}""")
        println(""""123a": ${processString("123a")}""")
        println(""""12a3": ${processString("12a3")}""")
        println(""""0": ${processString("0")}""")
        println(""""01": ${processString("01")}""")
        println(""""123": ${processString("123")}""")
        println(""""123.3": ${processString("123.3")}""")
        println(""""123.34": ${processString("123.34")}""")
        println(""""-1": ${processString("-1")}""")
        println(""""+1": ${processString("+1")}""")
        println(""""-198": ${processString("-198")}""")
        println(""""+198": ${processString("+198")}""")
        println(""""-123.3": ${processString("-123.3")}""")
        println(""""-123.34": ${processString("-123.34")}""")
        println(""""+123.3": ${processString("+123.3")}""")
        println(""""+123.34": ${processString("+123.34")}""")
    }

    private fun processString(str: String): Boolean {
        tailrec fun _process(position: Int): Boolean {
            if (position == 0) fsm.start()

            return if (position == str.length) {
                val result = fsm.sendEvent(Event.END) && fsm.state.id == State.END
                fsm.stop()
                result
            } else {
                val currentChar = when (str[position]) {
                    in '0'..'9' -> fsm.sendEvent(Event.DIGIT)
                    '+' -> fsm.sendEvent(Event.PLUS)
                    '-' -> fsm.sendEvent(Event.MINUS)
                    '.' -> fsm.sendEvent(Event.DOT)
                    else -> false
                }

                if (currentChar) _process(position + 1) else false
            }
        }

        return _process(0)
    }
}

fun main(args: Array<String>) {
    runApplication<FsmApplication>(*args)
}
