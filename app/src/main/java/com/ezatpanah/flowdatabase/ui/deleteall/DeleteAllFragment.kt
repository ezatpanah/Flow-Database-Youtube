package com.ezatpanah.flowdatabase.ui.deleteall

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ezatpanah.flowdatabase.databinding.FragmentDeleteAllBinding
import com.ezatpanah.flowdatabase.viewmodel.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DeleteAllFragment : DialogFragment() {

    private lateinit var binding: FragmentDeleteAllBinding

    companion object {
        const val TAG = "DeleteAllFragment"
    }

    private val viewModel: DatabaseViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteAllBinding.inflate(inflater, container, false)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            btnPositive.setOnClickListener {
                viewModel.deleteAllContacts()
                dismiss()
            }
            btnNegative.setOnClickListener {
                dismiss()
            }
        }
    }
}

