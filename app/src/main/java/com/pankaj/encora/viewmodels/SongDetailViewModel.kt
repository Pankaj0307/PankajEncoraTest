package com.pankaj.encora.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.pankaj.encora.database.SongsEntity

/**viewmodel for song detail screen
 * */
class SongDetailViewModel(application: Application) : BaseViewModel(application) {
    var entryLiveData = MutableLiveData<SongsEntity>()
}