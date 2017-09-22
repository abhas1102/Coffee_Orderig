/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */

package com.example.android.justjava;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity = 1;
    int priceOfOneCup = 5;


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();
        Log.v("MainActivity" , "Has Whipped Cream:" + hasWhippedCream);

        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = ChocolateCheckBox.isChecked();
        Log.v("MainActivity" , "Chocolate:" + hasChocolate);

        EditText nameInputField = (EditText) findViewById(R.id.name_field);
        String hasName = nameInputField.getText().toString();

        int price = calculatePrice(hasWhippedCream , hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, hasName);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + hasName);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }
        displayMessage(priceMessage);


    }
    public void increment(View view) {
        if ( quantity == 100){
            Toast.makeText(this, "You cannot have more than 100 coffees" , Toast.LENGTH_SHORT).show();

            return;
        }



        quantity = quantity+1;

        display(quantity);

    }

    public void decrement(View view) {

        if (quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 coffee" , Toast.LENGTH_SHORT).show();
            return;
        }

        quantity =quantity-1;
        display(quantity);

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));

    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }

    private int calculatePrice(boolean addWhippedCream , boolean addChocolate){
        int basePrice = 5;
        if(addWhippedCream){
            basePrice = basePrice +1;
        }
        if (addChocolate){
            basePrice = basePrice + 2;
        }
        int price = quantity*basePrice;
        return  price;
    }

    private String createOrderSummary(int price , boolean addWhippedCream, boolean addChocolate , String addName){
        String priceMessage = addName;
        priceMessage = priceMessage + "\nAdd Whipped Cream ?" + addWhippedCream;
        priceMessage = priceMessage + "\n Add Chocolate ?" + addChocolate;
        priceMessage = priceMessage + "\nQuantity :" + quantity;
        priceMessage = priceMessage +  "\nTotal: " + " $ " + price;
          priceMessage = priceMessage + "\n Thanks for shopping";
        return priceMessage;
    }

}