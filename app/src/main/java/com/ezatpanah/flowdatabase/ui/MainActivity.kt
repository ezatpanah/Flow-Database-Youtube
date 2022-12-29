package com.ezatpanah.flowdatabase.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.flowdatabase.R
import com.ezatpanah.flowdatabase.adapter.ContactsAdapter
import com.ezatpanah.flowdatabase.databinding.ActivityMainBinding
import com.ezatpanah.flowdatabase.ui.add.AddContactFragment
import com.ezatpanah.flowdatabase.ui.deleteall.DeleteAllFragment
import com.ezatpanah.flowdatabase.utils.DataStatus
import com.ezatpanah.flowdatabase.utils.SwipeToDelete
import com.ezatpanah.flowdatabase.utils.isVisible
import com.ezatpanah.flowdatabase.viewmodel.DatabaseViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var contactsAdapter: ContactsAdapter

//    @Inject
//    lateinit var entity: ContactsEntity

    private val viewModel: DatabaseViewModel by viewModels()

    private var selectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {

            setSupportActionBar(toolbar)

            btnShowDialog.setOnClickListener {
                AddContactFragment().show(supportFragmentManager, AddContactFragment().tag)
            }

            viewModel.getAllContacts()
            viewModel.contactsList.observe(this@MainActivity) {

                when (it.status) {
                    DataStatus.Status.LOADING -> {
                        loading.isVisible(true, rvContacts)
                        emptyBody.isVisible(false, rvContacts)
                    }
                    DataStatus.Status.SUCCESS -> {
                        it.isEmpty?.let { isEmpty -> showEmpty(isEmpty) }
                        loading.isVisible(false, rvContacts)
                        contactsAdapter.differ.submitList(it.data)
                        rvContacts.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = contactsAdapter
                        }
                    }
                    DataStatus.Status.ERROR -> {
                        loading.isVisible(false, rvContacts)
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionDeleteAll -> {
                        DeleteAllFragment().show(supportFragmentManager, DeleteAllFragment.TAG)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.actionSort -> {
                        filter()
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }

            val swipeToDeleteCallback = object : SwipeToDelete(this@MainActivity) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val pos = viewHolder.adapterPosition
                    val contact = contactsAdapter.differ.currentList[pos]
                    viewModel.deleteContact(contact)
                    Snackbar.make(binding.root, "Item Deleted!", Snackbar.LENGTH_LONG).apply {
                        setAction("UNDO") {
                            viewModel.saveContact(contact)
                        }
                    }.show()

                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(rvContacts)

        }
    }

    private fun showEmpty(isShown: Boolean) {
        binding.apply {
            if (isShown) {
                emptyBody.visibility = View.VISIBLE
                listBody.visibility = View.GONE
            } else {
                emptyBody.visibility = View.GONE
                listBody.visibility = View.VISIBLE
            }
        }
    }

    private fun filter() {
        val builder = AlertDialog.Builder(this)
        val priories = arrayOf("Newer(Default)", "Name : A-Z", "Name Z-A")
        builder.setSingleChoiceItems(priories, selectedItem) { dialog, item ->
            when (item) {
                0 -> {
                    viewModel.getAllContacts()
                }
                1 -> {
                    viewModel.getSortedListASC()
                }
                2 -> {
                    viewModel.getSortedListDESC()
                }
            }
            selectedItem = item
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getSearchContacts(newText!!)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}