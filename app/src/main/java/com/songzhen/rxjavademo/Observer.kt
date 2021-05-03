package com.songzhen.rxjavademo

//为什么设计成接口  因为Rxjava不需要知道观察者具体怎么改变
interface Observer<T> {

    fun onSubscribe()

    fun onNext(item: T)

    fun onError(e: Throwable)

    fun onComplete()
}