package com.demo

import java.util.Scanner

class CoffeeResource(_water: Int, _milk: Int, _coffeeBeans: Int, _money: Int, _cups: Int = 1) {
    var water: Ingredient = Ingredient("water", _water)
    var milk: Ingredient = Ingredient("milk", _milk)
    var coffeeBeans: Ingredient = Ingredient("coffee beans", _coffeeBeans)
    var money: Int = _money
    var cups: Ingredient = Ingredient("disposable cups", _cups)
}

class Ingredient(val name: String, var value: Int)

val coffeeMachine = CoffeeResource(400, 540, 120, 550, 9)
val espresso = CoffeeResource(250, 0, 16, 4)
val latte = CoffeeResource(350, 75, 20, 7)
val cappuccino = CoffeeResource(200, 100, 12, 6)

fun printStatus() {
    println("The coffee machine has:")
    println("${coffeeMachine.water.value} of ${coffeeMachine.water.name}")
    println("${coffeeMachine.milk.value} of ${coffeeMachine.milk.name}")
    println("${coffeeMachine.coffeeBeans.value} of ${coffeeMachine.coffeeBeans.name}")
    println("${coffeeMachine.cups.value} of ${coffeeMachine.cups.name}")
    if (coffeeMachine.money > 0) {
        println("$${coffeeMachine.money} of money")
    } else {
        println("${coffeeMachine.money} of money")
    }
}

fun startWork(scanner: Scanner) {
    while (true) {
        println("Write action (buy, fill, take, remaining, exit):")
        when (scanner.next()) {
            "buy" -> buy(scanner)
            "take" -> take()
            "fill" -> fill(scanner)
            "remaining" -> printStatus()
            "exit" -> return
        }
    }
}

fun buy(scanner: Scanner) {
    println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
    when (scanner.next()) {
        "1" -> makeCoffee(espresso)
        "2" -> makeCoffee(latte)
        "3" -> makeCoffee(cappuccino)
        "back" -> return
    }
}

fun makeCoffee(coffee: CoffeeResource) {
    if (isEnough(coffee)) {
        println("I have enough resources, making you a coffee!")
        coffeeMachine.cups.value -= coffee.cups.value
        coffeeMachine.coffeeBeans.value -= coffee.coffeeBeans.value
        coffeeMachine.water.value -= coffee.water.value
        coffeeMachine.milk.value -= coffee.milk.value
        coffeeMachine.money += coffee.money
    }
}

fun isEnough(coffee: CoffeeResource): Boolean {
    return isEnough(coffee.water, coffeeMachine.water)
            && isEnough(coffee.milk, coffeeMachine.milk)
            && isEnough(coffee.coffeeBeans, coffeeMachine.coffeeBeans)
            && isEnough(coffee.cups, coffeeMachine.cups)
}

fun isEnough(coffeeResource: Ingredient, machineResource: Ingredient): Boolean {
    if (machineResource.value < coffeeResource.value) {
        println("Sorry, not enough ${machineResource.name}!")
        return false
    }
    return true
}

fun take() {
    println("I gave you $${coffeeMachine.money}")
    coffeeMachine.money = 0
}

fun fill(scanner: Scanner) {
    println("Write how many ml of water do you want to add:")
    coffeeMachine.water.value += scanner.nextInt()

    println("Write how many ml of milk do you want to add:")
    coffeeMachine.milk.value += scanner.nextInt()

    println("Write how many grams of coffee beans do you want to add:")
    coffeeMachine.coffeeBeans.value += scanner.nextInt()

    println("Write how many disposable cups of coffee do you want to add:")
    coffeeMachine.cups.value += scanner.nextInt()
}

fun main() {
    val scanner = Scanner(System.`in`)
    startWork(scanner)
}