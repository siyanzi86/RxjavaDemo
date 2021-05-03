package com.songzhen.rxjavademo


//真正的被观察者  被观察者需要有向下游传递数据的能力
interface ObservableOnSubscribe<T> {
    //上游需要像下游传递数据   需要持有下游的引用 调用下游方法 实际上就是回调
    fun setObserver(observer: Observer<T>) //该方法是设置下游 获取下游引用
}