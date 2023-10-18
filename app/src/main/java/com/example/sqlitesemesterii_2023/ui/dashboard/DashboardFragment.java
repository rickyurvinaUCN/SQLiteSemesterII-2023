package com.example.sqlitesemesterii_2023.ui.dashboard;

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
import com.example.sqlitesemesterii_2023.databinding.FragmentDashboardBinding;
import com.example.sqlitesemesterii_2023.ui.Product;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private EditText txt_cod, txt_des, txt_pri;

    private ArrayList<Product> products = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        txt_cod= binding.txtCod;
        txt_des= binding.txtDes;
        txt_pri= binding.txtPri;

        binding.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        binding.btnEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        binding.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void save(){
        String cod = txt_cod.getText().toString();
        String des = txt_des.getText().toString();
        String pri = txt_pri.getText().toString();
        validateForm();
        Product p = new Product();
        p.setCode(Integer.parseInt(cod));
        p.setDescription(des);
        p.setPrice(pri);
        products.add(p);
        clean();
    }

    public void search(){
        for (Product product : products) {
            if (product.getCode() == Integer.parseInt(txt_cod.getText().toString())) {
                txt_des.setText(product.getDescription());
                txt_pri.setText(product.getPrice());
                Toast.makeText(getContext(), "Producto encontrado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void edit(){
        validateForm();
        for (Product product : products) {
            if (product.getCode() == Integer.parseInt(txt_cod.getText().toString())) {
                product.setDescription(txt_des.getText().toString());
                product.setPrice(txt_pri.getText().toString());
                product.setCode(Integer.parseInt(txt_cod.getText().toString()));
                Toast.makeText(getContext(), "Producto editado", Toast.LENGTH_SHORT).show();
                clean();
            }
        }
    }

    public void delete(){
        for (Product product : products) {
            if (product.getCode() == Integer.parseInt(txt_cod.getText().toString())) {
                products.remove(product);
                clean();
                Toast.makeText(getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clean(){
        txt_cod.setText("");
        txt_des.setText("");
        txt_pri.setText("");
        txt_cod.requestFocus();
    }

    public void validateForm(){
        String cod = txt_cod.getText().toString();
        String des = txt_des.getText().toString();
        String pri = txt_pri.getText().toString();
        if (cod.length() == 0){
            txt_cod.setError("Campo obligatorio");
            showMessage("Campo obligatorio");
            return;
        }
        if (des.length() == 0){
            txt_des.setError("Campo obligatorio");
            showMessage("Campo obligatorio");
            return;
        }
        if (pri.length() == 0){
            txt_pri.setError("Campo obligatorio");
            showMessage("Campo obligatorio");
            return;
        }
    }

    public void showMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void searchUniqueKey(Integer code){
        for (Product product : products) {
            if (product.getCode() == code) {
                showMessage("Código duplicado");
                return;
            }
        }
    }
}