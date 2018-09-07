package todolist.laurent68k.todolistdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


/*
    Main activity off the App

    Usefull 3rd libraries:

    OkHttp  an Alamofire like
    Gson    to easily handle Json parsing
    Picasso to handle easily image

 */

class MainActivity : AppCompatActivity() {

    //  View Model for this activity
    private val activityModel = MainActivityModel()

    //  Layout Model to handle activity display
    private val activityLayout = MainActivityLayout( this.activityModel )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.floatingAddButton.setOnClickListener {

            Toast.makeText(this@MainActivity, "Add new note", Toast.LENGTH_SHORT).show()

            val position = this.activityModel.add()

            this.mainRecyclerView.adapter?.notifyItemInserted(position)
            this.mainRecyclerView.scrollToPosition(position)
        }

        //  Initialize the RecyclerView
        this.mainRecyclerView.layoutManager = LinearLayoutManager( this )
        this.mainRecyclerView.adapter = this.activityLayout.itemsAdapter

        this.activityLayout.setRecyclerViewItemTouchListener( this.mainRecyclerView )
    }
}
