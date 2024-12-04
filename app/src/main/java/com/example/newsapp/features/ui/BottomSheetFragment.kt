package com.example.newsapp.features.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.R
import androidx.fragment.app.activityViewModels
import com.example.newsapp.databinding.FragmentBottomSheetBinding
import com.example.newsapp.features.viewmodel.NewsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!! // Access the binding instance
    private var bottomSheetClick: BottomSheetClick? = null

    fun setUpClicked(bottomSheetClick: BottomSheetClick?) {
        this.bottomSheetClick = bottomSheetClick
    }

    // Get the ViewModel from the activity scope
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        radioButtonsAndTitleConditions()

        // Observe the LiveData from the ViewModel
        viewModel.lastSelectedOption.observe(viewLifecycleOwner) { selectedOption ->
            viewModel.resetState()
            viewModel.resetPagination()
            viewModel.getAllNews()
        }

        // Set up listener to handle selection changes
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioTechnology.id -> {
                    // Update the ViewModel with the selected option
                    viewModel.lastSelectedOption.value = "Technology"
                    bottomSheetClick?.radioButtonClick("Technology")
                    binding.radioTechnology.isChecked = true
                    binding.selectedOptionText.text = "Last selected: Technology"
                    dismiss() // Properly dismiss the BottomSheet
                }

                binding.radioBusiness.id -> {
                    // Update the ViewModel with the selected option
                    binding.radioBusiness.isChecked = true
                    viewModel.lastSelectedOption.value = "Business"
                    bottomSheetClick?.radioButtonClick("Business")
                    binding.selectedOptionText.text = "Last selected: Business"
                    dismiss() // Properly dismiss the BottomSheet
                }
            }
        }

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    private fun radioButtonsAndTitleConditions() {
        if (viewModel.lastSelectedOption.value == "Business") {
            binding.radioBusiness.isChecked = true
            binding.selectedOptionText.text = "Last selected: Business"
        } else {
            binding.radioTechnology.isChecked = true
            binding.selectedOptionText.text = "Last selected: Technology"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks by nullifying the binding
    }
}

interface BottomSheetClick {
    fun radioButtonClick(title: String)
}
