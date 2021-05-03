package space.taran.arkbrowser.ui.adapter

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import space.taran.arkbrowser.R
import space.taran.arkbrowser.mvp.presenter.adapter.IItemGridPresenter
import space.taran.arkbrowser.mvp.view.item.FileItemView
import space.taran.arkbrowser.utils.loadImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_file_grid.view.*
import space.taran.arkbrowser.mvp.model.entity.common.IconOrImage
import space.taran.arkbrowser.utils.iconToImageResource

class ItemGridRVAdapter(
    val presenter: IItemGridPresenter<Any> //todo
): RecyclerView.Adapter<ItemGridRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_file_grid,
                parent,
                false
            )
        )

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("flow", "onBindViewHolder in ItemGridRVAdapter")
        holder.pos = position
        presenter.bindView(holder)

        holder.itemView.setOnClickListener {
            presenter.itemClicked(position)
        }
    }

    fun backClicked() {
        presenter.backClicked()
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer, FileItemView {

        override var pos = -1

        override fun setIcon(icon: IconOrImage): Unit = with(containerView) {
            if (icon.icon != null) {
                iv.setImageResource(iconToImageResource(icon.icon))
                iv.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray))
            } else {
                loadImage(icon.image!!, iv)
            }
        }

        override fun setFav(isFav: Boolean) = with(containerView) {
            if (isFav)
                iv_fav.visibility = View.VISIBLE
            else
                iv_fav.visibility = View.GONE
        }

        override fun setText(title: String) = with(containerView) {
            tv_title.text = title
        }
    }
}