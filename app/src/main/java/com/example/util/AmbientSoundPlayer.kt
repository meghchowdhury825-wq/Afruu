package com.example.util

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.sin

class AmbientSoundPlayer {
    private var audioTrack: AudioTrack? = null
    private var job: Job? = null
    private var isPlaying = false

    fun start(scope: CoroutineScope) {
        if (isPlaying) return
        isPlaying = true

        job = scope.launch(Dispatchers.Default) {
            val sampleRate = 22050
            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize)
                .build()

            audioTrack?.play()

            // Gentle ambient notes: F#4, A4, C#5, E5 pentatonic scale
            val frequencies = doubleArrayOf(369.99, 440.00, 554.37, 659.25, 739.99)
            var noteIndex = 0

            while (isActive && isPlaying) {
                val freq = frequencies[noteIndex % frequencies.size]
                noteIndex++

                val durationSeconds = 1.2
                val numSamples = (durationSeconds * sampleRate).toInt()
                val buffer = ShortArray(numSamples)

                for (i in 0 until numSamples) {
                    val time = i.toDouble() / sampleRate
                    // Sine wave with soft exponential decay
                    val envelope = kotlin.math.exp(-3.0 * time)
                    val wave = sin(2.0 * Math.PI * freq * time)
                    buffer[i] = (wave * envelope * 0.15 * Short.MAX_VALUE).toInt().toShort()
                }

                audioTrack?.write(buffer, 0, buffer.size)
                delay(1500)
            }
        }
    }

    fun stop() {
        isPlaying = false
        job?.cancel()
        try {
            audioTrack?.stop()
            audioTrack?.release()
        } catch (_: Exception) {}
        audioTrack = null
    }

    fun isPlaying(): Boolean = isPlaying
}
