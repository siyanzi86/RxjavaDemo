package com.songzhen.rxjavademo

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.util.concurrent.Executors

class Schedulers() {
    private val threadPool = Executors.newCachedThreadPool()//io线程池
    private var handler = Handler(Looper.getMainLooper()) { message ->
        //这里是主线程
        message.callback.run()
        return@Handler true
    }

    companion object {
        //定义一个线程安全的单例模式
        val INSTANCE: Schedulers by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Schedulers()
        }

        val io = 0//IO策略
        val main = 1//main策略
    }


    //线程切换的方法
    fun <T> submitSubscribeWork(
        source: ObservableOnSubscribe<T>,//上游
        downStream: Observer<T>,//下游
        thread: Int//制定线程策略
    ) {
        when (thread) {
            io -> {
                threadPool.submit {
                    //在线程池中执行连接操作
                    source.setObserver(downStream)
                }
            }

            main -> {
                handler.let {
                    val message = Message.obtain(it) {
                        source.setObserver(downStream)
                    }
                    it.sendMessage(message)
                }

            }
        }
    }


    fun submitObserverWork(func: () -> Unit, thread: Int) {
        when (thread) {
            io -> {
                threadPool?.submit {
                    func.invoke()
                }
            }
            main -> {
                handler.let {
                    val message = Message.obtain(it) {
                        func.invoke()
                    }
                    it.sendMessage(message)
                }

            }
        }
    }
}