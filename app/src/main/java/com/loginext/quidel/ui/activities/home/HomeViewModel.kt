package com.loginext.quidel.ui.activities.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loginext.quidel.helpers.asLiveData
import com.loginext.quidel.models.AutoResolver
import com.loginext.quidel.models.home.HomeComponentResponse
import com.loginext.quidel.repository.home.HomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepo,
) : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        homeComponent.postValue(AutoResolver.failure())
    }

    private val scope by lazy {
        viewModelScope.coroutineContext + Dispatchers.IO + errorHandler
    }

    val homeComponent = MutableLiveData<AutoResolver<HomeComponentResponse>>()

    fun getHomeComponents() {
        homeComponent.postValue(AutoResolver.loading())
        viewModelScope.launch(scope) {
            val data = homeRepo.homeScreenComponents()
            data.data.restaurant_collections = data.data.restaurant_collections.sortedBy {
                it.priority
            }
            homeComponent.postValue(AutoResolver.success(data))
        }
    }
}