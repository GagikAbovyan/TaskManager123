package vtc.room.a101.taskmanager.fragmens;


import android.content.Intent;
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
import vtc.room.a101.taskmanager.activities.TasksActivity;
import vtc.room.a101.taskmanager.model.Account;
import vtc.room.a101.taskmanager.providers.DataProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {

    }

    private boolean flag = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        openRegisterFragment(new RegisterFragment(), view);
        final ImageButton login = (ImageButton) view.findViewById(R.id.login_button);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAccount(view);
                }
        });
        isPassword(view);
        return view;
    }

    private void isPassword(final View view){
        ImageButton isPasswordButton = (ImageButton) view.findViewById(R.id.is_password);
        final EditText editText = (EditText) view.findViewById(R.id.input_password);
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

    private void openRegisterFragment(final Fragment fragment, final View view) {
        final ImageButton registerButton = (ImageButton) view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
    }

    private void openAccount(final View view) {
        final EditText email = (EditText) view.findViewById(R.id.input_email);
        final EditText password = (EditText) view.findViewById(R.id.input_password);
        final String emailString = email.getText().toString();
        final String passwordString = password.getText().toString();
        for (int i = 0; i < DataProvider.getList().size(); i++) {
            Account account = DataProvider.getList().get(i);
            if (emailString.equals(account.getEmail()) &&
                    passwordString.equals(account.getPassword())) {
                final Intent intent = new Intent(getActivity(), TasksActivity.class);

                getActivity().startActivity(intent);
                return;
            } else {
                if (i == DataProvider.getList().size() - 1) {
                    Toast.makeText(view.getContext(), R.string.incorrect_data, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
