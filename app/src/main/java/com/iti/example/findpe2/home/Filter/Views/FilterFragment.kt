package com.iti.example.findpe2.home.filter.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.slider.RangeSlider
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentFilterBinding
import com.iti.example.findpe2.home.filter.viewModels.FilterViewModel
import java.text.NumberFormat
import java.util.*


class FilterFragment : Fragment() {


    /*private lateinit var binding: FragmentFilterBinding
    private lateinit var navController: NavController
    lateinit var filterViewModel: FilterViewModel*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFilterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        //binding.filterFragment = this
        val filterViewModel = ViewModelProvider(this).get(FilterViewModel::class.java)
        binding.filterViewModel = filterViewModel


        filterViewModel.autoCompleteFromPlacesList.observe(viewLifecycleOwner) {
            it?.let {
                val placesAdapter =
                    ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1, it)
                binding.fromSourceACTxtViewFilterHome.setAdapter(placesAdapter)
            }
        }

        filterViewModel.autoCompleteToPlacesList.observe(viewLifecycleOwner) {
            it?.let {
                val placesAdapter =
                    ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1, it)
                binding.toSourceACTxtViewFilterHome.setAdapter(placesAdapter)
            }
        }

        /*binding.fromSourceACTxtViewFilterHome.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    filterViewModel.setFromSelectedCity(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    filterViewModel.setFromSelectedCity(-1)
                }
            }

        binding.toSourceACTxtViewFilterHome.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    filterViewModel.setToSelectedCity(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    filterViewModel.setToSelectedCity(-1)
                }
            }*/

        //show range slider labels
        binding.priceRangeSliderFilterHome.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            format.format(value.toDouble())
        }
        binding.priceRangeSliderFilterHome.addOnSliderTouchListener(object :
            RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                // Responds to when slider's touch event is being started

            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                // Responds to when slider's touch event is being stopped
                filterViewModel.storePriceRange(slider.values)
            }
        })

        filterViewModel.onNavigateUpData.observe(viewLifecycleOwner) {
            it?.let {
                val navController = findNavController()
                //return data in SavedStateMap
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    Keys.FULL_FILTER_MAP_KEY,
                    it
                )
                //pop current fragment and destroy it
                navController.popBackStack()
                filterViewModel.onDoneNavigatingUpFromFilter()
            }
        }


        binding.filterSaveBtnFilterHome.setOnClickListener {
            filterViewModel.getFilterResult(
                binding.fromSourceACTxtViewFilterHome.text.toString(),
                binding.toSourceACTxtViewFilterHome.text.toString()
            )
        }



        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }*/

    /*fun showDatePicker(type:Int){
        filterViewModel.showDatePicker(type).show(parentFragmentManager, "FilterFragment")
    }*/


    /*fun toggleFeatureButtons(feature : Int){
        var featureButton:MaterialButton? = null
        when(feature){
            0 -> featureButton = binding.wifiToggleBtnFilterHome as MaterialButton
            1 -> featureButton = binding.hotelToggleBtnFilterHome as MaterialButton
            2 -> featureButton = binding.swimmingToggleBtnFilterHome as MaterialButton
            3 -> featureButton = binding.innToggleBtnFilterHome as MaterialButton
            4 -> featureButton = binding.parkingToggleBtnFilterHome as MaterialButton

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
    }*/


}

