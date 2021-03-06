package hyunwook.co.kr.githubrepository;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import hyunwook.co.kr.githubrepository.adapter.RepositoryAdapter;
import hyunwook.co.kr.githubrepository.databinding.ActivityMainBinding;
import hyunwook.co.kr.githubrepository.model.GithubInfo;
import hyunwook.co.kr.githubrepository.model.GithubRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    GithubClient client;
    private Disposable disposable;
    private RepositoryAdapter adapter;

    private static final String OWNER = "c004245";
    private static final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        adapter = new RepositoryAdapter(new ArrayList<>());

        binding.recyclerView.setAdapter(adapter);

        client = new GithubClient();
//        disposable = client.getApi().getRepos(OWNER)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
////                .subscribeOn(Scheduler.)
//                .subscribe(items -> adapter.updateItems(items));

        Retrofit ret = client.getRepoApi();
        GithubApi repoAPi = ret.create(GithubApi.class);
        Call<List<GithubRepository>> resultRepo = repoAPi.getRepos(OWNER);

        resultRepo.enqueue(new Callback<List<GithubRepository>>() {
            @Override
            public void onResponse(Call<List<GithubRepository>> call, Response<List<GithubRepository>> response) {

//                for (int i = 0;  i <response.body().size(); i++) {
                    Log.d(TAG, "onResponse name ---->" +response.body().get(0).name);
                    Log.d(TAG, "onResponse description---->" +response.body().get(0).description);
                    Log.d(TAG, "onResponse star---->" +response.body().get(0).stargazers_count);
//
//                }


            }

            @Override
            public void onFailure(Call<List<GithubRepository>> call, Throwable t) {

            }
        });



        Retrofit retrofit = client.getInfoApi();
        GithubApi avatar = retrofit.create(GithubApi.class);
        Call<GithubInfo> result = avatar.getInfo(OWNER);

        result.enqueue(new Callback<GithubInfo>() {
            @Override
            public void onResponse(Call<GithubInfo> call, Response<GithubInfo> response) {
                Log.d(TAG, "OnResponse ->" + response.body().avatar_url);
                Log.d(TAG, "OnResponse ->" + response.body().name);
                onSuccess(response.body().avatar_url, response.body().name);
            }

            @Override
            public void onFailure(Call<GithubInfo> call, Throwable t) {
                Log.d(TAG, "Retrofit onFailure");
            }
        });
    }

    public void onSuccess(String avatar, String name) {
        Glide.with(this).load(avatar).into(binding.ivProfile);
        binding.tvName.setText(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
