package hyunwook.co.kr.githubrepository;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import hyunwook.co.kr.githubrepository.adapter.RepositoryAdapter;
import hyunwook.co.kr.githubrepository.databinding.ActivityMainBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    GithubClient client;
    private Disposable disposable;
    private RepositoryAdapter adapter;

    private static final String OWNER = "c004245";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        adapter = new RepositoryAdapter(new ArrayList<>());

        binding.recyclerView.setAdapter(adapter);

        client = new GithubClient();
        disposable = client.getApi().getRepos(OWNER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> adapter.updateItems(items));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
