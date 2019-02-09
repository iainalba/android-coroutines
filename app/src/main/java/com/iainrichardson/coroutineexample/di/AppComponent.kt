package com.iainrichardson.coroutineexample.di

import com.iainrichardson.coroutineexample.facts.FactsActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(activity: FactsActivity)
}