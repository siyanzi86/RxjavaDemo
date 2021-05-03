package com.songzhen.rxjavademo

class MapObservable<T, R>(
    private val source: ObservableOnSubscribe<T>,
    private val func: ((T) -> R)
) : ObservableOnSubscribe<R> {

    //observer 是真正的下游  map是连接作用
    override fun setObserver(observer: Observer<R>) {
        println("mapObservable.setObserver")
        val map = MapObserver(observer, func)
        source.setObserver(map)
    }

    class MapObserver<T, R>(
        private val downStream: Observer<R>,
        private val func: (T) -> R
    ) : Observer<T> {
        override fun onSubscribe() {
            downStream.onSubscribe()//接受到事件后 传递给下游
        }

        override fun onNext(item: T) {
            //转换 得到转换后数据 继续传递给下游
            val result = func.invoke(item)
            downStream.onNext(result)
        }

        override fun onError(e: Throwable) {
            downStream.onError(e)
        }

        override fun onComplete() {
            downStream.onComplete()
        }

    }
}