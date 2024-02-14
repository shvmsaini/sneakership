package io.github.shvmsaini.sneakership.strategy

import io.github.shvmsaini.sneakership.interfaces.TaxationStrategy

class DefaultTaxStrategy : TaxationStrategy {
    override fun getTaxPercent(): Int = 18
}