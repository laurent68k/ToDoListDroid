package todolist.laurent68k.todolistdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //  View Model for this activity
    private val activityModel = MainActivityModel()

    //  Layout Model to handle activity display
    private val activityLayout = MainActivityLayout( this.activityModel )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  Initialize the RecyclerView
        this.mainRecyclerView.layoutManager = LinearLayoutManager( this )
        this.mainRecyclerView.adapter = this.activityLayout.itemsAdapter

    }
}