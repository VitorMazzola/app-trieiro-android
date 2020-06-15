package br.com.hackccr.features.telemedicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import br.com.hackccr.App
import br.com.hackccr.R
import br.com.hackccr.data.HealthItem
import br.com.hackccr.features.telemedicine.di.DaggerHealthComponent
import br.com.hackccr.features.telemedicine.di.HealthModule
import kotlinx.android.synthetic.main.fragment_telemedicine.*
import javax.inject.Inject

class TelemedicineFragment: Fragment(), HealthView {

    @Inject
    lateinit var presenter: HealthPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_telemedicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDagger()
        presenter.onCreate()
    }

    private fun initDagger() {
        DaggerHealthComponent.builder()
            .applicationComponent(App.getAppComponent(activity!!))
            .healthModule(HealthModule(this))
            .build()
            .inject(this)
    }

    override fun configureHealthItems(items: List<HealthItem>) {
        val adapter = HealthAdapter(items)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance() = TelemedicineFragment()
    }
}