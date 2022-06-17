package com.example.mycomposecookbook.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class FragmentA : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->

                    Box(modifier = Modifier.padding(paddingValues))
                    {
                        Text(
                            text = "Hello Fragment A",
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(20.dp)
                                .clickable {

                                    navigate()
                                }
                        )
                    }
                }

            }
        }
    }

    fun navigate() {


        findNavController().navigate(FragmentADirections.actionFragAToB("ASd"))
    }
}