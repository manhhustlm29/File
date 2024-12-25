package com.example.filemanagement

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.File

class FileActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_FILE_PATH = "EXTRA_FILE_PATH"

        fun newIntent(context: Context, filePath: String): Intent {
            val intent = Intent(context, FileActivity::class.java)
            intent.putExtra(EXTRA_FILE_PATH, filePath)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_list)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
        val filePath = intent.getStringExtra(EXTRA_FILE_PATH)
        if (filePath != null) {
            val file = File(filePath)
            if (file.exists() && file.isFile) {
                val content = file.readText()
                val textView: TextView = findViewById(R.id.txtContentView)
                textView.text = content
            } else {
                finish()
            }
        } else {
            finish() // Invalid file path, close activity
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
