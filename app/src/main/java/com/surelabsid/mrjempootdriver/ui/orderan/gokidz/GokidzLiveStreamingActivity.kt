package com.surelabsid.mrjempootdriver.ui.orderan.gokidz

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityGokidzLiveStreamingBinding
import dagger.hilt.android.AndroidEntryPoint
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas
import io.agora.rtc.video.VideoEncoderConfiguration
import java.lang.Exception

@AndroidEntryPoint
class GokidzLiveStreamingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGokidzLiveStreamingBinding

    private var mRtcEngine: RtcEngine? = null
    private var channelName: String? = null
    private var userRole = Constants.CLIENT_ROLE_BROADCASTER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGokidzLiveStreamingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        channelName = "halo"
//        userRole =

        initAgoraEngineAndJoinChannel()

        initView()

    }

    override fun onDestroy() {
        super.onDestroy()
        mRtcEngine?.leaveChannel()
        RtcEngine.destroy()
        mRtcEngine = null
    }

    private fun initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine()

        mRtcEngine?.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
        mRtcEngine?.setClientRole(userRole)

//        mRtcEngine?.enableVideo()
//        if (userRole == 1) {
//            setupLocalVideo()
//        } else {
//            binding.localVideoViewContainer.isVisible = false
//        }

        setupVideoProfile()
        setupLocalVideo()

        joinChannel()
    }

    private fun setupVideoProfile() {
        mRtcEngine?.enableVideo()
        mRtcEngine?.setVideoEncoderConfiguration(
            VideoEncoderConfiguration(
                VideoEncoderConfiguration.VD_640x480,
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT
            )
        )
    }

    private fun setupLocalVideo() {
        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        surfaceView.setZOrderMediaOverlay(true)
        binding.localVideoViewContainer.addView(surfaceView)
        mRtcEngine?.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0))
    }

    private fun joinChannel() {
        mRtcEngine?.joinChannel(getString(R.string.agora_app_token), channelName, null, 0)
    }

    private fun setupRemoteVideo(uid: Int) {
//        if (binding.remoteVideoViewContainer.childCount >= 1) {
//            return
//        }

        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        binding.remoteVideoViewContainer.addView(surfaceView)
        mRtcEngine?.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0))
        surfaceView.tag = uid
    }

    private fun onRemoteUserLeft() {
        binding.remoteVideoViewContainer.removeAllViews()
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
//        override fun onUserJoined(uid: Int, elapsed: Int) {
//            runOnUiThread {
//                setupRemoteVideo(uid)
//            }
//        }

        override fun onFirstRemoteVideoDecoded(uid: Int, width: Int, height: Int, elapsed: Int) {
            runOnUiThread {
                setupRemoteVideo(uid)
            }
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            runOnUiThread {
                onRemoteUserLeft()
            }
        }



        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            runOnUiThread {
                println("Join channel success : $uid")
            }
        }
    }

    private fun initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(baseContext, getString(R.string.agora_app_id), mRtcEventHandler)
        } catch (e: Exception) {
            println("Exception: ${e.message}")
        }
    }

    private fun initView() {
        with(binding) {
            toolbar.title.text = "Live Streaming"

            ivMute.setOnClickListener {
                if (ivMute.isSelected) {
                    ivMute.isSelected = false
                    ivMute.clearColorFilter()
                } else {
                    ivMute.isSelected = true
                    ivMute.setColorFilter(resources.getColor(R.color.maroon), PorterDuff.Mode.MULTIPLY)
                }

                mRtcEngine?.muteLocalAudioStream(ivMute.isSelected)
            }

            ivCamera.setOnClickListener {
                mRtcEngine?.switchCamera()
            }

            btnEnd.setOnClickListener {
                finish()
            }

        }
    }
}