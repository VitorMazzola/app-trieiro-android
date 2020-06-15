package br.com.hackccr.features.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.hackccr.R
import br.com.hackccr.data.CityCovid
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_covid.*

class CovidBottomSheet: BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_covid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureSheet()
    }

    private fun configureSheet() {
        arguments?.let {
            val covid = it.getSerializable(CONTENT_ARG) as CityCovid

            if(covid.distance > 1000) {
                tvDistance.text = "${covid.distance.toDouble()/1000}km de Distância"
            } else {
                tvDistance.text = "${covid.distance}m de Distância"
            }
            tvObservations.text = "${covid.cases} casos confirmados na região. Use máscara e álcool em gel. Se precisar, fale “ajuda” e saiba onde encontrar o kit anticoronavírus"
        }
    }

    companion object {
        const val CONTENT_ARG = "CONTENT_ARG"

        fun newInstance(covid: CityCovid) =
            CovidBottomSheet().apply {
                arguments = Bundle().apply {
                    putSerializable(CONTENT_ARG, covid)
                }
            }

    }
}