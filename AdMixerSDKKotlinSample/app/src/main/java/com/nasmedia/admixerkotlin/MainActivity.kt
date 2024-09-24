package com.nasmedia.admixerkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    lateinit var menuAdapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        menuAdapter = MenuAdapter()

        menuAdapter.addItem("일반배너1")
        menuAdapter.addItem("일반배너2")
        menuAdapter.addItem("전면배너")
        menuAdapter.addItem("네이티브")
        menuAdapter.addItem("동영상")
        menuAdapter.addItem("전면동영상")
        menuAdapter.addItem("전면리워드동영상")

        recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView.adapter = menuAdapter
    }

    class MenuAdapter : RecyclerView.Adapter<ViewHolder>() {

        private var menuList = ArrayList<String>()

        override fun getItemCount(): Int {
            return menuList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val context: Context = parent.context
            val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(R.layout.item_menu, parent, false)
            return ViewHolder(context, view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val tvMenu: TextView = holder.tvMenu

            tvMenu.text = menuList[position]
        }

        fun addItem(item: String) {
            menuList.add(item)
        }

    }

    class ViewHolder(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvMenu = itemView.findViewById(R.id.tv_menu) as TextView

        init {
            tvMenu.setOnClickListener {
                when (adapterPosition) {
                    0 -> context.startActivity(Intent(context, BannerActivity::class.java))
                    1 -> context.startActivity(Intent(context, Banner2Activity::class.java))
                    2 -> context.startActivity(Intent(context, InterstitialActivity::class.java))
                    3 -> context.startActivity(Intent(context, NativeActivity::class.java))
                    4 -> context.startActivity(Intent(context, VideoActivity::class.java))
                    5 -> context.startActivity(Intent(context, InterstitialVideoActivity::class.java))
                    6 -> context.startActivity(Intent(context, RewardInterstitialVideoActivity::class.java))
                }
            }
        }
    }
}