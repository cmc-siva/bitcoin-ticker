package au.cmcmarkets.ticker.feature.orderticket

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import au.cmcmarkets.ticker.BR
import au.cmcmarkets.ticker.utils.format
import au.cmcmarkets.ticker.utils.formatCurrency

/**
 * Created by Sivaraj on 25/6/20.
 *
 * Purpose of this holder is to handle the data binding for the
 * user entered inputs
 */
class LiveDataHolder : BaseObservable() {

    val editUnits = MutableLiveData<Double>()
    val editAmount = MutableLiveData<Double>()

    @Bindable
    fun getFormattedAmount(): String {
        return editAmount.value.formatCurrency()
    }

    @Bindable
    fun getFormattedUnits(): String {
        return editUnits.value.format()
    }

    val isConfirmBtnEnabled = MutableLiveData<Boolean>()

    fun notifyAmountChange() {
        validateUserInputs()
        notifyPropertyChanged(BR.formattedAmount)
    }

    fun notifyUnitChange() {
        validateUserInputs()
        notifyPropertyChanged(BR.formattedUnits)
    }

    private fun validateUserInputs() {
        isConfirmBtnEnabled.value = ((editUnits.value != null && editUnits.value!! > 0) &&
                (editAmount.value != null && editAmount.value!! > 0))
    }

}
