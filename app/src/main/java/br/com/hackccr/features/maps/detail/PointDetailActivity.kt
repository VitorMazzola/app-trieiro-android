package br.com.hackccr.features.maps.detail

import android.R.id
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.hackccr.App
import br.com.hackccr.R
import br.com.hackccr.data.Comment
import br.com.hackccr.data.Point
import br.com.hackccr.features.maps.detail.di.DaggerPointDetailComponent
import br.com.hackccr.features.maps.detail.di.PointDetailModule
import br.com.hackccr.util.addSpan
import kotlinx.android.synthetic.main.activity_point_detail.*
import javax.inject.Inject


@Suppress("UNREACHABLE_CODE")
class PointDetailActivity : AppCompatActivity(), PointDetailView {

    @Inject
    lateinit var presenter: PointDetailPresenter

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point_detail)

        initDagger()

        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Detalhes"

        configureDetailInfos()
        presenter.onCreate()
    }

    private fun initDagger() {
        DaggerPointDetailComponent.builder()
            .applicationComponent(App.getAppComponent(this))
            .pointDetailModule(PointDetailModule(this))
            .build()
            .inject(this)
    }

    private fun configureDetailInfos() {
        intent.extras?.let {
            val point = it.getSerializable(CONTENT_ARG) as Point?

            point?.let {
                configureHeader(it.category, it.distance)
                configurePointInfos(
                    name = it.fantasyName,
                    rating = it.rating,
                    address = it.getAddress(),
                    time = it.getTime(),
                    days = it.period
                )
            }
        }
    }

    private fun configureHeader(category: String, distance: Long) {
        tvCategory.text = category
        if(distance > 1000) {
            tvDistance.text = "${distance.toDouble()/1000}km de Distância"
        } else {
            tvDistance.text = "${distance}m de Distância"
        }
    }

    private fun configurePointInfos(name: String, rating: Int, address: String, time: String, days: String) {
        tvName.text = name
        ratingBar.rating = rating.toFloat()

        tvAddress.append("Endereço: ".addSpan(this))
        tvAddress.append(address)

        tvTime.append("Horário de funcionamento: ".addSpan(this))
        tvTime.append(time)

        tvOpeningDays.append("Dias abertos: ".addSpan(this))
        tvOpeningDays.append(days)
    }

    override fun configureComments(comments: List<Comment>?) {
        comments?.let {
            val adapter = CommentsAdapter(it)

            recyclerView.isNestedScrollingEnabled = false
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        when (item.itemId) {
            id.home -> {
                onBackPressed()
                return true
            }
        }
    }

    companion object {
        const val CONTENT_ARG = "CONTENT_ARG"

        fun start(context: Activity, point: Point) {
            val intent = Intent(context, PointDetailActivity::class.java)
            intent.putExtra(CONTENT_ARG, point)
            context.startActivity(intent)
        }
    }
}