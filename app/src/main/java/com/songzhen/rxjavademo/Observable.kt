package com.songzhen.rxjavademo

class Observable<T> constructor() {

    //类似代理模式  使用的真观察者的方法
    private var source: ObservableOnSubscribe<T>? = null

    constructor(source: ObservableOnSubscribe<T>) : this() {
        this.source = source
    }

    //给上游设置下游
    //静态方法创建一个真正的被观察者
    companion object {
        fun <T> create(source: ObservableOnSubscribe<T>): Observable<T> {
            return Observable(source)
        }
    }

    fun setObserver(downStream: Observer<T>) {
        downStream.onSubscribe()
        source?.setObserver(downStream)
    }

    //操作符  map定义
    //承上启下 处理上游事件完成之后 返回一个被观察者对象——>可以继续往下游走
    //转换的能力  所以不一定是T类型数据  需要换一种其他数据站位
    fun <R> map(func: (T) -> R): Observable<R> {
        val map = MapObservable(this.source!!, func)
        return Observable(map)
    }


    fun subscribeOn(thread: Int): Observable<T> {
        val subscribe = SubscribeObservable(this.source!!, thread)
        return Observable(subscribe)
    }

    fun observerOn(thread: Int): Observable<T> {
        val downStreamObservable = DownStreamObservable(this.source!!, thread)
        return Observable(downStreamObservable)
    }


}

