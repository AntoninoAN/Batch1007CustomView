package com.example.customedittext


import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat

class CustomEditText(context: Context, attr: AttributeSet):
    AppCompatEditText(context, attr) {

    var mClearButtonImage: Drawable?
    init {
        mClearButtonImage = ResourcesCompat
            .getDrawable(
                resources,
                R.drawable.ic_clear_opaque_24dp,
                null)
        // TODO: If the clear (X) button is tapped, clear the text.
        setOnTouchListener { view, motionEvent ->
            if(compoundDrawablesRelative[2] != null){
                var clearButtonStart: Float? = null
                var clearButtonEnd: Float? = null
                var isClearButtonClicked = false
                if(layoutDirection == View.LAYOUT_DIRECTION_RTL){
                    clearButtonEnd =
                        (mClearButtonImage?.intrinsicWidth?.plus(paddingStart))?.toFloat()
                    if(motionEvent.x < clearButtonEnd!!){
                        isClearButtonClicked = true
                    }
                }else{
                    clearButtonStart = (
                            width - paddingEnd - (mClearButtonImage?.intrinsicWidth?:0)
                            ).toFloat()
                    if(motionEvent.x > clearButtonStart!!){
                        isClearButtonClicked = true
                    }
                }
                if(isClearButtonClicked){
                    if(motionEvent.action == MotionEvent.ACTION_DOWN){
                        mClearButtonImage = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_clear_black_24dp,
                            null)
                        showClearButton()
                    }
                    if(motionEvent.action == MotionEvent.ACTION_UP){
                        mClearButtonImage = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_clear_opaque_24dp,
                            null)
                        text!!.clear()
                        hideClearButton()
                        return@setOnTouchListener true
                    }
                }
                else{
                    return@setOnTouchListener false
                }
            }
            false
        }
        // TODO: If the text changes, show or hide the clear (X) button.
        addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                showClearButton()
            }
        })
    }

    private fun showClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,//start
              null,//top
                   mClearButtonImage,//end
            null//bottom
         )
    }

    private fun hideClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,//start
            null,//top
            null,//end
            null)//bottom
    }
}