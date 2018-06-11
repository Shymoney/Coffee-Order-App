/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        //
        if (quantity == 100) {
            //show an error as a toast
            Toast.makeText(this,"You cannot have more than 100 Coffee", Toast.LENGTH_SHORT).show();
            //exit the method early because there is nothing left to
            return;
        }
        quantity = quantity + 1;
        display( quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            //Show an Error as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //exit the method early because there is nothing left to do
            return;
        }
        quantity = quantity - 1;
        display( quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    */

    /**
     * Calculates the price of the order.
     *
     * @return the total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean hasChocolate) {
        //Price for 1 Coffee
        int basePrice = quantity * 5;

        //Add $1 if the user wants WhippedCream
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        //Add $2 if the user wants Chocolate
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }
        //Calculate the total amount by multiplying the quantity
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(String name,int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = "Name: " + name;
        priceMessage +=  "\n" + getString(R.string.WhippedCream)+ addWhippedCream;
        priceMessage += "\n" + getString(R.string.Chocolate) + addChocolate;
        priceMessage += "\n"+ getString(R.string.Quantity) + quantity;
        priceMessage += "\n"+ getString(R.string.Total) + price;
        priceMessage += "\n"+ getString(R.string.Thankyou);
        return priceMessage;
    }


    /**
     * This method is called when the order button is clicked.
     * first get the Id of the checkbox then determine the state of the Chechbox using the isChecked()
     *
     */
    public void submitOrder(View view) {
        //get the id of the edit text field
        EditText nameField = (EditText) findViewById(R.id.name_feild);
        String name = nameField.getText().toString();
        //figure out if the user wants whippedcream coffee
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //figure out if the user wants chocolate coffee
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        /**
         *  start an Email Intent here so when the user place an order it uses our email app
         * */
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Jave Order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }



        //displayMessage(priceMessage);



    }


}