package me.deedee.it4785currencyconvertor


class Currency(currencyName: CurrencyName) {
    private var currencyName: CurrencyName
    private val rates: HashMap<CurrencyName, Double>

    init {
        this.currencyName = currencyName
        this.rates = generateRates()
    }

    fun getCurrencyName(): CurrencyName {
        return currencyName
    }

    fun setCurrencyName(currencyName: CurrencyName) {
        this.currencyName = currencyName
    }

    fun getRates(): HashMap<CurrencyName, Double> {
        return rates
    }

    private fun generateRates(): HashMap<CurrencyName, Double> {
        val rates: HashMap<CurrencyName, Double> = HashMap<CurrencyName, Double>()
        for (name in CurrencyName.values()) {
            if (this.currencyName === name) {
                rates[name] = 1.0
            } else {
                rates[name] = Math.round(Math.random() * 3 * 100).toDouble() / 100
            }
        }
        return rates
    }

    override fun toString(): String {
        return currencyName.toString()
    }
}