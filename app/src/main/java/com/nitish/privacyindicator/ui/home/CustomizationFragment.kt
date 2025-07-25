package com.nitish.privacyindicator.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nitish.privacyindicator.R
import com.nitish.privacyindicator.databinding.ContentCustomizationBinding
import com.nitish.privacyindicator.databinding.FragmentCustomizationBinding
import com.nitish.privacyindicator.helpers.setViewTint
import com.nitish.privacyindicator.helpers.updateOpacity
import com.nitish.privacyindicator.helpers.updateSize
import com.nitish.privacyindicator.models.IndicatorOpacity
import com.nitish.privacyindicator.models.IndicatorPosition
import com.nitish.privacyindicator.models.IndicatorSize


class CustomizationFragment : Fragment(R.layout.fragment_customization) {

    lateinit var binding: FragmentCustomizationBinding

    lateinit var viewModel: HomeViewModel

    lateinit var customizationBinding: ContentCustomizationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCustomizationBinding.bind(view)

        customizationBinding = binding.contentCustomization
        viewModel = (activity as HomeActivity).viewModel

        setUpView()
        setUpObservers()
        setUpListeners()
    }

    private fun setUpView() {
        customizationBinding.switchHorizontalHeight.isChecked = viewModel.indicatorPosition.value!!.horizontal == 1
        customizationBinding.switchVerticalHeight.isChecked = viewModel.indicatorPosition.value!!.vertical == 1
//        customizationBinding.switchIndicatorSize.isChecked = viewModel.indicatorSize.value == IndicatorSize.LARGE
//        customizationBinding.switchOpacity.isChecked = viewModel.indicatorOpacity.value == IndicatorOpacity.HIGH
    }

    private fun setUpObservers() {
        viewModel.indicatorForegroundColor.observe(viewLifecycleOwner, {
            customizationBinding.tileForeGround.setViewTint(it)
            binding.indicatorsLayout.ivCam.setViewTint(it)
            binding.indicatorsLayout.ivMic.setViewTint(it)
            binding.indicatorsLayout.ivLoc.setViewTint(it)
        })

        viewModel.indicatorBackgroundColor.observe(viewLifecycleOwner, {
            customizationBinding.tileBackGround.setViewTint(it)
            binding.indicatorsLayout.llBackground.setBackgroundColor(Color.parseColor(it))
        })

        viewModel.indicatorSize.observe(viewLifecycleOwner, {
            binding.indicatorsLayout.ivCam.updateSize(it.size)
            binding.indicatorsLayout.ivMic.updateSize(it.size)
            binding.indicatorsLayout.ivLoc.updateSize(it.size)
        })

        viewModel.indicatorOpacity.observe(viewLifecycleOwner, {
            binding.indicatorsLayout.root.updateOpacity(it.opacity)
        })
    }

    private fun setUpListeners() {
        customizationBinding.switchVerticalHeight.setOnCheckedChangeListener { _, isChecked ->
            val vertical = if (isChecked) 1 else 0
            val horizontal = if (customizationBinding.switchHorizontalHeight.isChecked) 1 else 0
            viewModel.setIndicatorPosition(IndicatorPosition.getIndicatorPosition(vertical, horizontal))
        }
        customizationBinding.switchHorizontalHeight.setOnCheckedChangeListener { _, isChecked ->
            val horizontal = if (isChecked) 1 else 0
            val vertical = if (customizationBinding.switchVerticalHeight.isChecked) 1 else 0
            viewModel.setIndicatorPosition(IndicatorPosition.getIndicatorPosition(vertical, horizontal))
        }

//        customizationBinding.switchIndicatorSize.setOnCheckedChangeListener { _, isChecked ->
//            val size = if (isChecked) IndicatorSize.LARGE else IndicatorSize.SMALL
//            viewModel.setIndicatorSize(size)
//        }
//        customizationBinding.switchOpacity.setOnCheckedChangeListener { _, isChecked ->
//            val opacity = if (isChecked) IndicatorOpacity.HIGH else IndicatorOpacity.LOW
//            viewModel.setIndicatorOpacity(opacity)
//        }

    }
}