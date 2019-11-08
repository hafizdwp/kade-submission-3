package me.hafizdwp.kade_submission_3.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.hafizdwp.kade_submission_3.util.binding.MyRecyclerPaddingMargin
import me.hafizdwp.kade_submission_3.util.ext.dividerItemDecoration
import me.hafizdwp.kade_submission_3.util.livedata.SingleListLiveEvent
import me.hafizdwp.kade_submission_3.util.widget.EndlessRecyclerOnScroll

/**
 * @author hafizdwp
 * 29/10/2019
 **/
abstract class BaseFragment<T : AppCompatActivity, B : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    lateinit var mActivity: T
    lateinit var mContext: Context

    @get:LayoutRes
    abstract val bindLayoutRes: Int
    abstract val mLifecycleOwner: LifecycleOwner
    abstract val mViewModel: VM
    lateinit var mViewBinding: B
    var mOnLoadMoah: EndlessRecyclerOnScroll? = null

    abstract fun onSetupBindingVariable(): B
    abstract fun onViewReady()
    abstract fun start()
    abstract fun setupObserver(): VM?
    open fun setupToolbar() {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivity = requireActivity() as T
        mContext = requireContext()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewBinding = DataBindingUtil.inflate(inflater, bindLayoutRes, container, false)
        mViewBinding.lifecycleOwner = mLifecycleOwner
        onSetupBindingVariable()

        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onViewReady()
        setupToolbar()
        setupObserver()
        start()
    }

    fun <T> MutableLiveData<T>.observe(
            action: (T?) -> Unit) {
        observe(mLifecycleOwner, Observer {
            action.invoke(it)
        })
    }

    fun <T> SingleListLiveEvent<T>.observe(
            action: (MutableList<T>?) -> Unit) {
        observe(mLifecycleOwner, Observer {
            action.invoke(it)
        })
    }

    /**
     * @param mAdapter           adapter class that extend BaseRecyclerAdapter
     * @param withDivider        divider for each item
     * @param useDefaultColor    divider color; TRUE for use default color grey, FALSE for use the color 'blueV2';
     *                           only works when withDivider=true
     * @param onLoadMoreListener interface that should implement BaseRecyclerSwipeLoadMoreListener,
     *                           install recyclerView to have 'load-more' mechanism
     *                           and assign it to local var 'mOnLoadMoah'.
     *                           RecyclerView will fire 'onLoadMore()' function that you should implement
     *                           in the fragment.
     * ---------------------------------------------------------------------------------------------
     * */
    inline fun <reified M, reified B : ViewDataBinding> RecyclerView.buildRecyclerView(
            mAdapter: BaseRecyclerAdapter<M, B>?,
            withDivider: Boolean = true,
            useDefaultColor: Boolean = true,
            onLoadMoreListener: BaseRecyclerSwipeLoadMoreListener? = null,
            layoutMgr: RecyclerView.LayoutManager = LinearLayoutManager(requireContext()),
            paddingMargin: MyRecyclerPaddingMargin? = null) {

        this.apply {
            adapter = mAdapter
            layoutManager = layoutMgr
            itemAnimator = null

            if (withDivider)
                if (itemDecorationCount < 1)
                    addItemDecoration(dividerItemDecoration())

            if (onLoadMoreListener != null) {
                mOnLoadMoah = EndlessRecyclerOnScroll(onLoadMoreListener, layoutManager as LinearLayoutManager)
                addOnScrollListener(mOnLoadMoah!!)
            }

            if (paddingMargin != null)
                setPaddingMargin(paddingMargin)
        }
    }

    fun RecyclerView.setPaddingMargin(paddingMargin: MyRecyclerPaddingMargin?) {
        paddingMargin?.let {

            val layoutParam = ViewGroup.MarginLayoutParams(layoutParams)
            layoutParam.apply {
                if (paddingMargin.padding != 0)
                    setPadding(paddingMargin.padding, paddingMargin.padding, paddingMargin.padding, paddingMargin.padding)
                else
                    setPadding(paddingMargin.paddingLeft, paddingMargin.paddingTop, paddingMargin.paddingRight, paddingMargin.paddingBottom)

                if (paddingMargin.margin != 0)
                    setMargins(paddingMargin.margin, paddingMargin.margin, paddingMargin.margin, paddingMargin.margin)
                else
                    setMargins(paddingMargin.marginLeft, paddingMargin.marginTop, paddingMargin.marginRight, paddingMargin.marginBottom)
            }

            layoutParams = layoutParam
            requestLayout()
        }
    }
}