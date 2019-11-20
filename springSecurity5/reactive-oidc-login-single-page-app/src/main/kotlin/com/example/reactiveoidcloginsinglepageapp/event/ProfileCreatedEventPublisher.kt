package com.example.reactiveoidcloginsinglepageapp.event

import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.function.Consumer
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils
import reactor.core.publisher.FluxSink

@Component
class ProfileCreatedEventPublisher(private val executor: Executor) : ApplicationListener<ProfileCreatedEvent>,
    Consumer<FluxSink<ProfileCreatedEvent>> {
    private val queue = LinkedBlockingQueue<ProfileCreatedEvent>()

    override fun onApplicationEvent(event: ProfileCreatedEvent) {
        queue.offer(event)
    }

    override fun accept(sink: FluxSink<ProfileCreatedEvent>) {
        executor.execute {
            while (true) {
                try {
                    sink.next(queue.take())
                } catch (err: InterruptedException) {
                    ReflectionUtils.rethrowRuntimeException(err)
                }
            }
        }
    }
}
