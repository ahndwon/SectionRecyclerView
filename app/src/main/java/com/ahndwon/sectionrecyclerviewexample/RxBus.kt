package com.ahndwon.sectionrecyclerviewexample

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable

object RxBus {
//    val listSubject = BehaviorSubject.create<List<Sectionable>>()

    private val publisher = PublishRelay.create<Any>()

    fun publish(event: Any) {
        publisher.accept(event)
    }
    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}