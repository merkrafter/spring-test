package com.merkrafter.estimation

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * @author merkrafter
 */
@Entity
class Product(
    var name: String,
    var priceInEuroCents: Int,
    @Id
    @GeneratedValue
    var id: Long? = null
)