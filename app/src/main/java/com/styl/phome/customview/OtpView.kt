/*
 * (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved
 * This source code and any compilation or derivative thereof is the sole
 * property of STYL Solutions Pte. Ltd. and is provided pursuant to a
 * Software License Agreement.  This code is the proprietary information of
 * STYL Solutions Pte. Ltd. and is confidential in nature. Its use and
 * dissemination by any party other than STYL Solutions Pte. Ltd. is strictly
 * limited by the confidential information provisions of the Agreement
 * referenced above.
 */

package com.styl.phome.customview

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.MovementMethod
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import com.styl.phome.R.*

class OtpView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = attr.otpViewStyle
) :
    AppCompatEditText(context, attrs, defStyleAttr) {
    private val viewType: Int
    private var otpViewItemCount: Int
    private var otpViewItemWidth: Int
    private var otpViewItemHeight: Int
    private var otpViewItemRadius: Int
    private var otpViewItemSpacing: Int
    private val paint: Paint
    private val animatorTextPaint: TextPaint?
    var lineColors: ColorStateList?
        private set

    @get:ColorInt
    var currentLineColor: Int
        private set
    private var lineWidth: Int
    private val textRect: Rect
    private val itemBorderRect: RectF
    private val itemLineRect: RectF
    private val path: Path
    private val itemCenterPoint: PointF
    private var defaultAddAnimator: ValueAnimator? = null
    private var isAnimationEnable: Boolean
    private var blink: Blink? = null
    private var isCursorVisible: Boolean
    private var drawCursor = false
    private var cursorHeight = 0f
    private var cursorWidth: Int
    private var cursorColor: Int
    private var itemBackgroundResource = 0
    private var itemBackground: Drawable?
    private var hideLineWhenFilled: Boolean
    private var onOtpCompletionListener: OnOtpCompletionListener? = null
    override fun setTypeface(tf: Typeface?, style: Int) {
        super.setTypeface(tf, style)
    }

    override fun setTypeface(tf: Typeface?) {
        super.setTypeface(tf)
        if (animatorTextPaint != null) {
            animatorTextPaint.set(getPaint())
        }
    }

    private fun setMaxLength(maxLength: Int) {
        this.filters =
            if (maxLength >= 0) arrayOf<InputFilter>(LengthFilter(maxLength)) else NO_FILTERS
    }

    private fun setupAnimator() {
        defaultAddAnimator = ValueAnimator.ofFloat(0.5f, 1.0f)
        defaultAddAnimator?.duration = 150L
        defaultAddAnimator?.interpolator = DecelerateInterpolator()
        defaultAddAnimator?.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            val alpha = (255.0f * scale).toInt()
            animatorTextPaint!!.textSize = this@OtpView.textSize * scale
            animatorTextPaint.alpha = alpha
            this@OtpView.postInvalidate()
        }
    }

    private fun checkItemRadius() {
        val halfOfItemWidth: Float
        if (viewType == 1) {
            halfOfItemWidth = lineWidth.toFloat() / 2.0f
            require(otpViewItemRadius.toFloat() <= halfOfItemWidth) { "The itemRadius can not be greater than lineWidth when viewType is line" }
        } else if (viewType == 0) {
            halfOfItemWidth = otpViewItemWidth.toFloat() / 2.0f
            require(otpViewItemRadius.toFloat() <= halfOfItemWidth) { "The itemRadius can not be greater than itemWidth" }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val boxHeight = otpViewItemHeight
        var width: Int
        if (widthMode == 1073741824) {
            width = widthSize
        } else {
            val boxesWidth =
                (otpViewItemCount - 1) * otpViewItemSpacing + otpViewItemCount * otpViewItemWidth
            width = boxesWidth + ViewCompat.getPaddingEnd(this) + ViewCompat.getPaddingStart(this)
            if (otpViewItemSpacing == 0) {
                width -= (otpViewItemCount - 1) * lineWidth
            }
        }
        val height =
            if (heightMode == 1073741824) heightSize else boxHeight + this.paddingTop + this.paddingBottom
        setMeasuredDimension(width, height)
    }

    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (start != text.length) {
            moveSelectionToEnd()
        }
        if (text.length == otpViewItemCount && onOtpCompletionListener != null) {
            onOtpCompletionListener!!.onOtpCompleted(text.toString())
        }
        makeBlink()
        if (isAnimationEnable) {
            val isAdd = lengthAfter - lengthBefore > 0
            if (isAdd && defaultAddAnimator != null) {
                defaultAddAnimator!!.end()
                defaultAddAnimator!!.start()
            }
        }
    }

    override fun onFocusChanged(
        focused: Boolean,
        direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            moveSelectionToEnd()
            makeBlink()
        }
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if (selEnd != this.text!!.length) {
            moveSelectionToEnd()
        }
    }

    private fun moveSelectionToEnd() {
        this.setSelection(this.text!!.length)
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        if (lineColors == null || lineColors!!.isStateful) {
            updateColors()
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        updatePaints()
        drawOtpView(canvas)
        canvas.restore()
    }

    private fun updatePaints() {
        paint.color = currentLineColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = lineWidth.toFloat()
        getPaint().color = this.currentTextColor
    }

    private fun drawOtpView(canvas: Canvas) {
        val highlightIdx = this.text!!.length
        var i: Int
        i = 0
        while (i < otpViewItemCount) {
            val highlight = this.isFocused && highlightIdx == i
            paint.color =
                if (highlight) getLineColorForState(*HIGHLIGHT_STATES) else currentLineColor
            updateItemRectF(i)
            updateCenterPoint()
            canvas.save()
            if (viewType == 0) {
                updateOtpViewBoxPath(i)
                canvas.clipPath(path)
            }
            drawItemBackground(canvas, highlight)
            canvas.restore()
            if (highlight) {
                drawCursor(canvas)
            }
            if (viewType == 0) {
                drawOtpBox(canvas, i)
            } else if (viewType == 1) {
                drawOtpLine(canvas, i)
            }
            if (this.text!!.length > i) {
                if (isPasswordInputType(this.inputType)) {
                    drawCircle(canvas, i)
                } else {
                    drawText(canvas, i)
                }
            } else if (!TextUtils.isEmpty(this.hint) && this.hint.length == otpViewItemCount) {
                drawHint(canvas, i)
            }
            ++i
        }
        if (this.isFocused && this.text!!.length != otpViewItemCount && viewType == 0) {
            i = this.text!!.length
            updateItemRectF(i)
            updateCenterPoint()
            updateOtpViewBoxPath(i)
            paint.color = getLineColorForState(*HIGHLIGHT_STATES)
            drawOtpBox(canvas, i)
        }
    }

    private fun getLineColorForState(vararg states: Int): Int {
        return if (lineColors != null) lineColors!!.getColorForState(
            states,
            currentLineColor
        ) else currentLineColor
    }

    private fun drawItemBackground(
        canvas: Canvas,
        highlight: Boolean
    ) {
        if (itemBackground != null) {
            val delta = lineWidth.toFloat() / 2.0f
            val left = Math.round(itemBorderRect.left - delta)
            val top = Math.round(itemBorderRect.top - delta)
            val right = Math.round(itemBorderRect.right + delta)
            val bottom = Math.round(itemBorderRect.bottom + delta)
            itemBackground!!.setBounds(left, top, right, bottom)
            itemBackground!!.state = if (highlight) HIGHLIGHT_STATES else this.drawableState
            itemBackground!!.draw(canvas)
        }
    }

    private fun updateOtpViewBoxPath(i: Int) {
        var drawRightCorner = false
        var drawLeftCorner = false
        if (otpViewItemSpacing != 0) {
            drawRightCorner = true
            drawLeftCorner = true
        } else {
            if (i == 0 && i != otpViewItemCount - 1) {
                drawLeftCorner = true
            }
            if (i == otpViewItemCount - 1 && i != 0) {
                drawRightCorner = true
            }
        }
        this.updateRoundRectPath(
            itemBorderRect,
            otpViewItemRadius.toFloat(),
            otpViewItemRadius.toFloat(),
            drawLeftCorner,
            drawRightCorner
        )
    }

    private fun drawOtpBox(canvas: Canvas, i: Int) {
        if (!hideLineWhenFilled || i >= this.text!!.length) {
            canvas.drawPath(path, paint)
        }
    }

    private fun drawOtpLine(canvas: Canvas, i: Int) {
        if (!hideLineWhenFilled || i >= this.text!!.length) {
            var drawRight = true
            var drawLeft = true
            if (otpViewItemSpacing == 0 && otpViewItemCount > 1) {
                if (i == 0) {
                    drawRight = false
                } else if (i == otpViewItemCount - 1) {
                    drawLeft = false
                } else {
                    drawRight = false
                    drawLeft = false
                }
            }
            paint.style = Paint.Style.FILL
            paint.strokeWidth = lineWidth.toFloat() / 10.0f
            val halfLineWidth = lineWidth.toFloat() / 2.0f
            itemLineRect[itemBorderRect.left - halfLineWidth, itemBorderRect.bottom - halfLineWidth, itemBorderRect.right + halfLineWidth] =
                itemBorderRect.bottom + halfLineWidth
            this.updateRoundRectPath(
                itemLineRect,
                otpViewItemRadius.toFloat(),
                otpViewItemRadius.toFloat(),
                drawLeft,
                drawRight
            )
            canvas.drawPath(path, paint)
        }
    }

    private fun drawCursor(canvas: Canvas) {
        if (drawCursor) {
            val cx = itemCenterPoint.x
            val cy = itemCenterPoint.y
            val y = cy - cursorHeight / 2.0f
            val color = paint.color
            val width = paint.strokeWidth
            paint.color = cursorColor
            paint.strokeWidth = cursorWidth.toFloat()
            canvas.drawLine(cx, y, cx, y + cursorHeight, paint)
            paint.color = color
            paint.strokeWidth = width
        }
    }

    private fun updateRoundRectPath(
        rectF: RectF,
        rx: Float,
        ry: Float,
        l: Boolean,
        r: Boolean
    ) {
        this.updateRoundRectPath(rectF, rx, ry, l, r, r, l)
    }

    private fun updateRoundRectPath(
        rectF: RectF,
        rx: Float,
        ry: Float,
        tl: Boolean,
        tr: Boolean,
        br: Boolean,
        bl: Boolean
    ) {
        path.reset()
        val l = rectF.left
        val t = rectF.top
        val r = rectF.right
        val b = rectF.bottom
        val w = r - l
        val h = b - t
        val lw = w - 2.0f * rx
        val lh = h - 2.0f * ry
        path.moveTo(l, t + ry)
        if (tl) {
            path.rQuadTo(0.0f, -ry, rx, -ry)
        } else {
            path.rLineTo(0.0f, -ry)
            path.rLineTo(rx, 0.0f)
        }
        path.rLineTo(lw, 0.0f)
        if (tr) {
            path.rQuadTo(rx, 0.0f, rx, ry)
        } else {
            path.rLineTo(rx, 0.0f)
            path.rLineTo(0.0f, ry)
        }
        path.rLineTo(0.0f, lh)
        if (br) {
            path.rQuadTo(0.0f, ry, -rx, ry)
        } else {
            path.rLineTo(0.0f, ry)
            path.rLineTo(-rx, 0.0f)
        }
        path.rLineTo(-lw, 0.0f)
        if (bl) {
            path.rQuadTo(-rx, 0.0f, -rx, -ry)
        } else {
            path.rLineTo(-rx, 0.0f)
            path.rLineTo(0.0f, -ry)
        }
        path.rLineTo(0.0f, -lh)
        path.close()
    }

    private fun updateItemRectF(i: Int) {
        val halfLineWidth = lineWidth.toFloat() / 2.0f
        var left =
            (this.scrollX + ViewCompat.getPaddingStart(this) + i * (otpViewItemSpacing + otpViewItemWidth)).toFloat() + halfLineWidth
        if (otpViewItemSpacing == 0 && i > 0) {
            left -= (lineWidth * i).toFloat()
        }
        val right =
            left + otpViewItemWidth.toFloat() - lineWidth.toFloat()
        val top =
            (this.scrollY + this.paddingTop).toFloat() + halfLineWidth
        val bottom =
            top + otpViewItemHeight.toFloat() - lineWidth.toFloat()
        itemBorderRect[left, top, right] = bottom
    }

    private fun drawText(canvas: Canvas, i: Int) {
        val paint = getPaintByIndex(i)
        drawTextAtBox(canvas, paint, this.text, i)
    }

    private fun drawHint(canvas: Canvas, i: Int) {
        val paint = getPaintByIndex(i)
        paint!!.color = this.currentHintTextColor
        drawTextAtBox(canvas, paint, this.hint, i)
    }

    private fun drawTextAtBox(
        canvas: Canvas,
        paint: Paint?,
        text: CharSequence?,
        charAt: Int
    ) {
        paint!!.getTextBounds(text.toString(), charAt, charAt + 1, textRect)
        val cx = itemCenterPoint.x
        val cy = itemCenterPoint.y
        val x =
            cx - Math.abs(textRect.width().toFloat()) / 2.0f - textRect.left.toFloat()
        val y =
            cy + Math.abs(textRect.height().toFloat()) / 2.0f - textRect.bottom.toFloat()
        canvas.drawText(text ?: "", charAt, charAt + 1, x, y, paint)
    }

    private fun drawCircle(canvas: Canvas, i: Int) {
        val paint = getPaintByIndex(i)
        val cx = itemCenterPoint.x
        val cy = itemCenterPoint.y
        canvas.drawCircle(cx, cy, paint!!.textSize / 2.0f, paint)
    }

    private fun getPaintByIndex(i: Int): Paint? {
        return if (isAnimationEnable && i == this.text!!.length - 1) {
            animatorTextPaint!!.color = getPaint().color
            animatorTextPaint
        } else {
            getPaint()
        }
    }

    private fun drawAnchorLine(canvas: Canvas) {
        var cx = itemCenterPoint.x
        var cy = itemCenterPoint.y
        paint.strokeWidth = 1.0f
        cx -= paint.strokeWidth / 2.0f
        cy -= paint.strokeWidth / 2.0f
        path.reset()
        path.moveTo(cx, itemBorderRect.top)
        path.lineTo(
            cx,
            itemBorderRect.top + Math.abs(itemBorderRect.height())
        )
        canvas.drawPath(path, paint)
        path.reset()
        path.moveTo(itemBorderRect.left, cy)
        path.lineTo(
            itemBorderRect.left + Math.abs(itemBorderRect.width()),
            cy
        )
        canvas.drawPath(path, paint)
        path.reset()
        paint.strokeWidth = lineWidth.toFloat()
    }

    private fun updateColors() {
        var shouldInvalidate = false
        val color = if (lineColors != null) lineColors!!.getColorForState(
            this.drawableState,
            0
        ) else this.currentTextColor
        if (color != currentLineColor) {
            currentLineColor = color
            shouldInvalidate = true
        }
        if (shouldInvalidate) {
            this.invalidate()
        }
    }

    private fun updateCenterPoint() {
        val cx =
            itemBorderRect.left + Math.abs(itemBorderRect.width()) / 2.0f
        val cy =
            itemBorderRect.top + Math.abs(itemBorderRect.height()) / 2.0f
        itemCenterPoint[cx] = cy
    }

    override fun getDefaultMovementMethod(): MovementMethod? {
        return DefaultMovementMethod.instance
    }

    fun setLineColor(@ColorInt color: Int) {
        lineColors = ColorStateList.valueOf(color)
        updateColors()
    }

    fun setLineColor(colors: ColorStateList?) {
        requireNotNull(colors) { "Color cannot be null" }
        lineColors = colors
        updateColors()
    }

    fun setLineWidth(@Px borderWidth: Int) {
        lineWidth = borderWidth
        checkItemRadius()
        requestLayout()
    }

    fun getLineWidth(): Int {
        return lineWidth
    }

    var itemCount: Int
        get() = otpViewItemCount
        set(count) {
            otpViewItemCount = count
            setMaxLength(count)
            requestLayout()
        }

    var itemRadius: Int
        get() = otpViewItemRadius
        set(itemRadius) {
            otpViewItemRadius = itemRadius
            checkItemRadius()
            requestLayout()
        }

    @get:Px
    var itemSpacing: Int
        get() = otpViewItemSpacing
        set(itemSpacing) {
            otpViewItemSpacing = itemSpacing
            requestLayout()
        }

    var itemHeight: Int
        get() = otpViewItemHeight
        set(itemHeight) {
            otpViewItemHeight = itemHeight
            updateCursorHeight()
            requestLayout()
        }

    var itemWidth: Int
        get() = otpViewItemWidth
        set(itemWidth) {
            otpViewItemWidth = itemWidth
            checkItemRadius()
            requestLayout()
        }

    fun setAnimationEnable(enable: Boolean) {
        isAnimationEnable = enable
    }

    fun setHideLineWhenFilled(hideLineWhenFilled: Boolean) {
        this.hideLineWhenFilled = hideLineWhenFilled
    }

    override fun setTextSize(size: Float) {
        super.setTextSize(size)
        updateCursorHeight()
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        updateCursorHeight()
    }

    fun setOtpCompletionListener(otpCompletionListener: OnOtpCompletionListener?) {
        onOtpCompletionListener = otpCompletionListener
    }

    fun setItemBackgroundResources(@DrawableRes resId: Int) {
        if (resId == 0 || itemBackgroundResource == resId) {
            itemBackground = ResourcesCompat.getDrawable(
                this.resources,
                resId,
                this.context.theme
            )
            setItemBackground(itemBackground)
            itemBackgroundResource = resId
        }
    }

    fun setItemBackgroundColor(@ColorInt color: Int) {
        if (itemBackground is ColorDrawable) {
            (itemBackground?.mutate() as ColorDrawable).color = color
            itemBackgroundResource = 0
        } else {
            setItemBackground(ColorDrawable(color))
        }
    }

    fun setItemBackground(background: Drawable?) {
        itemBackgroundResource = 0
        itemBackground = background
        this.invalidate()
    }

    fun setCursorWidth(@Px width: Int) {
        cursorWidth = width
        if (isCursorVisible()) {
            invalidateCursor(true)
        }
    }

    fun getCursorWidth(): Int {
        return cursorWidth
    }

    fun setCursorColor(@ColorInt color: Int) {
        cursorColor = color
        if (isCursorVisible()) {
            invalidateCursor(true)
        }
    }

    fun getCursorColor(): Int {
        return cursorColor
    }

    override fun setCursorVisible(visible: Boolean) {
        if (isCursorVisible != visible) {
            isCursorVisible = visible
            invalidateCursor(isCursorVisible)
            makeBlink()
        }
    }

    override fun isCursorVisible(): Boolean {
        return isCursorVisible
    }

    override fun onScreenStateChanged(screenState: Int) {
        super.onScreenStateChanged(screenState)
        if (screenState == 1) {
            resumeBlink()
        } else if (screenState == 0) {
            suspendBlink()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        resumeBlink()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        suspendBlink()
    }

    private fun shouldBlink(): Boolean {
        return isCursorVisible() && this.isFocused
    }

    private fun makeBlink() {
        if (shouldBlink()) {
            if (blink == null) {
                blink = Blink()
            }
            removeCallbacks(blink)
            drawCursor = false
            postDelayed(blink, 500L)
        } else if (blink != null) {
            removeCallbacks(blink)
        }
    }

    private fun suspendBlink() {
        if (blink != null) {
            blink?.cancel()
            invalidateCursor(false)
        }
    }

    private fun resumeBlink() {
        if (blink != null) {
            blink?.unCancel()
            makeBlink()
        }
    }

    private fun invalidateCursor(showCursor: Boolean) {
        if (drawCursor != showCursor) {
            drawCursor = showCursor
            this.invalidate()
        }
    }

    private fun updateCursorHeight() {
        val delta = 2 * dpToPx()
        cursorHeight =
            if (otpViewItemHeight.toFloat() - this.textSize > delta.toFloat()) this.textSize + delta.toFloat() else this.textSize
    }

    private fun dpToPx(): Int {
        return (2.0f * this.resources.displayMetrics.density + 0.5f).toInt()
    }

    private inner class Blink : Runnable {
        private var cancelled = false
        override fun run() {
            if (!cancelled) {
                removeCallbacks(this)
                if (shouldBlink()) {
                    invalidateCursor(!drawCursor)
                    postDelayed(this, 500L)
                }
            }
        }

        fun cancel() {
            if (!cancelled) {
                removeCallbacks(this)
                cancelled = true
            }
        }

        fun unCancel() {
            cancelled = false
        }
    }

    companion object {
        private const val DBG = false
        private const val BLINK = 500
        private const val DEFAULT_COUNT = 4
        private val NO_FILTERS = arrayOfNulls<InputFilter>(0)
        private val HIGHLIGHT_STATES = intArrayOf(16842913)
        private const val VIEW_TYPE_RECTANGLE = 0
        private const val VIEW_TYPE_LINE = 1
        private fun isPasswordInputType(inputType: Int): Boolean {
            val variation = inputType and 4095
            return variation == 129 || variation == 225 || variation == 18
        }
    }

    init {
        animatorTextPaint = TextPaint()
        currentLineColor = -16777216
        textRect = Rect()
        itemBorderRect = RectF()
        itemLineRect = RectF()
        path = Path()
        itemCenterPoint = PointF()
        isAnimationEnable = false
        val res = this.resources
        paint = Paint(1)
        paint.style = Paint.Style.STROKE
        animatorTextPaint.set(getPaint())
        val theme = context.theme
        val typedArray =
            theme.obtainStyledAttributes(attrs, styleable.OtpView, defStyleAttr, 0)
        viewType = typedArray.getInt(styleable.OtpView_viewType, 0)
        otpViewItemCount = typedArray.getInt(styleable.OtpView_itemCount, 4)
        otpViewItemHeight = typedArray.getDimension(
            styleable.OtpView_itemHeight,
            res.getDimensionPixelSize(dimen.otp_view_item_size).toFloat()
        ).toInt()
        otpViewItemWidth = typedArray.getDimension(
            styleable.OtpView_itemWidth,
            res.getDimensionPixelSize(dimen.otp_view_item_size).toFloat()
        ).toInt()
        otpViewItemSpacing = typedArray.getDimensionPixelSize(
            styleable.OtpView_itemSpacing,
            res.getDimensionPixelSize(dimen.otp_view_item_spacing)
        )
        otpViewItemRadius = typedArray.getDimension(styleable.OtpView_itemRadius, 0.0f).toInt()
        lineWidth = typedArray.getDimension(
            styleable.OtpView_lineWidth,
            res.getDimensionPixelSize(dimen.otp_view_item_line_width).toFloat()
        ).toInt()
        lineColors = typedArray.getColorStateList(styleable.OtpView_lineColor)
        isCursorVisible = typedArray.getBoolean(styleable.OtpView_android_cursorVisible, true)
        cursorColor =
            typedArray.getColor(styleable.OtpView_cursorColor, this.currentTextColor)
        cursorWidth = typedArray.getDimensionPixelSize(
            styleable.OtpView_cursorWidth,
            res.getDimensionPixelSize(dimen.otp_view_cursor_width)
        )
        itemBackground = typedArray.getDrawable(styleable.OtpView_android_itemBackground)
        hideLineWhenFilled = typedArray.getBoolean(styleable.OtpView_hideLineWhenFilled, false)
        typedArray.recycle()
        if (lineColors != null) {
            currentLineColor = lineColors!!.defaultColor
        }
        updateCursorHeight()
        checkItemRadius()
        setMaxLength(otpViewItemCount)
        paint.strokeWidth = lineWidth.toFloat()
        setupAnimator()
        super.setCursorVisible(false)
        setTextIsSelectable(false)
    }
}