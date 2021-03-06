package hyunwook.co.kr.githubrepository.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hyunwook.co.kr.githubrepository.databinding.ItemRepositoryBinding;
import hyunwook.co.kr.githubrepository.model.GithubInfo;
import hyunwook.co.kr.githubrepository.model.GithubRepository;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    private final List<GithubRepository> repos;

    public RepositoryAdapter(List<GithubRepository> repos) {
        this.repos = repos;
    }

    public void updateItems(List<GithubRepository> items) {
//    public void updateItem(GithubInfo items) {
        Log.d("TEST", "updateItems ->" + items.size());
        this.repos.clear();
        this.repos.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RepositoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepositoryAdapter.ViewHolder(ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RepositoryAdapter.ViewHolder holder, int position) {
        Log.d("TEST", "respose ->" + repos.get(position).stargazers_count);
        holder.bind(repos.get(position));
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemRepositoryBinding binding;

        ViewHolder(ItemRepositoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(GithubRepository repo) {
//            Glide.with(itemView).load("https://avatars3.githubusercontent.com/u/14107165?v=4").into(binding.ivImage);
            binding.tvRepoName.setText(repo.name);
            binding.tvRepoDesc.setText(repo.description);
            binding.tvStarCount.setText(repo.stargazers_count);
        }
    }
}
