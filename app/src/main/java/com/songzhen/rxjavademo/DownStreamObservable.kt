package com.songzhen.rxjavademo

class DownStreamObservable<T>(
    private val source: ObservableOnSubscribe<T>,
    private val thread: Int
) : ObservableOnSubscribe<T> {

    override fun setObserver(observer: Observer<T>) {
        val downStream = DownStreamObserver(observer, thread)
        source.setObserver(downStream)
    }

    class DownStreamObserver<T>(private val downStream: Observer<T>, private val thread: Int) : Observer<T> {
        override fun onSubscribe() {
            Schedulers.INSTANCE.submitObserverWork({
                downStream.onSubscribe()
            }, thread)
        }

        override fun onNext(item: T) {
            println("DownStreamObservable 当前线程${Thread.currentThread().name}")
            Schedulers.INSTANCE.submitObserverWork({
                downStream.onNext(item)
                println("DownStreamObservable 当前线程${Thread.currentThread().name}")

            }, thread)

        }

        override fun onError(e: Throwable) {
            Schedulers.INSTANCE.submitObserverWork({
                downStream.onError(e)
            }, thread)
        }

        override fun onComplete() {
            Schedulers.INSTANCE.submitObserverWork({
                downStream.onComplete()
            }, thread)
        }

    }
}