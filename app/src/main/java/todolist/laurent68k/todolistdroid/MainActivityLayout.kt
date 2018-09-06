package todolist.laurent68k.todolistdroid

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main_itemcell.view.*
import todolist.laurent68k.todolistdroid.TodoDetails.TodoActivity
import android.view.MotionEvent
import android.widget.Toast


//  This class handle the RecyclerView with the dataModel given
class MainActivityLayout(private val mainActivityModel:MainActivityModel) {

    //  --------------------------------------------------------------------------------------------
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
            holder.mainActivityModel = this.mainActivityModel

            //  Background color alternate
            if (index.rem(2) == 0) {
                holder.itemView.setBackgroundColor( Color.parseColor( "#f0f0f0") )
            }
            else {
                holder.itemView.setBackgroundColor( Color.WHITE )
            }
        }
    }
    //  --------------------------------------------------------------------------------------------

    //  --------------------------------------------------------------------------------------------
    //  Define the cell view
    class ViewHolder(view : View,
                     var itemModel : MainActivityModel.ItemModel? = null,
                     var mainActivityModel:MainActivityModel? = null) : RecyclerView.ViewHolder(view), View.OnClickListener {

        companion object {

            val DETAILS_ACTIVITY_OBJECT = "DetailsActivityObject"
        }

        init {

            itemView.setOnClickListener(this)

            itemView.setOnLongClickListener {

                v ->

                Toast.makeText(v.context, "Long press", Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(v.context)
                        .setTitle("Partager")
                        .setMessage("Voulez-vous partager cette note ${this.itemModel?.title} ?")
                        .setPositiveButton("OK", {

                            dialog, _ ->

                            //  todo : share this note
                            dialog.dismiss()
                        })
                        .setNegativeButton("Annuler", {

                            dialog, _ ->

                            dialog.cancel()
                        })
                        .create()
                        .show()


                true
            }
        }

        override fun onClick(view: View) {

            var intent = Intent(view.context, TodoActivity::class.java)

            intent.putExtra(DETAILS_ACTIVITY_OBJECT, this.itemModel )

            view.context.startActivity(intent)
        }
    }

    //  --------------------------------------------------------------------------------------------
    //  --------------------------------------------------------------------------------------------


    //  Adapter to use by the RecyclerView
    var itemsAdapter : ItemsAdapter? = null
        get() = field
        set(value) {
            field = value
        }

    init {

        this.itemsAdapter = ItemsAdapter( mainActivityModel )
    }

    //  --------------------------------------------------------------------------------------------
    //  Attach a swipe gesture to manage the remove
    fun setRecyclerViewItemTouchListener(recyclerView: RecyclerView) {

        //You create the callback and tell it what events to listen for. It takes two parameters, one for drag directions and one for swipe directions, but you’re only interested in swipe, so you pass 0 to inform the callback not to respond to drag events.
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                //You return false in onMove because you don’t want to perform any special behavior here.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //onSwiped is called when you swipe an item in the direction specified in the ItemTouchHelper. Here, you request the viewHolder parameter passed for the position of the item view, then you remove that item from your list of photos. Finally, you inform the RecyclerView adapter that an item has been removed at a specific position.
                val position = viewHolder.adapterPosition
                val note = mainActivityModel?.items!![position]

                mainActivityModel?.remove(note as MainActivityModel.ItemModel)

                recyclerView.adapter?.notifyItemRemoved(position)
            }
        }

        //ou initialize the ItemTouchHelper with the callback behavior you defined, and then attach it to the RecyclerView.
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}