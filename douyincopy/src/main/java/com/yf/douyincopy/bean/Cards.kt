package com.yf.douyincopy.bean

object Cards {

    var observerList = arrayListOf<OnCardChangeListener>()

    private var currentCard: Card? = null

    val DATA = arrayListOf<Card>(
        Card(1),
        Card(2),
        Card(3),
        Card(4),
        Card(5),
        Card(6)
    )

    fun changeCard(card: Card) {
        currentCard = card
        observerList.forEach {
            it.onChange(card)
        }
    }

    fun registerCardChangeListener(listenerCard: OnCardChangeListener) {
        observerList.add(listenerCard)
    }

    fun unregisterCardChangeListener(listenerCard: OnCardChangeListener) {
        observerList.remove(listenerCard)
    }
}

interface OnCardChangeListener {
    fun onChange(card: Card)
}