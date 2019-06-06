package hyunwook.co.kr.githubrepository;

import java.util.List;

import hyunwook.co.kr.githubrepository.model.GithubInfo;
import hyunwook.co.kr.githubrepository.model.GithubRepository;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {

    //사용자 Info
    @GET("users/{username}")
    Call<GithubInfo> getInfo(@Path("username") String username);

    @GET("users/{owner}/repos")
//    Single<List<GithubRepository>> getRepos(@Path("owner") String owner);
    Call<List<GithubRepository>> getRepos(@Path("owner") String owner);

}