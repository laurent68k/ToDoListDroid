package todolist.laurent68k.todolistdroid

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main_itemcell.view.*
import todolist.laurent68k.todolistdroid.TodoDetails.TodoActivity

//  This class handle the RecyclerView with the dataModel given
class MainActivityLayout(private val mainActivityModel:MainActivityModel) {

    //  Define the Adapter custom class
    class ItemsAdapter(private val mainActivityModel:MainActivityModel ) : RecyclerView.Adapter<MainActivityLayout.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val layout = LayoutInflater.from(parent.context)
            val viewCell = layout.inflate(R.layout.activity_main_itemcell, parent, false)

            return ViewHolder( viewCell )
        }

        //  Number of item in the data structure
        override fun getItemCount(): Int {

            return this.mainActivityModel.items?.size ?: 0
        }

        override fun onBindViewHolder(holder: MainActivityLayout.ViewHolder, index: Int) {

            //  Set the data to display by accessing directly via itemView
            holder.itemView.titleText.text = this.mainActivityModel.items!![index].title
            holder.itemView.noteText.text = this.mainActivityModel.items!![index].note

            //  Keep the object for details Activity
            holder.itemModel = this.mainActivityModel.items!![index]

            //  Background color alternate
            if (index.rem(2) == 0) {
                holder.itemView.setBackgroundColor( Color.LTGRAY )
            }
            else {
                holder.itemView.setBackgroundColor( Color.WHITE )
            }
        }
    }

    //  Define the cell view
    class ViewHolder(view : View, var itemModel : MainActivityModel.ItemModel? = null) : RecyclerView.ViewHolder(view), View.OnClickListener {

        companion object {

            val DETAILS_ACTIVITY_OBJECT = "DetailsActivityObject"
        }

        init {

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {

            var intent = Intent(view.context, TodoActivity::class.java)

            intent.putExtra(DETAILS_ACTIVITY_OBJECT, this.itemModel )

            view.context.startActivity(intent)
        }
    }

    //  Adapter to use by the RecyclerView
    var itemsAdapter : ItemsAdapter? = null
        get() = field
        set(value) {
            field = value
        }

    init {

        this.itemsAdapter = ItemsAdapter( mainActivityModel )
    }

}