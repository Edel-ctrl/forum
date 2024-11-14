package telran.forum.dao;

import telran.forum.model.Post;

import java.time.LocalDate;

public interface Forum {
    boolean addPost(Post post);

    boolean removePost(int postId);

    boolean updatePost(int postId, String content);

    Post getPostById(int postId);

    Post[] getPostByAuthor(String author);

    Post[] getPostsByAuthor(String autho, LocalDate dateFrom, LocalDate dateTo);

    int size();
}
