package com.android.recipeapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.recipeapp.R
import com.android.recipeapp.databinding.FragmentSearchOptionsBinding
import com.android.recipeapp.util.Constants.Companion.CALORIES_MAX_KEY
import com.android.recipeapp.util.Constants.Companion.CALORIES_MIN_KEY
import com.android.recipeapp.util.Constants.Companion.RANGE_SLIDER_DEFAULT_MAX_VALUE
import com.android.recipeapp.util.Constants.Companion.RANGE_SLIDER_DEFAULT_MIN_VALUE
import com.android.recipeapp.util.Constants.Companion.SELECTED_DIET_CHIP_ID_KEY
import com.android.recipeapp.util.Constants.Companion.SELECTED_MEAL_TYPE_CHIP_ID_KEY
import com.android.recipeapp.util.Constants.Companion.SORT_MENU_STATUS_KEY
import com.android.recipeapp.util.Constants.Companion.TIME_MAX_KEY
import com.android.recipeapp.viewmodels.SearchOptionsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_options.*
import kotlinx.coroutines.launch
import androidx.lifecycle.MediatorLiveData
import com.android.recipeapp.util.Constants.Companion.DEFAULT_SELECTED_SORT_MENU_ITEM
import com.android.recipeapp.util.Constants.Companion.DEFAULT_SELECTED_SORT_MENU_POSITION
import com.android.recipeapp.util.Constants.Companion.DIET_TYPE_DEFAULT_SELECTED_CHIP_ID
import com.android.recipeapp.util.Constants.Companion.DIET_TYPE_DEFAULT_SELECTED_CHIP_NAME
import com.android.recipeapp.util.Constants.Companion.MEAL_TYPE_DEFAULT_SELECTED_CHIP_ID
import com.android.recipeapp.util.Constants.Companion.MEAL_TYPE_DEFAULT_SELECTED_CHIP_NAME
import com.android.recipeapp.util.Constants.Companion.SLIDER_DEFAULT_MAX_VALUE
import com.google.android.material.chip.Chip


@AndroidEntryPoint
class SearchOptionsFragment : BottomSheetDialogFragment(){

    private lateinit var binding: FragmentSearchOptionsBinding
    val searchOptionsViewModel: SearchOptionsViewModel by viewModels()
    private lateinit var arrayAdapter:ArrayAdapter<String>

    companion object{
        var currentRangeSliderMinValue:Float=RANGE_SLIDER_DEFAULT_MIN_VALUE
        var currentRangeSliderMaxValue:Float=RANGE_SLIDER_DEFAULT_MAX_VALUE
        var currentSliderMaxValue:Float= SLIDER_DEFAULT_MAX_VALUE
        var currentCheckedDietTypeChipId :Int = MEAL_TYPE_DEFAULT_SELECTED_CHIP_ID
        var currentCheckedMealTypeChipName: String = MEAL_TYPE_DEFAULT_SELECTED_CHIP_NAME
        var currentCheckedMealTypeChipId :Int = DIET_TYPE_DEFAULT_SELECTED_CHIP_ID
        var currentCheckedDietTypeChipName: String = DIET_TYPE_DEFAULT_SELECTED_CHIP_NAME
        var currentSelectedMenuPosition:Int = DEFAULT_SELECTED_SORT_MENU_POSITION
        var currentSelectedMenuItem:String = DEFAULT_SELECTED_SORT_MENU_ITEM
    }

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_search_options,container,false)
        getTimeSliderMaxValue()
        rangeSliderOnChangeListener()
        sliderOnChangeListener()
        dietTypeChipsOnCheckListener()
        mealTypeChipsOnCheckListener()
        sortMenuSelectionListener()
        getCalorieSliderValues()
        getTypeChips()
        initSortMenu()
        getMenuPreference()
        applyButtonListener()
        return binding.root
    }

    private fun rangeSliderOnChangeListener(){
        binding.calorieSlider.addOnChangeListener { slider, value, fromUser ->
            currentRangeSliderMaxValue=slider.values[1]
            currentRangeSliderMinValue=slider.values[0]
        }
    }

    private fun sliderOnChangeListener(){
        binding.timeSlider.addOnChangeListener { slider, value, fromUser ->
            currentSliderMaxValue=value
        }
    }

    private fun mealTypeChipsOnCheckListener(){
        binding.mealTypeChips.setOnCheckedChangeListener { group, checkedId ->
            currentCheckedMealTypeChipId=checkedId

            if(checkedId!=-1){
                val chip= mealTypeChips.findViewById<Chip>(checkedId)
                if(chip!=null){
                    currentCheckedMealTypeChipName= chip.text.toString().lowercase()
                }
                else{
                    currentCheckedMealTypeChipName=""
                }

            }

        }
    }

    private fun dietTypeChipsOnCheckListener(){
        binding.dietsChipGroup.setOnCheckedChangeListener { group, checkedId ->
            currentCheckedDietTypeChipId=checkedId

            if(checkedId!=-1){
                val chip= dietsChipGroup.findViewById<Chip>(checkedId)
                if(chip!=null){
                    currentCheckedDietTypeChipName= chip.text.toString().lowercase()
                }
                else{
                    currentCheckedDietTypeChipName=""
                }

            }

        }
    }

    private fun sortMenuSelectionListener(){
        binding.sortItem.setOnItemClickListener { adapterView, view, i, l ->
            currentSelectedMenuPosition=i
        }
    }




    private fun initSortMenu(){
        val sortOptions=resources.getStringArray(R.array.sortOptions)
        arrayAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_item,sortOptions)
        binding.sortItem.setAdapter(arrayAdapter)

    }


    private fun getCalorieSliderValues()=lifecycleScope.launch{
        searchOptionsViewModel.apply {
            val combinedLiveData=calorieSliderMinValue.asLiveData().zipWith(calorieSliderMaxValue.asLiveData())
            combinedLiveData.observe(viewLifecycleOwner,{
               binding.calorieSlider.setValues(it.first.toFloat(),it.second.toFloat())
            })
        }
    }

    fun <A, B> LiveData<A>.zipWith(stream: LiveData<B>): LiveData<Pair<A, B>> {
        val result = MediatorLiveData<Pair<A, B>>()
        result.addSource(this) { a ->
            if (a != null && stream.value != null) {
                result.value = Pair(a, stream.value!!)
            }
        }
        result.addSource(stream) { b ->
            if (b != null && this.value != null) {
                result.value = Pair(this.value!!, b)
            }
        }
        return result
    }



    private fun getTimeSliderMaxValue()=lifecycleScope.launch{
        searchOptionsViewModel.timeSliderMaxValue.asLiveData().observe(viewLifecycleOwner,{
            binding.timeSlider.value=it.toFloat()
        })
    }



    private fun getMenuPreference()=lifecycleScope.launch{
        searchOptionsViewModel.selectedSort.asLiveData().observe(viewLifecycleOwner,{
            Log.d("SELECTED SORT POSITION",it)
            val selectedItem=arrayAdapter.getItem(it.toInt())!!
            binding.sortItem.setText(selectedItem,false)
            currentSelectedMenuItem=selectedItem.lowercase()

        })
    }


    private fun getTypeChips()=lifecycleScope.launch {
        searchOptionsViewModel.apply {
            mealTypeChipId.asLiveData().observe(viewLifecycleOwner, {
                val selectedChipId=it.toInt()
                mealTypeChips.check(selectedChipId)
                Log.d("555555555 MEAL TYPE SELECTED CHIP ID",selectedChipId.toString())
                if(selectedChipId!=-1){
                    val chip= mealTypeChips.findViewById<Chip>(selectedChipId)
                    if(chip!=null){
                        currentCheckedMealTypeChipName= chip.text.toString().lowercase()
                    }

                }
            })

            dietTypeChipId.asLiveData().observe(viewLifecycleOwner, {
                val selectedChipId=it.toInt()
                Log.d("444444444 DIET TYPE SELECTED CHIP ID",selectedChipId.toString())
                dietsChipGroup.check(selectedChipId)
                if(selectedChipId!=-1){
                    val chip= dietsChipGroup.findViewById<Chip>(selectedChipId)
                    if(chip!=null){
                        currentCheckedDietTypeChipName= chip.text.toString().lowercase()
                    }

                }
            })
            }
        }



    private fun applyButtonListener(){
        binding.apply {
            applyOptionsButton.setOnClickListener {
                searchOptionsViewModel.apply {
                    saveRangeSliderValue(currentRangeSliderMinValue.toString(), currentRangeSliderMaxValue.toString(), CALORIES_MIN_KEY,CALORIES_MAX_KEY)
                    saveSliderValue(currentSliderMaxValue.toString(), TIME_MAX_KEY)
                    saveSelectedChipId(currentCheckedDietTypeChipId, SELECTED_DIET_CHIP_ID_KEY)
                    Log.d("SAVED SELECTED DIET CHIP ID ",currentCheckedDietTypeChipId.toString())
                    saveSelectedChipId(currentCheckedMealTypeChipId,SELECTED_MEAL_TYPE_CHIP_ID_KEY)
                    Log.d("SAVED SELECTED MEAL CHIP ID ",currentCheckedMealTypeChipId.toString())
                    saveSortType(currentSelectedMenuPosition, SORT_MENU_STATUS_KEY)
                }

                val action = SearchOptionsFragmentDirections.actionSearchOptionsFragmentToHomeFragment(true)
                findNavController().navigate(action)

            }
        }
    }


}