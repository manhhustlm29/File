package com.example.filemanagement

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanagement.adapter.FileAdapter
import com.example.filemanagement.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val path = Environment.getExternalStorageDirectory().path
        val directory = File(path)
        if (directory.exists() && directory.isDirectory) {
            val txtFiles = fetchTxtFiles(directory)
            val fileAdapter = FileAdapter(txtFiles) { file ->
                val intent = FileActivity.newIntent(this, file.absolutePath)
                startActivity(intent)
            }
            recyclerView.adapter = fileAdapter
        } else {
            Toast.makeText(this, "Directory not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchTxtFiles(directory: File): List<File> {
        return directory.listFiles { file -> file.isFile && file.extension == "txt" }?.toList() ?: emptyList()
    }

}