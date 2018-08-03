package vtc.room.a101.taskmanager.fragmens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import vtc.room.a101.taskmanager.R;
import vtc.room.a101.taskmanager.model.Account;
import vtc.room.a101.taskmanager.providers.DataProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    private boolean flag = true;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);
        createAccount(view);
        isPassword(view);
        return view;
    }

    private void createAccount(final View view){
        final ImageButton addAccount = (ImageButton) view.findViewById(R.id.button_ok);
        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!logicRegister(view)){
                    Toast.makeText(view.getContext(), R.string.incorrect_argument, Toast.LENGTH_SHORT).show();
                }else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment()).commit();
                }
            }
        });
    }

    private void isPassword(final View view){
        ImageButton isPasswordButton = (ImageButton) view.findViewById(R.id.is_password_reg );
        final EditText editText = (EditText) view.findViewById(R.id.input_password_reg);
        isPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag = false;
                }else {
                    flag = true;
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

    }

    private boolean logicRegister(final View view){
        final EditText name = (EditText) view.findViewById(R.id.input_name);
        final EditText surname = (EditText) view.findViewById(R.id.input_surname);
        final EditText number = (EditText) view.findViewById(R.id.input_number);
        final EditText email = (EditText) view.findViewById(R.id.input_email_reg);
        final EditText password = (EditText) view.findViewById(R.id.input_password_reg);
        final String emailString = email.getText().toString();
        if (name.getText().toString().length() < 4){
            Toast.makeText(view.getContext(), R.string.incorrect_name, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (surname .getText().toString().length() < 4){
            Toast.makeText(view.getContext(), R.string.incorrect_surname, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!number.getText().toString().startsWith("374")){
            Toast.makeText(view.getContext(), R.string.incorrect_number, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (number.getText().toString().length() != 11){
            Toast.makeText(view.getContext(), R.string.incorrect_number1, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!emailString.endsWith("@mail.ru")){
            Toast.makeText(view.getContext(), R.string.incorrect_email, Toast.LENGTH_SHORT).show();
        return false;
        }
        if (password.getText().toString().length() < 4){
            Toast.makeText(view.getContext(), R.string.incorrect_password, Toast.LENGTH_SHORT).show();
            return false;
        }
        Account account = new Account(emailString, password.getText().toString());
        account.setName(name.getText().toString());
        account.setSurname(surname.getText().toString());
        account.setNumber(number.getText().toString());
        DataProvider.getList().add(account);
        return true;
    }

}
