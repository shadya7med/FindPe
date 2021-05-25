package com.iti.example.findpe2.home.filter.views

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.RangeSlider
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.FragmentFilterBinding
import com.iti.example.findpe2.home.filter.viewModels.FilterViewModel
import java.text.NumberFormat
import java.util.*


class FilterFragment : Fragment() {



    private lateinit var binder: FragmentFilterBinding
    private lateinit var navController: NavController
    lateinit var filterViewModel: FilterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binder = FragmentFilterBinding.inflate(inflater, container, false)
        binder.lifecycleOwner = this
        binder.filterFragment = this
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterViewModel = ViewModelProvider(this).get(FilterViewModel::class.java)
        binder.filterViewModel = filterViewModel
        navController = view.findNavController()
        //show range slider labels
        binder.priceRangeSliderFilterHome.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            format.format(value.toDouble())
        }
        binder.priceRangeSliderFilterHome.addOnSliderTouchListener(object :
            RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                // Responds to when slider's touch event is being started

            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                // Responds to when slider's touch event is being stopped
                filterViewModel.storePriceRange(slider.values)
            }
        })



    }

    fun showDatePicker(type:Int){
        filterViewModel.showDatePicker(type).show(parentFragmentManager, "FilterFragment")
    }


    fun toggleFeatureButtons(feature : Int){
        var featureButton:MaterialButton? = null
        when(feature){
            0 -> featureButton = binder.wifiToggleBtnFilterHome as MaterialButton
            1 -> featureButton = binder.hotelToggleBtnFilterHome as MaterialButton
            2 -> featureButton = binder.swimmingToggleBtnFilterHome as MaterialButton
            3 -> featureButton = binder.innToggleBtnFilterHome as MaterialButton
            4 -> featureButton = binder.parkingToggleBtnFilterHome as MaterialButton

        }
        if (featureButton == null){
            return
        }else{
            if(filterViewModel.toggleFeaturesState(feature)){
                //true --> active feature
                featureButton.setIconTintResource(R.color.feature_selected_btn_icon)
                featureButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.feature_selected_btn_background))

            }else{
                //false --> inactive feature
                featureButton.setIconTintResource(R.color.feature_unselected_btn_icon)
                featureButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.feature_unselected_btn_background))


            }
        }
    }

    fun saveFilterResult(){
        //return data in SavedStateMap
        navController.previousBackStackEntry?.savedStateHandle?.set(FilterViewModel.FULL_MAP_KEY, filterViewModel.getFilterResult())
        //pop current fragment and destroy it
        navController.popBackStack()
    }

}

