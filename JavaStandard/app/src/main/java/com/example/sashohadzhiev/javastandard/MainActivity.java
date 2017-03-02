package com.example.sashohadzhiev.javastandard;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.leanplum.Leanplum;
import com.leanplum.Var;
import com.leanplum.activities.LeanplumActivity;
import com.leanplum.annotations.Variable;
import com.leanplum.callbacks.VariableCallback;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends LeanplumActivity {

    @Variable
    public static int startQuantity = 2;
    @Variable
    public static String lastUpdate = "and Have a nice day";
    @Variable
    public static String MigrateTestJustJ02 = "Migrate Test Just Java 01";
    @Variable(group = "timeBasedTrial")
    public static String upgradeWallContent = "asd";

    int quantity = startQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
        Map<String, Object> IncrementParams = new HashMap<String, Object>();
        IncrementParams.put("Text", lastUpdate);
        Leanplum.track("Increment", IncrementParams);
        Log.e("##Track event", "Increment");
        Log.e("##StrOnIncrementClick", lastUpdate);
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("SignatureSec", "someSignatureSec");
//        attributes.put("Signature", "someSignature");
//        Leanplum.setUserAttributes(attributes);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 0) {
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Quantity", quantity);
        Leanplum.track("Decrement", params);
        Log.e("##Track event", "Decrement");
        Log.e("##StrOnDecrementClick", lastUpdate);
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("SignatureSec", "");
//        attributes.put("Signature", "");
//        Leanplum.setUserAttributes(attributes);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Get user's name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        Editable nameEditable = nameField.getText();
        String name = nameEditable.toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants whipped cream topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

        displayLastUpdate();

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            Leanplum.track("Order");
            Log.e("##Track event", "Order");
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not we should include whipped cream topping in the price
     * @param addChocolate    is whether or not we should include whipped cream topping in the price
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // First calculate the price of one cup of coffee
        int basePrice = 5;

        // If the user wants whipped cream, add $1 per cup
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        // If the user wants chocolate, add $2 per cup
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        // Calculate the total order price by multiplying by the quantity
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param name            on the order
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream,
                                      boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        if (addWhippedCream) {
            priceMessage += "\n" + getString(R.string.order_summary_whipped_cream);
        }
        if (addChocolate) {
            priceMessage += "\n" + getString(R.string.order_summary_chocolate);
        }
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the info-bottom text on the screen.
     */
    private void displayLastUpdate() {
        TextView quantityTextView = (TextView) findViewById(
                R.id.info_text_view);
        quantityTextView.setText(getString(R.string.last_update_text) + " " + lastUpdate);
    }

    /**
     * This method displays the info-bottom text on the screen.
     */
    public void displayImage() {

        MyApplication.itestsec.addFileReadyHandler(new VariableCallback<String>() {
            @Override
            public void handle(Var<String> variable) {
                final ImageView imageView = (ImageView) findViewById(R.id.imageView1);
                Drawable d = Drawable.createFromStream(MyApplication.itestsec.stream(),"src");
                imageView.setImageDrawable(d);
            }
        });
    }

    /**
     * This method is called when the additional button is clicked.
     */
    public void additional(View view) {
        Leanplum.track("Additional", 2);
        Log.e("##Track event", "Additional");
        Log.e("Sasho", "After addVariablesChangedHandler, secondVar is: " + MyApplication.secondVar);
        displayImage();
    }
}




