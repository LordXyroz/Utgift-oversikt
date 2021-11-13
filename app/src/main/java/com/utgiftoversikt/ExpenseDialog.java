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

public class ExpenseDialog  extends Dialog {

    public MainActivity activity;

    String[] spinnerItems = new String[] {
            "Marco", "Ida Marie"
    } ;

    EditText expenseAmount;
    EditText expenseDescription;
    Spinner expensePersonOption;

    Button addButton;
    Button cancelButton;

    public ExpenseDialog(MainActivity mainActivity, Activity activity) {
        super(activity);

        this.activity = mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.expenselayout);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        expenseAmount = findViewById(R.id.editExpenseAmount);
        expenseDescription = findViewById(R.id.editExpenseDescription);
        expensePersonOption = findViewById(R.id.editExpenseSpinner);
        addButton = findViewById(R.id.newExpenseAddButton);
        cancelButton = findViewById(R.id.newExpenseCancelButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_layout, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expensePersonOption.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = (expensePersonOption.getSelectedItem() == "Marco") ? Person.Marco : Person.Ida;

                LogInfo item = new LogInfo(person, Float.parseFloat(expenseAmount.getText().toString()), TransactionType.Utgift, expenseDescription.getText().toString());
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
