package com.example.mycomposecookbook.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mycomposecookbook.R

class WorkManagerExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_demo)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment


    }

}