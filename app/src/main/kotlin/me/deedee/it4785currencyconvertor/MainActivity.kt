package me.deedee.it4785currencyconvertor

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import me.hanhngo.mycurrencyconverter.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var fromAmount = ""
    private var afterDecimalPointIndex = 0
    private val currencies: MutableList<Currency?> = ArrayList()
    private var fromCurrency: CurrencyName? = null
    private var toCurrency: CurrencyName? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(getLayoutInflater())
        super.onCreate(savedInstanceState)
        setContentView(binding.getRoot())

        setupSpinner()
        setupFunctionalButton()
    }

    private fun setupSpinner() {
        val adapter: ArrayAdapter<Currency> = ArrayAdapter<Any?>(
            this,
            R.layout.color_spinner_layout,
            getCurrencies()
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        binding.fromSpn.setAdapter(adapter)
        binding.fromSpn.setSelection(0)
        fromCurrency = CurrencyName.entries[0]
        binding.toSpn.setAdapter(adapter)
        binding.toSpn.setSelection(1)
        toCurrency = CurrencyName.entries[1]

        binding.fromSpn.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                fromCurrency = (adapterView.adapter.getItem(i) as Currency).getCurrencyName()
                val rateText = "1 " +
                        fromCurrency.toString() +
                        " = " +
                        getCurrency(fromCurrency)!!.getRates()[toCurrency] +
                        " " +
                        toCurrency.toString()
                binding.rateTv.setText(rateText)
                updateFromTextView()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        })

        binding.toSpn.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                toCurrency = (adapterView.adapter.getItem(i) as Currency).getCurrencyName()
                val rateText = "1 " +
                        fromCurrency.toString() +
                        " = " +
                        getCurrency(fromCurrency)!!.getRates()[toCurrency] +
                        " " +
                        toCurrency.toString()
                binding.rateTv.setText(rateText)
                updateFromTextView()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        })

        val rateText = "1 " +
                fromCurrency.toString() +
                " = " +
                getCurrency(fromCurrency)!!.getRates()[toCurrency] +
                " " +
                toCurrency.toString()
        binding.rateTv.setText(rateText)
    }

    private fun getCurrency(name: CurrencyName?): Currency? {
        return getCurrencies().stream()
            .filter { currency: Currency? -> currency!!.getCurrencyName() == name }.findFirst()
            .orElse(null)
    }

    private fun getCurrencies(): List<Currency?> {
        if (currencies.size > 0) {
            return currencies
        }

        for (name in CurrencyName.entries) {
            currencies.add(Currency(name))
        }
        return currencies
    }

    private fun setupFunctionalButton() {
        binding.btn0.setOnClickListener { view ->
            if (fromAmount == "") {
                return@setOnClickListener
            }
            buttonOperator(0)
        }

        binding.btn1.setOnClickListener { view -> buttonOperator(1) }

        binding.btn2.setOnClickListener { view -> buttonOperator(2) }

        binding.btn3.setOnClickListener { view -> buttonOperator(3) }

        binding.btn4.setOnClickListener { view -> buttonOperator(4) }

        binding.btn5.setOnClickListener { view -> buttonOperator(5) }

        binding.btn6.setOnClickListener { view -> buttonOperator(6) }

        binding.btn7.setOnClickListener { view -> buttonOperator(7) }

        binding.btn8.setOnClickListener { view -> buttonOperator(8) }

        binding.btn9.setOnClickListener { view -> buttonOperator(9) }

        binding.btnDot.setOnClickListener { view ->
            if (!fromAmount.contains(".")) {
                fromAmount += ".00"
                afterDecimalPointIndex = 1
                updateFromTextView()
            }
        }

        binding.CEBtn.setOnClickListener { view ->
            fromAmount = ""
            afterDecimalPointIndex = 0
            updateFromTextView()
        }

        binding.deleteBtn.setOnClickListener { view ->
            if (fromAmount == "") {
                return@setOnClickListener
            }
            if (afterDecimalPointIndex == 0) {
                fromAmount = fromAmount.substring(0, fromAmount.length - 1)
            } else if (afterDecimalPointIndex == 1) {
                fromAmount = fromAmount.substring(0, fromAmount.length - 3)
                afterDecimalPointIndex--
            } else {
                val index = fromAmount.indexOf(".") + afterDecimalPointIndex - 1
                fromAmount = replaceCharAt(index, fromAmount, '0')
                afterDecimalPointIndex--
            }
            updateFromTextView()
        }
    }

    private fun updateFromTextView() {
        binding.fromTv.setText(fromAmount)
        try {
            var rate = 0.0
            val map: HashMap<CurrencyName?, Double> = getCurrency(fromCurrency)!!.getRates()
            if (map.containsKey(toCurrency)) {
                rate = map[toCurrency]!!
            }
            val toAmount = String.format(Locale.ROOT, "%.2f", fromAmount.toDouble() * rate)
            binding.toTv.setText(toAmount)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun buttonOperator(number: Int) {
        if (afterDecimalPointIndex == 0) {
            fromAmount += number.toString()
        } else if (afterDecimalPointIndex <= 2) {
            val index = fromAmount.indexOf(".") + afterDecimalPointIndex
            fromAmount = replaceCharAt(index, fromAmount, Character.forDigit(number, 10))
            afterDecimalPointIndex++
        }
        updateFromTextView()
    }

    private fun replaceCharAt(index: Int, input: String, replaceChar: Char): String {
        val newString = StringBuilder(input)
        newString.setCharAt(index, replaceChar)
        return newString.toString()
    }


    protected override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}