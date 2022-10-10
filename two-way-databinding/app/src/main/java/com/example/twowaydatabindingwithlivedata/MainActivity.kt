package com.example.twowaydatabindingwithlivedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.twowaydatabindingwithlivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * You can use the inflate method on the generated class.
         * This is useful for using data binding in fragments, ListView or RecyclerView.
         */

       /* var binding: ActivityMainBinding? =
            ActivityMainBinding.inflate(layoutInflater, container, attachToContainer)
        // get the root view
        val view: View = binding!!.root*/

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        /**
         * Livedata only notify the active observer.
         * As we know Livedata is lifecycle aware  component, we have to pass lifecycleOwner to it.
         * So that it can identify the active/inactive state of lifecycleOwner before notifying.
         *
         */
        /*mainViewModel.quoteLiveData.observe(this) {
            binding.quoteText.text = it
        }*/

        /**
         * Hover and read lifecycleOwner
         * If we don't set lifecycleOwner here, binding in xml will not observe changes to livedata
         *
         * Note: When using Data Binding with Fragments, make sure to use Fragment.getViewLifecycleOwner().
         * Using the Fragment as the LifecycleOwner might cause memory leaks since the Fragment's
         * Lifecycle outlives the view Lifecycle.
         */

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        //We can have separate user variable in layout then
        //binding.user = mainViewModel.user

        /**
         * We will set onClick, using data binding
         */
        /*binding.btnupdate.setOnClickListener {
            mainViewModel.updateQuote()
        }*/
    }
}