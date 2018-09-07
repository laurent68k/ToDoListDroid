package todolist.laurent68k.todolistdroid

import android.os.Parcel
import android.os.Parcelable

class MainActivityModel {

    //  Represent the element model to display in each RecyclerView cell
    class ItemModel(val title: String,
                    val note: String,
                    val subscriptionDate: String = "24 Juillet 2018",
                    val country: String = "France",
                    val city: String = "Champs Sur Marne",
                    val mobileNumber: String = "+336 01 02 03 04") : Parcelable {



        constructor(parcel: Parcel) : this( parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString() ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(title)
            parcel.writeString(note)
            parcel.writeString(subscriptionDate)
            parcel.writeString(country)
            parcel.writeString(city)
            parcel.writeString(mobileNumber)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<ItemModel> {
            override fun createFromParcel(parcel: Parcel): ItemModel {
                return ItemModel(parcel)
            }

            override fun newArray(size: Int): Array<ItemModel?> {
                return arrayOfNulls(size)
            }
        }
    }

    var items : ArrayList<ItemModel>? = null

    init {

        this.items = ArrayList()

        for (i in 1..15) {

            this.items!!.add( ItemModel( "Titre ${i}", "Note ${i}" ) )
        }
    }

    fun remove(note: ItemModel) {

        this.items?.remove(note)
    }

    fun add() : Int {

        this.items!!.add( ItemModel( "Nouvelle note", "Texte de la note..." ) )

        return (this.items!!.size) - 1
    }
}