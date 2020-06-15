package br.com.hackccr.features.maps

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import br.com.hackccr.R
import br.com.hackccr.data.Point
import br.com.hackccr.features.maps.detail.PointDetailActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_point.*

class PointBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_point, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureSheet()
    }

    private fun configureSheet() {
        arguments?.let {
            val point = it.getSerializable(CONTENT_ARG) as Point

            tvCategory.text = point.category
            if(point.distance > 1000) {
                tvDistance.text = "${point.distance.toDouble()/1000}km de Distância"
            } else {
                tvDistance.text = "${point.distance}m de Distância"
            }

            tvName.text = point.fantasyName
            point.observations?.let {
                tvObservations.text = it
                tvObservations.visibility = View.VISIBLE
            } ?: run {
                tvObservations.visibility = View.GONE
            }
            ratingBar.rating = point.rating.toFloat()

            btComments.setOnClickListener {
                PointDetailActivity.start(this.requireActivity(), point)
            }

        }
    }

    companion object {
        const val CONTENT_ARG = "CONTENT_ARG"

        fun newInstance(point: Point) =
            PointBottomSheet().apply {
                arguments = Bundle().apply {
                    putSerializable(CONTENT_ARG, point)
                }
            }

    }
}