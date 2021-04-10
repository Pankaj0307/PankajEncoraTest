package com.pankaj.encora.views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pankaj.encora.R
import com.pankaj.encora.database.SongsEntity
import com.pankaj.encora.databinding.HomeFragmentBinding
import com.pankaj.encora.interfaces.HomeInterface
import com.pankaj.encora.viewmodels.HomeViewModel
import com.pankaj.encora.views.adapters.OnEntryClickListener
import com.pankaj.encora.views.adapters.SongsAdapter
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(), HomeInterface {

    private lateinit var data: ArrayList<SongsEntity>
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var songsAdapter: SongsAdapter
    private lateinit var sharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.homeInterface = this
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
        val isSyncDone = sharedPref.getBoolean(getString(R.string.isApiCalled), false)
        viewModel.getTopSongs(isSyncDone)

        setDataChangeListener(viewModel)
    }

    private fun setDataChangeListener(viewModel: HomeViewModel) {
        viewModel.entryList.observe(requireActivity(), Observer {
            this.data.clear()
            this.data.addAll(it)
            viewModel.isProgressVisible.postValue(false)
            recyclerView.adapter?.notifyDataSetChanged()
        })
    }

    /* init the all related views and listener
    * */
    private fun initViews() {
        data = ArrayList()
        initRecyclerView()
    }

    /*
   * init the recycler view with layout manager and set the adapter
   *  adapter also handle the on item tap click lister
    */
    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            songsAdapter = SongsAdapter(data, object : OnEntryClickListener {
                override fun onClickEntry(entryEntity: SongsEntity?) {
                    //navigate to songs details screen
                    val bundle =
                        bundleOf(
                            SongDetailFragment.SongsObj to entryEntity
                        )
                    findNavController().navigate(R.id.actionSongDetailFragment, bundle)
                }
            })
            adapter = songsAdapter
        }
    }

    override fun showError(title: String, message: String) {
//        showAlertDialog(title, message)
        showSnackBar(binding.recyclerView, message)
    }

    override fun updateData(value: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.isApiCalled), value)
            commit()
        }
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(
            view,
            message, Snackbar.LENGTH_LONG
        ).show()
    }
}