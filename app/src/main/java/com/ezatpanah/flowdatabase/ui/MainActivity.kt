package com.ezatpanah.flowdatabase.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezatpanah.flowdatabase.adapter.ContactsAdapter
import com.ezatpanah.flowdatabase.databinding.ActivityMainBinding
import com.ezatpanah.flowdatabase.db.ContactsEntity
import com.ezatpanah.flowdatabase.ui.add.AddContactFragment
import com.ezatpanah.flowdatabase.utils.DataStatus
import com.ezatpanah.flowdatabase.utils.isVisible
import com.ezatpanah.flowdatabase.viewmodel.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var contactsAdapter: ContactsAdapter

    @Inject
    lateinit var entity: ContactsEntity

    private val viewModel: DatabaseViewModel by viewModels()

    var id = 0
    var name = ""
    var phone = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            btnShowDialog.setOnClickListener {
                    AddContactFragment().show(supportFragmentManager, AddContactFragment().tag)
//                id = 0
//                name = edtName.text.toString()
//                phone = edtPhone.text.toString()
//                if (name.isEmpty() || phone.isEmpty()) {
//
//                } else {
//
//                    entity.id = id
//                    entity.name = name
//                    entity.phone = phone
//
//                    viewModel.saveNote(entity)
//                    edtName.setText("")
//                    edtPhone.setText("")
//                }

            }

            viewModel.getAllNotes()

//            viewModel.contactsList.observe(this@MainActivity) {
//                when (it.status) {
//                    DataStatus.Status.LOADING -> {
//                        loading.isVisible(true, rvContacts)
//                    }
//                    DataStatus.Status.SUCCESS -> {
//                        loading.isVisible(false, rvContacts)
//                        contactsAdapter.setData(it.data)
//                        rvContacts.apply {
//                            layoutManager = LinearLayoutManager(this@MainActivity)
//                            adapter = contactsAdapter
//                        }
//
//                    }
//                    DataStatus.Status.ERROR -> {
//                        loading.isVisible(false, rvContacts)
//
//                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//            }

        }
    }
}