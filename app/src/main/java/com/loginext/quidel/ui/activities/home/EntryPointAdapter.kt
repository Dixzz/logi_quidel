package com.loginext.quidel.ui.activities.home

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.loginext.quidel.R
import com.loginext.quidel.databinding.CollItemBinding
import com.loginext.quidel.databinding.FoodBannerItemBinding
import com.loginext.quidel.databinding.FoodCategoryItemBinding
import com.loginext.quidel.databinding.OfferFooterTextBinding
import com.loginext.quidel.databinding.RecForYouBinding
import com.loginext.quidel.helpers.diffUtil
import com.loginext.quidel.models.home.Banner
import com.loginext.quidel.models.home.FoodCategory
import com.loginext.quidel.models.home.OfferCollection
import com.loginext.quidel.models.home.Restaurant
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@InstallIn(ActivityComponent::class)
@EntryPoint
interface AdapterEntryPoint {
    fun bannerAdapter(): BannerAdapter
    fun foodCategoryAdapter(): FoodCategoryAdapter
    fun offerCollAdapter(): OfferCollectionAdapter
    fun restaurantAdapter(): RestaurantAdapter.Factory
}

class OfferCollectionAdapter @Inject constructor(
    private val inflater: LayoutInflater,
) : ListAdapter<OfferCollection, OfferCollectionAdapter.Holder>(
    diffUtil<OfferCollection>(
        onItemSame = { p1, p2 ->
            p1.name == p2.name
        })
) {

    inner class Holder(val binding: CollItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(CollItemBinding.inflate(inflater, p0, false))
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val item = getItem(p1)
        with(p0.binding) {
            this.item = item
            Glide.with(image).load(item.image)
                .override(image.width, image.height)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerInside()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(image)
        }
    }
}

@ActivityScoped
class BannerAdapter @Inject constructor(
    private val inflater: LayoutInflater,
) : ListAdapter<Banner, BannerAdapter.Holder>(
    diffUtil<Banner>(
        onItemSame = { p1, p2 ->
            p1.image_url == p2.image_url
        })
) {
    inner class Holder(val binding: FoodBannerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(FoodBannerItemBinding.inflate(inflater, p0, false))
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val item = getItem(p1)
        with(p0.binding) {

            Glide.with(image).load(item.image_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.ic_plate)
                .error(R.drawable.ic_placeholder)
                .into(image)
        }
    }
}

class FoodCategoryAdapter @Inject constructor(
    activity: FragmentActivity,
) : ListAdapter<FoodCategory, FoodCategoryAdapter.Holder>(
    diffUtil<FoodCategory>(
        onItemSame = { p1, p2 ->
            p1.name == p2.name
        })
) {
    private val inflater by lazy {
        activity.layoutInflater
    }

    inner class Holder(val binding: FoodCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            adapterPosition.takeIf { it != RecyclerView.NO_POSITION }?.also {
                getItem(it).also { item ->

                    Glide.with(binding.root).asBitmap()
                        .transition(BitmapTransitionOptions.withCrossFade())
                        .load(item.icon).into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(p0: Bitmap, p1: Transition<in Bitmap>?) {
                                Palette.from(p0).generate {
                                    it?.also {
                                        binding.mvCategory.setBackgroundColor(
                                            it.getVibrantColor(
                                                Color.GRAY
                                            )
                                        )
                                    }
                                }
                            }

                            override fun onLoadCleared(p0: Drawable?) {
                            }
                        })
                }
            }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(FoodCategoryItemBinding.inflate(inflater, p0, false))
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val item = getItem(p1)
        with(p0.binding) {
            this.item = item

            Glide.with(image).load(item.icon)
                .placeholder(R.drawable.ic_plate)
                .error(R.drawable.ic_plate)
                .into(image)
        }
    }
}

class RestaurantAdapter @AssistedInject constructor(
    private val inflater: LayoutInflater,
    @Assisted
    private val showAddOffer: Boolean,
) :
    ListAdapter<Restaurant, RestaurantAdapter.Holder>(diffUtil<Restaurant>(onItemSame = { p1, p2 ->
        p1.name == p2.name
    })) {

    @AssistedFactory
    interface Factory {
        fun create(showAddOffer: Boolean): RestaurantAdapter
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(RecForYouBinding.inflate(inflater, p0, false))
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val item = getItem(p1)
        val binding = p0.binding
        binding.item = item
        Glide.with(binding.imageHolder).load(item.image_url)
            .override(binding.image.width, binding.image.height)
            .placeholder(R.drawable.ic_plate)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerInside()
            .error(R.drawable.ic_plate)
            .into(binding.image)
        binding.tvAddOffer.isVisible = showAddOffer && item.additional_offer.isNotEmpty()

        item.offers.map {
            val textBinding = OfferFooterTextBinding.inflate(inflater, binding.offerCon, false)
            textBinding.root.setCardBackgroundColor(it.background)
            textBinding.tvOfferName.text = it.name
            textBinding.tvOfferName.setTextColor(it.textcolor)
            ViewCompat.generateViewId().apply {
                textBinding.root.id = this
                binding.offerCon.addView(textBinding.root)
            }
        }.toIntArray().also {
            binding.offersFlow.referencedIds += it
        }
        binding.executePendingBindings()
    }

    class Holder(val binding: RecForYouBinding) : RecyclerView.ViewHolder(binding.root)
}