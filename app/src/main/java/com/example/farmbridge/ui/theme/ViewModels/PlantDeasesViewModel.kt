package com.example.farmbridge.ui.theme.ViewModels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
class PlantDiseaseViewModel : ViewModel() {
    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result

    private var selectedCrop: String = ""

    // Function to set the crop selected by the user
    fun setCrop(crop: String) {
        selectedCrop = crop
    }

    // Function to recognize the disease
    fun recognizeDisease(imageUri: Uri, crop: String, context: Context) {
        // Load the model and make predictions using the imageUri
        val result = predictDisease(imageUri, crop, context)
        _result.value = result
    }

    private fun predictDisease(imageUri: Uri, crop: String, context: Context): String {
        // Initialize TensorFlow Lite interpreter
        val interpreter = Interpreter(loadModelFile(context))
        // Preprocess the image and pass it to the model for inference
        val image = preprocessImage(imageUri, context)
        val output = Array(1) { FloatArray(3) } // Adjust based on your classes (Healthy, Blight, etc.)

        interpreter.run(image, output)
        interpreter.close()

        // Get the class with the highest confidence
        return getPredictedClass(output)
    }

    private fun loadModelFile(context: Context): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("model_unquant.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.length)
    }

    private fun preprocessImage(imageUri: Uri, context: Context, targetSize: Int = 224): ByteBuffer {
        val bitmap = loadBitmapFromUri(imageUri, context)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetSize, targetSize, true)
        return convertBitmapToByteBuffer(resizedBitmap)
    }

    private fun loadBitmapFromUri(imageUri: Uri, context: Context): Bitmap {
        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val inputSize = 224
        val byteBuffer = ByteBuffer.allocateDirect(4 * inputSize * inputSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(inputSize * inputSize)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        var pixel = 0
        for (i in 0 until inputSize) {
            for (j in 0 until inputSize) {
                val `val` = intValues[pixel++]
                val r = (`val` shr 16) and 0xFF
                val g = (`val` shr 8) and 0xFF
                val b = `val` and 0xFF

                byteBuffer.putFloat(r / 255.0f)
                byteBuffer.putFloat(g / 255.0f)
                byteBuffer.putFloat(b / 255.0f)
            }
        }

        return byteBuffer
    }

    private fun getPredictedClass(output: Array<FloatArray>): String {
        val maxIndex = output[0].indices.maxByOrNull { output[0][it] } ?: -1
        return when (maxIndex) {
            0 -> "Healthy"
            1 -> "Blight"
            2 -> "Rust"
            else -> "Unknown"
        }
    }
}
