package com.songzhen.rxjavademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //两个角色：观察者、被观察者  一个关系：订阅
        //事件：被观察者通知订阅事件的观察者update
        Observable.create(object : ObservableOnSubscribe<Int> {
            override fun setObserver(observer: Observer<Int>) {
                println("上游发送数据：10  上游线程${Thread.currentThread().name}")
                observer.onNext(10)
            }
        }).subscribeOn(Schedulers.io)
            .observerOn(Schedulers.main)
            .setObserver(object : Observer<Int> {
                override fun onSubscribe() {
                    println("onSubscribe")
                }

//            override fun onNext(item: Int) {
//                println("下游接受到的数据：${item}")
//            }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }

                override fun onNext(item: Int) {
                    println("下游接受到的数据：${item}   下游线程：${Thread.currentThread().name}")
                }

            })
    }
}