package com.caneryilmaz.apps.luckywheelview

import android.animation.ValueAnimator
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.caneryilmaz.apps.luckywheel.constant.RotationStatus
import com.caneryilmaz.apps.luckywheel.data.WheelData
import com.caneryilmaz.apps.luckywheel.ui.LuckyWheelView

class MainActivity : AppCompatActivity() {

    private lateinit var luckyWheelView: LuckyWheelView
    private lateinit var btnRotate: AppCompatButton
//    private lateinit var shineView: View

    private val backgroundColorList = arrayListOf(
        "#F79F1F".toColorInt(),
        "#EC1B24".toColorInt(),
        "#EE5A24".toColorInt(),
        "#000000".toColorInt(),
        "#FFC312".toColorInt(),
        "#EC1B24".toColorInt(),
    )


    private val wheelItems = arrayListOf(
        WheelItem( "5k \nCASH"),      // 1st
        WheelItem( "5k \nCASH"), // 2nd (text)
        WheelItem( "5k \nCASH"),
        WheelItem("Try \nAgain"),// 3rd // 4th (text)
        WheelItem( "5k \nCASH"),
        WheelItem("10k \nCASH"),       // 6th
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        luckyWheelView = findViewById(R.id.luckyWheel)
        btnRotate = findViewById(R.id.btnRotate)
        val shineView = findViewById<View>(R.id.shineView)

        setWheelData()

        startShimmerLoop(btnRotate,shineView)
        btnRotate.setOnClickListener {
            luckyWheelView.rotateWheel()
        }
    }

    private fun setWheelData() {
        val dummyWheelData = ArrayList<WheelData>()

        backgroundColorList.forEachIndexed {i,j->
            val item = WheelData(
                text = wheelItems[i].text,
                textColor = intArrayOf("#ffffff".toColorInt()),
                backgroundColor = intArrayOf(
                    backgroundColorList[i],
                ),
                icon = wheelItems[i].image?.let {BitmapFactory.decodeResource(resources,it)},
            )
            dummyWheelData.add(item)
        }

        luckyWheelView.setWheelCenterImage(R.drawable.ic_spin,100f,100f)

        luckyWheelView.drawWheelStroke(true)
        luckyWheelView.setWheelStrokeThickness(25F)

        luckyWheelView.drawCornerPoints(true)
        luckyWheelView.setCornerPointsEachSlice(6)
        luckyWheelView.setCornerPointsRadius(8f)
        luckyWheelView.setUseRandomCornerPointsColor(false)
        luckyWheelView.setUseCornerPointsGlowEffect(false)
        luckyWheelView.setCornerPointDrawable(
            ContextCompat.getDrawable(this, R.drawable.gradient_yellow_red)
        )
        luckyWheelView.setArrowAnimationStatus(false)


        luckyWheelView.setIconSizeMultiplier(1.6f)
        luckyWheelView.setIconPosition(0.6f)

        luckyWheelView.setTextPositionFraction(0.65f)
        luckyWheelView.setTextFont(Typeface.DEFAULT_BOLD)

        luckyWheelView.setWheelData(wheelData = dummyWheelData)

        luckyWheelView.setRotationCompleteListener { wheelData ->
            // do something with winner wheel data
            Toast.makeText(this, wheelData.text.orEmpty(), Toast.LENGTH_LONG).show()
        }

        luckyWheelView.setRotationStatusListener { status ->
            when (status) {
                RotationStatus.ROTATING -> { // do something
                }

                RotationStatus.IDLE -> { // do something
                }

                RotationStatus.COMPLETED -> { // do something
                }

                RotationStatus.CANCELED -> { // do something
                }
            }
        }
    }
    fun startShimmerLoop(button: View, shine: View) {

        shine.post {
            shine.visibility = View.VISIBLE

            val shineWidth = shine.width.toFloat()
            val startX = -shineWidth
            val endX = button.width.toFloat()

            shine.translationX = startX

            val animator = ValueAnimator.ofFloat(startX, endX).apply {
                duration = 2500            // speed of shine
                interpolator = LinearInterpolator()
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.RESTART

                addUpdateListener {
                    shine.translationX = it.animatedValue as Float
                }
            }

            animator.start()
        }
    }


}