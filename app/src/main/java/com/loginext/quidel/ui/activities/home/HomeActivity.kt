package com.loginext.quidel.ui.activities.home

import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updateMarginsRelative
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loginext.quidel.R
import com.loginext.quidel.databinding.ActivityHomeBinding
import com.loginext.quidel.databinding.RestroColBinding
import com.loginext.quidel.helpers.dp
import com.loginext.quidel.helpers.failResource
import com.loginext.quidel.helpers.loadingResource
import com.loginext.quidel.helpers.px
import com.loginext.quidel.helpers.successResource
import com.loginext.quidel.ui.activities.BaseActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.delay
import java.util.Locale

@AndroidEntryPoint
class HomeActivity : BaseActivityBinding<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    @VisibleForTesting
    val homeViewModel by viewModels<HomeViewModel>()
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(
            window,
            findViewById(android.R.id.content)
        ).also { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, findViewById(android.R.id.content)).show(
            WindowInsetsCompat.Type.systemBars()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding.mainRoot.isVisible = false
        hideSystemUI()
        window.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg_logo))
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            delay(500)
            window.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            binding.tempBg.animate().scaleX(0f).scaleY(0f).setDuration(800)
                .setInterpolator(AnticipateInterpolator()).withEndAction {
                    showSystemUI()
                    homeViewModel.viewModelBlock()
                }
        }
    }

    private fun fetch() {
        lifecycleScope.launchWhenCreated {
            loadingDialog =
                ProgressDialog.show(this@HomeActivity, "Loading", "Please wait...", true)
            delay(1000)
            homeViewModel.getHomeComponents()
        }
    }

    private fun HomeViewModel.viewModelBlock() {
        homeComponent.observe(this@HomeActivity) {
            it.loadingResource {
                if (!it) {
                    loadingDialog?.cancel()
                }
            }.successResource {
                binding.mainRoot.isVisible = true
                val data = it.data
                binding.lvRoot.removeAllViews()
                binding.voucher = data.number_of_active_vouchers
                val entryPoints = EntryPointAccessors.fromActivity(
                    this@HomeActivity,
                    AdapterEntryPoint::class.java
                )
                data.banners.also { list ->
                    entryPoints.bannerAdapter().also {
                        with(binding.rvBanner) {
                            adapter = it
                            layoutManager =
                                LinearLayoutManager(
                                    this@HomeActivity,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            setHasFixedSize(true)
                            it.submitList(list)
                        }
                    }
                }
                data.food_categories.also { list ->
                    entryPoints.foodCategoryAdapter().also {
                        with(binding.rvFoodCat) {
                            adapter = it
                            layoutManager =
                                LinearLayoutManager(
                                    this@HomeActivity,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            setHasFixedSize(true)
                            it.submitList(list)
                        }
                    }
                }
                data.offer_collections.also { list ->
                    entryPoints.offerCollAdapter().also {
                        with(binding.rvColl) {

                            adapter = it
                            layoutManager =
                                GridLayoutManager(
                                    this@HomeActivity,
                                    2,
                                    RecyclerView.HORIZONTAL,
                                    false,
                                ).apply {

                                }
                            setHasFixedSize(true)
                            it.submitList(list)
                        }
                    }
                }
                data.restaurant_collections.forEach {
                    val b = RestroColBinding.inflate(layoutInflater)
                    b.root.id = ViewCompat.generateViewId()
                    b.tvTitle.text = it.name
                    b.root.layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        updateMarginsRelative(top = 12.dp.px)
                    }
                    binding.lvRoot.addView(b.root)
                    with(b.rvRec) {
                        adapter = entryPoints.restaurantAdapter()
                            .create(it.priority == 1)
                            .apply {
                                submitList(it.restaurants)
                            }
                        layoutManager =
                            LinearLayoutManager(
                                this@HomeActivity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        setHasFixedSize(true)
                    }
                }
            }.failResource {
                retryDialog = AlertDialog.Builder(this@HomeActivity).run {
                    setCancelable(false)
                    setMessage(it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() })
                    setNegativeButton("Close") { ds, _ ->
                        ds.dismiss()
                        finish()
                    }
                    setPositiveButton("Retry") { ds, _ ->
                        fetch()
                        ds.cancel()
                    }
                    show()
                }
            }
        }
        binding.mainRoot.isInvisible = true
        fetch()
    }

    private var retryDialog: Dialog? = null
    private var loadingDialog: Dialog? = null
}