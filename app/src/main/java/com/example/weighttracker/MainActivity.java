package com.example.weighttracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText date,weight,id;
    Button insert_button,viewall_button,update_button,delete_button,avg_button,go_to_graph;
    public static DecimalFormat df = new DecimalFormat("0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        date = (EditText) findViewById(R.id.date_id);
        id = (EditText) findViewById(R.id.id_delete_id);
        weight = (EditText) findViewById(R.id.weight_id);
        insert_button = (Button) findViewById(R.id.insert_id);
        viewall_button = (Button) findViewById(R.id.viewall_id);
        update_button = (Button)findViewById(R.id.update_button_id);
        delete_button = (Button)findViewById(R.id.delete_button);
        avg_button = (Button)findViewById(R.id.average_button);
        go_to_graph = (Button)findViewById(R.id.graph_button);
        AddData();
        ViewAll();
        ChangeData();
        DeleteRow();
        AverageData();
        secondActivity();


    }

    public void ChangeData(){
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean done = myDb.UpdateData(id.getText().toString(),date.getText().toString(),weight.getText().toString());
                if(done){
                    Toast.makeText(MainActivity.this,"Data Updated Successfully",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Data Has Not been Updated",Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    public void DeleteRow(){
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int done = myDb.deleteData(id.getText().toString());
                if(done==0){
                    Toast.makeText(MainActivity.this,"Data for Id: "+id.getText().toString()+"not deleted.",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Data for Id: "+id.getText().toString()+" deleted successfully",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void AddData(){
        insert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean done = myDb.insertdata(date.getText().toString(),weight.getText().toString());
                if(done==true){
                    Toast.makeText(MainActivity.this,"Data Inserted Successfully",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Data Was Not Inserted",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void AverageData(){
        avg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cr = myDb.getallData();
                if(cr.getCount()==0){
                    showMessage("Average Weight: ","Nothing Has Been Inserted Yet!");
                    return;
                }else{
                    double total=0.00;
                    int count = 0;
                    while(cr.moveToNext()){
                        total+=cr.getDouble(2);
                        count++;

                    }
                     double result = total/(double)count;
                    //String s = Double.toString(result);
                    showMessage("Average Weight: ", String.format("Your Average weight from " + count + " records is: " + df.format(result) + "kg(s) and the total is: " + df.format(total)+"kg(s)") );


                }
            }
        });
    }

    public void ViewAll(){
        viewall_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cr = myDb.getallData();
                if(cr.getCount()==0){
                    //showMessage
                    showMessage("Your Records: ","Nothing Has Been Inserted Yet!");
                    return;
                }else{
                    StringBuffer sb = new StringBuffer();
                    while(cr.moveToNext()){
                        sb.append("ID: "+ cr.getString(0)+"\n");
                        sb.append("DATE: "+ cr.getString(1)+"\n");
                        sb.append("WEIGHT: "+ cr.getString(2)+"\n\n");

                    }
                    showMessage("Your Records:",sb.toString());

                }



            }
        });

    }


    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void secondActivity(){
        go_to_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

    }





}
