package com.iainrichardson.coroutineexample.repos

import com.iainrichardson.coroutineexample.AsyncTaskManager
import com.iainrichardson.coroutineexample.api.ApiAnimalFact
import com.iainrichardson.coroutineexample.api.AnimalFactsService
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.HttpException
import javax.inject.Inject


class AnimalFactRepo @Inject constructor(private val service: AnimalFactsService,
                                         private val asyncTaskManager: AsyncTaskManager) {

    fun getFactsSerially(success: (ApiAnimalFact,
                                  ApiAnimalFact,
                                  ApiAnimalFact,
                                  ApiAnimalFact) -> Unit,
                        error: (Throwable) -> Unit) {

        val job = launchAsync {
            try {
                // Serially
                val character1Task = service.getAnimalFacts("cat", 1).await()
                val character2Task = service.getAnimalFacts("dog", 1).await()

                val character3Task = service.getAnimalFacts("dog", 1).await()
                val character4Task = service.getAnimalFacts("cat", 1).await()

                success(character1Task.body()!!,
                        character2Task.body()!!,
                        character3Task.body()!!,
                        character4Task.body()!!)
            } catch (httpException: HttpException) {
                error(httpException)
            } catch (exception: Exception) {
                error(exception)
            }
        }
        asyncTaskManager.jobs.add(job)
    }

    fun getFactsConcurrently(success: (ApiAnimalFact,
                                       ApiAnimalFact,
                                       ApiAnimalFact,
                                       ApiAnimalFact) -> Unit,
                             error: (Throwable) -> Unit) {

        val job = launchAsync {
            try {
                val character1Task = service.getAnimalFacts("cat", 1)
                val character2Task = service.getAnimalFacts("dog", 1)
                val character3Task = service.getAnimalFacts("dog", 1)
                val character4Task = service.getAnimalFacts("cat", 1)

                success(character1Task.await().body()!!,
                        character2Task.await().body()!!,
                        character3Task.await().body()!!,
                        character4Task.await().body()!!)
            } catch (httpException: HttpException) {
                error(httpException)
            } catch (exception: Exception) {
                error(exception)
            }
        }
        asyncTaskManager.jobs.add(job)
    }

    private fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job {
        return launch(UI) { block() }
    }

}
