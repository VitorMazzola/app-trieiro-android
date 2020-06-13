package br.com.hackccr.features.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.hackccr.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import kotlinx.android.synthetic.main.fragment_maps.*
import org.json.JSONArray

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)


    }

    private fun getJsonDataFromAsset(): JSONArray? {
        return try {
            activity?.let {
                val jsonString = it.assets?.open("district_data.json")?.bufferedReader().use { it?.readText() }
                JSONArray(jsonString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun generateHeatMapData(): ArrayList<WeightedLatLng> {
        val data = ArrayList<WeightedLatLng>()

        // call our function which gets json data from our asset file
        val jsonData = getJsonDataFromAsset()

        // ensure null safety with let call
        jsonData?.let {
            // loop over each json object
            for (i in 0 until it.length()) {
                // parse each json object
                val entry = it.getJSONObject(i)
                val lat = entry.getDouble("lat")
                val lon = entry.getDouble("lon")
                val density = entry.getDouble("density")

                // optional: remove edge cases like 0 population density values
                if (density != 0.0) {
                    val weightedLatLng = WeightedLatLng(LatLng(lat, lon), density)
                    data.add(weightedLatLng)
                }
            }
        }

        return data
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val data = generateHeatMapData()
        val heatMapProvider = HeatmapTileProvider.Builder()
                .weightedData(data) // load our weighted data
                .radius(50) // optional, in pixels, can be anything between 20 and 50
                .build()

        googleMap.addTileOverlay(TileOverlayOptions().tileProvider(heatMapProvider))

        val indiaLatLng = LatLng(20.5937, 78.9629)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indiaLatLng, 5f))
    }

    companion object {
        fun newInstance() = MapsFragment()

    }
}
