package com.example.mycomposecookbook.screen.home.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor() : ViewModel() {

    val location = MutableStateFlow(LatLng(0.0, 0.0))

    fun fetchCurrentLocation() {
        viewModelScope.launch {
            delay(5000)
            location.value = LatLng(23.0225, 72.5714)
        }
    }
}