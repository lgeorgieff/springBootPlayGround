package com.example.fsm.core

import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.config.EnableStateMachine
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer

@Configuration
@EnableStateMachine
class NumberFsmConfig : StateMachineConfigurerAdapter<State, Event>() {
    override fun configure(states: StateMachineStateConfigurer<State, Event>) {
        super.configure(states)

        states
            .withStates()
            .initial(State.START)
            .end(State.END)
            .states(setOf(State.PLUS, State.MINUS, State.DOT, State.INT_DIGIT, State.FRACTION_DIGIT))
    }

    override fun configure(transitions: StateMachineTransitionConfigurer<State, Event>) {
        super.configure(transitions)

        transitions
            .withExternal()
                .source(State.START).target(State.PLUS).event(Event.PLUS)
            .and().withExternal()
                .source(State.START).target(State.MINUS).event(Event.MINUS)
            .and().withExternal()
                .source(State.START).target(State.INT_DIGIT).event(Event.DIGIT)
            .and().withExternal()
                .source(State.INT_DIGIT).target(State.INT_DIGIT).event(Event.DIGIT)
            .and().withExternal()
                .source(State.PLUS).target(State.INT_DIGIT).event(Event.DIGIT)
            .and().withExternal()
                .source(State.MINUS).target(State.INT_DIGIT).event(Event.DIGIT)
            .and().withExternal()
                .source(State.INT_DIGIT).target(State.DOT).event(Event.DOT)
            .and().withExternal()
                .source(State.DOT).target(State.FRACTION_DIGIT).event(Event.DIGIT)
            .and().withExternal()
                .source(State.FRACTION_DIGIT).target(State.FRACTION_DIGIT).event(Event.DIGIT)
            .and().withExternal()
                .source(State.INT_DIGIT).target(State.END).event(Event.END)
            .and().withExternal()
                .source(State.FRACTION_DIGIT).target(State.END).event(Event.END)
    }
}
