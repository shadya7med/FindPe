package com.iti.example.findpe2.tripCheckout.booking.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.iti.example.findpe2.databinding.FragmentBookingBinding
import com.iti.example.findpe2.tripCheckout.booking.viewmodels.BookingViewModel
import com.iti.example.findpe2.tripCheckout.booking.viewmodels.BookingViewModelFactory


class BookingFragment : Fragment() {


    private lateinit var binding: FragmentBookingBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: BookingViewModel
    private var tripPrice = 0.0f
    private var numOfSeats = 0
    private var tripFromDuration = ""
    private var tripToDuraion = ""
    override fun onCreateView(
        inflater: LayoutInflater, parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //fake data for testing
//        val tripInfoList = mutableListOf<TripInfo>(
//            TripInfo(1,12,"22-10","23-11"),
//            TripInfo(2,11,"22-10","23-11"),
//            TripInfo(3,5,"22-10","23-11"),
//            TripInfo(4,32,"22-10","23-11"),
//            TripInfo(5,22,"22-10","23-11"),
//            TripInfo(6,23,"22-10","23-11"),
//            TripInfo(7,6,"22-10","23-11"),
//            TripInfo(8,19,"22-10","23-11"),
//        )
        navController = findNavController()
        val args  = BookingFragmentArgs.fromBundle(requireArguments())
        tripPrice = args.tripPrice
        val viewModelFactory = BookingViewModelFactory(args.tripId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BookingViewModel::class.java)

        // Inflate the layout for this fragment
        binding = FragmentBookingBinding.inflate(inflater,parent,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.numberOfSeats.observe(viewLifecycleOwner, Observer { numOfSeats ->
            numOfSeats?.let {
                binding.numberPicker.maxValue = numOfSeats
            }
        })
        viewModel.navigateToCheckout.observe(viewLifecycleOwner, Observer {
            it?.let {
                navController.navigate(BookingFragmentDirections.actionBookingFragmentToPaymentFragment(it.second, it.first, tripPrice))
                viewModel.displayCheckoutComplete()
            }
        })



        binding.tripDuraionsListRcyViewBookingTripHolder.adapter = TripDurationsAdapter(requireActivity().application, TripDurationsAdapter.TripDurationClickListener {
            viewModel.setSelectedDuration(it)
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.setSelectedDuration(null)
    }

}