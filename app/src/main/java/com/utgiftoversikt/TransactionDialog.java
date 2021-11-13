package com.utgiftoversikt;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TransactionDialog extends Dialog {

    public MainActivity activity;

    String[] spinnerItems = new String[] {
            "Marco", "Ida Marie"
    } ;

    EditText transactionAmount;
    EditText transactionDescription;
    Spinner transactionPersonOption;

    Button addButton;
    Button cancelButton;

    public TransactionDialog(MainActivity mainActivity, Activity activity) {
        super(activity);

        this.activity = mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.transactionlayout);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        transactionAmount = findViewById(R.id.editTransactionAmount);
        transactionDescription = findViewById(R.id.editTransactionDescription);
        transactionPersonOption = findViewById(R.id.editTransactionSpinner);
        addButton = findViewById(R.id.newTransactionAddButton);
        cancelButton = findViewById(R.id.newTransactionCancelButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_layout, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionPersonOption.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = (transactionPersonOption.getSelectedItem() == "Marco") ? Person.Marco : Person.Ida;

                LogInfo item = new LogInfo(person, Float.parseFloat(transactionAmount.getText().toString()), TransactionType.Overføring, transactionDescription.getText().toString());
                activity.AddNewItem(activity, item);
                onBackPressed();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
