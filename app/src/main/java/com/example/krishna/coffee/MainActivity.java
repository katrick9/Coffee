package com.example.krishna.coffee;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            desplayQuantity(quantity);
        } else {
            quantity = 1;
            desplayQuantity(quantity);
            Toast toast = Toast.makeText(this, "u can not have less then one cup !", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            desplayQuantity(quantity);
        } else {
            quantity = 100;
            desplayQuantity(quantity);
            Toast toast = Toast.makeText(this, "max 100 u can order at once", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void submitOrder(View view) {
        CheckBox wippedcream = (CheckBox) findViewById(R.id.wipped_Cream);
        Boolean hasWippedCream = wippedcream.isChecked();
        CheckBox haschocolate = (CheckBox) findViewById(R.id.Chocolate);
        Boolean hasChocolate = haschocolate.isChecked();
        CheckBox extraCoca = (CheckBox) findViewById(R.id.Extra_Coca);
        Boolean hasExtraCoca = extraCoca.isChecked();
        CheckBox creamChocolate = (CheckBox) findViewById(R.id.cream_Chocolate);
        Boolean hasCreamChocolate = creamChocolate.isChecked();
        EditText enterName = (EditText) findViewById(R.id.name);
        String name = enterName.getText().toString();
        int price = calculatePrice(quantity, hasWippedCream, hasChocolate, hasExtraCoca, hasCreamChocolate);
        String priceMessage = createOrderSummary(price, hasWippedCream, hasChocolate, hasExtraCoca, hasCreamChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee is for :" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        desplayPrice(priceMessage);

    }

    public int calculatePrice(int quantity, Boolean hasWippedCream, Boolean hasChocolate, Boolean hasExtraCoca, Boolean hasCreamChocolate) {
        int price = 5;
        if (hasWippedCream) {
            price = price + 1;
        }
        if (hasChocolate) {
            price = price + 2;
        }
        if (hasCreamChocolate) {
            price = price + 3;
        }
        if (hasExtraCoca) {
            price = price + 4;
        }
        return price * quantity;
    }

    public String createOrderSummary(int price, Boolean hasWippedCream, Boolean hasChocolate, Boolean hasExtraCoca, Boolean hasCreamChocolate, String name) {
        String priceMessage = "Name :" + name;
        priceMessage = priceMessage + "\nAdd Wipped Cream ? " + hasWippedCream;
        priceMessage = priceMessage + "\nAdd Chocolate ? " + hasChocolate;
        priceMessage = priceMessage + "\nAdd ExtraCoca ? " + hasExtraCoca;
        priceMessage = priceMessage + "\nAdd  Cream Chocolate? " + hasCreamChocolate;
        priceMessage = priceMessage + "Total : $ " + price;
        priceMessage = priceMessage + "\nThank you!";
        return priceMessage;
    }

    private void desplayQuantity(int quantity) {
        TextView textView = (TextView) findViewById(R.id.quantity);
        textView.setText("" + quantity);
    }

    private void desplayPrice(String message) {
        TextView textView = (TextView) findViewById(R.id.price);
        textView.setText(message);
    }
}