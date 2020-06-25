package au.cmcmarkets.ticker.utils

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Sivaraj on 25/6/20.
 *
 * Purpose is to allow override of only required methods when used
 */
open class SimpleTextWatcher : TextWatcher {
    override fun afterTextChanged(sequence: Editable?) {}

    override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {}
}