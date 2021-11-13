package com.utgiftoversikt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button newExpenseButton;
    Button newTransferButton;

    TextView textMarcoExpenseVariable;
    TextView textIdaExpenseVariable;
    TextView textMarcoOwedVariable;
    TextView textIdaOwedVariable;

    ArrayList<LogInfo> log = new ArrayList<>();

    LogInfoDAO logInfoDAO;

    LogRecyclerView logRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textMarcoExpenseVariable = findViewById(R.id.textMarcoExpenseVariable);
        textIdaExpenseVariable = findViewById(R.id.textIdaExpenseVariable);
        textMarcoOwedVariable = findViewById(R.id.textMarcoOwedVariable);
        textIdaOwedVariable = findViewById(R.id.textIdaOwedVariable);

        newExpenseButton = findViewById(R.id.addExpense);
        newTransferButton = findViewById(R.id.addTransfer);

        AsyncTask.execute(() -> {
            LogInfoDB db = Room.databaseBuilder(getApplicationContext(), LogInfoDB.class, "loginfo").fallbackToDestructiveMigration().build();
            logInfoDAO = db.logInfoDAO();

            log = new ArrayList<>(logInfoDAO.getAll());

            UpdateStatus();

            RecyclerView recyclerView = findViewById(R.id.logView);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            logRecyclerView = new LogRecyclerView(log);
            recyclerView.setAdapter(logRecyclerView);
        });

        newExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseDialog expenseDialog = new ExpenseDialog(MainActivity.this, MainActivity.this);
                expenseDialog.show();
            }
        });

        newTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionDialog transactionDialog = new TransactionDialog(MainActivity.this, MainActivity.this);
                transactionDialog.show();
            }
        });
    }

    @Override
    protected void onPause() {
        AsyncTask.execute(() -> {
            logInfoDAO.deleteAll();

            for (LogInfo l: log) {
                logInfoDAO.insertAll(l);
            }
        });

        super.onPause();
    }

    private void UpdateStatus() {
        float marcoExpense = 0.0f;
        float idaExpense = 0.0f;
        float marcoOwed = 0.0f;
        float idaOwed = 0.0f;

        for (LogInfo l: log) {
            if (l.person == Person.Marco) {
                if (l.transactionType == TransactionType.Utgift)
                {
                    marcoExpense += l.transactionAmount;

                    if (marcoOwed >= l.transactionAmount / 2f) {
                        marcoOwed -= l.transactionAmount / 2f;
                    }
                    else if (marcoOwed > 0f && marcoOwed < l.transactionAmount / 2f) {
                        float rest = (l.transactionAmount / 2f - marcoOwed);
                        marcoOwed = 0.0f;
                        idaOwed += rest;
                    }
                    else {
                        idaOwed += l.transactionAmount / 2f;
                    }
                }
                else {
                    if (marcoOwed >= l.transactionAmount) {
                        marcoOwed -= l.transactionAmount;
                    }
                    else if (marcoOwed > 0f && marcoOwed < l.transactionAmount){
                        float rest = l.transactionAmount - marcoOwed;
                        marcoOwed = 0.0f;
                        idaOwed += rest;
                    }
                    else {
                        idaOwed += l.transactionAmount;
                    }

                    idaExpense -= l.transactionAmount;
                    marcoExpense += l.transactionAmount;
                }
            }
            else {
                if (l.transactionType == TransactionType.Utgift)
                {
                    idaExpense += l.transactionAmount;

                    if (idaOwed >= l.transactionAmount / 2f) {
                        idaOwed -= l.transactionAmount / 2f;
                    }
                    else if (idaOwed > 0f && idaOwed < l.transactionAmount / 2f) {
                        float rest = (l.transactionAmount / 2f - idaOwed);
                        idaOwed = 0.0f;
                        marcoOwed += rest;
                    }
                    else {
                        marcoOwed += l.transactionAmount / 2f;
                    }
                }
                else {
                    if (idaOwed >= l.transactionAmount) {
                        idaOwed -= l.transactionAmount;
                    }
                    else if (idaOwed > 0f && idaOwed < l.transactionAmount){
                        float rest = l.transactionAmount - idaOwed;
                        idaOwed = 0.0f;
                        marcoOwed += rest;
                    }
                    else {
                        marcoOwed += l.transactionAmount;
                    }

                    marcoExpense -= l.transactionAmount;
                    idaExpense += l.transactionAmount;
                }
            }
        }

        String marcoExpenseText = String.format(Locale.ENGLISH, "%.2f", marcoExpense) + " kr";
        String idaExpenseText = String.format(Locale.ENGLISH, "%.2f", idaExpense) + " kr";
        String marcoOwedText = String.format(Locale.ENGLISH, "%.2f", marcoOwed) + " kr";
        String idaOwedText = String.format(Locale.ENGLISH, "%.2f", idaOwed) + " kr";

        textMarcoExpenseVariable.setText(marcoExpenseText);
        textIdaExpenseVariable.setText(idaExpenseText);
        textMarcoOwedVariable.setText(marcoOwedText);
        textIdaOwedVariable.setText(idaOwedText);
    }

    public void AddNewItem(MainActivity activity, LogInfo item) {
        AsyncTask.execute(() -> {
            logInfoDAO.insertAll(item);

            log = new ArrayList<>(logInfoDAO.getAll());

            UpdateStatus();

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RecyclerView recyclerView = findViewById(R.id.logView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                    logRecyclerView = new LogRecyclerView(log);
                    recyclerView.setAdapter(logRecyclerView);
                }
            });
        });
    }
}