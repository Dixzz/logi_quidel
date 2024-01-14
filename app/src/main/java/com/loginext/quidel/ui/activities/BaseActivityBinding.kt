package com.loginext.quidel.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.loginext.quidel.R

/**
 * BaseActivity
 * Activity with reference to views in the class.
 * Type inference is used to provide a type-safe reference to the binding.
 * @param bindingFactory : LayoutInflater -> ViewBinding inflater from ViewBinging::LayoutInflater
 * @see B : ViewBinding type-safe reference to the binding.
 * @property binding usable for reference of views.
 *
 * @author Satyam Sharma
 * @date 2021/11/03
 */
open class BaseActivityBinding<B : ViewBinding>(private val bindingFactory: (LayoutInflater) -> B) :
    AppCompatActivity() {
    val binding: B by lazy { bindingFactory(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        setContentView(binding.root)

    }
}