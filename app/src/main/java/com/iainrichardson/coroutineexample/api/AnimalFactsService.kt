package com.iainrichardson.coroutineexample.api

import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface AnimalFactsService {

 @GET("/facts/random")
 fun getAnimalFacts(@Query("animal") animal: String,
                    @Query("amount") amount: Int): Deferred<Response<ApiAnimalFact>>

}