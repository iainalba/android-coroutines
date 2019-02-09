package com.iainrichardson.coroutineexample.facts

import com.iainrichardson.coroutineexample.AsyncTaskManager
import com.iainrichardson.coroutineexample.base.BasePresenter
import com.iainrichardson.coroutineexample.base.BaseView
import com.iainrichardson.coroutineexample.repos.AnimalFactRepo
import javax.inject.Inject


interface FactsView : BaseView {
    fun showLoading(show: Boolean)
    fun showFact1(character: String)
    fun showFact2(character: String)
    fun showFact3(character: String)
    fun showFact4(character: String)
    fun showFacts(show: Boolean)
    fun showError()
    fun setSerialTime()
    fun setConcurrentTime()
}

class FactsPresenter @Inject constructor(private val repo: AnimalFactRepo)
    : BasePresenter<FactsView>(asyncTaskManager = AsyncTaskManager()) {

    fun getFactsSerially() {
        view?.showFacts(false)
        view?.showLoading(true)
        repo.getFactsSerially(success = { fact1, fact2, fact3, fact4 ->
            view?.showLoading(false)
            view?.showFact1(fact1.text)
            view?.showFact2(fact2.text)
            view?.showFact3(fact3.text)
            view?.showFact4(fact4.text)
            view?.showFacts(true)
            view?.setSerialTime()
        },
        error = {
            view?.showLoading(false)
            view?.showError()
        })
    }

    fun getFactsConcurrently() {
        view?.showFacts(false)
        view?.showLoading(true)
        repo.getFactsConcurrently(success = { fact1, fact2, fact3, fact4 ->
            view?.showLoading(false)
            view?.showFact1(fact1.text)
            view?.showFact2(fact2.text)
            view?.showFact3(fact3.text)
            view?.showFact4(fact4.text)
            view?.showFacts(true)
            view?.setConcurrentTime()
        },
        error = {
            view?.showLoading(false)
            view?.showError()
        })
    }

}