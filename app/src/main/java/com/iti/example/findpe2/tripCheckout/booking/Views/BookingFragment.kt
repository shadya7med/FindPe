package com.iti.example.findpe2.tripCheckout.booking.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.iti.example.findpe2.databinding.FragmentBookingBinding
import com.iti.example.findpe2.pojos.TripInfo


class BookingFragment : Fragment() {


    private lateinit var binding: FragmentBookingBinding
    private lateinit var navController: NavController

    private var numOfSeats = 0
    private var tripFromDuration = ""
    private var tripToDuraion = ""
    override fun onCreateView(
        inflater: LayoutInflater, parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //fake data for testing
        val tripInfoList = mutableListOf<TripInfo>(
            TripInfo(1,12,"22-10","23-11"),
            TripInfo(2,11,"22-10","23-11"),
            TripInfo(3,5,"22-10","23-11"),
            TripInfo(4,32,"22-10","23-11"),
            TripInfo(5,22,"22-10","23-11"),
            TripInfo(6,23,"22-10","23-11"),
            TripInfo(7,6,"22-10","23-11"),
            TripInfo(8,19,"22-10","23-11"),
        )
        // Inflate the layout for this fragment
        binding = FragmentBookingBinding.inflate(inflater,parent,false)
        binding.bookingFragment = this

        val numOfSeatsAdapter = NumOfSeatsAdapter()
        binding.numOfSeatsListsRcyViewBookingTripHolder.adapter = numOfSeatsAdapter
        numOfSeatsAdapter.submitList(tripInfoList)

        val tripDurationsAdapter = TripDurationsAdapter()
        binding.tripDuraionsListRcyViewBookingTripHolder.adapter = tripDurationsAdapter
        tripDurationsAdapter.submitList(tripInfoList)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        val tripId = BookingFragmentArgs.fromBundle(requireArguments()).tripId
        //get dataInfo with tripID
        Log.i("FiBooking", "id : ${tripId}")
    }

    fun navigateToCheckout(){
        navController.navigate(BookingFragmentDirections.actionBookingFragmentToPaymentFragment(numOfSeats,tripFromDuration,tripToDuraion))
    }
    

}