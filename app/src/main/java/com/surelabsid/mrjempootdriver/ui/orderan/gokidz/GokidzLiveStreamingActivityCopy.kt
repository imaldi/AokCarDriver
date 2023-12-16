package com.surelabsid.mrjempootdriver.ui.orderan.gokidz

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
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
class GokidzLiveStreamingActivityCopy : AppCompatActivity() {

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


    private fun initAgoraEngineAndJoinChannel() {
        initalizeAgoraEngine()
        mRtcEngine!!.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
        mRtcEngine!!.setClientRole(userRole)
        setupVideoProfile()
        setupLocalVideo()
        joinChannel()
    }

    private fun joinChannel() {
        mRtcEngine!!.joinChannel(
            getString(R.string.agora_app_token),
            channelName,
            "Optional Data",
            0
        )
    }

    private fun initalizeAgoraEngine() {
        try {
            mRtcEngine =
                RtcEngine.create(baseContext, getString(R.string.agora_app_id), mRtcEventHandler)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupVideoProfile() {
        mRtcEngine!!.enableVideo()
        mRtcEngine!!.setVideoEncoderConfiguration(
            VideoEncoderConfiguration(
                VideoEncoderConfiguration.VD_640x480,
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT
            )
        )
    }

    private fun setupLocalVideo() {
        val container = findViewById<View>(R.id.local_video_view_container) as FrameLayout
        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        surfaceView.setZOrderMediaOverlay(true)
        container.addView(surfaceView)
        mRtcEngine!!.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0))
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        override fun onFirstRemoteVideoDecoded(uid: Int, width: Int, height: Int, elapsed: Int) {
            runOnUiThread { setupRemoteVideo(uid) }
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            runOnUiThread { onRemoteUserLeft() }
        }

        override fun onUserMuteVideo(uid: Int, muted: Boolean) {
            runOnUiThread { onRemoteUserVideoMuted(uid, muted) }
        }
    }

    private fun onRemoteUserLeft() {
        val container = findViewById<View>(R.id.remote_video_view_container) as FrameLayout
        container.removeAllViews()
    }

    private fun setupRemoteVideo(uid: Int) {
        val container = findViewById<View>(R.id.remote_video_view_container) as FrameLayout
        //        if (container.getChildCount() > 1) {
//            return;
//        }
        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        container.addView(surfaceView)
        mRtcEngine!!.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid))
    }

    fun onLocalAudioMuteClicked(view: View) {
        val iv = view as ImageView
        if (iv.isSelected) {
            iv.isSelected = false
            iv.clearColorFilter()
        } else {
            iv.isSelected = true
            iv.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY)
        }
        mRtcEngine!!.muteLocalAudioStream(iv.isSelected)
    }

    private fun onRemoteUserVideoMuted(uid: Int, muted: Boolean) {
        val container = findViewById<View>(R.id.remote_video_view_container) as FrameLayout
        val surfaceView = container.getChildAt(0) as SurfaceView
        val tag = surfaceView.tag
        if (tag != null && tag as Int == uid) {
            surfaceView.visibility = if (muted) View.GONE else View.VISIBLE
        }
    }
}