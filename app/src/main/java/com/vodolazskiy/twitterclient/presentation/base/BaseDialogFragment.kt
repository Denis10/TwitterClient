package com.vodolazskiy.twitterclient.presentation.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegate
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegateImpl
import com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback
import dagger.android.support.AndroidSupportInjection

abstract class BaseDialogFragment<V : BaseView, P : BasePresenter<V>> : DialogFragment(), MvpDelegateCallback<V, P>,
        BaseView {

    protected var mvpDelegateLoc: FragmentMvpDelegate<V, P>? = null

    protected lateinit var presenterLoc: P

    abstract override fun createPresenter(): P

    protected fun getMvpDelegate(): FragmentMvpDelegate<V, P> {
        if (mvpDelegateLoc == null) {
            mvpDelegateLoc = FragmentMvpDelegateImpl(this, this, true, true)
        }

        return mvpDelegateLoc!!
    }

    override fun getPresenter(): P {
        return presenterLoc
    }

    override fun setPresenter(presenter: P) {
        this.presenterLoc = presenter
    }

    override fun getMvpView(): V {
        return this as V
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMvpDelegate().onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getMvpDelegate().onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMvpDelegate().onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        getMvpDelegate().onDestroy()
    }

    override fun onPause() {
        super.onPause()
        getMvpDelegate().onPause()
    }

    override fun onResume() {
        super.onResume()
        getMvpDelegate().onResume()
    }

    override fun onStart() {
        super.onStart()
        getMvpDelegate().onStart()
    }

    override fun onStop() {
        super.onStop()
        getMvpDelegate().onStop()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMvpDelegate().onActivityCreated(savedInstanceState)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        getMvpDelegate().onAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
        getMvpDelegate().onDetach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        getMvpDelegate().onSaveInstanceState(outState)
    }
}