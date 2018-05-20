package tomislav.piskur.com.povrsinatrokuta.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

import tomislav.piskur.com.povrsinatrokuta.R;
import tomislav.piskur.com.povrsinatrokuta.Model.Povrsina;


public class IzracunPovrsineFragment extends Fragment {

    private SendMessage SM;
    private EditText etA, etB, etC;
    private Button btnIzracunaj;
    private Double a, b, c, d;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_izracun_povrsine, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWidgets(view);

        setUpListeners();

    }

    private void initWidgets(View view){

        etA = view.findViewById(R.id.eta);
        etB = view.findViewById(R.id.etb);
        etC = view.findViewById(R.id.etc);
        btnIzracunaj = view.findViewById(R.id.btnIzracunaj);

    }

    private void setUpListeners(){

        btnIzracunaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etA.getText().toString().equals("") && !etB.getText().toString().equals("") && !etC.getText().toString().equals("")){
                    getNumbers();
                    Povrsina pov = new Povrsina(a,b,c);
                    Intent i = new Intent(getContext(), Rezultat.class);
                    DecimalFormat form = new DecimalFormat("0.00");
                    d = pov.povrsina();
                    i.putExtra("rezultat", d);
                    startActivity(i);

                    SM.sendData(etA.getText().toString().trim() + "x" + etB.getText().toString().trim()
                            + "x" + etC.getText().toString().trim() + "x" + form.format(d));
                }
                else {
                    etA.setText("");
                    etB.setText("");
                    etC.setText("");
                    Toast.makeText(getActivity(), "Unesite vrijednosti u sva polja!", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
    private void getNumbers() {

        a = Double.parseDouble(etA.getText().toString());
        b = Double.parseDouble(etB.getText().toString());
        c = Double.parseDouble(etC.getText().toString());

    }



    interface SendMessage {
        void sendData(String message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();

        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}
