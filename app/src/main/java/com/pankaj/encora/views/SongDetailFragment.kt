package com.pankaj.encora.views

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.pankaj.encora.R
import com.pankaj.encora.databinding.SongDetailFragmentBinding
import com.pankaj.encora.viewmodels.SongDetailViewModel
import kotlinx.android.synthetic.main.song_detail_fragment.*

class SongDetailFragment : Fragment() {

    companion object {
        const val SongsObj = "songsObj"
    }

    private lateinit var viewModel: SongDetailViewModel
    private lateinit var binding: SongDetailFragmentBinding
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var mediaSource: MediaSource
    private lateinit var dataSourceFactory: DefaultDataSourceFactory
    var isAudioPlaying = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.song_detail_fragment, container, false)
        viewModel = ViewModelProvider(this)[SongDetailViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            viewModel.entryLiveData.value = it.getParcelable(SongsObj)
        }
        init()
    }

    private fun init() {
        setupExoPlayer()
    }

    private fun setupExoPlayer() {
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(activity)

        dataSourceFactory =
            DefaultDataSourceFactory(
                activity,
                Util.getUserAgent(activity, getString(R.string.app_name))
            )

        mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(viewModel.entryLiveData.value?.audioLink))
        simpleExoPlayer.prepare(mediaSource)
        with(simpleExoPlayer) {
            btn_play_pause.setOnClickListener {
                if (isAudioPlaying) {
                    pauseSong()
                    playWhenReady = false
                } else {
                    playWhenReady = true
                    playSong()
                }
            }
        }
    }

    private fun playSong() {
        isAudioPlaying = true
        btn_play_pause.setImageResource(R.drawable.pause_white)
    }

    private fun pauseSong() {
        isAudioPlaying = false
        btn_play_pause.setImageResource(R.drawable.play_arrow_white)
    }

    override fun onDestroy() {
        simpleExoPlayer.playWhenReady = false
        super.onDestroy()
    }
}