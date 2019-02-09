package com.iainrichardson.coroutineexample.base

import com.iainrichardson.coroutineexample.AsyncTaskManager
import javax.inject.Inject

open class BasePresenter<V : BaseView> @Inject constructor(private val asyncTaskManager: AsyncTaskManager){

    protected var view: V? = null

    fun attach(view: V) {
        this.view = view
    }

    fun detach() {
        view = null
        asyncTaskManager.cancelJobs()
    }

}
