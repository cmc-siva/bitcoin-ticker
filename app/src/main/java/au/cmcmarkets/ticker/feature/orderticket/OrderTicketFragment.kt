package au.cmcmarkets.ticker.feature.orderticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import au.cmcmarkets.ticker.core.di.viewmodel.ViewModelFactory
import au.cmcmarkets.ticker.databinding.FragmentOrderTicketBinding
import au.cmcmarkets.ticker.feature.orderticket.OrderTicketContract.Actions
import au.cmcmarkets.ticker.utils.SimpleTextWatcher
import au.cmcmarkets.ticker.utils.hideKeyboard
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_order_ticket.view.*
import javax.inject.Inject

class OrderTicketFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding: FragmentOrderTicketBinding

    private val viewModel: OrderTicketViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(OrderTicketViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderTicketBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        addTextWatchListeners()
        initialiseLiveDataObservers()
        return binding.root
    }

    private fun addTextWatchListeners() {
        binding.root.edtTextAmount.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (binding.root.edtTextAmount.hasFocus()) {
                    viewModel.onAmountChanged(sequence)
                }
            }
        })
        binding.root.edtTextUnits.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // If there is a focus, then its user entered
                if (binding.root.edtTextUnits.hasFocus()) {
                    viewModel.onUnitChanged(sequence)
                }
            }
        })
    }

    private fun initialiseLiveDataObservers() {

        viewModel.actions.observe(viewLifecycleOwner,
            Observer { action ->
                when (action) {
                    Actions.Cancel -> binding.root.hideKeyboard()
                    Actions.Confirm -> {
                        // Handle Confirm
                    }
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

}

