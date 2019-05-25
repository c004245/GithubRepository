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
}
