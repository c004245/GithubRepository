package hyunwook.co.kr.githubrepository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

import hyunwook.co.kr.githubrepository.model.GithubInfo;
import hyunwook.co.kr.githubrepository.model.GithubRepository;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubClient {

    private static final String BASE_URL = "https://api.github.com";

    private static final class GithubRepoDeserializer implements JsonDeserializer<GithubRepository> {
        @Override
        public GithubRepository deserialize(JsonElement json, Type type, JsonDeserializationContext context) {

            GithubRepository githubRepository = new GithubRepository();

            JsonObject repoJsonObject = json.getAsJsonObject();
            JsonElement userJsonElement = repoJsonObject.get("username");
            JsonObject userJsonObject = userJsonElement.getAsJsonObject();

            githubRepository.name = userJsonObject.get("name").getAsString();
            githubRepository.description = userJsonObject.get("description").getAsString();
            githubRepository.stargazers_count = userJsonObject.get("stargazers_count").getAsString();

            return githubRepository;
        }
    }

    public GithubApi getApi() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GithubRepository.class, new GithubRepoDeserializer())
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(GithubApi.class);
    }
}
