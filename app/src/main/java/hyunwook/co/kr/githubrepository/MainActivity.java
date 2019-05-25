package hyunwook.co.kr.githubrepository;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    GithubClient client;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
