package com.iainrichardson.coroutineexample.facts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.iainrichardson.coroutineexample.App
import com.iainrichardson.coroutineexample.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.math.roundToInt

class FactsActivity : AppCompatActivity(), FactsView {

    @Inject
    lateinit var presenter: FactsPresenter

    private var serialStartTime: Long = 0
    private var concurrentStartTime: Long = 0
    private var serialTimes: ArrayList<Long> = arrayListOf()
    private var concurrentTimes: ArrayList<Long> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
        button_serial.setOnClickListener {
            serialStartTime = System.currentTimeMillis()
            presenter.getFactsSerially()
        }
        button_concurrent.setOnClickListener {
            concurrentStartTime = System.currentTimeMillis()
            presenter.getFactsConcurrently()
        }
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun showLoading(show: Boolean) {
        if (show) {
            progressbar_character.visibility = View.VISIBLE
        } else {
            progressbar_character.visibility = View.GONE
        }

    }

    override fun showFact1(character: String) {
        text_character_1.text = character
    }

    override fun showFact2(character: String) {
        text_character_2.text = character
    }

    override fun showFact3(character: String) {
        text_character_3.text = character
    }

    override fun showFact4(character: String) {
        text_character_4.text = character
    }

    override fun showFacts(show: Boolean) {
        if (show) {
            text_character_1.visibility = View.VISIBLE
            text_character_2.visibility = View.VISIBLE
            text_character_3.visibility = View.VISIBLE
            text_character_4.visibility = View.VISIBLE
        } else {
            text_character_1.visibility = View.GONE
            text_character_2.visibility = View.GONE
            text_character_3.visibility = View.GONE
            text_character_4.visibility = View.GONE
        }
    }

    override fun setSerialTime() {
        val time = (System.currentTimeMillis() - serialStartTime)
        serialTimes.add(time)
        val average = serialTimes.average().roundToInt()
        text_time_serial.text = "Avg. " + average.toString() + "ms"
    }

    override fun setConcurrentTime() {
        val time = (System.currentTimeMillis() - concurrentStartTime)
        concurrentTimes.add(time)
        val average = concurrentTimes.average().roundToInt()
        text_time_concurrent.text = "Avg. " + average.toString() + "ms"
    }

    override fun showError() {
        text_error.visibility = View.VISIBLE
    }
}
