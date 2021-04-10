package com.pankaj.encora.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pankaj.encora.database.AppDatabase
import com.pankaj.encora.database.SongsEntity
import com.pankaj.encora.database.mapDto
import com.pankaj.encora.interfaces.HomeInterface
import com.pankaj.encora.network.APIResult
import com.pankaj.encora.network.SongsImp
import com.pankaj.encora.network.SongsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : BaseViewModel(application) {
    private val appDatabase = AppDatabase.getDatabase(application)
    private var songsRepository = SongsRepository(SongsImp(appDatabase))
    var homeInterface: HomeInterface? = null
    var entryList = MutableLiveData<List<SongsEntity>>()

    fun getTopSongs(isSyncDone: Boolean = false) {
        isProgressVisible.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            if (isSyncDone) {
                getDataFromDB()
            } else {
                getDataFromServer()
            }
        }
    }

    private suspend fun getDataFromDB() {
        entryList.postValue(songsRepository.getTopSongsFromDB())
    }

    private suspend fun getDataFromServer() {
        val response = songsRepository.getTopSongs()
        withContext(Dispatchers.Main) {
            isProgressVisible.postValue(false)
            if (response.status == APIResult.Status.SUCCESS && response.data != null) {
                response.data.entryList?.map { it.mapDto() }?.let {
                    appDatabase.entryDao().insertEntry(
                        it
                    )
                    homeInterface?.updateData(true)
                }
                getDataFromDB()
            } else {
                homeInterface?.updateData(false)
                homeInterface?.showError("Error", response.message.toString())
            }
        }
    }
}