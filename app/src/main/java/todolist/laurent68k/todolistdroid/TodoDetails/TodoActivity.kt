package todolist.laurent68k.todolistdroid.TodoDetails

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.activity_todo.view.*
import todolist.laurent68k.todolistdroid.MainActivityLayout
import todolist.laurent68k.todolistdroid.MainActivityModel
import todolist.laurent68k.todolistdroid.R

class TodoActivity : AppCompatActivity() {

    var itemModel : MainActivityModel.ItemModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        this.itemModel = intent.getParcelableExtra<MainActivityModel.ItemModel>(MainActivityLayout.ViewHolder.DETAILS_ACTIVITY_OBJECT)

        if (this.itemModel == null) {

            this.titleActivity.text = "Nouveau titre:"
            supportActionBar?.title = "Ajouter"
        }
        else {
            this.titleActivity.text = "Titre de la note:"
            supportActionBar?.title = "Note: " + this.itemModel?.title

            this.displayItem()
        }

    }

    private fun displayItem() {

        this.itemModel.let { it } ?: return

        this.titleEditField.setText(this.itemModel?.title)
        this.noteEditText.setText(this.itemModel?.note)
    }
}
