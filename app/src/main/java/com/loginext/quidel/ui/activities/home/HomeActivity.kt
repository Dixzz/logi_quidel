package com.loginext.quidel.ui.activities.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import com.loginext.quidel.R
import com.loginext.quidel.databinding.ActivityHomeBinding
import com.loginext.quidel.helpers.dp
import com.loginext.quidel.helpers.logit
import com.loginext.quidel.helpers.px
import com.loginext.quidel.helpers.scaledDrawable
import com.loginext.quidel.helpers.successResource
import com.loginext.quidel.ui.activities.BaseActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivityBinding<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    private lateinit var navController: androidx.navigation.NavController
    private lateinit var toggle: ActionBarDrawerToggle

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
//        window.setBackgroundDrawable(scaledDrawable(R.drawable.quidel_logo, 50.dp.px, 50.dp.px))
        super.onCreate(savedInstanceState)
//        window.setBackgroundDrawable(null)
        homeViewModel.viewModelBlock()
    }

    private fun HomeViewModel.viewModelBlock() {
        homeComponent.observe(this@HomeActivity) {
            it.successResource {
                logit("Wew ${it.data.restaurant_collections.first()}")
            }
        }
        getHomeComponents()
    }
}