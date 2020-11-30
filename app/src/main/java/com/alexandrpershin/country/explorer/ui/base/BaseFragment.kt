package com.alexandrpershin.country.explorer.ui.base


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.alexandrpershin.country.explorer.R
import com.alexandrpershin.country.explorer.api.ErrorType
import com.alexandrpershin.country.explorer.extensions.makeGone
import com.alexandrpershin.country.explorer.extensions.makeVisible
import com.alexandrpershin.country.explorer.extensions.showInfoMessage
import com.alexandrpershin.country.explorer.utils.NavigationCommand

/**
 *  Base class for [Fragment] with [BaseViewModel] instance inside
 * */

abstract class BaseFragment<DB : ViewBinding, VM : BaseViewModel> : Fragment() {
    protected val TAG: String = this::class.java.simpleName

    protected var binding: DB? = null

    abstract val viewModel: VM

    private val progressBar: ProgressBar? by lazy {
        binding?.root?.findViewById(R.id.progressBar)
    }

    /**
     * Abstract methods what should be implemented by derived classes
     * */

    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DB

    abstract fun initComponents(binding: DB)
    abstract fun addListeners(binding: DB)
    abstract fun addObservers(binding: DB)
    abstract fun errorHandler(errorType: ErrorType)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initBinding(inflater, container)
        initComponents(binding!!)
        addListeners(binding!!)
        addObservers(binding!!)

        viewModel.errorNotifier.observe(viewLifecycleOwner, Observer {
            errorHandler(it)
        })

        viewModel.loadingState.observe(viewLifecycleOwner, Observer { visible ->
            if (visible) showLoading(binding!!) else hideLoading(binding!!)
        })

        viewModel.infoMessage.observe(viewLifecycleOwner, Observer { message ->
            showInfoMessage(getString(message))
        })
        viewModel.forceKeyboardState.observe(viewLifecycleOwner, Observer
        { keyboardState ->
            keyboardState?.let { safeKeyboardState ->
                when (safeKeyboardState) {
                    KeyboardState.Show -> openKeyboard()
                    KeyboardState.Hide -> closeKeyboard()
                }
            }
        })

        return binding!!.root
    }

    open fun showLoading(binding: DB) {
        progressBar?.makeVisible()
    }

    open fun hideLoading(binding: DB) {
        progressBar?.makeGone()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigation.observe(viewLifecycleOwner, Observer<NavigationCommand> { command ->
            when (command) {
                is NavigationCommand.ToWithNavDirection ->
                    findNavController().navigate(command.directions)
                is NavigationCommand.Back ->
                    findNavController().popBackStack().also {
                        if (!it) {
                            requireActivity().finish()
                        }
                    }
            }
        })

    }

    private fun closeKeyboard() {
        activity?.let { safeActivity ->
            val view = safeActivity.currentFocus
            view?.let { v ->
                val imm =
                    safeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
    }

    private fun openKeyboard() {
        activity?.let { safeActivity ->
            val imm: InputMethodManager =
                safeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}