package com.jay.kotlinapplication

import android.view.View

/**
 * Created by rocklu on 2017/6/15.
 */
class Lambda {
    fun main() {
        val view: View = View()
        view.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                toast("click")
            }

        })
        view.setOnClickListener({view ->  })
    }
    fun setOnClick(listtener: (View) -> Unit) {

    }
}