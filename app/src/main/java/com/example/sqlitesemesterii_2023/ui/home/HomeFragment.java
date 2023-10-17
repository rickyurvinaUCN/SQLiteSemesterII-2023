package com.example.sqlitesemesterii_2023.ui.home;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sqlitesemesterii_2023.AdminSQLiteOpenHelper;
import com.example.sqlitesemesterii_2023.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private EditText txt_code, txt_description, txt_price;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_code = binding.txtCode;
        txt_description =  binding.txtDescription;
        txt_price =  binding.txtPrice;

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        binding.btnSearchP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });
    }

    //Metodo para guardar los productos
    public void save() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.getActivity(), "administration", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        String code = txt_code.getText().toString();
        String description = txt_description.getText().toString();
        String price = txt_price.getText().toString();

        if (!code.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
            ContentValues register = new ContentValues();
            register.put("code", code);
            register.put("description", description);
            register.put("price", price);
            database.insert("products", null, register);
            database.close();
            cleanForm();
            Toast.makeText(this.getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getActivity(), "Completar todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para buscar productos
    public void search() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.getActivity(), "administration", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();
        admin.getWritableDatabase();
        String code = txt_code.getText().toString();
        if (!code.isEmpty()) {
            Cursor row = database.rawQuery
                    ("select description, price  from products where code =" + code, null);
            if (row.moveToFirst()) {
                txt_description.setText(row.getString(0));
                txt_price.setText(row.getString(1));
                database.close();
            } else {
                Toast.makeText(this.getActivity(), "No se encontraron registros", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.getActivity(), "Ingresar código", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo para eliminar un producto
    public void delete() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.getActivity(), "administration", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();
        admin.getWritableDatabase();
        String code = txt_code.getText().toString();
        if (!code.isEmpty()) {
            int count = database.delete("products", "code=" + code, null);
            if (count == 1) {
                database.close();
                cleanForm();
                Toast.makeText(this.getActivity(), "Eliminado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getActivity(), "El producto no existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.getActivity(), "Ingresar código", Toast.LENGTH_SHORT).show();
        }
    }

    public void edit() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.getActivity(), "administration", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        String code = txt_code.getText().toString();
        String description = txt_description.getText().toString();
        String price = txt_price.getText().toString();

        if (!code.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
            ContentValues register = new ContentValues();
            register.put("code", code);
            register.put("description", description);
            register.put("price", price);
            int cant = database.update("products", register, "code=" + code, null);
            if (cant == 1) {
                database.close();
                cleanForm();
                Toast.makeText(this.getActivity(), "Actualizado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getActivity(), "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.getActivity(), "Ingresar código", Toast.LENGTH_SHORT).show();
        }
    }

    public void cleanForm() {
        txt_code.setText("");
        txt_description.setText("");
        txt_price.setText("");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}