package com.sriram.themevest.service;


import com.sriram.themevest.model.Post;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;

@Service
public class PostServiceClient {

    private final RestClient restClient;

    // Injecting the configured bean
    public PostServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    // GET all records
    public List<Post> getAllPosts() {
        return restClient.get()
                .uri("/posts")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Post>>() {});
    }

    // GET single record by ID
    public Post getPostById(Integer id) {
        return restClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .body(Post.class);
    }

    // POST a new record
    public Post createPost(Post newPost) {
        return restClient.post()
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(newPost)
                .retrieve()
                .body(Post.class);
    }

    // PUT to update an existing record
    public Post updatePost(Integer id, Post updatedPost) {
        return restClient.put()
                .uri("/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedPost)
                .retrieve()
                .body(Post.class);
    }

    // DELETE a record
    public void deletePost(Integer id) {
        restClient.delete()
                .uri("/posts/{id}", id)
                .retrieve()
                .toBodilessEntity(); // Executes request discarding body response
    }
}

