package com.alexandrpershin.country.explorer.ui.cutomview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import com.alexandrpershin.country.explorer.databinding.ToolbarDefaultBinding
import com.alexandrpershin.country.explorer.extensions.makeGone
import com.alexandrpershin.country.explorer.extensions.makeVisible

/**
 * Custom Toolbar view
 * */

class ToolbarDefault : CardView {

    private lateinit var binding: ToolbarDefaultBinding

    var title: String
        set(value) {
            binding.tvTitle.text = value
        }
        get() = binding.tvTitle.text.toString()

    constructor(context: Context) : super(context) {
        initComponent()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initComponent()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initComponent()
    }

    private fun initComponent() {
        binding = ToolbarDefaultBinding.inflate(
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            this,
            true
        )
        binding.btnTextAction.makeGone()
    }

    /**
     * Defines left navigation icon type, @see [ToolbarPrimaryButton.Type]
     * */

    fun setNavigationIcon(type: ToolbarPrimaryButton.Type, onClick: (() -> Unit)? = null) {
        binding.btnPrimary.makeVisible()
        binding.btnPrimary.type = type
        binding.btnPrimary.setOnClickListener { onClick?.invoke() }
    }

    /**
     * Sets text menu item
     * */

    fun setTextAction(actionText: String, onClick: (() -> Unit)?) {
        binding.btnTextAction.makeVisible()
        binding.btnTextAction.text = actionText
        binding.btnTextAction.isEnabled = (onClick != null)
        binding.btnTextAction.setOnClickListener { onClick?.invoke() }
    }

    /**
     * Sets image menu item
     * */

    fun setImageAction(@DrawableRes resId: Int, onClick: (() -> Unit)?) {
        binding.btnImageAction.makeVisible()
        binding.btnImageAction.setImageResource(resId)
        binding.btnImageAction.setOnClickListener { onClick?.invoke() }
    }

    /**
     * Removes text menu item action and makes text menu item gone
     * */

    fun removeAction() {
        binding.btnTextAction.makeGone()
        binding.btnTextAction.text = ""
        binding.btnTextAction.setOnClickListener(null)
    }
}