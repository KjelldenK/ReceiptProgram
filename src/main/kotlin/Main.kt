import java.util.*
import java.io.FileWriter
import java.io.File
// this will allow you to read user input as ints doubles and others
val read = Scanner(System.`in`)

// main this will run the program
fun main(){
    val receipt = Receipt()   // Creates a receipt class
    var keepShopping: Boolean = true
    // while the user wants to keeps shopping keeps asking these questions.
    while(keepShopping){
        println("\n1: Add item to your cart ")
        println("2: Show current total of cart ")
        println("3: Checkout and stop shopping ")
        print("\n What would you like to do? ")

        when(read.nextInt()){
            // when the user picks to add an item to there cart it this will open the file and check to see if the
            // item they are adding is new or not.
            1 ->{
                var newItem = true
                print("What item would you like to buy: ")
                var itemToBuy: String? = readLine()
                var storeData: List<String> = File(receipt.getFile()).readLines()
                for (item in storeData){
                    var splitItem: List<String> = item.split(",")
                    if(itemToBuy == splitItem[0]){
                        newItem = false
                        print("How many ${splitItem[0]} would you like to buy: ")
                        var quantity = read.nextInt()
                        var itemPrice: Double = splitItem[1].toDouble()
                        receipt.addItem(splitItem[0], itemPrice, quantity)
                    }
                }
                // If the item is new add it to the file.
                if(newItem){
                    println("This item is new")
                    var item: String = itemToBuy.toString()
                    print("What is the price of $itemToBuy: ")
                    var price: Double = read.nextDouble()
                    receipt.addToFile(item, price)
                    print("How much $item would you like to buy: ")
                    var quantity = read.nextInt()
                    receipt.addItem(item, price, quantity)
                }
            }
            // print out your current total.
            2 ->{
                receipt.getTotal()
            }
            // print out the receipt and close the program
            3 ->{
                receipt.printReceipt()
                keepShopping = false
            }
        }
    }
}

// a class that holds the users name store name the items and prices and the path to the file
class Receipt{
    private var shopperName: String = ""
    private var storeName: String = ""
    private var budget: Double = 0.0
    private var filePath: String = ""
    private var itemList = mutableListOf<String>()
    private var priceList = mutableListOf<Double>()
    private var numberOfItems = mutableListOf<Int>()
    private var totalCostOfItem = mutableListOf<Double>()
    private var total: Double = 0.00

    init {
        setShopperName()
        setStoreName()
        setBudget()
    }

    // set the shoppers name
    private fun setShopperName(){
        print("What is your name: ")
        this.shopperName = read.nextLine()
    }

    // set the store name that the user wants to shop at
    private fun setStoreName() {
        var i: Boolean = false
        println("\n1: Walmart")
        println("2: Costco")
        println("3: Target")
        print("What store would you like to shop at (Enter the number):")
        while (!i){
            when (read.nextInt()) {
                1 -> {
                    this.storeName = ("Walmart")
                    i = true

                    this.filePath = "Walmart.csv"
                }

                2 -> {
                    this.storeName = ("Costco")
                    i = true
                    this.filePath = "Costco.csv"
                }

                3 -> {
                    this.storeName = ("Target")
                    i = true
                    this.filePath = "Target.csv"
                }

                else -> {
                    println("Please enter a valid number.")

                }
            }
        }
   }

    // set Budget of the user
    private fun setBudget(){
        print("What is your shopping budget? ")
        this.budget = read.nextDouble()
    }

    // takes the total and the budget and tells the user if they are over or under budget
    fun getTotal(){
        println("\nYour total is: $${this.total}")
        var balance: Double = this.budget - this.total
        if (balance > 0){
            println("You are $$balance above budget!")
        }else if(balance < 0){
            println("You are $$balance over budget...")
        }else{
            println("You are right on budget")
        }
    }

    // get the file path
    fun getFile(): String{
        return this.filePath
    }

    // add new items to the file
    fun addToFile(itemName: String, itemPrice: Double){
        var stringItemPrice = itemPrice.toString()
        val storeData = FileWriter(this.getFile(), true)
        storeData.write("${itemName},${stringItemPrice}\n")
        storeData.close()
    }

    // add item to the shopping cart of the user
    fun addItem(itemName: String, itemPrice: Double, howMany: Int){
        this.itemList.add(itemName)
        this.priceList.add(itemPrice)
        this.numberOfItems.add(howMany)
        var cost:Double = (itemPrice * howMany)
        this.totalCostOfItem.add(cost)
        this.total = (this.total + cost)
        println("${this.itemList}")
        println("${this.priceList}")
        println("${this.numberOfItems}")
        println("${this.totalCostOfItem}")
        println("${this.total}")
    }

    // print out the receipt 
    fun printReceipt(){
        println("${this.shopperName}")
        println("${this.storeName}")
        println("")
        var count = 0
        println("item,price,quantity,total")
        for(i in 0 until this.itemList.size){
            println("${this.itemList[i]} | $${this.priceList[i]} X ${this.numberOfItems[i]} | $${this.totalCostOfItem[i]}")
        }
        this.getTotal()
        println("")
        println("Thank you for shopping at ${this.storeName}!")
    }
}