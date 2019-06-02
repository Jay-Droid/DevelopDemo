package com.jay.developdemo.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.developdemo.R
import com.jay.developdemo.um.UMActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun getDemoData(): List<DemoListAdapter.DemoItem> {
        val demoList: ArrayList<DemoListAdapter.DemoItem> = ArrayList()
        demoList.add(DemoListAdapter.DemoItem("友盟", "友盟系列Demo", UMActivity::class.java))
        demoList.add(DemoListAdapter.DemoItem("Tinker", "Tinker热修复", UMActivity::class.java))
        return demoList

    }

    private fun initView() {
        recyclerview.layoutManager = getLayoutManager()
        recyclerview.adapter = getAdapter()
    }

    private fun getAdapter(): DemoListAdapter {
        return DemoListAdapter(getDemoData(), object : DemoListAdapter.OnItemClickListener {
            override fun onItemClick(item: DemoListAdapter.DemoItem) {
                startActivity(Intent(this@MainActivity, item.activity))
            }
        })
    }

    private fun getLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(this)
    }
}
