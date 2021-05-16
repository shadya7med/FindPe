package com.iti.example.findpe.home.explore.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.iti.example.findpe.Home.Activities.Views.ActivitiesFragment
import com.iti.example.findpe.Home.Discover.Views.DiscoverFragment
import com.iti.example.findpe.R
import com.iti.example.findpe.databinding.FragmentExploreBinding
import com.iti.example.findpe.home.chat.views.ChatFragment
import com.iti.example.findpe.home.profile.views.ProfileFragment
import com.iti.example.findpe.home.traveling.views.TravelingFragment


class ExploreFragment : Fragment() {
    private lateinit var demoCollectionAdapter: DemoCollectionAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentExploreBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_explore, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionAdapter = DemoCollectionAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = demoCollectionAdapter
        val tabLayout: TabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when(position){
                0 -> "Discover"
                else -> "Activities"
            }
        }.attach()

    }

}

class DemoCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 ->  DiscoverFragment()
            else -> ActivitiesFragment()
        }
    }
}

