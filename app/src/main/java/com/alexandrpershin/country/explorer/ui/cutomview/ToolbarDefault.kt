package com.alexandrpershin.country.explorer.ui.cutomview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.alexandrpershin.country.explorer.databinding.ToolbarDefaultBinding
import com.alexandrpershin.country.explorer.extensions.isVisible
import com.alexandrpershin.country.explorer.extensions.makeGone
import com.alexandrpershin.country.explorer.extensions.makeVisible
import com.alexandrpershin.country.explorer.extensions.setVisible
import com.alexandrpershin.country.explorer.utils.SingleLiveEvent

class ToolbarDefault : ConstraintLayout {

    private lateinit var binding: ToolbarDefaultBinding

    var onPrimaryButtonPressed: SingleLiveEvent<ToolbarPrimaryButton.Type> = SingleLiveEvent()

    var title: String
        set(value) {
            binding.tvTitle.text = value
        }
        get() = binding.tvTitle.text.toString()

    var btnTextActionVisibility: Boolean
        set(value) = binding.btnTextAction.setVisible(value)
        get() = binding.btnTextAction.isVisible()

    constructor(context: Context) : super(context) {
        initComponent()
        setAllListeners()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initComponent()
        setAllListeners()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initComponent()
        setAllListeners()
    }

    private fun initComponent() {
        binding = ToolbarDefaultBinding.inflate(
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            this,
            true
        )
        binding.btnTextAction.makeGone()
    }

    private fun setAllListeners() {
        binding.btnPrimary.apply {
            setOnClickListener {
                onPrimaryButtonPressed.value = type
            }
        }
    }

    fun setNavigationIcon(type: ToolbarPrimaryButton.Type, onClick: (() -> Unit)? = null) {
        binding.btnPrimary.makeVisible()
        binding.btnPrimary.type = type
        binding.btnPrimary.setOnClickListener { onClick?.invoke() }
    }

    fun setTextAction(actionText: String, onClick: (() -> Unit)?) {
        binding.btnTextAction.makeVisible()
        binding.btnTextAction.text = actionText
        binding.btnTextAction.isEnabled = (onClick != null)
        binding.btnTextAction.setOnClickListener { onClick?.invoke() }
    }

    fun setImageAction(@DrawableRes resId: Int, onClick: (() -> Unit)?) {
        binding.btnImageAction.makeVisible()
        binding.btnImageAction.setImageResource(resId)
        binding.btnImageAction.setOnClickListener { onClick?.invoke() }
    }

    fun removeAction() {
        binding.btnTextAction.makeGone()
        binding.btnTextAction.text = ""
        binding.btnTextAction.setOnClickListener(null)
    }
}