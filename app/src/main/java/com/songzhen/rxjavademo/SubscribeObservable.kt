package com.songzhen.rxjavademo

class SubscribeObservable<T>(
    private val source: ObservableOnSubscribe<T>,
    private val thread: Int //指定线程
) : ObservableOnSubscribe<T> {

    override fun setObserver(observer: Observer<T>) {
        val ob = SubscribeObserver(observer)
        Schedulers.INSTANCE.submitSubscribeWork(source, ob, thread)
    }

    class SubscribeObserver<T>(private val downStream: Observer<T>) : Observer<T> {
        override fun onSubscribe() {
            TODO("Not yet implemented")
        }

        override fun onNext(item: T) {
            println("--------------------${Thread.currentThread().name}")
            downStream.onNext(item)
        }

        override fun onError(e: Throwable) {
            TODO("Not yet implemented")
        }

        override fun onComplete() {
            TODO("Not yet implemented")
        }

    }
}