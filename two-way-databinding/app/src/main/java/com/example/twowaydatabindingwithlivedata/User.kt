package com.example.twowaydatabindingwithlivedata

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

/**
 * BaseObservable is useful when almost all properties of class are observable
 *  else we can use the ObservableFields and the following primitive-specific
 *  classes to make fields observable.
 *
 *  ObservableBoolean, ObservableByte, ObservableChar, ObservableShort, ObservableInt
 *  ObservableLong, ObservableFloat, ObservableDouble, ObservableParcelable
 *
 *  class User {
    val firstName = ObservableField<String>()
    val lastName = ObservableField<String>()
    val age = ObservableInt()
    }
 */

class User : BaseObservable() {

    @get:Bindable
    var firstName: String = "Raju"
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }

    @get:Bindable
    var lastName: String = "Singh"
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
        }

    /**
     * Whenever either firstName or lastName has a change notification,
     * name will also be considered dirty. This does not mean that
     * Observable.OnPropertyChangedCallback.onPropertyChanged(Observable, int)
     * will be notified for BR.name, only that binding expressions containing
     * name will be dirtied and refreshed.
     */

    @get:Bindable("firstName", "lastName")
    var name: String = "$firstName $lastName"
    get() = "$firstName $lastName"
}