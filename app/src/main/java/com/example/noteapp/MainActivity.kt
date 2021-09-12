package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var addProduct : Button
    private lateinit var updateProduct : Button
    private lateinit var productName : EditText
    private lateinit var productUnit : EditText

    private lateinit var productList : RecyclerView

    private lateinit var mProductListAdapter: ListAdapter
    private var modelToBeUpdated : Stack<Model> = Stack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productList = findViewById(R.id.product_list_recycler_view)
        productList.layoutManager = LinearLayoutManager(this)
        productList.setHasFixedSize(true)

        productName = findViewById(R.id.product_name)
        productUnit = findViewById(R.id.product_price)

        updateProduct = findViewById(R.id.update_product)
        updateProduct.setOnClickListener {

            if (modelToBeUpdated.isEmpty()) return@setOnClickListener
            val name = productName.text.toString()
            val price = productUnit.text.toString()

            if (!name.isBlank() && !price.isBlank()){
                val model = modelToBeUpdated.pop()
                model.name = name
                model.price = price
                mProductListAdapter.updateProduct(model)

                productName.setText("")
                productUnit.setText("")
            }

        }

            addProduct = findViewById(R.id.add_product)
            addProduct.setOnClickListener {
                val name = productName.text.toString()
                val price = productUnit.text.toString()

                if (!name.isBlank() && !price.isBlank()){
                    val id = mProductListAdapter.getNextItemId()
                    val model = Model(id,name,price)
                    mProductListAdapter.addProduct(model)

                    productName.setText("")
                    productUnit.setText("")
                }

            }
        mProductListAdapter = ListAdapter(this,mOnProductClickedListener)
        productList.adapter = mProductListAdapter

    }
    private val mOnProductClickedListener = object : OnProductClickedListener {
        override fun onUpdate(position: Int, model: Model) {

            modelToBeUpdated.add(model)
            productName.setText(model.name)
            productUnit.setText(model.price)
        }

        override fun onDelete(model: Model) {

            mProductListAdapter.removeProduct(model)
        }

    }
}