package com.iainrichardson.coroutineexample

import kotlinx.coroutines.experimental.Job

open class AsyncTaskManager {

    var jobs: ArrayList<Job> = arrayListOf()

    fun cancelJobs() {
        
    }

}