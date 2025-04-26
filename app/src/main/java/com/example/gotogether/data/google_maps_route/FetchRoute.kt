package com.example.gotogether.data.google_maps_route

import com.example.gotogether.config.API_KEY
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

suspend fun fetchRoute(startLat: String, startLng: String, endLat: String, endLng: String): List<LatLng> {
    val url = "https://maps.googleapis.com/maps/api/directions/json?origin=$startLat,$startLng&destination=$endLat,$endLng&mode=driving&key=${API_KEY}"

    return withContext(Dispatchers.IO) {
        try {
            val result = URL(url).readText()
            val jsonObject = JSONObject(result)
            val routes = jsonObject.getJSONArray("routes")
            if (routes.length() > 0) {
                val points = routes.getJSONObject(0)
                    .getJSONObject("overview_polyline")
                    .getString("points")

                return@withContext PolyUtil.decode(points) 
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        emptyList()
    }
}

suspend fun getDirectionResponse(startLat: String, startLng: String, endLat: String, endLng: String): Pair<Int, Int>? {
    val url = "https://maps.googleapis.com/maps/api/directions/json?origin=$startLat,$startLng&destination=$endLat,$endLng&mode=driving&key=${API_KEY}"

    return withContext(Dispatchers.IO) {
        try {
            val result = URL(url).readText()
            val jsonObject = JSONObject(result)
            val routes = jsonObject.getJSONArray("routes")

            if (routes.length() > 0) {
                val firstRoute = routes.getJSONObject(0)
                val legs = firstRoute.getJSONArray("legs")
                if (legs.length() > 0) {
                    val firstLeg = legs.getJSONObject(0)

                    val distance = firstLeg.getJSONObject("distance").getInt("value") // метри
                    val duration = firstLeg.getJSONObject("duration").getInt("value") // секунди

                    return@withContext Pair(distance, duration)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        null
    }
}