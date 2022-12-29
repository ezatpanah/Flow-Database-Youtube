package com.ezatpanah.flowdatabase.ui.add

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ezatpanah.flowdatabase.databinding.FragmentAddContactBinding
import com.ezatpanah.flowdatabase.db.ContactsEntity
import com.ezatpanah.flowdatabase.viewmodel.DatabaseViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddContactFragment : DialogFragment() {

    @Inject
    lateinit var entity: ContactsEntity

    private val viewModel: DatabaseViewModel by viewModels()

    private lateinit var binding: FragmentAddContactBinding

    private var ContactId = 0
    private var name = ""
    private var phone = ""

    private var type = ""
    private var isEdit = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddContactBinding.inflate(layoutInflater, container, false)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imgClose.setOnClickListener {
                dismiss()
            }

            btnSave.setOnClickListener {

                name = edtName.text.toString()
                phone = edtPhone.text.toString()

                if (name.isEmpty() || phone.isEmpty()) {
                    Snackbar.make(it,"Name and Phone cannot be Empty!",Snackbar.LENGTH_SHORT).show()
                } else {

                    entity.id = ContactId
                    entity.name = name
                    entity.phone = phone

                    viewModel.saveContact(entity)

                    edtName.setText("")
                    edtPhone.setText("")

                    dismiss()
                }
            }
        }
    }

}